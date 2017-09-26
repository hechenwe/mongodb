package sooncode.mongodb.page;

import java.util.List;

public class One2Many<L, R> {
	private L one;
	private List<R> many;

	public One2Many(){
		
	}
	
	
	public One2Many(L one,List<R> many){
		this.one = one;
		this.many = many;
				
	}
	
	
	
	public L getOne() {
		return one;
	}

	public void setOne(L one) {
		this.one = one;
	}

	public List<R> getMany() {
		return many;
	}

	public void setMany(List<R> many) {
		this.many = many;
	}
}