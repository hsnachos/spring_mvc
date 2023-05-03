package com.hsnachos.domain;

import org.springframework.web.util.UriComponentsBuilder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Criteria {
	private int pageNum = 1;
	private int amount = 10;
	private String type; // 복합 검색, T C W TC TW CW TCW
	private String keyword;

	public int getOffset(){
		return (pageNum - 1) * amount;
	}
	public Criteria(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
	}

	public String getQueryString() {
		return UriComponentsBuilder.fromPath("")
				.queryParam("amount", amount)		
				.queryParam("type", type)		
				.queryParam("keyword", keyword)		
//				.queryParam("pageNum", pageNum)
				.build().toString();
	}
	
	public String getFullQueryString() {
		return UriComponentsBuilder.fromPath("")
				.queryParam("amount", amount)				
				.queryParam("pageNum", pageNum)
				.queryParam("type", type)		
				.queryParam("keyword", keyword)	
				.build().toString();
	}
	
	public String[] getTypeArr() {
		return type == null ? new String[] {} : type.split("");
	}
}
