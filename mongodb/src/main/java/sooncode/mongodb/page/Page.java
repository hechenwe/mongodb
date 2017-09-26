package sooncode.mongodb.page;

import java.util.LinkedList;
import java.util.List;

/**
 * 分页
 * 
 * @author hechen
 *
 * @param <T>
 */
public class Page {

	private List<Object> ones;
	private List<One2One> o2os;
	private List<One2Many<?, ?>> o2ms;
	private List<Many2Many<?, ?, ?>> m2ms;
	private List<One2Many2Many<?, ?, ?>> o2m2ms;

	/** 总记录数 */
	private Long total = 0L; // 总记录数

	/** 每页显示记录数 */
	private Long pageSize = 20L; // 默认20

	/** 总页数 */
	private Long totalPages = 1L; // 总页数

	/** 当前页 */
	private Long pageNumber = 1L; // 当前页

	/** 是否为第一页 */
	private boolean isFirstPage = false; // 是否为第一页

	/** 是否为最后一页 */
	private boolean isLastPage = false; // 是否为最后一页

	/** 是否有前一页 */
	private boolean hasPreviousPage = false; // 是否有前一页

	/** 是否有下一页 */
	private boolean hasNextPage = false; // 是否有下一页

	// --------------------------------------------- 构造器
	// ----------------------------------------------------------------

	public <L> Page(long pageNumber, long pageSize, long total) {
		init(total, pageNumber, pageSize);
		// this.ones = (List<Object>) ones;
	}

	public void initPage(long pageNumber, long pageSize, long total){
		init(total, pageNumber, pageSize);
	}
	
	
	public <L, R> Page(long pageNumber, long pageSize, long total, List<One2Many<L, R>> o2ms) {
		init(total, pageNumber, pageSize);
		List<One2Many<?, ?>> o2mes = new LinkedList<>();
		for (One2Many<?, ?> result : o2ms) {
			One2Many<?, ?> o2m = result;
			o2mes.add(o2m);
		}
		this.o2ms = o2mes;
	}

	public <L,R> void setOne2Manys (List<One2Many<L, R>> o2ms){
		List<One2Many<?, ?>> o2mes = new LinkedList<>();
		for (One2Many<?, ?> result : o2ms) {
			One2Many<?, ?> o2m = result;
			o2mes.add(o2m);
		}
		this.o2ms = o2mes;
	}
	
	
	public <L, M, R> Page(long pageNumber, long pageSize, long total, LinkedList<Many2Many<L, M, R>> m2ms) {
		init(total, pageNumber, pageSize);
		List<Many2Many<?, ?, ?>> m2mes = new LinkedList<>();
		for (Many2Many<?, ?, ?> result : m2ms) {
			Many2Many<?, ?, ?> m2m = result;
			m2mes.add(m2m);
		}
		this.m2ms = m2mes;
	}

	public <L, M, R> void setMany2Manys(List<Many2Many<L, M, R>> m2ms){
		List<Many2Many<?, ?, ?>> m2mes = new LinkedList<>();
		for (Many2Many<?, ?, ?> result : m2ms) {
			Many2Many<?, ?, ?> m2m = result;
			m2mes.add(m2m);
		}
		this.m2ms = m2mes;
	}
	
	public Page() {

	}

	/** 设置基本参数 */
	private void init(long total, long pageNumber, long pageSize) {
		// 设置基本参数
		this.total = total;
		this.pageSize = pageSize;
		if (total == 0L) {
			this.totalPages = 0L;
		} else {
			this.totalPages = (this.total - 1) / this.pageSize + 1;
		}

		// 根据输入可能错误的当前号码进行自动纠正
		if (pageNumber < 1) {
			this.pageNumber = 1L;
		} else if (pageNumber > this.totalPages) {
			this.pageNumber = this.totalPages;
		} else {
			this.pageNumber = pageNumber;
		}
		judgePageBoudary();
	}

	/**
	 * 判定页面边界
	 */
	private void judgePageBoudary() {
		isFirstPage = pageNumber == 1;
		isLastPage = pageNumber == totalPages && pageNumber != 1;
		hasPreviousPage = pageNumber > 1;
		hasNextPage = pageNumber < totalPages;
	}
	// -----------------------------------------------------get set
	// 方法-----------------------------------------------------------------------

	// --------------------------------------------------------------------------------
	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	// --------------------------------------------------------------------------------
	public Long getPageSize() {
		return pageSize;
	}

