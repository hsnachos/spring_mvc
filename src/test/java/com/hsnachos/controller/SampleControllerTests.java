package com.hsnachos.controller;

import com.google.gson.Gson;
import com.hsnachos.domain.BoardVO;
import com.hsnachos.domain.SampleVO;
import lombok.extern.log4j.Log4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
	"file:src/main/webapp/WEB-INF/spring/root-context.xml"
	, "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"
	})
@WebAppConfiguration
@Log4j
public class SampleControllerTests {
	@Autowired
	private WebApplicationContext context;
	
	private MockMvc mockMvc;
	
	// SVN 테스트!!
	
	// Junit Before
	@Before
	public void init() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	@Test
	public void testExist() {
		log.info(context);
		log.info(mockMvc);
	}

	@Test
	public void testSample() throws Exception {
		SampleVO sampleVO = new SampleVO(10, "길동", "홍");
		Gson gson = new Gson();
		String jsonStr = gson.toJson(sampleVO);

		RequestBuilder builder = MockMvcRequestBuilders.get("/sample/sample").content(jsonStr)
				.contentType(MediaType.APPLICATION_JSON);
		mockMvc.perform(builder);
	}
}
