package sooncode.mongodb.page;

import java.util.List;

public class One<L>  {
	
	
	
	private List<L> ones;

	public One(List<L> ones){
		this.ones = ones;
	}
	
	
	
	public List<L> getOnes() {
		return ones;
	}

	public void setOnes(List<L> ones) {
		this.ones = ones;
	}

}
