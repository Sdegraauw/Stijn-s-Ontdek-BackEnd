package Ontdekstation013.ClimateChecker;

import Ontdekstation013.ClimateChecker.Mocks.MockStationRepo;
import Ontdekstation013.ClimateChecker.models.*;
import Ontdekstation013.ClimateChecker.models.dto.*;
import Ontdekstation013.ClimateChecker.services.StationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class StationServiceTests {
	private StationService stationService;
	private MockStationRepo mockRepo;



	@BeforeEach
	void setup() throws Exception{
		this.mockRepo = new MockStationRepo();
		this.stationService = new StationService(mockRepo);
	}

	// No functionality in StationService
	@Test
	void findStationByIdTest() {
		registerStationDto dto = new registerStationDto();

		dto.setUserId(1);
		dto.setName("TestName1");
		dto.setHeight(54);
		dto.setLatitude(5687);
		dto.setAddress("Yes");
		stationService.createStation(dto);

		dto.setUserId(2);
		dto.setHeight(987);
		dto.setLatitude(98765);
		dto.setAddress("No");
		dto.setName("TestName2");
		stationService.createStation(dto);

		stationDto newDto = stationService.findStationById(2);

		Assertions.assertEquals(dto.getHeight(),newDto.getHeight());
		Assertions.assertEquals(dto.getName(),newDto.getName());
		Assertions.assertEquals(dto.getLatitude(),newDto.getLatitude());
		Assertions.assertEquals(dto.getLongitude(),newDto.getLongitude());
	}


	// No functionality in StationService
	@Test
	void stationToStationDTOTest() {
		Station station = new Station();
		station.setStationID(1);
		station.setHeight(2);
		station.setLocation(new Location());
		station.setOwner(new User());
		station.setSensors(new ArrayList<>());

		stationDto newDto = stationService.stationToStationDTO(station);

		Assertions.assertEquals(station.getStationID(),newDto.getId());
		Assertions.assertEquals(station.getHeight(),newDto.getHeight());
		Assertions.assertEquals(station.getLocation().getLocationName(),newDto.getLocationName());
		Assertions.assertEquals(station.getLocation().getLocationID(),newDto.getLocationId());
		Assertions.assertEquals(station.getLocation().getLongitude(),newDto.getLongitude());
		Assertions.assertEquals(station.getLocation().getLatitude(),newDto.getLatitude());
	}


	// No functionality in StationService

	// Veranderingen in Rest-call document, stationdto heeft geen user,
	// gebruik RegisterStationDTO

	@Test
	void getAllByUserIDTest() {
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

		List<stationTitleDto> newDtoList = stationService.getAllByUserId(2);


		Assertions.assertEquals(2,newDtoList.get(0).getId());
		Assertions.assertEquals(dto.getName(),newDtoList.get(0).getName());
	}


	// No functionality in StationService

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

		List<stationTitleDto> newDtoList = stationService.getAll();


		Assertions.assertEquals(2,newDtoList.get(1).getId());
		Assertions.assertEquals(dto.getName(),newDtoList.get(1).getName());
	}


	// No functionality in StationService
	// not sure about this one, how does pageId work? where do I get it?
	@Test
	void getAllByPageIdTest() {
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

		List<stationTitleDto> newDtoList = stationService.getAllByPageId(1);


		Assertions.assertEquals(2,newDtoList.get(0).getId());
		Assertions.assertEquals(dto.getName(),newDtoList.get(0).getName());
	}

	// No functionality in StationService


	@Test
	void createStationTest() {
		registerStationDto dto = new registerStationDto();

		dto.setUserId(1);
		dto.setName("TestName1");
		dto.setHeight(54);
		dto.setLatitude(5687);
		dto.setLongitude(89767);
		dto.setAddress("Yes");
		stationService.createStation(dto);

		stationDto newDto = stationService.findStationById(1);


		Assertions.assertEquals(1,newDto.getId());
		Assertions.assertEquals(dto.getName(),newDto.getName());
		Assertions.assertEquals(dto.getLatitude(),newDto.getLatitude());
		Assertions.assertEquals(dto.getLongitude(),newDto.getLongitude());
		Assertions.assertEquals(dto.getHeight(),newDto.getHeight());
	}

	// No functionality in StationService

	@Test
	void deleteStationTest() {
		registerStationDto dto = new registerStationDto();

		dto.setUserId(1);
		dto.setName("TestName1");
		dto.setHeight(54);
		dto.setLatitude(5687);
		dto.setLongitude(89767);
		dto.setAddress("Yes");
		stationService.createStation(dto);

		stationService.deleteStation(1);


		try {
			stationDto newDto = stationService.findStationById(1);
			Assertions.fail();
		} catch (Exception e) {
			Assertions.assertTrue(true);
		}
	}

	// No functionality in StationService


	@Test
	void editStationTest() {
		registerStationDto dto = new registerStationDto();

		dto.setUserId(1);
		dto.setName("TestName1");
		dto.setHeight(54);
		dto.setLatitude(5687);
		dto.setLongitude(89767);
		dto.setAddress("Yes");
		stationService.createStation(dto);

		editStationDto dto2 = new editStationDto();

		dto2.setId(1);
		dto2.setName("TestNameNEW");
		dto2.setHeight(876);
		dto2.setLatitude(9843);
		dto2.setLongitude(8745);
		dto2.setAddress("NEW");

		stationService.editStation(dto2);

		stationDto newDto = stationService.findStationById(1);


		Assertions.assertEquals(dto2.getId(),newDto.getId());
		Assertions.assertEquals(dto2.getName(),newDto.getName());
		Assertions.assertEquals(dto2.getLatitude(),newDto.getLatitude());
		Assertions.assertEquals(dto2.getLongitude(),newDto.getLongitude());
		Assertions.assertEquals(dto2.getHeight(),newDto.getHeight());
	}

}
