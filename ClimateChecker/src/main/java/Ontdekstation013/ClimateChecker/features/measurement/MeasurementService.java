package Ontdekstation013.ClimateChecker.features.measurement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

@Service
public class MeasurementService {

    RestTemplate restTemplate = new RestTemplate();
    private Logger LOG = LoggerFactory.getLogger(MeasurementService.class);
    private String BASE_URL = "https://meetjestad.net/data/?type=sensors&format=json&limit=1000";

    public List<Measurement> getLatestMeasurements(int minuteLimit) throws ParseException, JsonProcessingException {
        Instant endMoment = Instant.now();
        Instant startMoment = endMoment.minus(Duration.ofMinutes(minuteLimit));

        String startDateTime = meetJeStadDateTimeConverter(startMoment);
        String endDateTime = meetJeStadDateTimeConverter(endMoment);

        // get measurements from MeetJeStadAPI
        List<Measurement> latestMeasurements = getMeetJeStadMeasurements(startDateTime, endDateTime);
        // filter out older readings of same stationId
        Map<Integer, Measurement> uniqueLatestMeasurements = new LinkedHashMap<>();
        for (Measurement measurement : latestMeasurements) {
            int id = measurement.getId();
            // Check if this id is already in the map
            if (!uniqueLatestMeasurements.containsKey(id)) {
                uniqueLatestMeasurements.put(id, measurement);
            }
        }
        List<Measurement> temp = new ArrayList<>(uniqueLatestMeasurements.values());
        return temp;
    }

    private String meetJeStadDateTimeConverter(Instant instant) throws ParseException {
        String shortDateTime = instant
                .toString()
                .split("\\.")[0];

        SimpleDateFormat instantFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat meetJeStadFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        Date date = instantFormat.parse(shortDateTime);
        return meetJeStadFormat.format(date);
    }

    private List<Measurement> getMeetJeStadMeasurements(String startDateTime, String endDateTime) throws JsonProcessingException {
        BASE_URL += "&start=" + startDateTime + "&end=" + endDateTime;
        ResponseEntity<String> response = restTemplate.getForEntity(BASE_URL, String.class);
        String responseBody = response.getBody();

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        TypeReference<List<Measurement>> typeReference = new TypeReference<List<Measurement>>() {
        };
        return mapper.readValue(responseBody, typeReference);
    }
}
