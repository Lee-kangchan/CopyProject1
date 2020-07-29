package com.victolee.sampleproject.jedis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.victolee.sampleproject.constant.CommonConstants;
import com.victolee.sampleproject.helper.BackendAPIResult;
import com.victolee.sampleproject.model.SessionInfo;
import com.victolee.sampleproject.utils.CryptUtil;
import com.victolee.sampleproject.utils.StringUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.exceptions.JedisDataException;
import redis.clients.util.Pool;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
/*
* RedisManager Class
* spring-servlet에 설정한 데이터 기반으로 redis conncetion
* @author  Pakerbb
*/

public class JedisClient {
	
	private static ObjectMapper mapper = new ObjectMapper();
	private JedisClient() {
	}

	private static Pool<Jedis> jedisPool;
	private static int JEDISPOOL_DEFAULT_SIZE = 1000;
	private static int JEDIS_TIMEOUT = 10000;
	private Logger logger = LogManager.getLogger(this.getClass());

	private static class JedisClientFactory {
		private static final JedisClient instance = new JedisClient();
	}

	public static JedisClient getInstance(String host, int port, String password) {
		return JedisClient.getInstance(host, port, password, null);
	}

	/**
	 * redis instance 생성
	 * 
	 * @param host          : Redis 서버 아이피
	 * @param port          : Redis 포트
	 * @param password      : Redis에 설정된 비밀번호
	 * @param sentinelHosts : 센티널을 사용할 시 host:ip,... 구조의 String 값. 이 값이 있으면 위 host,port parameter는 무시됨.
	 * @return JedisClient instance
	 */
	public static JedisClient getInstance(String host, int port, String password, String sentinelHosts) {
		JedisClient instance = JedisClientFactory.instance;
		try {
			if (jedisPool == null) {
				// 미사용
				if (sentinelHosts != null && !"".equals(sentinelHosts)) {
					JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
					jedisPoolConfig.setMaxTotal(JEDISPOOL_DEFAULT_SIZE);
					String[] arrHostAndPort = sentinelHosts.split(",");

					Set<String> sentinels = new HashSet<>();
					for (String strHostAndPort : arrHostAndPort) {
						sentinels.add(strHostAndPort);
					}
					jedisPool = new JedisSentinelPool("mymaster", sentinels, jedisPoolConfig, password);
				} else {
					JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
					jedisPoolConfig.setMaxTotal(JEDISPOOL_DEFAULT_SIZE);
					jedisPool = new JedisPool(jedisPoolConfig, host, port, JEDIS_TIMEOUT, password);
				}
			}

		} catch (Exception e) {
			NullPointerException e2 = null;
			if (sentinelHosts != null || !"".equals(sentinelHosts)) {
				e2 = new NullPointerException("Jedis Sentinel Instance 객체를 생성하지 못했습니다. sentinelHosts=" + sentinelHosts
						+ ", password=" + password);
			} else {
				e2 = new NullPointerException(
						"Jedis Instance 객체를 생성하지 못했습니다. host=" + host + ", port=" + port + ", password=" + password);
			}
			e2.initCause(e.getCause());
			throw e2;
		}

		return instance;
	}

	/**
	 * redis list 조회
	 * 
	 * @return JedisClient instance
	 */
	public Map<String, String> getCustInfoList() {
		List<Map<String, String>> custInfoList = new ArrayList<>();
		Jedis jedis = null;
		String loggingGroupSeq = "";

		try {
			jedis = jedisPool.getResource();
			jedis.select(2);
			Map<String, String> redisInfo = getCustInfo(jedis, "manage_local.group.seq");
			custInfoList.add(redisInfo);
			return redisInfo;

		} catch (Exception e) {
			logger.error(
					"Redis 리스트 정보를 조회하는 중 오류가 발생했습니다. loggingGroupSeq=" + loggingGroupSeq + ", msg=" + e.getMessage());
			throw e;
		} finally {
			if (jedis != null) {
				if (jedis.isConnected()) {
					jedis.close();
				}
				jedis = null;
			}
		}
	}

	/**
	 * redis list 조회
	 * 
	 * @param jedis : jedis 객체
	 * @param PK    : Key 값
	 * @return MAP(JSON)
	 */
	private Map<String, String> getCustInfo(Jedis jedis, String PK) {
		Map<String, String> redisInfo = new HashMap<>();
		Map<String, String> dataInfo = null;
		try {
			dataInfo = jedis.hgetAll(PK);
		} catch (Exception e) {
			logger.error("[Redis hget ALL Fail ]", e);
			throw e;
		}
		redisInfo.put("Key", dataInfo.get("domain"));
		return redisInfo;
	}
	
