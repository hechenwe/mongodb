package sooncode.mongodb.builder;

import com.mongodb.BasicDBObject;

import sooncode.mongodb.reflect.RObject;
import sooncode.mongodb.reflect.Traversal;

public class BasicDBObjectBuilder {

	public static <T> BasicDBObject getBasicDBObject(T model) {
		BasicDBObject bdo = new BasicDBObject();
		RObject<T> rObj = new RObject<>(model);
		rObj.forEach(new Traversal<String, Object>() {
			@Override
			public void each(String key, Object value) {
				if (value != null) {
					bdo.append(key, value);
				}
			}
		});
		return bdo;
	}
}
