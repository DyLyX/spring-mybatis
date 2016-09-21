package com.cn.dyl.commom;

import com.dyl.es.sample.IndexType;
import java.util.Date;
import java.util.List;

public class QueryCondition {


	private IndexType indexType = IndexType.musicInfo;	// 索引类型
	private String keyId;						//keyId
	private Date beginTime;							// 开始时间，去ES检索字段
	private Date endTime;							// 结束时间，去ES检索字段
	private String sortField = "pubDate";			// 排序字段
	private String sortType = "desc";				// 排序方式（asc,desc）
	private Integer start = 0;							// 查询起始行号
	private Integer limit = 10;							// 查询的行数
	private String[] queryFields; // 检索字段
	private boolean highlighting = true;			// 关键字是否飘红
	private Integer currentPage = 1;                   // 当前页，接收页面传参
	private String timePeriod;					    //时间段，接收页面传参的时间类型
	private String beginDate;						//开始时间String,接收页面传来的开始字段
	private String endDate;							//结束时间String,接收页面传来的结束字段         
	private String[] facetFields; // 分组字段
	private boolean isFacet; // 是否分组统计
	private String name;			// app名称
	private String author;			// 作者名
	private String keywords;                        //敏感词列表
	private String musinType;
	
	

	public String getMusinType() {
		return musinType;
	}

	public void setMusinType(String musinType) {
		this.musinType = musinType;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getFacetFields() {
		return facetFields;
	}

	public void setFacetFields(String... facetFields) {
		this.facetFields = facetFields;
	}
	
	public boolean isFacet() {
		return isFacet;
	}

	public void setFacet(boolean isFacet) {
		this.isFacet = isFacet;
	}

	public String getTimePeriod() {
		return timePeriod;
	}

	public void setTimePeriod(String timePeriod) {
		this.timePeriod = timePeriod;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public IndexType getIndexType() {
		return indexType;
	}

	public void setIndexType(IndexType indexType) {
		this.indexType = indexType;
	}

	public String getKeyId() {
		return keyId;
	}

	public void setKeyId(String newsKeyId) {
		this.keyId = newsKeyId;
	}


	


	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	
	public String[] getQueryFields() {
		return queryFields;
	}

	public void setQueryFields(String[] queryFields) {
		this.queryFields = queryFields;
	}

	public boolean isHighlighting() {
		return highlighting;
	}

	public void setHighlighting(boolean highlighting) {
		this.highlighting = highlighting;
	}
	
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
		
	
}
