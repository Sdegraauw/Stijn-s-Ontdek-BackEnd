package Ontdekstation013.ClimateChecker.features.measurement.response;


import Ontdekstation013.ClimateChecker.features.measurement.endpoint.MeasurementDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MeasurementOverview {
    @JsonProperty("maxTemp")
    private float maxTemp;
    @JsonProperty("minTemp")
    private float minTemp;
    @JsonProperty("measurements")
    private List<MeasurementDTO> measurements;
}
