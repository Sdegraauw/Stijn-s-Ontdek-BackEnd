package Ontdekstation013.ClimateChecker.features.databasepolling;

import org.springframework.stereotype.Service;

import Ontdekstation013.ClimateChecker.features.station.ValidationService;
import Ontdekstation013.ClimateChecker.features.station.endpoint.MeetJeStadDto;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Service
public class MJSValidationService extends ValidationService {

    public MeetJeStadDto ValidateDTO(MeetJeStadDto inputDto){
        MeetJeStadDto validatedDto = new MeetJeStadDto();

        try
        {
            validateLongValue(inputDto.id, 0, 0);
            validateFloatValue(inputDto.temperature, -50.0F, 75.0F);
            validateFloatValue(inputDto.humidity, 0F, 100F);
            validateFloatValue(inputDto.supply, 0,100F);

            validatedDto = inputDto;

        }
        catch(Exception e)
        {
            validatedDto.id = inputDto.id;
            validatedDto.errorMessage = e.getMessage();
        }

        return validatedDto;
    }
    public MeetJeStadDto ValidateRegisterDTO(MeetJeStadDto inputDto){
        MeetJeStadDto validatedDto = new MeetJeStadDto();

        try
        {
            validateLongValue(inputDto.id, 0, 0);

            validatedDto = inputDto;

        }
        catch(Exception e)
        {
            validatedDto.id = inputDto.id;
            validatedDto.errorMessage = e.getMessage();
        }

        return validatedDto;
    }


    public List<MeetJeStadDto> ValidateAll(List<MeetJeStadDto> inputDtos){
        List<MeetJeStadDto> validatedDtos = new ArrayList<>();

        for(MeetJeStadDto Dto : inputDtos){
            Dto = ValidateDTO(Dto);

            validatedDtos.add(Dto);
        }

        return validatedDtos;
    }
}
