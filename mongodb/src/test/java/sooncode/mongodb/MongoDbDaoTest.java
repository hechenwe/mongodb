package sooncode.mongodb;

import org.apache.log4j.chainsaw.Main;
import org.bson.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class MongoDbDaoTest {

	@Autowired
	private MongoDbDao mongoDbDao;

	@Test
	public void getEmbedDocument() {
 
		Document d = mongoDbDao.getEmbedDocument("user", "id", 4, "address");
		
		System.out.println(d);
	}
	
	
	@Test
	public void correlationDocuments() {
		
		//Document d = mongoDbDao.correlationDocuments(collectionName, key, val, otherDocumentName, otherKey, otherValues); 
		
		 
	}
	public static int[] getXY(int index, int weekSize) {
		int x = index % weekSize;
		int y = index / weekSize;
		int[] num = new int[] { x, y };
		return num;

	}
	
	public static void main(String[] args) {
		
		for(int i = 0 ; i<35 ;i++){
			System.out.println(getXY(i,7)[0]+ ","+ getXY(i, 7)[1]);
			
		}
		
	}
	 
}
