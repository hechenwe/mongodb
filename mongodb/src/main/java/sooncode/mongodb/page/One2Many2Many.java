package sooncode.mongodb.page;

import java.util.List;

public class One2Many2Many<L, M, R> {
	private L one;

	private List<One2Many<M, R>> one2manys;

	public L getOne() {
		return one;
	}

	public void setOne(L one) {
		this.one = one;
	}

	public List<One2Many<M, R>> getOne2manys() {
		return one2manys;
	}

	public void setOne2manys(List<One2Many<M, R>> one2manys) {
		this.one2manys = one2manys;
	}

}
