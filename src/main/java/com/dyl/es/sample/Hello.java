package com.dyl.es.sample;
import static org.elasticsearch.common.xcontent.XContentFactory.*;

import java.io.IOException;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;

public class Hello {
	
	static Client client=null;
	
	static XContentBuilder builder=null;
	
	public void init()
	{
		client=new TransportClient().addTransportAddress(new InetSocketTransportAddress("localhost", 9200));
		//client=new TransportClient().addTransportAddress(new InetSocketTransportAddress("172.31.29.244", 9200));
	}
	public void close()
	{
		client.close();
	}
	/**
	 * 对象转换成json格式
	 * @param user
	 * @return
	 */
	public static String generateJson(User user)
	{
		String json="";
		
		 try {
			builder=jsonBuilder()
					 .startObject()
				        .field("id", user.getId()+"")
				        .field("name", user.getName())
				        .array("age", user.getAge()+"")
				    .endObject();
			json=builder.string();
		} catch (IOException e) {
			e.printStackTrace();
		}
		 
		
		 return json;
		 
	}
	
	/**
	 * create Index with type and id which is auto generating by uuids
	 */
	public void createIndex()
	{
	//	for(int i=0;i<1000;i++)
		//{
			User user=new User();
			user.setId(new Long(1));
			user.setName("huang fox");
			user.setAge(20%1000);
			client.prepareIndex("users", "user", "1").setSource(generateJson(user))
			.get();
		}
	
	public void search() {
        SearchResponse response = client.prepareSearch("users")
                .setTypes("user").get();
               // .setSearchType(org.elasticsearch.action.search.SearchType.DFS_QUERY_THEN_FETCH)
              //  .setQuery(QueryBuilders.termQuery("name", "fox")) // Query
              //  .setPostFilter(FilterBuilders.rangeFilter("age").from(20).to(30)) // Filter
             //   .setFrom(0).setSize(20).setExplain(true).execute().actionGet();
        SearchHits hits = response.getHits();
        System.out.println(hits.getTotalHits());
        for (int i = 0; i < hits.getHits().length; i++) {
            System.out.println(hits.getHits()[i].getSourceAsString());
        }
    }
	
	public static void main(String[] args) throws Exception {
		
		
		Hello hello=new Hello();
		hello.init();
		hello.createIndex();
		hello.search();
		hello.close();
		
		
		
		
	/**
	 * Using built-in helpers XContentFactory.jsonBuilder()
	 */
//			String[] valeus={"hello","dfjaslkj","jhgkla"};
//			
//			 builder=jsonBuilder()
//						 .startObject()
//					        .field("user", "kimchy")
//					        .field("postDate", new Date())
//					        .array("message", valeus)
//					    .endObject();
//			
//			 IndexResponse response = client.prepareIndex("twitter", "tweet", "1")
//				        .setSource(jsonBuilder()
//				                    .startObject()
//				                        .field("user", "kimchy")
//				                        .field("postDate", new Date())
//				                        .field("message", "trying out Elasticsearch")
//				                    .endObject()
//				                  )
//				        .get();
//			 
//			 /**
//			  * Note that you can also index your documents as JSON String and that you don’t have to give an ID
//			  */
//			 String json = "{" +
//				        "\"user\":\"kimchy\"," +
//				        "\"postDate\":\"2013-01-30\"," +
//				        "\"message\":\"trying out Elasticsearch\"" +
//				    "}";
//
//				IndexResponse response1 = client.prepareIndex("twitter", "tweet")
//				        .setSource(json)
//				        .get();
//		IndexResponse object will give you a report:

//		// Index name
//		String _index = response.getIndex();
//		// Type name
//		String _type = response.getType();
//		// Document ID (generated or not)
//		String _id = response.getId();
//		// Version (if it's the first time you index this document, you will get: 1)
//		long _version = response.getVersion();
//		// isCreated() is true if the document is a new one, false if it has been updated
//		boolean created = response.isCreated();	
//			System.out.println(builder.string());
	}

}
