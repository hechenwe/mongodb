package sooncode.mongodb;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.QueryOperators;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import sooncode.mongodb.db.MongoClientManager;
import sooncode.mongodb.db.MongoDatabaseUse;

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

	public void saveDocument(String collectionName, Document document) {

		this.mongoClientManager.mongoDatabaseCallBack(new MongoDatabaseUse<String>() {

			@Override
			public String use(MongoDatabase mongoDatabase) {
				MongoCollection<Document> collection = mongoDatabase.getCollection(collectionName);
				if (collection == null) {
					createCollection(collectionName);
					collection = mongoDatabase.getCollection(collectionName);
				}
				collection.insertOne(document);
				return null;
			}
		});

	}

	/**
	 * 内嵌文档
	 */
	public void embedDocument(String collectionName, String key, Object val, String otherDocumentName, Document otherDocument) {
		embed(collectionName, key, val, otherDocumentName, otherDocument);
	}

	public void embedDocuments(String collectionName, String key, Object val, String otherDocumentName, List<Document> otherDocuments) {
		embed(collectionName, key, val, otherDocumentName, otherDocuments);
	}

	/**
	 * 关联文档
	 * 
	 * @param collectionName
	 * @param key
	 * @param val
	 * @param otherDocumentName
	 * @param otherDocument
	 */
	public void correlationDocuments(String collectionName, String key, Object val, String otherDocumentName, String otherKey, List<Object> otherValues) {
		this.mongoClientManager.mongoDatabaseCallBack(new MongoDatabaseUse<String>() {

			@Override
			public String use(MongoDatabase mongoDatabase) {
				MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(collectionName);
				BasicDBObject mainBasicDBObject = new BasicDBObject();
				FindIterable<Document> FindIterable = mongoCollection.find(mainBasicDBObject);
				Document doc = FindIterable.first();

				BasicDBObject queryObject = new BasicDBObject().append("id", new BasicDBObject(QueryOperators.IN, otherValues.toArray()));

				MongoCollection<Document> otherCollection = mongoDatabase.getCollection(otherDocumentName);
				FindIterable<Document> otherFI =  otherCollection.find(queryObject);
				
				List<ObjectId> ids = new ArrayList<>();
				for (Document d : otherFI) {
					ObjectId objId = d.getObjectId("_id");
					ids.add(objId);
				}
				
				doc.append(otherDocumentName, ids);
				mongoCollection.insertOne(doc);
				
				return null;
			}

		});
	}

	private void embed(String collectionName, String key, Object val, String otherDocumentName, Object otherDocuments) {
		this.mongoClientManager.mongoDatabaseCallBack(new MongoDatabaseUse<String>() {

			@Override
			public String use(MongoDatabase mongoDatabase) {
				BasicDBObject mainBasicDBObject = new BasicDBObject();
				mainBasicDBObject.append(key, val);
				MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(collectionName);
				FindIterable<Document> FindIterable = mongoCollection.find(mainBasicDBObject);
				Document doc = FindIterable.first();
				doc.append(otherDocumentName, otherDocuments);
				updateDocument(collectionName, key, val, doc);
				return null;
			}

		});
	}

	/**
	 * 获取内嵌文档
	 * 
	 * @param collectionName
	 * @param key
	 * @param val
	 * @param otherDocumentName
	 * @param otherDocument
	 */
	public Document getEmbedDocument(String collectionName, String key, Object val, String otherDocumentName) {

		return this.mongoClientManager.mongoDatabaseCallBack(new MongoDatabaseUse<Document>() {

			@Override
			public Document use(MongoDatabase mongoDatabase) {

				MongoCollection<Document> mainCollection = mongoDatabase.getCollection(collectionName);
				BasicDBObject mainBasicDBObject = new BasicDBObject();
				mainBasicDBObject.append(key, val);
				FindIterable<Document> fi = mainCollection.find(mainBasicDBObject);
				Document d = fi.first();
				Document other = (Document) d.get(otherDocumentName);
				return other;
			}

		});

	}

	public List<Document> getEmbedDocuments(String collectionName, String key, Object val, String otherDocumentName) {

		return this.mongoClientManager.mongoDatabaseCallBack(new MongoDatabaseUse<List<Document>>() {

			@SuppressWarnings("unchecked")
			@Override
			public List<Document> use(MongoDatabase mongoDatabase) {
				MongoCollection<Document> mainCollection = mongoDatabase.getCollection(collectionName);
				BasicDBObject mainBasicDBObject = new BasicDBObject();
				mainBasicDBObject.append(key, val);
				FindIterable<Document> fi = mainCollection.find(mainBasicDBObject);
				Document other = fi.first();
				List<Document> list = (List<Document>) other.get(otherDocumentName);
				return list;
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

	public long getDocumentSize(String collectionName) {

		return this.mongoClientManager.mongoDatabaseCallBack(new MongoDatabaseUse<Long>() {

			@Override
			public Long use(MongoDatabase mongoDatabase) {

				MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(collectionName);
				return mongoCollection.count();
			}
		});
	}

}
