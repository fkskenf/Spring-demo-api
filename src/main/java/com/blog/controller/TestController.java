package com.blog.controller;

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
	
	// old : @RequestMapping(value = "/mainBanner", method= {RequestMethod.POST} )
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping(value = "test")
	public ResponseEntity<?> test3(TestDto dto) { // DTO or HttpServletRequest request(Map)
		System.out.println("sucess");
		
		System.out.println(dto.getId());
		System.out.println(dto.getPw());

		return new ResponseEntity("sucess", HttpStatus.OK);
	}
}
