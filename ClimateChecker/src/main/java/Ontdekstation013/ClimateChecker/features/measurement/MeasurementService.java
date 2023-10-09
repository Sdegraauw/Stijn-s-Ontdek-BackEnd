package Ontdekstation013.ClimateChecker.features.measurement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

import com.fasterxml.jackson.core.JsonProcessingException;
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

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

@Service
@RequiredArgsConstructor
public class MeasurementService {
    private final MeetJeStadService meetJeStadService;

    public List<Measurement> getLatestMeasurements(int minuteLimit) {
         //Voorbeeld voor meetjestad service
        Calendar calendarStart = Calendar.getInstance();
        calendarStart.set(Calendar.YEAR, 2023);
        calendarStart.set(Calendar.MONTH, Calendar.OCTOBER);
        calendarStart.set(Calendar.DATE, 4);

        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.set(Calendar.YEAR, 2023);
        calendarEnd.set(Calendar.MONTH, Calendar.OCTOBER);
        calendarEnd.set(Calendar.DATE, 5);

        MeetJeStadParameters params = new MeetJeStadParameters();
        params.StartDate = calendarStart.getTime();
        params.EndDate = calendarEnd.getTime();

        // get measurements from MeetJeStadAPI
        List<Measurement> latestMeasurements = meetJeStadService.getMeasurements(params);
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
//    private String meetJeStadDateTimeConverter(Instant instant) throws ParseException {
//        String shortDateTime = instant
//                .toString()
//                .split("\\.")[0];
//
//        SimpleDateFormat instantFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
//        SimpleDateFormat meetJeStadFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//
//        Date date = instantFormat.parse(shortDateTime);
//        return meetJeStadFormat.format(date);
//    }
}
