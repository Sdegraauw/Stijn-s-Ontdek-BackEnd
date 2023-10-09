package Ontdekstation013.ClimateChecker.features.meetjestad;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MeetJeStadParameters {
    public Instant StartDate;
    public Instant EndDate;
    public List<Integer> StationIds = new ArrayList<>();
    public int Limit = 0;
}
