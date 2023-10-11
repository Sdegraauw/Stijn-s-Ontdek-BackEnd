package Ontdekstation013.ClimateChecker.features.station;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import jdk.jfr.Timespan;
import net.bytebuddy.asm.Advice;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class StationService {
    public List<Measurement> GetLastStationsData() throws JsonProcessingException {
        Instant currentTimeUTC = Instant.now();
        Duration timeSpan = Duration.ofMinutes(35);
        Instant startTime = currentTimeUTC.minus(timeSpan);

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String currentTimeString = null;
        String startTimeString = null;
        try {
            String[] currentTemp = currentTimeUTC.toString().split("\\.");
            String currentTempFinish = currentTemp[0];
            Date date = inputFormat.parse(currentTempFinish);
            currentTimeString = outputFormat.format(date);

            String[] startTemp = startTime.toString().split("\\.");
            String startTempFinish = startTemp[0];
            Date date2 = inputFormat.parse(startTempFinish);
            startTimeString = outputFormat.format(date2);
        } catch (ParseException e) {

        }

        String uri = "https://meetjestad.net/data/?type=sensors&begin=" +
                startTimeString + "&end=" + currentTimeString + "&format=json&limit=100";

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);

        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module =
                new SimpleModule("CustomMeasurementDeserializer", new Version(1, 0, 0, null, null, null));
        module.addDeserializer(Measurement.class, new CustomMeasurementDeserializer());
        mapper.registerModule(module);
        List<Measurement> measurementList = mapper.readValue(result, new TypeReference<List<Measurement>>(){});
        ObjectMapper objectMapper = new ObjectMapper();
        return measurementList;

    }
}
