package com.victolee.sampleproject.search;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import javax.servlet.FilterConfig;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.victolee.sampleproject.jedis.JedisClient;
import com.victolee.sampleproject.model.SessionInfo;

/*
* RedisManager Class
* spring-servlet에 설정한 데이터 기반으로 redis conncetion 및 기능처리 구현을 위한 클래스
* @author  Pakerbb
*/
@Component("redisManager")
public class RedisManager {
	private Logger logger = LogManager.getLogger(this.getClass());

	private static JedisClient jedisClient = null;
	private Properties properties;
	// redis 생성자
	public RedisManager(String ip, String port, String password, String sentinalHosts) {
		try {
			int portNum = Integer.parseInt(port);
			jedisClient = JedisClient.getInstance(ip, portNum, password, sentinalHosts);
			logger.info("Redis Connection (ip:" + ip + "/port:" + port + "/password:" + password
					+ "/sentinalHosts:" + sentinalHosts + ")");
		} catch (Exception e) {
			logger.error("ERROR=>" + e + "[Redis -Connection error. (ip:" + ip + "/port:" + port + "/password:"
					+ password + "/sentinalHosts:" + sentinalHosts + ")]");
		}
	}

	// redis 디비에 저장된 데이터 조회
	public Map<String, String> getRedisInfoList() {
		return jedisClient.getCustInfoList();
	}
	
	// redis 디비에 저장된 데이터 조회
		public SessionInfo getJedisInfoClient(String AuthToken) throws Exception {
			return jedisClient.getAUTHUserInfo(AuthToken);
		}

		public JedisClient CreateJedisClient(FilterConfig config) {
			properties = getProperties(config);
			if (properties == null) {
				logger.error("FilterConfig File is not available");
			}
			else {
				String ip = properties.getProperty("redis.ip", "");
				String port = properties.getProperty("redis.port", "");
				String passwd = properties.getProperty("redis.password", "");
				
				jedisClient = jedisClient.getInstance(ip, Integer.valueOf(port), passwd, null);
			}

			return jedisClient;

		}
		
		private Properties getProperties(FilterConfig filterConfig) {
			String initParamName = "properties";
			String pathToProperties = filterConfig.getInitParameter(initParamName);
			Properties properties = new Properties();
			String path = this.getClass().getResource("").getPath();

			if (pathToProperties == null)
				return null;

			try {
				InputStream in = new FileInputStream(pathToProperties);
				properties.load(in);
				return properties;
			} catch (IOException e) {
					logger.error("Properties Load Failed. " + e.getMessage(), e);
				return null;
			}
		}

		public void DestroyJedisClient() {
			if (jedisClient != null)
				jedisClient.destory();
		}



}