	public void setPageSize(Long pageSize) {
		this.pageSize = pageSize;
	}

	// --------------------------------------------------------------------------------
	public Long getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Long totalPages) {
		this.totalPages = totalPages;
	}

	// --------------------------------------------------------------------------------
	public Long getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Long pageNumber) {
		this.pageNumber = pageNumber;
	}

	// --------------------------------------------------------------------------------
	public boolean isFirstPage() {
		return isFirstPage;
	}

	public void setFirstPage(boolean isFirstPage) {
		this.isFirstPage = isFirstPage;
	}

	// --------------------------------------------------------------------------------
	public boolean isLastPage() {
		return isLastPage;
	}

	public void setLastPage(boolean isLastPage) {
		this.isLastPage = isLastPage;
	}

	// --------------------------------------------------------------------------------
	public boolean isHasPreviousPage() {
		return hasPreviousPage;
	}

	public void setHasPreviousPage(boolean hasPreviousPage) {
		this.hasPreviousPage = hasPreviousPage;
	}

	// --------------------------------------------------------------------------------
	public boolean isHasNextPage() {
		return hasNextPage;
	}

	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}

	@SuppressWarnings("unchecked")
	public <L> List<L> getOnes() {
		return (List<L>) ones;
	}

	public <L, R> One2Many<L, R> getOne2Many() {

		One2Many<L, R> o2m = new One2Many<>();
		List<One2Many<L, R>> o2ms = getOne2Manys();
		if (o2ms.size() == 1) {
			o2m = o2ms.get(0);
		}
		return o2m;
	}

	public <L, M, R> Many2Many<L, M, R> getMany2Many() {

		Many2Many<L, M, R> m2m = new Many2Many<>();
		List<Many2Many<L, M, R>> m2ms = getMany2Manys();
		if (m2ms.size() == 1) {
			m2m = m2ms.get(0);
		}
		return m2m;
	}

	public <L, R> List<One2Many<L, R>> getOne2Manys() {
		List<One2Many<L, R>> o2ms = new LinkedList<>();
		if (this.o2ms != null && this.o2ms.size() > 0) {
			for (One2Many<?, ?> result : this.o2ms) {
				@SuppressWarnings("unchecked")
				One2Many<L, R> o2m = (One2Many<L, R>) result;
				o2ms.add(o2m);
			}
		}
		return o2ms;
	}

	public <L, M, R> List<Many2Many<L, M, R>> getMany2Manys() {
		List<Many2Many<L, M, R>> m2ms = new LinkedList<>();
		if (this.m2ms != null && this.m2ms.size() > 0) {
			for (Many2Many<?, ?, ?> result : this.m2ms) {
				@SuppressWarnings("unchecked")
				Many2Many<L, M, R> m2m = (Many2Many<L, M, R>) result;
				m2ms.add(m2m);
			}
		}
		return m2ms;
	}

	@SuppressWarnings("unchecked")
	public <L> void setOnes(List<L> ones) {
		this.ones = (List<Object>) ones;
	}

	 

	@SuppressWarnings("unchecked")
	public <L, M, R> List<One2Many2Many<L, M, R>> getOne2Many2Manys() {
		List<One2Many2Many<L, M, R>> list = new LinkedList<>();
		if (this.o2m2ms != null && this.o2m2ms.size() > 0) {
			for (One2Many2Many<?, ?, ?> o2m2m : this.o2m2ms) {
				list.add((One2Many2Many<L, M, R>) o2m2m);
			}
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public <L, M, R> One2Many2Many<L, M, R> getOne2Many2Many() {
		List<One2Many2Many<L, M, R>> list = new LinkedList<>();
		if (this.o2m2ms != null && this.o2m2ms.size() > 0) {
			for (One2Many2Many<?, ?, ?> o2m2m : this.o2m2ms) {
				list.add((One2Many2Many<L, M, R>) o2m2m);
			}
		}
		return list.get(0);
	}

	public <L, M, R> void setOne2Many2Manys(List<One2Many2Many<L, M, R>> o2m2ms) {
		List<One2Many2Many<?, ?, ?>> list = new LinkedList<>();
		if (o2m2ms.size() > 0) {
			for (One2Many2Many<L, M, R> o2m2m : o2m2ms) {
				list.add(o2m2m);
			}
		}
		this.o2m2ms = list;
	}

	public List<One2One> getOne2One() {
		return o2os;
	}

	public void setOne2One(List<One2One> o2os) {
		this.o2os = o2os;
	}

	
	
}