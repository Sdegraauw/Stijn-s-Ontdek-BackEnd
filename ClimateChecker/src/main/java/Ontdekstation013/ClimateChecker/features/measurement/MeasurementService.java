package Ontdekstation013.ClimateChecker.features.measurement;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MeasurementService {

    RestTemplate restTemplate = new RestTemplate();
    private Logger LOG = LoggerFactory.getLogger(MeasurementService.class);
    private String BASE_URL = "https://meetjestad.net/data/?type=sensors&format=json&limit=100";

    public List<Measurement> getFilterMeasurements(String startDate, String endDate) {

        try {

            if (startDate != null && endDate != null) {
                BASE_URL += "&start=" + startDate + "&end=" + endDate;
            }

            ResponseEntity<String> response = restTemplate.getForEntity(BASE_URL, String.class);
            String responseBody = response.getBody();

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            TypeReference<List<Measurement>> typeReference = new TypeReference<List<Measurement>>() {};
            List<Measurement> measurements = mapper.readValue(responseBody, typeReference);

            return measurements;
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new RuntimeException();
        }
    }
}
