package com.dyl.es.sample;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import com.dyl.es.util.ESUtils;
/**
 * 向ES添加索引对象
 * @author dyl
 */
public class IndexTest {
	
			static Settings settings = ImmutableSettings.settingsBuilder()  
            //指定集群名称  
            .put("cluster.name", "dengyinlei")  
            //探测集群中机器状态  
            .put("client.transport.sniff", true).build();  
		    /* 
		     * 创建客户端，所有的操作都由客户端开始，这个就好像是JDBC的Connection对象 
		     * 用完记得要关闭 
		     */  
		    static Client client = new TransportClient(settings)  
		    .addTransportAddress(new InetSocketTransportAddress("192.168.0.113", 9300)); 
	
	public static void main(String[] args) {
		 
	    curd(); 
	}




	private static void curd() {
		String json = ESUtils.objectToJson(new LogModel());
	    System.out.println(json);
	    //在这里创建我们要索引的对象  
	    IndexResponse response = client.prepareIndex("twitter", "tweet")  
	            //必须为对象单独指定ID  
	            .setId("1")  
	            .setSource(json)  
	            .execute()  
	            .actionGet(); 
	    //在这里创建我们要索引的对象删除  
        DeleteResponse response1 = client.prepareDelete("twitter", "tweet", "1")  
                .execute().actionGet();  
        //在这里创建我们要索引的对象  查询
        GetResponse response3 = client.prepareGet("twitter", "tweet", "1")  
                .execute().actionGet();  
	    //多次index这个版本号会变  
	System.out.println("response.version():"+response.getVersion());  
	client.close();
	}
	
	
	
	
	//上面只是批量索引的方法，将client.pre	pareIndex改为client.prepareDelete就是批量删除操作
	//。ES对批量操作作了优化，所以大家使用时，尽量将操作集中起来调用批量接口，速度会更快一些。
	 public static void bulkInsert()
	 {
		 
		 BulkRequestBuilder bulkRequest = client.prepareBulk();  
	        for(int i=500;i<1000;i++){  
	            //业务对象  
	            String json = ESUtils.objectToJson(new LogModel());  
	            IndexRequestBuilder indexRequest = client.prepareIndex("twitter", "tweet")  
	            //指定不重复的ID        
	            .setSource(json).setId(String.valueOf(i));  
	            //添加到builder中  
	            bulkRequest.add(indexRequest);  
	        }  
	          
	        BulkResponse bulkResponse = bulkRequest.execute().actionGet();  
	        if (bulkResponse.hasFailures()) {  
	            System.out.println(bulkResponse.buildFailureMessage());  
	        }  
	 
	 }
	// 搜索有两种方法，一种是使用Filter进行搜索，一种是使用Query进行搜索，例如，想只搜索某个字段为具体值的数据，也可以这样写。 
	 public static void filterSearch()
	 {
		 SearchResponse response = client.prepareSearch("twitter")  
				//这个在prepareSearch中指定还不行，必须使用setTypes  
				                .setTypes("tweet")  
				                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				                .setQuery(QueryBuilders.termQuery("systemName", "oa"))
				                //设置查询条件,  
				                .setPostFilter(FilterBuilders.matchAllFilter())  
				                .setFrom(0).setSize(1000).setExplain(true)  
				                .execute()  
				                .actionGet();  
				        /** 
				         * SearchHits是SearchHit的复数形式，表示这个是一个列表 
				         */  
				        SearchHits shs = response.getHits();  
				        for(SearchHit hit : shs){  
				            System.out.println("id:"+hit.getId()+":"+hit.getSourceAsString());  
				        }  
				        client.close();  
				
	 }
    public static void querySearch()
    {
    	  /** 
         * 创建查询条件，QueryBuilders相当于Hibernate的Restrictions， 
         * 而QueryBuilder则相当于一个Criteria,可以不停的增加本身对象 
         */  
        BoolQueryBuilder query = QueryBuilders.boolQuery();  
                 //systemName为字段名称，oa未字段值  
        query.must(QueryBuilders.termQuery("systemName", "oa"));  
        SearchResponse response = client.prepareSearch("twitter")  
        		//这个在prepareSearch中指定还不行，必须使用setTypes  
                .setTypes("tweet")  
                //设置查询条件,  
                .setQuery(query)  
                .setFrom(0).setSize(60)  
                .execute()  
                .actionGet();  
        /** 
         * SearchHits是SearchHit的复数形式，表示这个是一个列表 
         */  
        SearchHits shs = response.getHits();  
        for(SearchHit hit : shs){  
            System.out.println(hit.getSourceAsString());  
        }  
        client.close();  
    }
}
