package Ontdekstation013.ClimateChecker.features.meetjestad;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MeetJeStadParameters {
    public Date StartDate;
    public Date EndDate;
    public List<Integer> StationIds = new ArrayList<>();
    public int Limit = 0;
}
