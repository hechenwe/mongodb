package sooncode.mongodb;

import com.mongodb.client.MongoDatabase;

public interface MongoDatabaseUse <T> {

	public T use ( MongoDatabase mongoDatabase );
}
