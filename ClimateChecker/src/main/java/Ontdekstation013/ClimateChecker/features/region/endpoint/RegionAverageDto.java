package Ontdekstation013.ClimateChecker.features.region.endpoint;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegionAverageDto {

    public int id;
    public String name;
    public double data;

    public RegionAverageDto(int Id, String name, double data)
    {
        this.id = Id;
        this.name = name;
        this.data = data;
    }


}
