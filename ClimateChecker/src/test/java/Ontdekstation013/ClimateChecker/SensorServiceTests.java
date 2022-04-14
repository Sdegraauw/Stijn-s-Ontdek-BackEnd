package Ontdekstation013.ClimateChecker;

import Ontdekstation013.ClimateChecker.Mocks.MockSensorRepo;
import Ontdekstation013.ClimateChecker.models.Sensor;
import Ontdekstation013.ClimateChecker.models.SensorType;
import Ontdekstation013.ClimateChecker.models.Station;
import Ontdekstation013.ClimateChecker.models.dto.sensorDto;
import Ontdekstation013.ClimateChecker.models.dto.sensorTypeDto;
import Ontdekstation013.ClimateChecker.services.SensorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class SensorServiceTests {
	private SensorService sensorService;
	private MockSensorRepo mockRepo;

	private sensorDto sensorDto;



	@BeforeEach
	void setup() throws Exception{
		this.mockRepo = new MockSensorRepo();
		this.sensorService = new SensorService(mockRepo);
	}


	@Test
	void sensorToSensorDtoTest() {
		Sensor sensor = new Sensor();
		sensor.setSensorID(0);
		sensor.setSensorData(1);
		sensor.setSensorType(new SensorType());
		sensor.getSensorType().setTypeID(2);
		sensor.setStation(new Station());
		sensor.getStation().setStationID(3);

		sensorDto newDto = new sensorDto();
		newDto = sensorService.sensorToSensorDTO(sensor);

		Assertions.assertEquals(0,newDto.getId());
		Assertions.assertEquals(1,newDto.getData());
		Assertions.assertEquals(2,newDto.getTypeId());
		Assertions.assertEquals(3,newDto.getStationId());
	}


	// No functionality in SensorService, tests the amount of sensors
	@Test
	void getAllTest() {
		sensorDto sensorDto = new sensorDto();
		sensorDto.setId(1);
		sensorDto.setData(2);
		sensorDto.setTypeId(3);
		sensorDto.setStationId(4);

		sensorService.createSensor(sensorDto);

		long x = 100;
		List<sensorDto> sensorDtoList = sensorService.getAll();

		Assertions.assertEquals(1, sensorDtoList.stream().count());

		Assertions.assertEquals(sensorDto.getId(),sensorDtoList.get(0).getId() );
		Assertions.assertEquals(sensorDto.getData(),sensorDtoList.get(0).getData() );
		Assertions.assertEquals(sensorDto.getTypeId(),sensorDtoList.get(0).getTypeId() );
		Assertions.assertEquals(sensorDto.getStationId(),sensorDtoList.get(0).getStationId() );

	}

	//No functionality in SensorService, tests sensorTypes
	@Test
	void getAllSensorTypesTest() {

		sensorTypeDto dto1 = new sensorTypeDto();
		dto1.setId(1);
		dto1.setName("Test1");
		sensorService.createSensorType(dto1);

		sensorTypeDto dto2 = new sensorTypeDto();
		dto2.setId(2);
		dto2.setName("Test2");
		sensorService.createSensorType(dto2);

		List<sensorTypeDto> TypeList = sensorService.getAllSensorTypes();


		Assertions.assertEquals(2, TypeList.size());

		Assertions.assertEquals(dto1.getName(), TypeList.get(0).getName());
		Assertions.assertEquals(dto1.getId(), TypeList.get(0).getId());
		Assertions.assertEquals(dto2.getId(), TypeList.get(1).getId());
		Assertions.assertEquals(dto2.getName(), TypeList.get(1).getName());
	}




	// Not yet functional, can't create sensors yet
	@Test
	void createSensorTest() {
		sensorDto dto = new sensorDto();
		dto.setId(1);
		dto.setStationId(6);
		dto.setTypeId(3);
		dto.setData(2);
		sensorService.createSensor(dto);



		sensorDto newDto = sensorService.findSensorById(1);

		Assertions.assertEquals(dto.getId(), newDto.getId());
		Assertions.assertEquals(dto.getStationId(), newDto.getStationId());
		Assertions.assertEquals(dto.getTypeId(), newDto.getTypeId());
		Assertions.assertEquals(dto.getData(), newDto.getData());
	}

	@Test
	void createSensorTypeTest(){
		sensorTypeDto typeDto = new sensorTypeDto();
		typeDto.setName("Test1");
		typeDto.setId(1);
		sensorService.createSensorType(typeDto);


		sensorTypeDto newDto = sensorService.getAllSensorTypes().get(0);

		Assertions.assertEquals(typeDto.getName(), newDto.getName());
		Assertions.assertEquals(typeDto.getId(),newDto.getId());
	}


	@Test
	void deleteSensorTest() {
		sensorDto dto = new sensorDto();
		dto.setId(1);
		dto.setStationId(6);
		dto.setTypeId(3);
		dto.setData(2);
		sensorService.createSensor(dto);
		sensorService.deleteSensor(dto.getId());


		try {
			sensorDto newDto = sensorService.findSensorById(dto.getId());
			Assertions.assertTrue(false);
		} catch (Exception e) {
			Assertions.assertTrue(true);
		}
	}


	@Test
	void findSensorByIdTest(){
		sensorDto dto = new sensorDto();
		dto.setStationId(1);
		dto.setData(2);
		dto.setId(3);
		dto.setTypeId(4);
		sensorService.createSensor(dto);

		dto.setStationId(4);
		dto.setData(3);
		dto.setId(2);
		dto.setTypeId(1);
		sensorService.createSensor(dto);


		sensorDto newDto = sensorService.findSensorById(dto.getId());

		Assertions.assertEquals(newDto.getStationId(), dto.getStationId());
		Assertions.assertEquals(newDto.getId(),dto.getId());
		Assertions.assertEquals(newDto.getData(),dto.getData());
		Assertions.assertEquals(newDto.getTypeId(),dto.getTypeId());

	}


	@Test
	void getSensorsByTypeTest(){
		sensorDto dto1 = new sensorDto();
		dto1.setStationId(1);
		dto1.setData(2);
		dto1.setId(3);
		dto1.setTypeId(999);
		sensorService.createSensor(dto1);

		sensorDto dto2 = new sensorDto();
		dto2.setStationId(5);
		dto2.setData(6);
		dto2.setId(7);
		dto2.setTypeId(8);
		sensorService.createSensor(dto2);

		sensorDto dto3 = new sensorDto();
		dto2.setStationId(1);
		dto2.setData(10);
		dto2.setId(11);
		dto2.setTypeId(999);
		sensorService.createSensor(dto3);


		List<sensorDto> newDtoList = sensorService.getSensorsByType(999);

		Assertions.assertEquals(newDtoList.get(0).getId() ,dto1.getId() );
		Assertions.assertEquals(newDtoList.get(0).getTypeId() ,dto1.getTypeId() );
		Assertions.assertEquals(newDtoList.get(0).getData() ,dto1.getData() );
		Assertions.assertEquals(newDtoList.get(0).getStationId() ,dto1.getStationId() );

		Assertions.assertEquals(newDtoList.get(1).getId() ,dto3.getId() );
		Assertions.assertEquals(newDtoList.get(1).getTypeId() ,dto3.getTypeId() );
		Assertions.assertEquals(newDtoList.get(1).getData() ,dto3.getData() );
		Assertions.assertEquals(newDtoList.get(1).getStationId() ,dto3.getStationId() );
	}


	@Test
	void getSensorByStation(){
		sensorDto dto1 = new sensorDto();
		dto1.setStationId(1);
		dto1.setData(2);
		dto1.setId(3);
		dto1.setTypeId(999);
		sensorService.createSensor(dto1);

		sensorDto dto2 = new sensorDto();
		dto2.setStationId(5);
		dto2.setData(6);
		dto2.setId(7);
		dto2.setTypeId(8);
		sensorService.createSensor(dto2);

		sensorDto dto3 = new sensorDto();
		dto2.setStationId(1);
		dto2.setData(10);
		dto2.setId(11);
		dto2.setTypeId(999);
		sensorService.createSensor(dto3);


		List<sensorDto> newDtoList = sensorService.getSensorsByStation(1);

		Assertions.assertEquals(newDtoList.get(0).getId() ,dto1.getId() );
		Assertions.assertEquals(newDtoList.get(0).getTypeId() ,dto1.getTypeId() );
		Assertions.assertEquals(newDtoList.get(0).getData() ,dto1.getData() );
		Assertions.assertEquals(newDtoList.get(0).getStationId() ,dto1.getStationId() );

		Assertions.assertEquals(newDtoList.get(1).getId() ,dto3.getId() );
		Assertions.assertEquals(newDtoList.get(1).getTypeId() ,dto3.getTypeId() );
		Assertions.assertEquals(newDtoList.get(1).getData() ,dto3.getData() );
		Assertions.assertEquals(newDtoList.get(1).getStationId() ,dto3.getStationId() );
	}
}
