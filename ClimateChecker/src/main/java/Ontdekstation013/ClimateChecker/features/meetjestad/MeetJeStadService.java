package Ontdekstation013.ClimateChecker.features.meetjestad;

import Ontdekstation013.ClimateChecker.features.measurement.Measurement;
import Ontdekstation013.ClimateChecker.features.measurement.MeasurementDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneOffset;
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

        TypeReference<List<MeasurementDTO>> typeReference = new TypeReference<List<MeasurementDTO>>() {};

        List<MeasurementDTO> measurementsDto = new ArrayList<>();

        try {
            measurementsDto = mapper.readValue(responseBody, typeReference);
        } catch (JsonProcessingException ignored)
        {}

        // Convert dto's to measurements for use in service
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        List<Measurement> measurements = new ArrayList<>();
        for (MeasurementDTO dto : measurementsDto) {
            Measurement measurement = new Measurement();
            measurement.setId(dto.getId());
            measurement.setLongitude(dto.getLongitude());
            measurement.setLatitude(dto.getLatitude());
            measurement.setTemperature(dto.getTemperature());
            measurement.setHumidity(dto.getHumidity());

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
