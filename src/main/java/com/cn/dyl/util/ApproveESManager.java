package com.cn.dyl.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.admin.indices.flush.FlushResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.admin.indices.optimize.OptimizeResponse;
import org.elasticsearch.action.admin.indices.refresh.RefreshResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.deletebyquery.DeleteByQueryResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.support.QuerySourceBuilder;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.Requests;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.joda.time.DateTime;
import org.projectx.elasticsearch.ClientCallback;
import org.projectx.elasticsearch.ClientTemplate;
import org.projectx.elasticsearch.NodeCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.cn.dyl.commom.QueryCondition;
import com.dyl.es.sample.IndexType;


@Component
public class ApproveESManager {

	private static final Logger logger = LoggerFactory.getLogger(ApproveESManager.class);

	@Autowired
	@Qualifier("approveClientTemplate")
	private ClientTemplate clientTemplate;

	
	/**
	 * 根据keyId查询帖子
	 * 
	 * @author ycl
	 * @date 2013-03-04
	 */
	public Map<String, Object> findDataByKeyId(IndexType type, String keyId) {
		SearchSourceBuilder ssb = new SearchSourceBuilder();
		ssb.query(QueryBuilders.idsQuery().ids(keyId)).size(1);
		SearchResponse response = search(ssb, type, SearchType.DEFAULT);
		if (response.getHits().totalHits() > 0) {
			return response.getHits().getAt(0).getSource();
		}
		return null;
	}

	/**
	 * 根据keyId查询帖子
	 * 
	 * @author ycl
	 * @date 2013-03-04
	 */
	public List<Map<String, Object>> findDataByKeyIds(IndexType type,
			String... keyId) {
		List<Map<String, Object>> result = null;
		SearchSourceBuilder ssb = new SearchSourceBuilder();
		ssb.query(QueryBuilders.idsQuery().ids(keyId)).size(keyId.length);
		SearchResponse response = search(ssb, type, SearchType.DEFAULT);

		if (response.getHits().totalHits() > 0) {
			result = new ArrayList<Map<String, Object>>();
			for (SearchHit hit : response.getHits()) {
				result.add(hit.sourceAsMap());
			}
		}
		return result;
	}

	public SearchResponse findData(QueryCondition qc) {
		SearchSourceBuilder ssb = buildQueryCondition(qc);
		// 排序
		if (StringUtils.isNotBlank(qc.getSortField())) {
			if ("desc".equalsIgnoreCase(qc.getSortType())) {
				ssb.sort(qc.getSortField(), SortOrder.DESC);
			} else {
				ssb.sort(qc.getSortField(), SortOrder.ASC);
			}
		}

		// 分页
		if (qc.getStart() > 0) {
			ssb.from(qc.getStart());
		}

		if (qc.getLimit() > 0) {
			ssb.size(qc.getLimit());
		}

		// 飘红
		if (qc.isHighlighting()) {
			HighlightBuilder hb = new HighlightBuilder();
			if (qc.getQueryFields() != null
					&& !Arrays.toString(qc.getQueryFields()).equals("[]")) {
				for (String field : qc.getQueryFields()) {
					hb.field(field);
				}
			} else {
				hb.field("name");
			}
			ssb.highlight(hb);
		}

		logger.debug("rensou ssb: {}", ssb);
		return search(ssb, qc.getIndexType(), SearchType.QUERY_THEN_FETCH);
	}

	private String getQueryStringWithBlank(String keyword) {
		return "\"" + keyword.trim().replaceAll(" ", "\" \"") + "\"";
	}

