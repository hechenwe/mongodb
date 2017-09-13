package sooncode.mongodb;

import java.util.LinkedList;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

public class MongoDbDao {

	private MongoDatabase mongoDatabase;

	public void setMongoClientManager(MongoClientManager mongoClientManager) {
		this.mongoDatabase = mongoClientManager.getMongoDatabase();
	}

	/**
	 * 创建集合
	 * 
	 * @param collectionName
	 *            集合名称
	 */
	public void createCollection(String collectionName) {

		this.mongoDatabase.createCollection(collectionName);

	}

	public MongoCollection<Document> getMongoCollection(String collectionName) {

		MongoCollection<Document> collection = mongoDatabase.getCollection(collectionName);
		return collection;
	}

	public void saveDocuments(String collectionName, List<Document> documents) {

		MongoCollection<Document> collection = getMongoCollection(collectionName);

		if (collection == null) {
			createCollection(collectionName);
			collection = getMongoCollection(collectionName);
		}
		collection.insertMany(documents);
	}

	public <T> List<Document> getDocuments(String collectionName) {

		MongoCollection<Document> collection = getMongoCollection(collectionName);
		FindIterable<Document> findIterable = collection.find();
		MongoCursor<Document> mongoCursor = findIterable.iterator();
		List<Document> documents = new LinkedList<>();
		while (mongoCursor.hasNext()) {
			documents.add(mongoCursor.next());
		}

		return documents;

	}

	public UpdateResult updateDocument(String collectionName, String key, Object value, Document document) {
		MongoCollection<Document> collection = getMongoCollection(collectionName);
		return collection.updateMany(Filters.eq(key, value), new Document("$set", document));

	}

	public void updateDocuments(String collectionName, String key, Object value, List<Document> documents) {
		MongoCollection<Document> collection = getMongoCollection(collectionName);

		for (Document doc : documents) {
			collection.updateMany(Filters.eq(key, value), new Document("$set", doc));
		}

	}

	public DeleteResult deleteDocuments(String collectionName, String key, Object value) {

		MongoCollection<Document> collection = getMongoCollection(collectionName);
		DeleteResult deleteResult = collection.deleteMany(Filters.eq(key, value));
		return deleteResult;

	}

	public DeleteResult deleteDocument(String collectionName, String key, Object value) {

		MongoCollection<Document> collection = getMongoCollection(collectionName);
		DeleteResult deleteResult = collection.deleteOne(Filters.eq(key, value));
		return deleteResult;

	}

}
