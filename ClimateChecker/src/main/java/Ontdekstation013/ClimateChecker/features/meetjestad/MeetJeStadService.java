package Ontdekstation013.ClimateChecker.features.meetjestad;

import Ontdekstation013.ClimateChecker.features.measurement.Measurement;
import Ontdekstation013.ClimateChecker.features.measurement.endpoint.MeasurementDTO;
import Ontdekstation013.ClimateChecker.utility.GpsTriangulation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.persistence.criteria.CriteriaBuilder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.*;

@Service
public class MeetJeStadService {
    private final String baseUrl = "https://meetjestad.net/data/?type=sensors&format=json";
    @Getter
    private final int minuteLimit = 35;
    private final float[][] cityLimits = {
            {51.65156373065635f, 5.217787919413907f},
            {51.51818572766462f, 5.227145728754213f},
            {51.52666590649518f, 4.911805911309284f},
            {51.65077670571181f, 4.957086656750303f}
    };

    public List<Measurement> getMeasurements(MeetJeStadParameters params) {
        StringBuilder url = new StringBuilder(baseUrl);

        if (params.StartDate != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd,HH:mm").withZone(ZoneOffset.UTC);
            String dateFormat = formatter.format(Instant.parse(params.StartDate.toString()));
            url.append("&begin=").append(dateFormat);
        }

        if (params.EndDate != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd,HH:mm").withZone(ZoneOffset.UTC);
            String dateFormat = formatter.format(Instant.parse(params.EndDate.toString()));
            url.append("&end=").append(dateFormat);
        }

        // Do we limit the amount of measurements
        if (params.Limit != 0)
            url.append("&limit=").append(params.Limit);

        // Do we want a specific station or all stations
        if (!params.StationIds.isEmpty()) {
            url.append("&ids=");

            for (int stationId : params.StationIds) {
                url.append(stationId).append(",");
            }
        }

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url.toString(), String.class);
        String responseBody = response.getBody();

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        TypeReference<List<MeasurementDTO>> typeReference = new TypeReference<List<MeasurementDTO>>() {
        };

        List<MeasurementDTO> measurementsDto = new ArrayList<>();
        try {
            measurementsDto = mapper.readValue(responseBody, typeReference);
        } catch (JsonProcessingException ignored) {
        }

        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("yyyy-MM-dd HH:mm:ss")   // input pattern
                .withZone(ZoneOffset.UTC);          // input timezone

        List<Measurement> measurements = new ArrayList<>();
        for (MeasurementDTO dto : measurementsDto) {
            // Check if measurement is within city bounds
            float[] point = {dto.getLatitude(), dto.getLongitude()};
            if (!GpsTriangulation.pointInPolygon(cityLimits, point))
                continue;

            Measurement measurement = new Measurement();
            measurement.setId(dto.getId());
            measurement.setLongitude(dto.getLongitude());
            measurement.setLatitude(dto.getLatitude());
            measurement.setTemperature(dto.getTemperature());
            measurement.setHumidity(dto.getHumidity());

            TemporalAccessor temp = formatter.parse(dto.getTimestamp());
            measurement.setTimestamp(Instant.from(temp));

            measurements.add(measurement);
        }
        return measurements;
    }

    public List<Measurement> getLatestMeasurements() {
        // define start and end times
        Instant endMoment = Instant.now();
        Instant startMoment = endMoment.minus(Duration.ofMinutes(minuteLimit));

        MeetJeStadParameters params = new MeetJeStadParameters();
        params.StartDate = startMoment;
        params.EndDate = endMoment;

        // get measurements from MeetJeStadAPI
        List<Measurement> latestMeasurements = getMeasurements(params);
        // filter out older readings of same stationId
        Map<Integer, Measurement> uniqueLatestMeasurements = new HashMap<>();
        for (Measurement measurement : latestMeasurements) {
            int id = measurement.getId();
            // Check if this id is already in the map and if its more recent
            if (!uniqueLatestMeasurements.containsKey(id) ||
                    measurement.getTimestamp().isAfter(uniqueLatestMeasurements.get(id).getTimestamp()))
                uniqueLatestMeasurements.put(id, measurement);
        }

        latestMeasurements = new ArrayList<>(uniqueLatestMeasurements.values());
        return latestMeasurements;
    }

    public Measurement getLatestMeasurement(int id) {
        // define start and end times
        Instant endMoment = Instant.now();
        Instant startMoment = endMoment.minus(Duration.ofMinutes(minuteLimit));

        MeetJeStadParameters params = new MeetJeStadParameters();
        params.StartDate = startMoment;
        params.EndDate = endMoment;
        params.StationIds.add(id);

        // get measurements from MeetJeStadAPI
        List<Measurement> latestMeasurements = getMeasurements(params);
        Measurement latestMeasurement = null;

        // filter out older readings of same stationId
        for (Measurement measurement : latestMeasurements) {
            if (latestMeasurement == null ||
                    measurement.getTimestamp().isAfter(latestMeasurement.getTimestamp()))
                latestMeasurement = measurement;
        }

        return latestMeasurement;
    }
}
