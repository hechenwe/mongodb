package sooncode.mongodb.page;

import java.util.LinkedList;
import java.util.List;

public class Many2Many<L, M, R> {
	private L one;
	private List<One2One> many;

	public L getOne() {
		return one;
	}

	public void setOne(L one) {
		this.one = one;
	}

	public List<One2One> getMany() {
		return many;
	}
	/*public List<M> getMany(Class<M> mClass) {
		List<M> list = new LinkedList<>();
		for (One2One o2o : many) {
			list.add(o2o.getOne(mClass));
		}
		return list;
	}*/
	public List<R> getMany(Class<R> rClass) {
		List<R> list = new LinkedList<>();
		for (One2One o2o : many) {
			list.add(o2o.getOne(rClass));
		}
		return list;
	}

	public void setMany(List<One2One> many) {
		this.many = many;
	}

}