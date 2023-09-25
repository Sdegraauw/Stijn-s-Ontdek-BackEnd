package Ontdekstation013.ClimateChecker.features.region.endpoint;

import java.util.ArrayList;
import java.util.List;

import Ontdekstation013.ClimateChecker.features.region.Region;
import Ontdekstation013.ClimateChecker.features.region.RegionCords;

public class RegionInfoDto {


    public Region region;
    public List<RegionAverageDto> averageData;
    public List<double[]> cordsList;

    public RegionInfoDto(Region region, List<RegionCords> regionCordsList)
    {
        this.region = region;

        cordsList = new ArrayList<double[]>();
        for(RegionCords cords : regionCordsList)
        {
            if(cords.getRegion().getId() == region.getId())
            {
                double[] cords1 = new double[] {cords.getX_cord(), cords.getY_cord()};
                cordsList.add(cords1);
            }
        }
    }

}
