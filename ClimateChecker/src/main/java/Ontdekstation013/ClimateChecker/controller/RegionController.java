package Ontdekstation013.ClimateChecker.controller;

import Ontdekstation013.ClimateChecker.models.Region;
import Ontdekstation013.ClimateChecker.models.dto.RegionInfoDto;
import Ontdekstation013.ClimateChecker.services.RegionCordsService;
import Ontdekstation013.ClimateChecker.services.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/Region")
public class RegionController {

    private final RegionService regionService;
    private final RegionCordsService regionCordsService;

    @Autowired
    public RegionController(RegionService regionService, RegionCordsService regionCordsService)
    {
        this.regionService = regionService;
        this.regionCordsService = regionCordsService;
    }

    @GetMapping
    public List<Region> getAll()
    {
        return regionService.getAll();
    }

    @GetMapping("/regioninfo")
    public List<RegionInfoDto> getAllRegionCords()
    {
        return regionCordsService.getAll();
    }

    @GetMapping("/regioninfo/{id}")
    public RegionInfoDto getAllRegionCordsByRegion(@PathVariable(value = "id") long id)
    {
        return regionCordsService.getAllByRegionId(id);
    }

}
