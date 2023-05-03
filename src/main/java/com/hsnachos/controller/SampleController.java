package com.hsnachos.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hsnachos.domain.SampleVO;

import lombok.extern.log4j.Log4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("sample")
@Log4j
/**
 * Controller란?
 * 요청과 응답 처리를 제어
 * request : 1. URL(포트 이후 부분)
 *           2. 파라미터 수집(Http 는 기본적으로 상태가 없기 때문에 쿼리스트링으로 보냄)
 *           3. HTTP Method(GET, POST, PUT, DELETE) .. attr, session, cookie
 * response : 1. MIME-TYPE(JSP - viewResolver가 처리함) cf. forward, viewResolver
 *            2. header(text/html, application/json, text/xml, application/octet-stream)
 */
public class SampleController {

	@GetMapping(value = "getText", produces = "text/plain; charset=utf-8")
	public String getText2() {
		return "안녕하세요";
	}

	@GetMapping("getSample")
	public SampleVO getSample() {
		return new SampleVO(112, "스타", "로드");
	}

	@GetMapping("getList")
	public List<SampleVO> getList() {
		return IntStream.rangeClosed(1, 10)
				.mapToObj(i -> new SampleVO(i, "first " + i, "last " + i))
				.collect(Collectors.toList());
	}

	@GetMapping("getMap")
	public Map<String, SampleVO> getMap() {
		Map<String, SampleVO> map = new HashMap<>();
		map.put("First", new SampleVO(111, "그루트", "주니어"));
		return map;
	}

	@GetMapping(value = "check", params = {"height", "wieght"})
	public ResponseEntity<SampleVO> check(double height, double weight) {
		SampleVO vo = new SampleVO(0, String.valueOf(height), String.valueOf(weight));

		ResponseEntity<SampleVO> result = null;

		if(height < 150){
			result = ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(vo);
		} else {
			result = ResponseEntity.ok(vo);
		}

		return result;
	}

	@GetMapping("product/{cat}/{pid}")
	public String[] getPath(String cat, String pid){
		log.info("getPath" + "cat = " + cat + "pid = " + pid);

		return new String[] {
				"cat = " + cat , "pid = " + pid
		};
	}

	@GetMapping("product/{cat2}/{pid2}/{test}")
	public String[] getPath2(@PathVariable String cat2, @PathVariable String pid2){
		log.info("getPath2" + "cat = " + cat2 + "pid = " + pid2);

		return new String[] {
				"cat = " + cat2 , "pid = " + pid2
		};
	}

	@GetMapping("sample")
	public SampleVO convert(@RequestBody SampleVO sampleVO){
		log.info(sampleVO);
		return sampleVO;
	}

	@PostMapping("sample")
	@ResponseBody
	public SampleVO getSample(@RequestBody SampleVO sampleVO) {
		log.warn(sampleVO);
		return sampleVO;
	}
}
