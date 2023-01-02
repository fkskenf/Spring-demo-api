package com.blog.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.blog.dto.testDto.TestDto;
import com.blog.mapper.testMapper;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@CrossOrigin(origins = "*", maxAge = 3600)

// old :@Controller //(@RestController에 포함)Controller로 정의되어 있어야 jsp파일을 읽을 수 있다. 
@RestController // (@Controller + @ResponseBody)
public class TestController {
	
	@Resource(name = "testmapper")
	private final testMapper testMapper;

	@Autowired
	TestController(testMapper testMapper) {
		this.testMapper = testMapper;
	}
	
	@Autowired
	private SqlSessionFactory sqlSessionFactory;

	static {
		try {
			Class.forName("net.sf.log4jdbc.sql.jdbcapi.DriverSpy"); // driver 이슈발생시 : 1. 경로주의 2.log4jdbc.jar 유무 확인
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * Test Controller
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping(value = "testController") // old : @RequestMapping(value = "/mainBanner", method= {RequestMethod.POST} )
	public ResponseEntity<?> testController(TestDto dto) { // DTO or HttpServletRequest request(Map)
		System.out.println("TEST CONTROLLER SUCESS!!!!");
		System.out.println(dto.getId());
		System.out.println(dto.getPw());
		return new ResponseEntity("TEST CONTROLLER SUCESS!!!!", HttpStatus.OK);
	}

	/*
	 * Test Jdbc Connection (only MySQL)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping(value = "testJdbcConnection")
	public ResponseEntity<?> testJdbcConnection(TestDto dto) {
		System.out.println("JDBC CONNECTION RPOCESS...");
		
		// MySQL 인증방식변경으로 인해, useSSL=false 연결테스트시 추가필요
		try {
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/sys?serverTimezone=UTC&autoReconnect=true&useSSL=false"
					, "root"
					,"*******");
			System.out.println("JDBC CONNECTION SUCCESS!!!");
		} catch (Exception e) {
			System.out.println("JDBC CONNECTION FAIL!!!");
			e.printStackTrace();
		}

		return new ResponseEntity("JDBC CONNECTION SUCCESS!!!", HttpStatus.OK);
	}
	
	/*
	 * Test DB Connect
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping(value = "testDbConnect")
	public ResponseEntity<?> testDbConnect(TestDto dto) {
		System.out.println("DB CONNECT RPOCESS...");

		try (SqlSession session = sqlSessionFactory.openSession()) { // @Autowired 선언
			System.out.println(session);
			System.out.println("DB CONNECT SUCCESS!!!");
		} catch (Exception e) {
			System.out.println("DB CONNECT FAIL!!!");
			e.printStackTrace();
		}
		return new ResponseEntity("DB CONNECT SUCCESS!!!", HttpStatus.OK);
	}
	
	/*
	 * Test Dao/Mybatis Connect
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping(value = "testDaoConnect")
	public ResponseEntity<?> testDaoConnect(TestDto dto) {
		System.out.println("DAO RPOCESS...");
		
		HashMap<String, Object> selectMap = new HashMap<String, Object>();
		testMapper.select(selectMap);
		
		System.out.println("DAO SUCCESS...");

		return new ResponseEntity("DAO SUCCESS!!!", HttpStatus.OK);
	}
	
	/*
	 * Test Http
	 * - https://www.data.go.kr/data/15102239/openapi.do#tab_layer_detail_function
	 * - 기상청_전국 해수욕장 날씨 조회서비스
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping(value = "testHttp")
	public ResponseEntity<?> testHttp(TestDto dto) throws JSONException, JsonParseException, JsonMappingException, IOException {
		System.out.println("HTTP RPOCESS...");

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> result = restTemplate.getForEntity(
				"http://apis.data.go.kr/1360000/BeachInfoservice/getTwBuoyBeach?serviceKey=dwchvY9aMDuvIyEXpAng/VzGX7arSKOVVYWQuukBnQc86OWYba52QhO22gXDo0g7L6eywWqCcwkGEu0Bcz0tvg==&numOfRows=1&pageNo=10&dataType=json&beach_num=1&searchTime=202301021700",
				String.class);

		// JsonObject to Map
	    JSONObject jObject = new JSONObject(result.getBody());
        HashMap<String, Object> map = (HashMap<String, Object>) new ObjectMapper().readValue(jObject.toString(), Map.class);

	    HashMap<String, Object> response = (HashMap<String, Object>) map.get("response");
	    HashMap<String, Object> body = (HashMap<String, Object>) response.get("body");

		System.out.println("tw = " + ((ArrayList<HashMap<String, Object>>)((HashMap<String, Object>) body.get("items")).get("item")).get(0).get("tw"));
		return new ResponseEntity("HTTP SUCCESS!!!", HttpStatus.OK);
	}



}
