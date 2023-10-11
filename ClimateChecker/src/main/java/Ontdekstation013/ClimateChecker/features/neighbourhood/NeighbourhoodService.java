package Ontdekstation013.ClimateChecker.features.neighbourhood;

import Ontdekstation013.ClimateChecker.features.measurement.MeasurementService;
import Ontdekstation013.ClimateChecker.features.station.StationService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NeighbourhoodService {
    private final MeasurementService measurementService;

    public List<Neighbourhood> GetNeighbourhoodData()
    {
        List<Neighbourhood> neighbourhoods = new ArrayList<>();

        var data = measurementService.getFilterMeasurements()
        // todo: temporary
        int limit = 5000;
        String begin = "2023-10-04,00:00";
        String end = "2023-10-04,23:59";

        StringBuilder stringBuilder = new StringBuilder("https://meetjestad.net/data/?type=sensors&format=sensors");
        stringBuilder.append("&limit=").append(limit);
        stringBuilder.append("&begin=").append(begin);
        stringBuilder.append("&end=").append(end);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(stringBuilder.toString(), String.class);
        String responseBody = responseEntity.getBody();

        System.out.println(responseBody);

        return neighbourhoods;
    }
}
