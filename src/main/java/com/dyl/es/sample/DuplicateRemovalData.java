package com.dyl.es.sample;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.projectx.elasticsearch.ClientCallback;
import org.projectx.elasticsearch.ClientTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 根据app的fileMD5去重
 * @author dengyinlei
 * @since:2016-03-29
 */
public class DuplicateRemovalData {
	private final static Logger logger = LoggerFactory
			.getLogger(DuplicateRemovalData.class);
	private final static int SEARCH_SIZE = 500;
	private final static long KEEP_ALIVE = 180000l;
	private final static String ORDER_FIELD = "createTime";
	private final static String indexType = "appinfo";
	private ESManager ESManager;
	private ClientTemplate clientTemplate;

	public static void main(String[] args) {
		List<InetSocketAddress> esAdds = new ArrayList<InetSocketAddress>();
		if (esAdds.size() == 0) {
			// esAdds.add(new InetSocketAddress("172.31.29.245", 9300));

			esAdds.add(new InetSocketAddress("192.168.49.247", 9300));
			esAdds.add(new InetSocketAddress("192.168.49.244", 9300));
			esAdds.add(new InetSocketAddress("192.168.49.243", 9300));
			esAdds.add(new InetSocketAddress("192.168.49.242", 9300));
			esAdds.add(new InetSocketAddress("192.168.49.245", 9300));
			esAdds.add(new InetSocketAddress("192.168.49.246", 9300));

		}
		DuplicateRemovalData dr = new DuplicateRemovalData(esAdds);
		if (args[0] != null) {
			dr.doDuplicateRemoval(args[0]);
		}

	}

	public DuplicateRemovalData(List<InetSocketAddress> esAdds) {
		initES(esAdds);
	}

	private void initES(List<InetSocketAddress> esAdds) {
		ImmutableSettings.Builder builder = ImmutableSettings.settingsBuilder();
		//集群名称
		builder.put("cluster.name", "pscms_dev");
		TransportClient client = new TransportClient(builder);
		for (InetSocketAddress inetSocketAddress : esAdds) {
			client.addTransportAddress(new InetSocketTransportAddress(
					inetSocketAddress));
		}
		//索引名称，初始化clientTemplate，ESManager
		clientTemplate = new ClientTemplate(client, "app");
		ESManager = new ESManager();
		ESManager.setClientTemplate(new ClientTemplate(client, "app"));
	}

	public void doDuplicateRemoval(String marketId) {
		SearchResponse sr = this.initScroll(marketId);
		System.out.println("去重前：" + sr.getHits().totalHits());
		Map<String, Integer> distinctObjects = new HashMap<String, Integer>();
		while (true) {
			sr = this.scrollSearch(sr.getScrollId());
			if (sr.getHits().hits().length == 0)
				break;
			SearchHits hits = sr.getHits();
			Iterator<SearchHit> iterator = hits.iterator();
			while (iterator.hasNext()) {
				SearchHit searchHit = (SearchHit) iterator.next();
				Map<String, Object> source = searchHit.getSource();
				if (source.get("fileMD5") != null) {
					if(distinctObjects.get(source.get("fileMD5").toString()) == null){
						distinctObjects.put(source.get("fileMD5").toString(),
								1);
					}else{
						int count = distinctObjects.get(source.get("fileMD5").toString());
						count++;
						distinctObjects.put(source.get("fileMD5").toString(),
								count);
					}
				}
			}
		}
		System.out.println("该市场去重后的样本数量为" + distinctObjects.size());
		Set<String> setStr = distinctObjects.keySet();
		for(String set:setStr){
			if(distinctObjects.get(set) > 1){
				System.out.println("===fileMD5:"+set+"======count:" + distinctObjects.get(set));
			}
		}
	}

	private SearchResponse initScroll(String marketId) {
		QueryBuilder qbm = QueryBuilders.termsQuery("marketMd5", marketId);
		FilterBuilder missing = FilterBuilders.existsFilter("fileMD5");
		FilterBuilder exist = FilterBuilders.boolFilter().mustNot(
				FilterBuilders.prefixFilter("fileMD5", "#"));
		final SearchSourceBuilder ssb = new SearchSourceBuilder().query(qbm)
				.sort(ORDER_FIELD, SortOrder.ASC).size(SEARCH_SIZE)
				.postFilter(FilterBuilders.andFilter(missing, exist));
		return clientTemplate.executeGet(new ClientCallback<SearchResponse>() {
			public ActionFuture<SearchResponse> execute(Client client) {
				SearchRequest request = Requests
						.searchRequest(clientTemplate.getIndexName())
						.types(indexType).searchType(SearchType.SCAN)
						.scroll(new TimeValue(KEEP_ALIVE)).source(ssb);
				return client.search(request);
			}
		});
	}
	private SearchResponse scrollSearch(final String scrollId) {
		return clientTemplate.executeGet(new ClientCallback<SearchResponse>() {
			public ActionFuture<SearchResponse> execute(Client client) {
				SearchScrollRequest request = Requests.searchScrollRequest(
						scrollId).scroll(new TimeValue(KEEP_ALIVE));
				return client.searchScroll(request);
			}
		});
	}
}
