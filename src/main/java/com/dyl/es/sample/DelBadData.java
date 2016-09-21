package com.dyl.es.sample;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.projectx.elasticsearch.ClientCallback;
import org.projectx.elasticsearch.ClientTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DelBadData {
	private final static Logger logger = LoggerFactory.getLogger(DelBadData.class);
	//private static JsonMapper mapper = JsonMapper.nonEmptyMapper();
	private final static int SEARCH_SIZE = 200;
	private final static long KEEP_ALIVE = 180000l;
	private final static String ORDER_FIELD = "createTime";
	private final static String indexType = "appinfo";
    private ESManager ESManager;
    private ClientTemplate clientTemplate;
    private static Map<String,String> keySet = new HashMap<String,String>(); 

    public DelBadData(List<InetSocketAddress> esAdds) {
        initES(esAdds);
    }

    private void initES(List<InetSocketAddress> esAdds) {
        ImmutableSettings.Builder builder = ImmutableSettings.settingsBuilder();
        builder.put("cluster.name", "pscms_dev");
        TransportClient client = new TransportClient(builder);
        for (InetSocketAddress inetSocketAddress : esAdds) {
            client.addTransportAddress(new InetSocketTransportAddress(inetSocketAddress));
        }
        clientTemplate = new ClientTemplate(client, "app");
        ESManager = new ESManager();
        ESManager.setClientTemplate(new ClientTemplate(client, "app"));
    }

    public void doSync() {
    	SearchSourceBuilder ssb = new SearchSourceBuilder();
    	BoolQueryBuilder bqb = QueryBuilders.boolQuery();

        QueryBuilder qbm = QueryBuilders.termsQuery("marketMd5", "e8726c3af0a09301c1505b279969d2bc");
        bqb.must(qbm);
        
//        RangeQueryBuilder rqb = QueryBuilders.rangeQuery("pubDate");
//		rqb = rqb.to("2000-01-01");
//		bqb.must(rqb);
    	
    	QueryBuilder qbm1 = QueryBuilders.wildcardQuery("docId", "*_1");
        bqb.must(qbm1);
        
//        BoolQueryBuilder hasResult = QueryBuilders.boolQuery();
//        hasResult.should(QueryBuilders.queryString("\"type:1\"").field("virus_summary"));
//        hasResult.should(QueryBuilders.queryString("\"type:4\"").field("virus_summary"));
//        hasResult.should(QueryBuilders.queryString("\"type:5\"").field("virus_summary"));
//        hasResult.should(QueryBuilders.queryString("\"type:7\"").field("virus_summary"));
//        bqb.must(hasResult);
//        
		
		
		FilterBuilder missing = FilterBuilders.missingFilter("fileMD5").nullValue(true).existence(true);
//	    FilterBuilder success = FilterBuilders.boolFilter().must(FilterBuilders.prefixFilter("pubDate", "0001"));
//	    ssb.query(bqb).size(10000).postFilter(FilterBuilders.andFilter(missing, success));
		ssb.query(bqb).size(50000).postFilter(missing);
//		TermsBuilder ag3 = AggregationBuilders.terms("marketAgg").field("market");
//		TermsBuilder ag1 = AggregationBuilders.terms("nameAgg").minDocCount(2).field("name-full").size(1000);
//		TermsBuilder ag2 = AggregationBuilders.terms("docIdAgg").minDocCount(2).field("docId").size(1000);
//		TermsBuilder ag4 = AggregationBuilders.terms("versionAgg").minDocCount(2).field("version").size(1000);
//		TermsBuilder ag5 = AggregationBuilders.terms("sizeAgg").minDocCount(2).field("size").size(1000);
//		ag4.subAggregation(ag5);
//		ag2.subAggregation(ag4);
//		ag1.subAggregation(ag2);
//		ag3.subAggregation(ag1);
//        ssb.aggregation(ag1);
	    int i = 0;
        SearchResponse response = ESManager.search(ssb, IndexType.userInfo, SearchType.DEFAULT);
        int length = response.getHits().getHits().length;
        if (length > 0){
            for(SearchHit hit : response.getHits()){
            	ESManager.delete(IndexType.userInfo, hit.id());
            	i++;
            }
        }
        System.out.println("===============共删除"+i+"条数据");
//        Terms terms = response.getAggregations().get("nameAgg");
//        for (Terms.Bucket bk : terms.getBuckets()) {
//            if (bk.getDocCount() > 1) {
//                String name = bk.getKey();
//                Terms terms1 = bk.getAggregations().get("docIdAgg");
//                for (Terms.Bucket bk1 : terms1.getBuckets()) {
//                	if (bk1.getDocCount() > 1) {
//                		String docId = bk1.getKey();
//                        Terms terms2 = bk1.getAggregations().get("versionAgg");
//                        for (Terms.Bucket bk2 : terms2.getBuckets()) {
//                        	if (bk2.getDocCount() > 1) {
//                        		String version = bk2.getKey();
//                                Terms terms3 = bk2.getAggregations().get("sizeAgg");
//                                for (Terms.Bucket bk3 : terms3.getBuckets()) {
//                                	if (bk3.getDocCount() > 1) {
//                                		String size = bk3.getKey();
//                                		System.out.println("命中有重复的数据，重复数量："+bk3.getDocCount()+"，name："+name+"，docId："+docId+"，version："+version+"，size："+size);
//                                	}
//                                }
//                        	}
//                        }
//                	}
//                }
//            }
//        }
    }

    public static void main(String[] args) {
        List<InetSocketAddress> esAdds = new ArrayList<InetSocketAddress>();
        if (args.length > 0) {
            for (int i = 0; i < args.length/2; i++) {
                if ("-e".equals(args[i*2]) || "-es".equals(args[i*2])) {
                    String[] esHosts = args[i*2 + 1].split(",");
                    for (String host : esHosts) {
                        esAdds.add(formatSocketAddress(host, 9300));
                    }
                }
            }
        }
        
        if (esAdds.size() == 0) {
//            esAdds.add(new InetSocketAddress("172.31.29.243", 9300));
//            esAdds.add(new InetSocketAddress("172.31.29.244", 9300));
//            esAdds.add(new InetSocketAddress("172.31.29.245", 9300));
            
            esAdds.add(new InetSocketAddress("192.168.49.247", 9300));
            esAdds.add(new InetSocketAddress("192.168.49.244", 9300));
            esAdds.add(new InetSocketAddress("192.168.49.243", 9300));
            esAdds.add(new InetSocketAddress("192.168.49.242", 9300));
            esAdds.add(new InetSocketAddress("192.168.49.245", 9300));
            esAdds.add(new InetSocketAddress("192.168.49.246", 9300));
        }
        
        DelBadData syncESforNewStructure = new DelBadData(esAdds);
        syncESforNewStructure.doSync();
        
    }
    
    private String getLatestTime(){
    	  SearchSourceBuilder ssb1 = new SearchSourceBuilder().query(QueryBuilders.matchAllQuery());
          SearchResponse sr1 = ESManager.search(ssb1, IndexType.userInfo, SearchType.DEFAULT);
          if(sr1.getHits().getHits().length>0){
        	  SearchSourceBuilder ssb = new SearchSourceBuilder().query(QueryBuilders.matchAllQuery()).size(SEARCH_SIZE).sort("createTime", SortOrder.ASC);
              SearchResponse sr = ESManager.search(ssb, IndexType.userInfo, SearchType.DEFAULT);
        	  SearchHit hit = sr.getHits().getAt(0);
        	  Map<String, Object> map = hit.sourceAsMap();
        	  return map.get("createTime").toString();
          }
          return "";
    }
    
    private static InetSocketAddress formatSocketAddress(String host, int defaultPort) {
        if (host == null || host.trim().length() == 0) {
            return null;
        }
        
        int index = host.lastIndexOf(":");
        if (index > 0) {
            int port = Integer.parseInt(host.substring(index+1));
            return new InetSocketAddress(host.substring(0, index), port);
        } else {
            return new InetSocketAddress(host, defaultPort);
        }
    }
    
    private Map<String, Object> transfer(Map<String, Object> web){
		if(web!=null){
//			if(web.get("version_name")!=null){
//				web.put("version", web.get("version_name"));
//				web.remove("version_name");
//			}
			if(web.get("downloadUrl")!=null && web.get("fileMD5")!=null){
				String fileMD5 = keySet.get(web.get("downloadUrl").toString());
				if(fileMD5 != null){
					web.put("fileMD5", fileMD5);
					System.out.println("downloadUrl:"+web.get("downloadUrl").toString()+"命中fileMD5："+fileMD5);
				}
			}
			web.put("scanTime", web.get("updateTime"));
//			if(web.get("fileMD5") != null){
//				Map<String, Object> apkInfo = (Map<String, Object>) web.get("apkinfo");
//				if(apkInfo!=null){
//					web.put("package", apkInfo.get("package"));
//					web.put("version_code", apkInfo.get("version_code"));
//					web.put("version", apkInfo.get("version_name"));
//					Map<String, Object> certInfo = DataUtils.getMap(apkInfo, "certInfo");
//					web.put("certInfo", mapper.toJson(certInfo));
//					List<Map<String, Object>> permissions = DataUtils.getList(apkInfo,"permission");	
//					web.put("permission", mapper.toJson(permissions));
//					web.remove("apkinfo");
//				}
//				if(web.get("behavior")!=null){
//					List<Map<String, Object>> behaviors = DataUtils.getList(web,"behavior");
//					web.put("behavior", mapper.toJson(behaviors));
//				}
//				if(web.get("virus_summary")!=null){
//					web.put("virus_summary", web.get("virus_summary").toString());
//				}
//				web.put("ad", value);
//				web.put("senseword", value);
//			}
			return web;
		}
		return null;
	}
	
    private SearchResponse initScroll(String first){
		QueryBuilder query = QueryBuilders.matchAllQuery();
		if(StringUtils.isNotBlank(first)){
			query = QueryBuilders.rangeQuery(ORDER_FIELD).from(first);
		}
//        FilterBuilder exist = FilterBuilders.existsFilter("fileMD5");
//        FilterBuilder success = FilterBuilders.boolFilter().mustNot(FilterBuilders.prefixFilter("fileMD5", "#"));
//		.postFilter(FilterBuilders.andFilter(exist, success))
		final SearchSourceBuilder ssb = new SearchSourceBuilder().query(query)
				.sort(ORDER_FIELD, SortOrder.ASC).size(SEARCH_SIZE);
		return clientTemplate.executeGet(new ClientCallback<SearchResponse>() {
			public ActionFuture<SearchResponse> execute(Client client) {
				SearchRequest request = Requests.searchRequest(clientTemplate.getIndexName())
						.types(indexType).searchType(SearchType.SCAN)
						.scroll(new TimeValue(KEEP_ALIVE)).source(ssb);
				return client.search(request);
			}
		});
	}
	
	private SearchResponse scrollSearch(final String scrollId){
		return clientTemplate.executeGet(new ClientCallback<SearchResponse>() {
			public ActionFuture<SearchResponse> execute(Client client) {
				SearchScrollRequest request = Requests.searchScrollRequest(scrollId).scroll(new TimeValue(KEEP_ALIVE));
				return client.searchScroll(request);
			}
		});
	}
    
}
