package Ontdekstation013.ClimateChecker.features.meetjestad;

import Ontdekstation013.ClimateChecker.features.measurement.Measurement;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class MeetJeStadService {
    private String baseUrl = "https://meetjestad.net/data/?type=sensors&format=json";
    public List<Measurement> getMeasurements(MeetJeStadParameters params)
    {
        StringBuilder url = new StringBuilder(baseUrl);

        if (params.StartDate != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd,HH:mm");
            url.append("&begin=").append(formatter.format(params.StartDate));
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

        TypeReference<List<Measurement>> typeReference = new TypeReference<List<Measurement>>() {};

        List<Measurement> measurements = new ArrayList<>();

        try {
            measurements = mapper.readValue(responseBody, typeReference);
        } catch (JsonProcessingException ignored)
        {}

        return measurements;
    }
}
