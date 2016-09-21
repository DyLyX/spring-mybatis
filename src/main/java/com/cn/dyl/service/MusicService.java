package com.cn.dyl.service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Order;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.facet.FacetBuilder;
import org.elasticsearch.search.facet.FacetBuilders;
import org.elasticsearch.search.facet.terms.TermsFacet;
import org.elasticsearch.search.facet.terms.TermsFacet.ComparatorType;
import org.elasticsearch.search.facet.terms.TermsFacet.Entry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.mapper.JsonMapper;

import com.cn.dyl.parser.wangyiyun.entity.Singer;
import com.cn.dyl.parser.wangyiyun.entity.response.StatEntry;
import com.cn.dyl.pojo.Person;
import com.cn.dyl.util.ESManager;
import com.cn.dyl.util.Page;
import com.dyl.es.sample.IndexType;
import com.fasterxml.jackson.databind.JavaType;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Component
public class MusicService {
	
	private static String[] dateFormats = new String[] { "yyyy-MM-dd" };

	private static final Logger logger = LoggerFactory.getLogger(MusicService.class);
	
	private static JsonMapper mapper = JsonMapper.nonEmptyMapper();
	private final String MESSGE = "messge";
	private final String STATE = "state";
	private final String DATA = "data";
	@Autowired
	@Qualifier("ESManager")
	private ESManager ESManager;
	@Autowired
	private SingerService singerService;
	/**根据es中的属性分组查询 根据某个属性统计数量
	 * 当 size<=0 时返回所有的结果
	 * @param size
	 * @param fieldName 需要分组的属性 artistId或者artistsName
	 * @param params 查询过滤条件
	 * @return
	 */
	public 	List<StatEntry> statArtist(int size,String fieldName,List<String> params){
		 Map<String ,Singer> singers = singerService.getSingerSingIDData();
		 boolean all = size<=0;
		 size = all ? singers.size() : size;
		 SearchSourceBuilder ssb = new SearchSourceBuilder();
	     BoolQueryBuilder bqb = QueryBuilders.boolQuery();
         if (params != null && params.size() > 0) {
            QueryBuilder qbm = QueryBuilders.termsQuery(fieldName, params);
            bqb.must(qbm);
         }
		 //es的聚合相当于数据库的groud by 分组的概念 ，使用 AggregationBuilders 聚合出来的count数量是大于0的，
		TermsBuilder ag = AggregationBuilders.terms(fieldName).field(fieldName).size(size).order(Order.count(false));
		ssb.query(bqb).aggregation(ag);
		SearchResponse response = ESManager.search(ssb, IndexType.musicInfo, SearchType.COUNT);//只统计数量
		Terms terms = response.getAggregations().get(fieldName);
		//es中过时的聚合方式，会统计出包含零的
	/*	FacetBuilder market = FacetBuilders.termsFacet("marketMd5").field("marketMd5")
				.allTerms(true).size(size).order(ComparatorType.COUNT);
		ssb.facet(market);
		SearchResponse response = esManager.search(ssb, IndexType.appinfo, SearchType.COUNT);
		List<? extends Entry> entries = response.getFacets().facet(TermsFacet.class, "marketMd5").getEntries();
		Map<String,Integer> map = new HashMap<String,Integer>();
		for(Entry en : entries){
			map.put(en.getTerm().string(), en.getCount());
		}*/
		Map<String,Long> map = new HashMap<String,Long>();
		for(Terms.Bucket bk : terms.getBuckets()){
			map.put(bk.getKey(), bk.getDocCount());
		}
		List<StatEntry> sl = new ArrayList<StatEntry>();
		for(java.util.Map.Entry<String, Singer> m : singers.entrySet()){
			int n = 0;
			if(map.containsKey(m.getKey())){
				n = map.get(m.getKey()).intValue();
			}
			if(n>0 ||  all){
				StatEntry e = new StatEntry();
				e.setKey(m.getKey());
				e.setName(m.getValue().getName());
				e.setValue(n);
				sl.add(e);
			}
		}
		Collections.sort(sl, new StatComparator());
		if(sl.size()>size && size>0){
			sl = sl.subList(0, size);
		}
		return sl;
		
		
	}
	/*倒序，有大到小根据统计的数量由大到小排序*/
	class StatComparator implements Comparator<StatEntry> {
		@Override
		public int compare(StatEntry o1, StatEntry o2) {
			return o2.getValue() > o1.getValue() ? 1 : -1;
		}
	}
	//对于使用聚合查询出来的数据不能使用传统的ssb.from(start);ssb.size(limit);
	//方法取出，要先取出排序号的总数，然后根据分页参数，用list的subList()方法截取。
	public Page statAggregationData(Map<String,Object> params){
		int currentPage = Integer.valueOf(params.get("currentPage").toString());
		int limit = Integer.valueOf(params.get("limit").toString());
		int start = Integer.valueOf(params.get("start").toString());
		//选取出从es中查询出的聚合总数据
		List<StatEntry> datas =Lists.newArrayList();
		Page page = new Page(currentPage, limit);
		page.setResultCount(datas.size());
		if(datas.size()>=start+limit){
			datas = datas.subList(start, start+limit);
		}else if(datas.size()>start && datas.size()<start+limit){
			datas = datas.subList(start, datas.size());
		}else if(datas.size()<=start){
			datas = Lists.newArrayList();
		}
		page.setData(datas);
		return page;
		
	}
	@RequestMapping(value = "stat/InfoMusicDetail")
	@ResponseBody
	public String InfoAppDetails(HttpServletRequest request){
		boolean state = true;
		String messge = "";
		Page page = null;
		try{
			//把前台传过来的查询条件封装成map传到es中查询。
			Map<String, Object> param = buidlerQuery(request);
			page = statPageData(param);
		}catch(Exception e){
			logger.error("查询异常！",e);
			state = false;
			messge +="查询异常！";
		}
		if(page==null){
			page = new Page(1,10);
		}
		Map<String,Object> resultMap = Maps.newHashMap();
		resultMap.put(DATA, page);
		resultMap.put(MESSGE, messge);
		resultMap.put(STATE, state);
		return mapper.toJson(resultMap);
	}
	
