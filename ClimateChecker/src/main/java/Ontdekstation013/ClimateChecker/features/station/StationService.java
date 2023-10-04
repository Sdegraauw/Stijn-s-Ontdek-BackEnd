package Ontdekstation013.ClimateChecker.features.station;

import jdk.jfr.Timespan;
import net.bytebuddy.asm.Advice;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Date;
import java.util.List;

@Service
public class StationService {
    public List<Station> GetLastStationsData() {
        Instant currentTimeUTC = Instant.now();
        Duration timeSpan = Duration.ofMinutes(35);
        Instant startTime = currentTimeUTC.minus(timeSpan);

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd,HH:mm");
        String currentTimeString = null;
        String startTimeString = null;
        try {
            Date date = inputFormat.parse(currentTimeUTC.toString());
            currentTimeString = outputFormat.format(date);
            date = inputFormat.parse(startTime.toString());
            startTimeString = outputFormat.format(date);
        }catch(ParseException e){}

        String uri = "https://meetjestad.net/data/?type=sensors&begin="+
                startTimeString+ "&end="+currentTimeString+"&format=json&limit=100";

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
    }
}
