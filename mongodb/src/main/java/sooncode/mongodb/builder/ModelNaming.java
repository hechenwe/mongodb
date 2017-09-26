package sooncode.mongodb.builder;
/**
 * 模型 命名
 * @author hechenwe@gmail.com
 *
 */
public class ModelNaming {

	private static final String SUFFIX = "";
	 
	
	public  static <T> String getCollectionName (T model){
		
		String modelName = model.getClass().getSimpleName();
		return firstLowerCase(modelName)+SUFFIX;
		
	}
	public  static <T> String getCollectionName (Class<T> modelClass){
		
		String modelName = modelClass.getSimpleName();
		return firstLowerCase(modelName)+SUFFIX;
		
	}
	 
	
	public static String firstLowerCase (String name){
		
		String first = name.substring(0, 1).toLowerCase();
		return first + name.substring(1,name.length());
		 
	}
	
	
	 
	
}
