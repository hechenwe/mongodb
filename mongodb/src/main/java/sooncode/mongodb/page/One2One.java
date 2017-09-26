package sooncode.mongodb.page;

import java.util.HashMap;
import java.util.Map;


 
public class One2One {

	private Map<Class<?>  ,Object> map = new HashMap<Class<?>, Object>();
	
	@SuppressWarnings("unchecked")
	public <T> T getOne (Class<T> clas){
		 
		return (T) map.get(clas);
	}
	
	public <T> void add(T t ){
		
		map.put(t.getClass(), t);
		
	}
	
	public int size(){
		return map.size();
	}
	
	
}
