package sooncode.mongodb;

import java.util.ArrayList;
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
		u.setId(4);
		u.setPwd("111");
		u.setUsername("TOM");

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
	public void updateModelTest() {
		User u = new User();
		u.setPwd("zkdsljlskdfj");
		u.setUsername("HE CHEN");
		mongoDbService.updateModel( "id", 4, u);
	}
	@Test
	public void deleteModelTest(){
		
	long n = 	mongoDbService.deleteModel(User.class, "id", null);
		System.out.println("--- n : " + n);
	}
}
