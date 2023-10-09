package Ontdekstation013.ClimateChecker.features.measurement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import Ontdekstation013.ClimateChecker.features.meetjestad.MeetJeStadParameters;
import Ontdekstation013.ClimateChecker.features.meetjestad.MeetJeStadService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
public class MeasurementService {
    private final MeetJeStadService meetJeStadService;
    RestTemplate restTemplate = new RestTemplate();
    private Logger LOG = LoggerFactory.getLogger(MeasurementService.class);
    private String BASE_URL = "https://meetjestad.net/data/?type=sensors&format=json&limit=100";

    public List<Measurement> getFilterMeasurements(String startDate, String endDate) {
        // Voorbeeld voor meetjestad service
        //        Calendar calendarStart = Calendar.getInstance();
        //        calendarStart.set(Calendar.YEAR, 2023);
        //        calendarStart.set(Calendar.MONTH, Calendar.OCTOBER);
        //        calendarStart.set(Calendar.DATE, 4);
        //
        //        Calendar calendarEnd = Calendar.getInstance();
        //        calendarEnd.set(Calendar.YEAR, 2023);
        //        calendarEnd.set(Calendar.MONTH, Calendar.OCTOBER);
        //        calendarEnd.set(Calendar.DATE, 5);
        //
        //        MeetJeStadParameters params = new MeetJeStadParameters();
        //        params.StartDate = calendarStart.getTime();
        //        params.EndDate = calendarEnd.getTime();
        //
        //        meetJeStadService.getMeasurements(params); --> Geeft List<Measurements> terug!

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