	/**
	 * 封装应用信息检索条件
	 * 
	 * @param qc
	 * @return
	 */
	public SearchSourceBuilder buildQueryCondition(QueryCondition qc) {
		SearchSourceBuilder ssb = new SearchSourceBuilder();
		BoolQueryBuilder bqb = null;
		FilterBuilder exist = null;
		//类型：
		if(StringUtils.isNotBlank(qc.getMusinType())){
			 exist = FilterBuilders.existsFilter(qc.getMusinType());
		}


		// 音乐名称条件
		if (StringUtils.isNotBlank(qc.getName())) {
			QueryBuilder qbt = QueryBuilders.queryStringQuery(qc.getName()).field("name");
			if (bqb != null) {
				bqb.must(qbt);
			} else {
				bqb = QueryBuilders.boolQuery().must(qbt);
			}
		}

		// 歌手条件
		if (StringUtils.isNotBlank(qc.getAuthor())) {
			QueryBuilder qbt = QueryBuilders.termsQuery("author-full",
					qc.getAuthor());
			if (bqb != null) {
				bqb.must(qbt);
			} else {
				bqb = QueryBuilders.boolQuery().must(qbt);
			}
		}

		// keyId
		if (StringUtils.isNotBlank(qc.getKeyId())) {
			QueryBuilder qbt = QueryBuilders.termQuery("_id", qc.getKeyId());
			if (bqb != null) {
				bqb.must(qbt);
			} else {
				bqb = QueryBuilders.boolQuery().must(qbt);
			}
		}

	
		// 条件汇总
		QueryBuilder query = null;
		if (bqb == null) {
			query = QueryBuilders.matchAllQuery();
		} else {
			query = bqb;
		}
		FilterBuilder filterBuilder = null;
		if(exist == null){
			filterBuilder = FilterBuilders.matchAllFilter();
		}else{
			filterBuilder = exist;
		}
		ssb.query(query).postFilter(filterBuilder);
		logger.debug("ES检索条件: {}", ssb.toString());
		return ssb;
	}

	/**
	 * ES检索
	 * 
	 * @author ycl
	 * @date 2013-03-04
	 */
	public SearchResponse search(final SearchSourceBuilder ssb,
			final IndexType indexType, final SearchType searchType) {
		return clientTemplate.executeGet(new ClientCallback<SearchResponse>() {
			public ActionFuture<SearchResponse> execute(Client client) {
				SearchRequest request = Requests
						.searchRequest(clientTemplate.getIndexName())
						.preference("_primary_first").types(indexType.name())
						.searchType(searchType).source(ssb);
				return client.search(request);
			}
		});
	}

	/**
	 * ES检索
	 * 
	 * @author ycl
	 * @date 2013-03-04
	 */
	public SearchResponse search(final TermsBuilder termsBuilder,
			final IndexType indexType, final SearchType searchType,
			final int from, final int size) {
		return clientTemplate.executeGet(new ClientCallback<SearchResponse>() {
			public ActionFuture<SearchResponse> execute(Client client) {
				// SearchRequest request =
				SearchRequestBuilder searchRequestBuilder = client
						.prepareSearch()
						.setQuery(QueryBuilders.matchAllQuery())
						.addAggregation(termsBuilder).setSearchType(searchType)
						.setTypes(indexType.name()).setFrom(from).setSize(size);
				return searchRequestBuilder.execute();
			}
		});
	}

	/**
	 * 插入或修改文档
	 * 
	 * @author:ycl
	 * @date:2013-03-04
	 */
	public IndexResponse insertOrUpdate(final IndexType indexType,
			final String keyId, final Map<String, Object> source) {
		return clientTemplate.executeGet(new ClientCallback<IndexResponse>() {
			public ActionFuture<IndexResponse> execute(Client client) {
				IndexRequest request = Requests
						.indexRequest(clientTemplate.getIndexName())
						.type(indexType.name()).id(keyId).source(source);
				ActionFuture<IndexResponse> res = client.index(request);
				return res;
			}
		});
	}

	/**
	 * 批量插入数据
	 * 
	 * @author:ycl
	 * @date:2013-03-04
	 */
	public void batchInsertData(final IndexType indexType,
			final Map<String, Map<String, Object>> sourceMap) {
		try {
			clientTemplate.executeGet(new ClientCallback<BulkResponse>() {
				@Override
				public ActionFuture<BulkResponse> execute(Client client) {
					BulkRequest br = new BulkRequest();
					for (String id : sourceMap.keySet()) {
						br.add(Requests
								.indexRequest(clientTemplate.getIndexName())
								.type(indexType.name()).id(id)
								.source(sourceMap.get(id)));
					}
					ActionFuture<BulkResponse> result = client.bulk(br);
					br.requests().clear();
					return result;
				}
			});
			logger.debug("入库索引: " + clientTemplate.getIndexName() + ", type: "
					+ indexType.name() + ", total: " + sourceMap.size()
					+ ", 成功!");
		} catch (Exception e) {
			logger.error("入库索引: " + clientTemplate.getIndexName() + ", type: "
					+ indexType.name() + ", 异常!", e);
		}
	}

