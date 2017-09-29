package sooncode.mongodb.builder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;

import org.bson.Document;
import org.bson.types.ObjectId;

import sooncode.mongodb.MongoDbDao;
import sooncode.mongodb.reflect.RObject;
import sooncode.mongodb.reflect.Traversal;

public class DocumentBuilder {

	public static <T> List<Document> getDocuments(List<T> models) {
		 
		List<Document> documents = new ArrayList<Document>();
		for (T t : models) {
			Document doc = getDocument(t);
			documents.add(doc);
		}
		return documents;

	}

	public static <T> Document getDocument(T model) {
		String documentName = ModelNaming.getCollectionName(model);
		Document doc = new Document();
		RObject<T> rObj = new RObject<>(model);
		ObjectId _id = (ObjectId) rObj.invoke("get_id");
		rObj.forEach(new Traversal<String, Object>() {
			@Override
			public void each(String key, Object value) {
				 if(value != null){
					 doc.put(key, value);
				 }
			}
		});
		doc.put(MongoDbDao.DOCUMENT_NAME_KEY, documentName);
		doc.put("_id", _id);
		return doc;

	}

	public static <T> List<T> getModels(Class<T> modelClass, List<Document> documents) {
		List<T> models = new LinkedList<>();
		for (Document document : documents) {
			RObject<T> robj = new RObject<>(modelClass);
			document.forEach(new BiConsumer<String, Object>() {
				@Override
				public void accept(String fieldName, Object args) {
					robj.invokeSetMethod(fieldName, args);
				}
			});
			models.add(robj.getObject());
		}

		return models;

	}

	public static <T> T getModel(Class<T> modelClass, Document document) {

		if (document == null) {
			return null;
		}
		
		RObject<T> robj = new RObject<>(modelClass);
		document.forEach(new BiConsumer<String, Object>() {
			@Override
			public void accept(String fieldName, Object args) {
				robj.invokeSetMethod(fieldName, args);
			}
		});

		return robj.getObject();

	}
	public static <T> T getModel( T  model , Document document) {
		
		if (document == null) {
			return null;
		}
		
		RObject<T> robj = new RObject<>(model);
		document.forEach(new BiConsumer<String, Object>() {
			@Override
			public void accept(String fieldName, Object args) {
				robj.invokeSetMethod(fieldName, args);
			}
		});
		
		return robj.getObject();
		
	}

}