	/**
	 * 分页查询
	 */
	public Page statPageData(Map<String,Object> param){
		int currentPage = Integer.valueOf(param.get("currentPage").toString());
		int limit = Integer.valueOf(param.get("limit").toString());
		int start = Integer.valueOf(param.get("start").toString());
		String authorName = "";
		if(param.get("name")!=null){
			authorName = param.get("name").toString();
		}
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		SearchSourceBuilder ssb = new SearchSourceBuilder();
		BoolQueryBuilder bqb = QueryBuilders.boolQuery();
		bqb.must(QueryBuilders.termQuery("name", authorName));
		ssb.query(bqb);
		ssb.from(start);
		ssb.size(limit);
		SearchResponse response = ESManager.search(ssb, IndexType.musicInfo,SearchType.DEFAULT);
		long size =0l;
		if (response.getHits().totalHits() > 0) {
			for (SearchHit searchHit:response.getHits()) {
				result.add(searchHit.getSource());
			}
			size = response.getHits().totalHits();
		}
		Page page = new Page(currentPage, limit);
		page.setData(result);
		page.setResultCount(size);
		return page;
	}
	
	private HashMap<String, Object> getValue(Object orgJsonStr){
		//序列化复杂的list对象，List集合的元素为map
		JavaType javaType = mapper.createCollectionType(List.class, Map.class);
		List<Map<String, Object>> list = mapper.fromJson(orgJsonStr.toString(), javaType);
		for(Map<String , Object> m :list){
			//序列话简单mapMap<String, String>
			Map<String, String> sm = (Map<String, String>) mapper.fromJson(orgJsonStr.toString(), Map.class);
			Object sObj = m.get("summary");
			if (sObj != null) {
				Map<String, String> smm = (Map<String, String>) mapper.fromJson(
						sObj.toString(), Map.class);
				if (smm == null) {
					return new HashMap<>();
				}
			
		}
		}
		//序列化复杂的map对象* HashMap<String,MyBean>, 则调用(HashMap.class,String.class, MyBean.class)
		JavaType javaType1 = mapper.createCollectionType(HashMap.class,String.class,Person.class);
		Map<String, Person> sm1 = (Map<String, Person>) mapper.fromJson(orgJsonStr.toString(), javaType1);
		
		
		return null;
	}
	
	/**
	 * 构建 list 方法查询参数
	 * @param request
	 * @return
	 */
	private Map<String,Object> buidlerQuery(HttpServletRequest request){
		Map<String,Object> param = Maps.newHashMap();
		try{
			String limit = request.getParameter("limit");
			String currentPage = request.getParameter("currentPage");
			String name = request.getParameter("name");
			if(StringUtils.isNotBlank(name)){
				param.put("name", name.trim());
			}
			int size = 10;
			int currenPage = 1;
			if(StringUtils.isNotBlank(limit) || StringUtils.isNotBlank(currentPage)){
				int tempPage = Integer.valueOf(currentPage.trim());
				int tempLimit = Integer.valueOf(limit.trim());
				if(tempPage > 0){
					currenPage = tempPage;
				}
				if(tempLimit > 0){
					size = tempLimit;
				}
			}
			Page page = new Page(currenPage, size);//根据当前页以及每页显示的大小算出起始查询位置start
			param.put("limit", page.getLimit());
			param.put("start", page.getStart());
			param.put("currentPage", currenPage);
		}catch(Exception e){
			logger.error("构建 list 方法查询参数异常！",e);
		}
		return param;
	}
}
