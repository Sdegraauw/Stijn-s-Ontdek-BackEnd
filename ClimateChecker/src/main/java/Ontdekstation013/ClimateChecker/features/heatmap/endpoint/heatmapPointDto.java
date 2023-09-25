package Ontdekstation013.ClimateChecker.features.heatmap.endpoint;

import Ontdekstation013.ClimateChecker.features.Dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class heatmapPointDto extends Dto{
    float longitude;
    float latitude;
    float value;

    public heatmapPointDto(float longitude, float latitude, float value)
    {
        this.longitude = longitude;
        this.latitude = latitude;
        this.value = value;
    }
}
