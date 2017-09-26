package sooncode.mongodb.builder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;

import org.bson.Document;

import sooncode.mongodb.reflect.RObject;

public class DocumentBuilder {

	public static <T> List<Document> getDocuments(List<T> models) {

		List<Document> documents = new ArrayList<Document>();
		for (T t : models) {
			Document doc = new Document();
			RObject<T> rObj = new RObject<>(t);
			doc.putAll(rObj.getFiledAndValue());
			documents.add(doc);
		}

		return documents;

	}

	public static <T> Document getDocument(T model) {

		Document doc = new Document();
		RObject<T> rObj = new RObject<>(model);
		doc.putAll(rObj.getFiledAndValue());

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

}