	/**
	 * 删除文档
	 * 
	 * @author:ycl
	 * @date:2013-03-04
	 */
	public DeleteResponse delete(final IndexType indexType, final String keyId) {
		return clientTemplate.executeGet(new ClientCallback<DeleteResponse>() {
			public ActionFuture<DeleteResponse> execute(Client client) {
				DeleteRequest request = Requests
						.deleteRequest(clientTemplate.getIndexName())
						.type(indexType.name()).id(keyId);
				return client.delete(request);
			}
		});
	}

	/**
	 * 根据查询条件删除文档
	 * 
	 * @author:ycl
	 * @date:2013-03-04
	 */
	public void deleteByQuery(final IndexType indexType,
			final QueryBuilder query) {
		try {
			clientTemplate
					.executeGet(new ClientCallback<DeleteByQueryResponse>() {
						@Override
						public ActionFuture<DeleteByQueryResponse> execute(
								Client client) {
							QuerySourceBuilder querySourceBuilder = new QuerySourceBuilder();
							querySourceBuilder.setQuery(query);
							return client.deleteByQuery(Requests
									.deleteByQueryRequest(
											clientTemplate.getIndexName())
									.types(indexType.name())
									.source(querySourceBuilder));
						}
					});
			logger.debug("删除doc: " + clientTemplate.getIndexName() + ", type: "
					+ indexType.name() + "成功!");
		} catch (Exception e) {
			logger.error("删除doc: " + clientTemplate.getIndexName() + ", type: "
					+ indexType.name() + "异常!", e);
		}
	}

	/**
	 * 增加type mapping
	 * 
	 * @author ycl
	 * @date 2013-12-9
	 * @return PutMappingResponse
	 */
	public PutMappingResponse putMapping(final IndexType indexType,
			final XContentBuilder mapping) {
		return clientTemplate
				.executeGet(new NodeCallback<PutMappingResponse>() {
					@Override
					public ActionFuture<PutMappingResponse> execute(
							IndicesAdminClient admin) {
						PutMappingRequest mappingRequest = Requests
								.putMappingRequest(
										clientTemplate.getIndexName())
								.type(indexType.name()).source(mapping);
						return admin.putMapping(mappingRequest);
					}
				});
	}

	/**
	 * 优化索引
	 * 
	 * @author ycl
	 * @date 2013-5-30
	 */
	public void optimize() {
		try {
			clientTemplate.executeGet(new NodeCallback<OptimizeResponse>() {
				@Override
				public ActionFuture<OptimizeResponse> execute(
						IndicesAdminClient admin) {
					return admin.optimize(Requests
							.optimizeRequest(clientTemplate.getIndexName()));
				}
			});
			logger.debug("优化索引: " + clientTemplate.getIndexName() + "成功!");
		} catch (Exception e) {
			logger.error("优化索引: " + clientTemplate.getIndexName() + "异常!", e);
		}
	}

	/**
	 * flush索引
	 * 
	 * @author ycl
	 * @date 2013-5-30
	 */
	public void flush() {
		try {
			clientTemplate.executeGet(new NodeCallback<FlushResponse>() {
				@Override
				public ActionFuture<FlushResponse> execute(
						IndicesAdminClient admin) {
					return admin.flush(Requests.flushRequest(clientTemplate
							.getIndexName()));
				}
			});
			logger.debug("flush索引: " + clientTemplate.getIndexName() + "成功!");
		} catch (Exception e) {
			logger.error("flush索引: " + clientTemplate.getIndexName() + "异常!", e);
		}
	}

	/**
	 * refresh索引
	 * 
	 * @author ycl
	 * @date 2014-1-23
	 */
	public void refresh() {
		try {
			clientTemplate.executeGet(new NodeCallback<RefreshResponse>() {
				@Override
				public ActionFuture<RefreshResponse> execute(
						IndicesAdminClient admin) {
					return admin.refresh(Requests.refreshRequest(clientTemplate
							.getIndexName()));
				}
			});
			logger.debug("refresh索引: " + clientTemplate.getIndexName() + "成功!");
		} catch (Exception e) {
			logger.error("refresh索引: " + clientTemplate.getIndexName() + "异常!",
					e);
		}
	}
	
	
}