	/**
	 * 인증된 사용자의 정보를 Redis에서 반환한다.
	 * @param token_key
	 * @return SessionInfo
	 * @throws Exception
	 */
	public SessionInfo getAUTHUserInfo(String token_key) throws Exception {
		Jedis jedis = null;
		SessionInfo result = null;
		String tokenKey = token_key;
		try {
			jedis = jedisPool.getResource();
			jedis.select(CommonConstants.REDIS_AUTH_SIGN_DB);
			String Userinfo = jedis.get(tokenKey);
			//레디스에 저장된 정보를 복호화 하여 반환한다.
			//테스트시 저장정보가 암호화 되어 있지 않기때문에 주석처리 (실제 주석 제거)
			Userinfo = CryptUtil.getDecryptCbc(1, Userinfo);
			result = mapper.readValue(Userinfo, new TypeReference<SessionInfo>(){});
			
			//redis에 저장된 토큰 만료시간으로 토큰시간 연장(그룹별 세션 설정 가능)
//			String sessionExpireTime = getSessionExpireTime(result.getUcUserInfo().getGroupSeq());
			
			//sessionInfo 객체에서 session시간을 조회하여 ttl값 설정(개인별 세션 설정 가능)
			String sessionExpireTime = StringUtils.isNullOrEmpty(result.getSessionTime())? CommonConstants.DEFAULT_SESSION_TOKEN_EXPIRE_TIME : result.getSessionTime();
			jedis.expire(tokenKey, Integer.parseInt(sessionExpireTime));
			
		}catch(Exception e) {
			BackendAPIResult.setResultCode(131);
			throw e;
		}finally {
			if(jedis != null){
				if(jedis.isConnected()){
					jedis.close();
				}
				jedis = null;
			}
		}
		return result;
	}

	public void destory() {
		jedisPool.destroy();
	}

	public boolean isFreePathUrl(String url) {
		boolean result = false;
		Jedis jedis = null;
		
		try{
			jedis = jedisPool.getResource();
			jedis.select(CommonConstants.REDIS_UNAUTH_URL_DB);
			Map<String, String> freePathAll = jedis.hgetAll("REDIS_STORAGE_KEY_FREE_PATH_URL");
			for(String str : freePathAll.keySet()) {
				if (url.contains(freePathAll.get(str))) {
					result = true;
					break;
				}
			}

			return result;
		}catch(Exception e){
			
			throw e;
		}finally{
			if(jedis != null){
				if(jedis.isConnected()){
					jedis.close();
				}
				jedis = null;
			}
		}
	}

	// 비인증 URL 체크 
	public String getUnAuthURL(String url) {
		Jedis jedis = null;
		try{
			jedis = jedisPool.getResource();
			jedis.select(3);
			String value = jedis.get(url);
			return value;
		}catch(Exception e){
			BackendAPIResult.setResultCode(121);
			throw e;
		}finally{
			if(jedis != null){
				if(jedis.isConnected()){
					jedis.close();
				}
				jedis = null;
			}
		}
	}

	// byPass URL 체크
		public String getNonByPassURL(String url) {
			Jedis jedis = null;
			try {
				jedis = jedisPool.getResource();
				jedis.select(3);
				String value = jedis.hget("NON_BYPASS_URL", url);
				return value;
			}catch(Exception e) {
				BackendAPIResult.setResultCode(119);
				throw e;
			}finally {
				if(jedis != null){
					if(jedis.isConnected()){
						jedis.close();
					}
					jedis = null;
				}
			}
		}

		// 인증 Sign 체크 
		public String getAuthSign(String token) throws Exception {
			Jedis jedis = null;
			try{
				jedis = jedisPool.getResource();
				jedis.select(CommonConstants.REDIS_AUTH_SIGN_DB);
				String value = jedis.get(token);
				//인증 정보 복호화
				value = CryptUtil.getDecryptCbc(1, value);
				return value;
			}catch(JedisDataException e){
				BackendAPIResult.setResultCode(127);
				
				return "";
			}catch(Exception e){
				BackendAPIResult.setResultCode(128);
				throw e;
			}finally{
				if(jedis != null){
					if(jedis.isConnected()){
						jedis.close();
					}
					jedis = null;
				}
			}
		}

		// 비인증 Sign 체크 
		public String getUnAuthSign(String sign) {
			Jedis jedis = null;
			try{
				jedis = jedisPool.getResource();
				jedis.select(3);
				String value = jedis.get(sign);
				return value;
			}catch(JedisDataException e){
				BackendAPIResult.setResultCode(123);
				
				return "";
			}catch(Exception e){
				BackendAPIResult.setResultCode(124);
				
				throw e;
			}finally{
				if(jedis != null){
					if(jedis.isConnected()){
						jedis.close();
					}
					jedis = null;
				}
			}
		}

		// 비인증 Sign 저장 
		public void setUnAuthSign(String sign, String value) {
			Jedis jedis = null;
			try{
				jedis = jedisPool.getResource();
				jedis.select(3);
				jedis.set(sign, value);
				jedis.expire(sign, 60);
			}catch(Exception e){
				BackendAPIResult.setResultCode(125);
				throw e;
			}finally{
				if(jedis != null){
					if(jedis.isConnected()){
						jedis.close();
					}
					jedis = null;
				}
			}
		}






}
