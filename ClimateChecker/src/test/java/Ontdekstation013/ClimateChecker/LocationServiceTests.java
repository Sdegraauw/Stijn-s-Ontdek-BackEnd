package Ontdekstation013.ClimateChecker;

import Ontdekstation013.ClimateChecker.Mocks.MockLocationRepo;
import Ontdekstation013.ClimateChecker.Mocks.MockStationRepo;
import Ontdekstation013.ClimateChecker.Mocks.MockUserRepo;
import Ontdekstation013.ClimateChecker.models.Location;
import Ontdekstation013.ClimateChecker.models.User;
import Ontdekstation013.ClimateChecker.models.dto.*;
import Ontdekstation013.ClimateChecker.services.LocationService;
import Ontdekstation013.ClimateChecker.services.StationService;
import Ontdekstation013.ClimateChecker.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class LocationServiceTests {
	private LocationService locationService;
	private MockLocationRepo mockRepo;

	private StationService stationService;
	private MockStationRepo mockStationRepo;



	@BeforeEach
	void setup() throws Exception{
		this.mockRepo = new MockLocationRepo();
		this.locationService = new LocationService(mockRepo);

		this.mockStationRepo = new MockStationRepo();
		this.stationService = new StationService(mockStationRepo);
	}

	// No functionality in LocationService
	@Test
	void findLocationByIdTest() {
		registerStationDto dto = new registerStationDto();

		dto.setUserId(1);
		dto.setName("TestName1");
		dto.setHeight(54);
		dto.setLatitude(5687);
		dto.setLongitude(89767);
		dto.setAddress("Yes");
		stationService.createStation(dto);


		Location location = locationService.findLocationById(1);


		Assertions.assertEquals(location.getLatitude(), dto.getLatitude());
		Assertions.assertEquals(location.getLongitude(), dto.getLongitude());
		Assertions.assertEquals(location.getStation().getStationID(), 1); // assert station id = 1

	}


	// No functionality in LocationService
	@Test
	void getAllTest() {
		registerStationDto dto = new registerStationDto();

		dto.setUserId(1);
		dto.setName("TestName1");
		dto.setHeight(54);
		dto.setLatitude(5687);
		dto.setLongitude(89767);
		dto.setAddress("Yes");
		stationService.createStation(dto);

		dto.setUserId(2);
		dto.setHeight(987);
		dto.setLatitude(98765);
		dto.setLongitude(89767);
		dto.setAddress("No");
		dto.setName("TestName2");
		stationService.createStation(dto);


		List<Location> locations = locationService.getAll();


		Assertions.assertEquals(locations.get(1).getLatitude(), dto.getLatitude());
		Assertions.assertEquals(locations.get(1).getLongitude(), dto.getLongitude());
		Assertions.assertEquals(locations.get(1).getStation().getStationID(), 2); // assert station id = 2
	}
}
