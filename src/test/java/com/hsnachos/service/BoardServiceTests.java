package com.hsnachos.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hsnachos.domain.BoardVO;
import com.hsnachos.domain.Criteria;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BoardServiceTests {
	@Autowired
	private BoardService boardService;
	
	@Test
	public void testExist() {
		assertNotNull(boardService);
		log.info(boardService);
	}
	
	@Test
	public void testRegister() {
		BoardVO vo = new BoardVO();
		vo.setTitle("테스트 추가 제목");
		vo.setContent("테스트 추가 내용");
		vo.setWriter("테스트 추가 작가");
		
		boardService.register(vo);
		
		log.info(vo);
	}
	
	@Test
	public void testGet() {
		Long bno = 81967L;
		BoardVO vo = boardService.get(bno);
		
		log.info(vo);
	}
	
	@Test
	public void testGetList() {
		boardService.getList(new Criteria()).forEach(log::info);
	}
	
	@Test
	public void testRemove() {
		Long bno = 5L;
		
		BoardVO vo = boardService.get(bno);
		log.info(vo);
		log.info(vo.getBno());
		
		assertTrue(boardService.remove(vo.getBno()));
		
		vo = boardService.get(bno);
		
		assertNull(vo);
	}
	
	@Test
	public void testUpdate() {
		Long bno = 6L;
		
		BoardVO vo = boardService.get(bno);
		log.info(vo);
		
		vo.setTitle("테스트 추가 제목");
		vo.setContent("테스트 추가 내용");
		vo.setWriter("테스트 추가 작가");
		
		assertTrue(boardService.modify(vo));
		
		log.info(boardService.get(bno));
	}
}
