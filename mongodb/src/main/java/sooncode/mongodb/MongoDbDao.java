package sooncode.mongodb;

import java.util.LinkedList;
import java.util.List;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

public class MongoDbDao {

	private MongoClientManager mongoClientManager;

	public void setMongoClientManager(MongoClientManager mongoClientManager) {
		this.mongoClientManager = mongoClientManager;
	}

	/**
	 * 创建集合
	 * 
	 * @param collectionName
	 *            集合名称
	 */
	public void createCollection(String collectionName) {

		this.mongoClientManager.mongoDatabaseCallBack(new MongoDatabaseUse<String>() {

			@Override
			public String use(MongoDatabase mongoDatabase) {
				mongoDatabase.createCollection(collectionName);
				return null;
			}
		});

	}

	public void saveDocuments(String collectionName, List<Document> documents) {

		this.mongoClientManager.mongoDatabaseCallBack(new MongoDatabaseUse<String>() {

			@Override
			public String use(MongoDatabase mongoDatabase) {
				MongoCollection<Document> collection = mongoDatabase.getCollection(collectionName);
				if (collection == null) {
					createCollection(collectionName);
					collection = mongoDatabase.getCollection(collectionName);
				}
				collection.insertMany(documents);
				return null;
			}
		});

	}

	public <T> List<Document> getDocuments(String collectionName) {

		return this.mongoClientManager.mongoDatabaseCallBack(new MongoDatabaseUse<List<Document>>() {

			@Override
			public List<Document> use(MongoDatabase mongoDatabase) {
				FindIterable<Document> findIterable = mongoDatabase.getCollection(collectionName).find();
				MongoCursor<Document> mongoCursor = findIterable.iterator();
				List<Document> documents = new LinkedList<>();
				while (mongoCursor.hasNext()) {
					documents.add(mongoCursor.next());
				}

				return documents;
			}

		});

	}

	public UpdateResult updateDocument(String collectionName, String key, Object value, Document document) {

		return this.mongoClientManager.mongoDatabaseCallBack(new MongoDatabaseUse<UpdateResult>() {

			@Override
			public UpdateResult use(MongoDatabase mongoDatabase) {
				MongoCollection<Document> collection = mongoDatabase.getCollection(collectionName);
				return collection.updateMany(Filters.eq(key, value), new Document("$set", document));
			}
		});

	}

	public void updateDocuments(String collectionName, String key, Object value, List<Document> documents) {

		this.mongoClientManager.mongoDatabaseCallBack(new MongoDatabaseUse<String>() {

			@Override
			public String use(MongoDatabase mongoDatabase) {
				MongoCollection<Document> collection = mongoDatabase.getCollection(collectionName);
				for (Document doc : documents) {
					collection.updateMany(Filters.eq(key, value), new Document("$set", doc));
				}
				return null;
			}
		});

	}

	public DeleteResult deleteDocuments(String collectionName, String key, Object value) {

		return this.mongoClientManager.mongoDatabaseCallBack(new MongoDatabaseUse<DeleteResult>() {

			@Override
			public DeleteResult use(MongoDatabase mongoDatabase) {
				MongoCollection<Document> collection = mongoDatabase.getCollection(collectionName);
				DeleteResult deleteResult = collection.deleteMany(Filters.eq(key, value));
				return deleteResult;
			}
		});

	}

	public DeleteResult deleteDocument(String collectionName, String key, Object value) {

		return this.mongoClientManager.mongoDatabaseCallBack(new MongoDatabaseUse<DeleteResult>() {

			@Override
			public DeleteResult use(MongoDatabase mongoDatabase) {
				MongoCollection<Document> collection = mongoDatabase.getCollection(collectionName);
				DeleteResult deleteResult = collection.deleteOne(Filters.eq(key, value));
				return deleteResult;
			}
		});

	}

	public List<Document> getDocuments(String collectionName, BasicDBObject basicDBObject, int pageNumber, int pageSize) {

		return this.mongoClientManager.mongoDatabaseCallBack(new MongoDatabaseUse<List<Document>>() {

			@Override
			public List<Document> use(MongoDatabase mongoDatabase) {
				MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(collectionName);
				FindIterable<Document> findIterable = mongoCollection.find(basicDBObject).skip(pageNumber).limit(pageSize);
				findIterable.iterator();
				List<Document> documents = new LinkedList<>();
				for (Document document : findIterable) {
					documents.add(document);
				}
				return documents;

			}
		});

	}

}
