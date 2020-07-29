package com.victolee.sampleproject.helper;

import javax.servlet.http.HttpServletResponse;

public class BackendAPIResult {
	public static void setResultCode(int resultCode) {
		String resultMsg = "";
		int resultData = 0;

		switch (resultCode) {
			case 0:
				resultMsg = "정상";
				resultData = HttpServletResponse.SC_OK; 
				break;
			case 1:
				resultMsg = "세션이 만료되었습니다.";
				resultData = HttpServletResponse.SC_UNAUTHORIZED;
				break;
			case 101:
				resultMsg = "요청한 주소로 접속 할 수 없습니다.";
				resultData = HttpServletResponse.SC_BAD_REQUEST;
				break;
			case 102:
				resultMsg = "데이터베이스에 연결할 수 없습니다.";
				resultData = HttpServletResponse.SC_BAD_REQUEST;
				break;
			case 103:
				resultMsg = "레디스 데이터베이스에 연결할 수 없습니다.";
				resultData = HttpServletResponse.SC_BAD_REQUEST;
				break;
			case 104:
				resultMsg = "Timestamp 값이 존재하지 않습니다.";
				resultData = HttpServletResponse.SC_UNAUTHORIZED;
				break;
			case 105:
				resultMsg = "TransactionID 값이 존재하지 않습니다.";
				resultData = HttpServletResponse.SC_UNAUTHORIZED;
				break;
			case 106:
				resultMsg = "유효하지 않은 Method입니다.";
				resultData = HttpServletResponse.SC_ACCEPTED;
				break;
			case 107:
				resultMsg = "허용되지 않은 비인증 URL입니다.";
				resultData = HttpServletResponse.SC_UNAUTHORIZED;
				break;
			case 108:
				resultMsg = "Signature를 찾을 수 없습니다.";
				resultData = HttpServletResponse.SC_UNAUTHORIZED;
				break;
			case 109:
				resultMsg = "Signature 시간이 만료되었습니다.";
				resultData = HttpServletResponse.SC_UNAUTHORIZED;
				break;
			case 110:
				resultMsg = "유효하지 않은 토큰입니다.";
				resultData = HttpServletResponse.SC_UNAUTHORIZED;
				break;
			case 111:
				resultMsg = "유효하지 않은 Hash key입니다.";
				resultData = HttpServletResponse.SC_UNAUTHORIZED;
				break;
			case 112:
				resultMsg = "유효하지 않은 인증 파라미터입니다.";
				resultData = HttpServletResponse.SC_UNAUTHORIZED;
				break;
			case 114:
				resultMsg = "잘못된 인증 방식입니다.";
				resultData = HttpServletResponse.SC_UNAUTHORIZED;
				break;
			case 115:
				resultMsg = "유효한 싸인 값이 존재하지 않습니다.";
				resultData = HttpServletResponse.SC_UNAUTHORIZED;
				break;
			case 116:
				resultMsg = "인증에 필요한 토큰 값이 존재하지 않습니다.";
				resultData = HttpServletResponse.SC_UNAUTHORIZED;
				break;
			case 117:
				resultMsg = "인증에 필요한 토큰 값의 형식이 다릅니다.";
				resultData = HttpServletResponse.SC_UNAUTHORIZED;
				break;
			case 118:
				resultMsg = "레디스 ByPass URL 저장 중 오류가 발생했습니다.";
				resultData = HttpServletResponse.SC_UNAUTHORIZED;
				break;
			case 119:
				resultMsg = "레디스 ByPass URL 체크 중 오류가 발생했습니다.";
				resultData = HttpServletResponse.SC_UNAUTHORIZED;
				break;
			case 120:
				resultMsg = "레디스 Free Path URL 체크 중 오류가 발생했습니다.";
				resultData = HttpServletResponse.SC_UNAUTHORIZED;
				break;
			case 121:
				resultMsg = "비인증 URL 체크중 오류가 발생했습니다.";
				resultData = HttpServletResponse.SC_UNAUTHORIZED;
				break;
			case 122:
				resultMsg = "비인증 URL 저장 중 오류가 발생했습니다.";
				resultData = HttpServletResponse.SC_UNAUTHORIZED;
				break;
			case 123:
				resultMsg = "비인증 Sign 결과가 존재하지 않습니다.";
				resultData = HttpServletResponse.SC_UNAUTHORIZED;
				break;
			case 124:
				resultMsg = "비인증 Sign 체크 중 오류가 발생했습니다.";
				resultData = HttpServletResponse.SC_UNAUTHORIZED;
				break;
			case 125:
				resultMsg = "비인증 Sign 저장 중 오류가 발생했습니다.";
				resultData = HttpServletResponse.SC_UNAUTHORIZED;
				break;
			case 126:
				resultMsg = "인증 Sign 저장 중 오류가 발생했습니다.";
				resultData = HttpServletResponse.SC_UNAUTHORIZED;
				break;
			case 127:
				resultMsg = "인증 결과가 존재하지 않습니다.";
				resultData = HttpServletResponse.SC_UNAUTHORIZED;
				break;
			case 128:
				resultMsg = "인증 체크중 오류가 발생했습니다.";
				resultData = HttpServletResponse.SC_UNAUTHORIZED;
				break;
			case 129:
				resultMsg = "Jedis Sentinel Instance 객체를 생성하지 못했습니다.";
				resultData = HttpServletResponse.SC_UNAUTHORIZED;
				break;
			case 130:
				resultMsg = "Jedis Instance 객체를 생성하지 못했습니다.";
				resultData = HttpServletResponse.SC_UNAUTHORIZED;
				break;
			case 131:
				resultMsg = "사용자 정보가 Redis에 저장되어 있지 않습니다.";
				resultData = HttpServletResponse.SC_UNAUTHORIZED;
				break;
			case 132:
				resultMsg = "jedisClient 객체가 없어 고객사 DB에 접근할 수 없습니다.";
				resultData = HttpServletResponse.SC_UNAUTHORIZED;
				break;
			case 133:
				resultMsg = "고객사 정보가 Redis에 없어 고객사 DB에 접근할 수 없습니다.";
				resultData = HttpServletResponse.SC_UNAUTHORIZED;
				break;
			case 134:
				resultMsg = "get_token 요청 시 URL 정보가 포함되어 있지 않습니다.";
				resultData = HttpServletResponse.SC_UNAUTHORIZED;
				break;
			case 135:
				resultMsg = "FreePathURL입니다. 인증을 거치지 않고 필터를 통과합니다.";
				resultData = HttpServletResponse.SC_UNAUTHORIZED;
				break;
			case 200:
				resultMsg = "HTTP 연결에 실패했습니다.";
				resultData = HttpServletResponse.SC_BAD_REQUEST;
				break;
			case 201:
				resultMsg = "HTTP 연결을 취소했습니다.";
				resultData = HttpServletResponse.SC_OK;
				break;
			case 202:
				resultMsg = "HTTP 요청에 대한 응답에 실패했습니다.";
				resultData = HttpServletResponse.SC_NOT_ACCEPTABLE;
				break;
			case 300:
				resultMsg = "DB 패치 정보 중 seq에 값이 없습니다.";
				resultData = HttpServletResponse.SC_BAD_REQUEST;
				break;
			case 301:
				resultMsg = "DB 패치 정보 중 klagoVer에 값이 없습니다.";
				resultData = HttpServletResponse.SC_BAD_REQUEST;
				break;
			case 302:
				resultMsg = "DB 패치 정보 중 dbType에 값이 없습니다.";
				resultData = HttpServletResponse.SC_BAD_REQUEST;
				break;
			case 303:
				resultMsg = "DB XML 분석 중 오류가 발생하였습니다.";
				resultData = HttpServletResponse.SC_BAD_REQUEST;
				break;
			case 304:
				resultMsg = "DB 패치 진행 중 오류가 발생하였습니다.";
				resultData = HttpServletResponse.SC_BAD_REQUEST;
				break;
			case 305:
				resultMsg = "고객별 쿼리 수정을 위한 데이터 조회 중 오류가 발생하였습니다.";
				resultData = HttpServletResponse.SC_BAD_REQUEST;
				break;
			case 306:
				resultMsg = "패치 쿼리 내용중 고객별 데이터를 치환하는 중 오류가 발생하였습니다.";
				resultData = HttpServletResponse.SC_BAD_REQUEST;
				break;
			case 307:
				resultMsg = "DB 업데이트 쿼리 실행중 문제가 발생하였습니다.";
				resultData = HttpServletResponse.SC_BAD_REQUEST;
				break;
			case 308:
				resultMsg = "실패 조건 체크 쿼리를 실행 중 문제가 발생하였습니다.";
				resultData = HttpServletResponse.SC_BAD_REQUEST;
				break;
		}
		
	}
}
