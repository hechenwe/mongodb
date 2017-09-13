package sooncode.mongodb;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;

import org.bson.Document;

public class DocumentService {

	public static <T> List<Document> getDocuments (List<T> models){
		 
		List<Document> documents = new ArrayList<Document>();  
		for (T t : models) {
			Document doc = new Document();
			RObject<T> rObj = new RObject<>(t);
			doc.putAll(rObj.getFiledAndValue());
			documents.add(doc);
		}
		 
		return documents;
		
		
	}
	public static <T> List<T> getModels ( Class<T> modelClass,  List<Document> documents){
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
	
	
}
