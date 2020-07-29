package com.victolee.sampleproject.search;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/*
* CustomSearchClient Class
* elasticsearch connection / create index , type , setting , mapping
* @author  Pakerbb
* @version 1.0
*/
public class CustomSearchClient {
	@Autowired
	private SqlSession sqlSession;

	private String esClusterName = "testCluster";

	private Map<String, String> custInfo = null;

	private ConcurrentHashMap<String, SyncObject> sync = null;

	private String makeIndexFormat = "%s_%s";

	private String scriptLang = "painless";

	static RestHighLevelClient client = null;

	private Logger logger = LogManager.getLogger(this.getClass());

	public CustomSearchClient(String esHostName, String esHostPort) {
		
		logger.info("ElasticSearch Connection Info  (ip:" + esHostName + "/port:" + esHostPort +")");
		
		RestClientBuilder builder = RestClient.builder(new HttpHost(esHostName, Integer.parseInt(esHostPort), "http"))
				.setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
					@Override
					public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder requestConfigBuilder) {
						return requestConfigBuilder.setConnectTimeout(5000).setSocketTimeout(30000);
					}

				}).setMaxRetryTimeoutMillis(30000);
		client = new RestHighLevelClient(builder);
	}

	public void init() {
		
		logger.info("ElasticSearch init");
		sync = new ConcurrentHashMap<String, SyncObject>();
		server = new ConcurrentHashMap<String, ServerObject>();
	}

	public void close() {
		try {
			client.close();

			sync = null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ConcurrentHashMap<String, ServerObject> server = null;
	private static Pattern regexMentionTag = Pattern
			.compile("\\|>@empseq=\\\"(?:[^\\\"]+)?\\\",name=\\\"([^\\\"]+)?\\\"@<\\|");

	public class ServerObject {
		public long lastSyncTime = 0;
		public String absolPath;
		public String limitFileCount;
		public String pathSeq;
		public String lang;

		public ServerObject() {
			lastSyncTime = System.currentTimeMillis();
		}
	}

	private static class SyncObject {
		public long lastSyncTime = 0;

		public SyncObject() {
			lastSyncTime = System.currentTimeMillis();
		}
	}

	public synchronized void InitializeCust(Map<String, String> data1) {

		try {
			logger.info("ES_Initialize , Requst MAP => ", data1);
			testMapping1(data1);
		} catch (Exception e) {
			logger.error("CustomESClient.InitializeCust-error. data1 cust=" + data1 + "msg=" + e.getMessage());

		}
	}

	// 파일 업로드 경로 및 서버 업로드 경로 , 패스 시퀀스 및 파일용량 제한 카운트 같은 서버 설정을 할 수 있는 메소드
	private void serverSetting() throws SQLException {
		server.clear();
		if (sqlSession != null) {
			ServerObject serverObject = null;
			server.put("serverObject", serverObject);
		}
	}

	private void testMapping1(Map<String, String> data1) throws Exception {
		String index = "studypakerbb";
		String type = "pakerbbss";

		IndicesClient ESClient = client.indices();

		GetIndexRequest createIndex = new GetIndexRequest().indices(index);

		if (!ESClient.exists(createIndex, RequestOptions.DEFAULT)) {

			/*
			 * INDEX SETTINGS
			 */
			XContentBuilder indexSettings = XContentFactory.jsonBuilder();
			indexSettings.startObject();
			{
				indexSettings.field("number_of_shards", 5); // 샤드 갯수 지정
				indexSettings.field("number_of_replicas", 1); // 레플리카 갯수 지정
				indexSettings.startObject("analysis");
				{
					indexSettings.startObject("analyzer");
					{
						indexSettings.startObject("whitespace");
						{
							indexSettings.field("tokenizer", "whitespace");
							indexSettings.field("filter", new String[] { "trim", "lowercase" });
						}
						indexSettings.endObject();
					}
					indexSettings.endObject();
				}
				indexSettings.endObject();
			}
			indexSettings.endObject();
			/*
			 * INDEX MEPPINGS
			 */
			XContentBuilder indexMapping = XContentFactory.jsonBuilder();
			indexMapping.startObject();
			{
				indexMapping.startObject(type);
				{
					indexMapping.startObject("_all");
					{
						indexMapping.field("enabled", false);
					}
					indexMapping.endObject();

					indexMapping.startObject("properties");
					{
						indexMapping.startObject("PrivateSeq");
						{
							indexMapping.field("type", "keyword");
						}
						indexMapping.endObject();

						indexMapping.startObject("EmpSeq");
						{
							indexMapping.field("type", "keyword");
						}
						indexMapping.endObject();

						indexMapping.startObject("DeptSeq");
						{
							indexMapping.field("type", "keyword");
						}
						indexMapping.endObject();

						indexMapping.startObject("JobType");
						{
							indexMapping.field("type", "keyword");
						}
						indexMapping.endObject();

					}
					indexMapping.endObject();
				}
				indexMapping.endObject();
			}
			indexMapping.endObject();

			CreateIndexResponse indexCreateRequest = ESClient.create(
					new CreateIndexRequest(index).settings(indexSettings).mapping(type, indexMapping),
					RequestOptions.DEFAULT);
			if (!indexCreateRequest.isAcknowledged()) {
				System.out.print("not make index ");
			}
			System.out.print("Index create - end - Index : " + index + " type : " + type);
		} else {
			logger.info("Index already exists - Index : " + index + " type :" + type);
		}

	}

}