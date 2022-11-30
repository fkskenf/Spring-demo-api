package com.blog.dto.testDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class TestDto {
	private String id;
	private String pw;
	
//	@JsonProperty("pluse_map")
	private String pluseMap; // JSON Object를 stringify한 값


}
