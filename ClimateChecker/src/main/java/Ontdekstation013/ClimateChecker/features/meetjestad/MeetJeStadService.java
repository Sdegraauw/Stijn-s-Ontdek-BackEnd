package Ontdekstation013.ClimateChecker.features.meetjestad;

import Ontdekstation013.ClimateChecker.features.measurement.Measurement;
import Ontdekstation013.ClimateChecker.features.measurement.MeasurementDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class MeetJeStadService {
    private String baseUrl = "https://meetjestad.net/data/?type=sensors&format=json";
    public List<Measurement> getMeasurements(MeetJeStadParameters params)
    {
        StringBuilder url = new StringBuilder(baseUrl);

        if (params.StartDate != null) {
            //SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd,HH:mm");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd,HH:mm").withZone(ZoneId.)
            Instant instant = Instant.parse("yyyy-MM-dd,HH:mm");
            url.append("&begin=").append();
        }

        if (params.EndDate != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd,HH:mm");
            url.append("&end=").append(formatter.format(params.EndDate));
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

        TypeReference<List<MeasurementDto>> typeReference = new TypeReference<List<MeasurementDto>>() {};

        List<MeasurementDto> measurementsDto = new ArrayList<>();

        try {
            measurementsDto = mapper.readValue(responseBody, typeReference);
        } catch (JsonProcessingException ignored)
        {}

        // Convert dto's to measurements for use in service
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        List<Measurement> measurements = new ArrayList<>();
        for (MeasurementDto dto : measurementsDto) {
            Measurement measurement = new Measurement();
            measurement.setId(dto.getId());
            measurement.setLongitude(dto.getLongitude());
            measurement.setLatitude(dto.getLatitude());
            measurement.setTemperature(dto.getTemperature());

            // Convert date from string to date object
            try {
                measurement.setTimestamp(formatter.parse(dto.getTimestamp()));
            } catch (ParseException ignored)
            {}

            measurements.add(measurement);
        }

        return measurements;
    }
}
