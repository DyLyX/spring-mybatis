package com.cn.dyl.util;

import java.util.List;

/**
 * 分页
 * @author dyl
 *
 */
public class Page {
	private int currentPage = 1; // 当前页

	private int pageCount; // 总页数

	private long resultCount; // 总条数
	
	private int start; // 分页参数

	private int limit = 10; // 每页显示条数

	private List<?> data; // 每页显示信息
	
	private int previousPage; // 上一页

	private int nextPage; // 下一页
	
	private double useTime; // 检索用时
	
	public Page(int currentPage, int limit){
		if(currentPage > 0){
			this.currentPage = currentPage;
		}
		if(limit > 0){
			this.limit = limit;
		}
		this.start = (this.currentPage-1) * this.limit;
	}
	
	public long getResultCount() {
		return resultCount;
	}
	
	public void setResultCount(long resultCount) {
		if(resultCount <= 0){
			this.resultCount = 0;
			this.pageCount = 0;
			this.currentPage = 0;
			this.previousPage = 0;
			this.nextPage = 0;
		}else{
			this.resultCount = resultCount;
			this.pageCount = (int)Math.ceil((double)this.resultCount/this.limit);
			this.currentPage = this.currentPage>=this.pageCount?this.pageCount:this.currentPage;
			this.previousPage = this.currentPage<=1?this.currentPage:(this.currentPage-1);
			this.nextPage = this.currentPage>=this.pageCount?this.pageCount:(this.currentPage+1);
		}
	}
	
	public double getUseTime() {
		return useTime;
	}

	public void setUseTime(long millisecond) {
		this.useTime = (double) millisecond / 1000;
	}

	public List<?> getData() {
		return data;
	}

	public void setData(List<?> data) {
		this.data = data;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public int getPageCount() {
		return pageCount;
	}

	public int getStart() {
		return start;
	}

	public int getLimit() {
		return limit;
	}

	public int getPreviousPage() {
		return previousPage;
	}

	public int getNextPage() {
		return nextPage;
	}

}
