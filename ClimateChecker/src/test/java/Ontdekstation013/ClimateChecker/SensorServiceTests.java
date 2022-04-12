package Ontdekstation013.ClimateChecker;

import Ontdekstation013.ClimateChecker.Mocks.MockSensorRepo;
import Ontdekstation013.ClimateChecker.models.Sensor;
import Ontdekstation013.ClimateChecker.models.SensorType;
import Ontdekstation013.ClimateChecker.models.Station;
import Ontdekstation013.ClimateChecker.models.dto.sensorDto;
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

	private sensorDto sensorDto;



	@BeforeEach
	void setup() throws Exception{
		this.sensorService = new SensorService(new MockSensorRepo());
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

	@Test
	void AmountOfSensorTest() {

		List<sensorDto> sensorDtoList = sensorService.getAll();

		Assertions.assertEquals(0, sensorDtoList.stream().count());
	}

}
