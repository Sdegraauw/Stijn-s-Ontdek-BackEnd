package Ontdekstation013.ClimateChecker.services;

import Ontdekstation013.ClimateChecker.models.RegionCords;
import Ontdekstation013.ClimateChecker.repositories.RegionCordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RegionCordsService {

    private final RegionCordsRepository regionCordsRepository;

    public List<RegionCords> getAll()
    {
        return regionCordsRepository.findAll();
    }

    public List<RegionCords> getAllByRegionId(long regionId)
    {
        List<RegionCords> all = regionCordsRepository.findAll();
        List<RegionCords> retval = new ArrayList<RegionCords>();

        for(RegionCords cords : all)
        {
            if(cords.getRegion().getId() == regionId) retval.add(cords);
        }

        return retval;
    }

    @Autowired
    public RegionCordsService(RegionCordsRepository regionCordsRepository)
    {
        this.regionCordsRepository = regionCordsRepository;
    }
}
