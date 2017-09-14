package sooncode.mongodb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class MongoDbServiceTest {

	@Autowired
	private MongoDbService mongoDbService;
 
	@Test
	public void saveModels() {
		User u = new User();
		u.setId(5);
		u.setPwd("000000");
		u.setUsername("JACK");
		u.setScore(2344.34);
		u.setSex(true);
		u.setBirthDay(new Date());
		
		List<User> users = new ArrayList<>();
		users.add(u);
		mongoDbService.saveModels( users);

	}

	@Test
	public void getModelsTest() {
		List<User> users = mongoDbService.getModels(User.class);
		for (User user : users) {
			System.out.println("---: " + user);
		}

	}
	
	@Test
	public void getModelsTest4Page() {
		User user = new User();
		//user.setSex(true);
		List<User> users = mongoDbService.getModels(user,0,5);
		for (User u : users) {
			System.out.println("---: " + u);
		}
		
	}

	@Test
	public void updateModelTest() {
		User u = new User();
		u.setPwd("hechenwe@gmail.com");
		u.setUsername("HE_CHEN");
		mongoDbService.updateModel( "id", 4, u);
	}
	
	
	
	@Test
	public void deleteModelTest(){
	long n = mongoDbService.deleteModel(User.class, "id", 3);
		System.out.println("--- n : " + n);
	}
}
