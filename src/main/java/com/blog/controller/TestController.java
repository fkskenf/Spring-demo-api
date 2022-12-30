package com.blog.controller;

import java.sql.Connection;
import java.sql.DriverManager;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.dto.testDto.TestDto;

@CrossOrigin(origins = "*", maxAge = 3600)

// old :@Controller //(@RestController에 포함)Controller로 정의되어 있어야 jsp파일을 읽을 수 있다. 
@RestController // (@Controller + @ResponseBody)
public class TestController {

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
	 * Test Jdbc Connection
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
					,"ses8178@");
			System.out.println("JDBC CONNECTION SUCCESS!!!");
		} catch (Exception e) {
			System.out.println("JDBC CONNECTION FAIL!!!");
			e.printStackTrace();
		}

		return new ResponseEntity("JDBC CONNECTION SUCCESS!!!", HttpStatus.OK);
	}
}
