package sooncode.mongodb;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import sooncode.mongodb.page.Page;
import sooncode.mongodb.test.model.Address;
import sooncode.mongodb.test.model.Order;
import sooncode.mongodb.test.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class MongoDbServiceTest {

	@Autowired
	private MongoDbService mongoDbService;

	@Test
	public void saveModels() {

		List<User> users = new ArrayList<>();
		for (int i = 0; i < 100; i++) {

			User u = new User();
			u.set_id(new ObjectId());
			u.setPwd("000000" + i);
			u.setUsername("HELLO" + i);
			u.setScore(35544.34 + i);
			u.setSex(true);
			u.setBirthDay(new Date());
			users.add(u);
		}

		mongoDbService.saveModels(users);

	}

	@Test
	public void saveModel() {

		User u = new User();
		u.set_id(new ObjectId());
		u.setPwd("000000");
		u.setUsername("HELLO");
		u.setScore(35544.34);
		u.setSex(true);
		u.setBirthDay(new Date());

		 mongoDbService.saveModel(u);
		 
	}
	
	
	@Test
	public void getModel() {
		
		User u = new User();
		u.set_id(new ObjectId("59b8d80098299f777c260821"));
		u = mongoDbService.getModel(u);
		System.out.println(u);
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
		// user.setSex(true);
		List<User> users = mongoDbService.getModels(user, 0, 100000);
		System.out.println("---: " + users.size());
		// for (User u : users) {
		// System.out.println("---: " + u);
		// }

	}

	@Test
	public void updateModelTest() {
		User u = new User();
		u.set_id(new ObjectId("59b8d80098299f777c260821"));
		u.setPwd("9999999");
		mongoDbService.updateModel(u);
	}

	
	
	@Test
	public void deleteModelTest() {
		User u = new User();
		u.set_id(new ObjectId("59b8d80098299f777c260820"));
		long n = mongoDbService.deleteModel(u);
		System.out.println("--- n : " + n);
	}
	@Test
	public void deleteModelsTest() {
		 
		long n = mongoDbService.deleteModels(User.class,"sex",true);
		System.out.println("--- n : " + n);
	}

	@Test
	public void getDocumentSize() {
		long n = mongoDbService.getModelSize(User.class);
		System.out.println("--- n : " + n);
	}

	@Test
	public void embedModel() {

		User u = new User();
		u.set_id(new ObjectId(""));
		Address ad = new Address();
		ad.setAddressId(1);
		ad.setAddressName("北京-朝陽區");
		mongoDbService.embedModel(User.class, "id", 4, ad);

	}

	@Test
	public void embedModels() {

		List<Order> list = new LinkedList<>();
		for (int i = 0; i < 10; i++) {
			Order o = new Order();
			o.setOrderId(i);
			o.setOrderName("SOON-" + i);
			o.setOrderNumber("2017-" + i);
			o.setPrice(new Double(i));
			list.add(o);
		}

		mongoDbService.embedModels(User.class, "id", 4, list);

	}

	@Test
	public void getEmbedModels() {

		List<Order> list = mongoDbService.getEmbedModels(User.class, "id", 4, Order.class);
		System.out.println(list);

	}

	@Test
	public void getEmbedModel() {

		User u = new User();
		u.set_id(new ObjectId(""));
		Address ad = mongoDbService.getEmbedModel(User.class, "id", 4, Address.class);

		System.out.println(" --- :" + ad);

	}

	@Test
	public void getMongoPage() {

		User u = new User();

		Page mp = mongoDbService.getMongoPage(1, 10, u);
		List<User> list = mp.getOnes();
		System.out.println("---" + mp.getTotal() + " : " + mp.getPageSize());
		for (User user : list) {
			System.out.println("---" + user);
		}
	}
}
