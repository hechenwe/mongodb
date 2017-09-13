package sooncode.mongodb;

import java.util.LinkedList;
import java.util.List;

import org.bson.Document;

public class MongoDbService {

	private MongoDbDao mongoDbDao;

	public void setMongoDbDao(MongoDbDao mongoDbDao) {
		this.mongoDbDao = mongoDbDao;
	}
 

	public <T> void saveModels(  List<T> models) {
		
		T model = models.get(0);
		String modelName = ModelNaming.getCollectionName(model);
		List<Document> documents = DocumentService.getDocuments(models);
		mongoDbDao.saveDocuments(modelName, documents);
	}

	public <T> List<T> getModels(Class<T> modelClass) {
		String modelName =  ModelNaming.getCollectionName(modelClass);
		List<Document> documents = mongoDbDao.getDocuments(modelName);
		List<T> list = DocumentService.getModels(modelClass, documents);
		return list;

	}

	public <T> void updateModel(String key, Object value, T model) {
		String modelName = ModelNaming.getCollectionName(model);
		RObject<T> rObj = new RObject<>(model);
		List<Document> documents = new LinkedList<Document>();
		rObj.forEach(new Traversal<String, Object>() {
			@Override
			public void each(String key, Object value) {
				if (value != null) {
					 Document doc = new Document(key, value) ;
					 documents.add(doc);
					} 
			}
		});
		
		
		mongoDbDao.updateDocuments(modelName, key, value, documents);
	}
	
	public <T> long deleteModels (Class<T> modelClass, String key ,Object value ){
		  String modelName = ModelNaming.getCollectionName(modelClass);
		  return mongoDbDao.deleteDocuments(modelName, key, value).getDeletedCount() ; 
		
	}
	
	
	public <T> long deleteModel (Class<T> modelClass, String key ,Object value ){
		String modelName = ModelNaming.getCollectionName(modelClass);
		return mongoDbDao.deleteDocument(modelName, key, value).getDeletedCount();
	}
	
	
	

}
