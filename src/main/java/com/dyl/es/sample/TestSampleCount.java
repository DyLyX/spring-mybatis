package com.dyl.es.sample;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.slf4j.LoggerFactory;

public class TestSampleCount {
	private final static org.slf4j.Logger logger=LoggerFactory.getLogger(TestSampleCount.class);
	private final static int SEARCH_SIZE = 10000;
	private final static long KEEP_ALIVE = 180000l;
	static Settings settings = ImmutableSettings.settingsBuilder()  
            //指定集群名称  
            .put("cluster.name", "pscms_dev")  
            //探测集群中机器状态  
            .put("client.transport.sniff", true).build();  
		    /* 
		     * 创建客户端，所有的操作都由客户端开始，这个就好像是JDBC的Connection对象 
		     * 用完记得要关闭 
		     */  
		    static Client client = new TransportClient(settings)  
		    .addTransportAddress(new InetSocketTransportAddress("172.31.29.245", 9300));
		    public static void main(String[] args) {
		    	FilterBuilder missing = FilterBuilders.existsFilter("fileMD5");
		        FilterBuilder exist = FilterBuilders.boolFilter().mustNot(FilterBuilders.prefixFilter("fileMD5", "#"));
		        Map<String, SearchHit> distinctObjects = new HashMap<String,SearchHit>();
		        SearchResponse response = client.prepareSearch("app")
		    			.setTypes("appinfo")
		    			.setSearchType(SearchType.SCAN)
		    			.setScroll(new TimeValue(KEEP_ALIVE))
				         .setQuery(QueryBuilders.termQuery("marketMd5", "1efd091c559d4b8ca7e329ca13682e70"))
				                //设置查询条件,  
				         .setPostFilter(FilterBuilders.andFilter(missing,exist))
				         .setSize(SEARCH_SIZE)
		    			.execute().actionGet();
		        while(true){
			    	SearchHits hits = response.getHits();
			    	Iterator<SearchHit> iterator = hits.iterator();
			    	while (iterator.hasNext()) {
			    	    SearchHit searchHit = (SearchHit) iterator.next();
			    	    Map<String, Object> source = searchHit.getSource();
			    	    if(source.get("fileMD5") != null){
			    	        distinctObjects.put(source.get("fileMD5").toString(),searchHit);
			    	    	}
			    		}
			    	response=client.prepareSearchScroll(response.getScrollId()).setScroll(new TimeValue(KEEP_ALIVE))
			    			.execute()
			    			.actionGet();
			    	
			    	if(response.getHits().getHits().length==0)
			    	{
			    	  break;
			    	}
		    	
		        }
		        System.out.println("该市场去重后的样本数量为"+distinctObjects.size()); 
}
}