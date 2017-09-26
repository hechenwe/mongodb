package sooncode.mongodb;

import java.util.LinkedList;
import java.util.List;

import org.bson.Document;

import com.mongodb.BasicDBObject;

import sooncode.mongodb.builder.DocumentBuilder;
import sooncode.mongodb.builder.ModelNaming;
import sooncode.mongodb.page.Page;
import sooncode.mongodb.reflect.RObject;
import sooncode.mongodb.reflect.Traversal;

public class MongoDbService {

	private MongoDbDao mongoDbDao;

	public void setMongoDbDao(MongoDbDao mongoDbDao) {
		this.mongoDbDao = mongoDbDao;
	}

	public <T> void saveModels(List<T> models) {

		T model = models.get(0);
		String modelName = ModelNaming.getCollectionName(model);
		List<Document> documents = DocumentBuilder.getDocuments(models);
		mongoDbDao.saveDocuments(modelName, documents);
	}

	public <T> List<T> getModels(Class<T> modelClass) {
		String modelName = ModelNaming.getCollectionName(modelClass);
		List<Document> documents = mongoDbDao.getDocuments(modelName);
		List<T> list = DocumentBuilder.getModels(modelClass, documents);
		return list;

	}

	public <T> T getModel(T model) {

		return getModels(model, 1, 1).get(0);
	}

	public <T> List<T> getModels(T model, int pageNumber, int pageSize) {
		String modelName = ModelNaming.getCollectionName(model);
		BasicDBObject basicDBObject = new BasicDBObject();
		RObject<T> rObj = new RObject<>(model);
		rObj.forEach(new Traversal<String, Object>() {
			@Override
			public void each(String key, Object value) {
				if (value != null) {
					basicDBObject.append(key, value);
				}
			}
		});
		List<Document> documents = mongoDbDao.getDocuments(modelName, basicDBObject, (pageNumber - 1) * pageSize, pageSize);
		@SuppressWarnings("unchecked")
		List<T> list = DocumentBuilder.getModels((Class<T>) model.getClass(), documents);
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
					Document doc = new Document(key, value);
					documents.add(doc);
				}
			}
		});

		mongoDbDao.updateDocuments(modelName, key, value, documents);
	}

	public <T> long deleteModels(Class<T> modelClass, String key, Object value) {
		String modelName = ModelNaming.getCollectionName(modelClass);
		return mongoDbDao.deleteDocuments(modelName, key, value).getDeletedCount();

	}

	public <T> long deleteModel(Class<T> modelClass, String key, Object value) {
		String modelName = ModelNaming.getCollectionName(modelClass);
		return mongoDbDao.deleteDocument(modelName, key, value).getDeletedCount();
	}

	public <T> long getModelSize(Class<T> modelClass) {
		String modelName = ModelNaming.getCollectionName(modelClass);
		return mongoDbDao.getDocumentSize(modelName);
	}

	public <M, O> void embedModel(Class<M> mainModelClass, String key, Object val, O otherModel) {
		String collectionName = ModelNaming.getCollectionName(mainModelClass);
		String otherDocumentName = ModelNaming.getCollectionName(otherModel);
		Document otherDocument = DocumentBuilder.getDocument(otherModel);
		mongoDbDao.embedDocument(collectionName, key, val, otherDocumentName, otherDocument);

	}

	public <M, O> void embedModels(Class<M> mainModelClass, String key, Object val, List<O> otherModels) {
		if (otherModels.size() == 0) {
			return;
		}
		String collectionName = ModelNaming.getCollectionName(mainModelClass);
		String otherDocumentName = ModelNaming.getCollectionName(otherModels.get(0));
		List<Document> otherDocuments = DocumentBuilder.getDocuments(otherModels);
		mongoDbDao.embedDocuments(collectionName, key, val, otherDocumentName, otherDocuments);

	}

	public <M, O> O getEmbedModel(Class<M> mainModelClass, String key, Object val, Class<O> otherModelClass) {
		String collectionName = ModelNaming.getCollectionName(mainModelClass);
		String otherDocumentName = ModelNaming.getCollectionName(otherModelClass);
		Document otherDocument = mongoDbDao.getEmbedDocument(collectionName, key, val, otherDocumentName);
		return DocumentBuilder.getModel(otherModelClass, otherDocument);

	}

	public <M, O> List<O> getEmbedModels(Class<M> mainModelClass, String key, Object val, Class<O> otherModelClass) {
		String collectionName = ModelNaming.getCollectionName(mainModelClass);
		String otherDocumentName = ModelNaming.getCollectionName(otherModelClass);
		List<Document> otherDocuments = mongoDbDao.getEmbedDocuments(collectionName, key, val, otherDocumentName);
		return DocumentBuilder.getModels(otherModelClass, otherDocuments);

	}

	public <M, O> Page getMongoPage(int pageNumber, int pageSize, M mainModel, O... otherModels) {

		List<M> models = getModels(mainModel, pageNumber, pageSize);
		long total = getModelSize(mainModel.getClass());
		Page mp = new Page(pageNumber, pageSize, total);
		mp.setOnes(models);
		return mp;
	}

}
