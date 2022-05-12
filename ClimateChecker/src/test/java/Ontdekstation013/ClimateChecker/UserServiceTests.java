package Ontdekstation013.ClimateChecker;

import Ontdekstation013.ClimateChecker.Mocks.MockUserRepo;
import Ontdekstation013.ClimateChecker.models.User;
import Ontdekstation013.ClimateChecker.models.dto.*;
import Ontdekstation013.ClimateChecker.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class UserServiceTests {
	private UserService userService;
	private MockUserRepo mockRepo;



	@BeforeEach
	void setup() throws Exception{
		this.mockRepo = new MockUserRepo();
		this.userService = new UserService(mockRepo);

		List<User> userList = new ArrayList<>();

		User user = new User();

		// user 1
		user.setUserID(1);
		user.setUserName("name1");
		user.setMailAddress("mail1");

		userList.add(user);

		// user 2
		user = new User();

		user.setUserID(2);
		user.setUserName("name2");
		user.setMailAddress("mail2");

		userList.add(user);

		mockRepo.FillDatabase(userList);
	}

	// No functionality in UserService
	@Test
	void findUserByIdTest() {
		userDto newDto = userService.findUserById(2);

		Assertions.assertEquals("name2",newDto.getUsername());
		Assertions.assertEquals("mail2",newDto.getMailAddress());
	}


	@Test
	void userToUserDtoTest() {
		User user = new User();

		user.setUserName("Jan");
		user.setUserID(1);
		user.setMailAddress("Jan@home.nl");


		userDto newDto = userService.userToUserDto(user);

		Assertions.assertEquals(user.getUserName(),newDto.getUsername());
		Assertions.assertEquals(user.getMailAddress(),newDto.getMailAddress());
		Assertions.assertEquals(user.getUserID(),newDto.getId());

	}


	// No functionality in UserService
	@Test
	void getAllUsersTest() {
		List<userDataDto> userList = userService.getAllUsers();


		Assertions.assertEquals("name1",userList.get(0).getUsername());
		Assertions.assertEquals("mail1",userList.get(0).getMailAddress());
		Assertions.assertEquals(1,userList.get(0).getId());


		Assertions.assertEquals("name2",userList.get(1).getUsername());
		Assertions.assertEquals("mail2",userList.get(1).getMailAddress());
		Assertions.assertEquals(2,userList.get(1).getId());
	}


	// No functionality in UserService
	@Test
	void getAllByPageIdTest() {
		/*List<userDataDto> newDtoList = userService.getAllByPageId(1);


		Assertions.assertEquals(2,newDtoList.get(0).getId());
		Assertions.assertEquals(dto.getUsername(),newDtoList.get(0).getUsername());*/
		Assertions.fail();
	}


	// No functionality in UserService
	@Test
	void deleteUserTest() {
		userService.deleteUser(2);

		Assertions.assertTrue(true);
	}


	// No functionality in UserService
	@Test
	void createNewUserTest() {
		registerDto dto = new registerDto();

		dto.setUsername("Jan");
		dto.setPassword("fuckingpassword");
		dto.setEmail("Jan@home.nl");
		userService.createNewUser(dto);

		Assertions.assertTrue(true);
	}



	// No functionality in UserService
	@Test
	void loginUserTest() {
		loginDto dto = new loginDto();

		dto.setPassword("fuckingpassword");
		dto.setMailAddress("Jan@home.nl");
		userService.loginUser(dto);


		Assertions.assertTrue(true);
	}


	@Test
	void editUserTest() {

		editUserDto dto2 = new editUserDto();
		dto2.setId(1);
		dto2.setUsername("Piet");
		dto2.setPassword("qwerty1234567890");
		dto2.setMailAddress("Piet@home.nl");

		userService.editUser(dto2);

		Assertions.assertTrue(true);
	}
}
