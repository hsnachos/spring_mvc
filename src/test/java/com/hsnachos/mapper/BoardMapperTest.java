package com.hsnachos.mapper;

import com.hsnachos.config.RootConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hsnachos.domain.BoardVO;
import com.hsnachos.domain.Criteria;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@ContextConfiguration(classes = RootConfig.class)
@Log4j
public class BoardMapperTest {
	
	@Autowired
	private BoardMapper boardMapper;
	
//	@Test
//	public void testGetList() {
//		boardMapper.getList().forEach(log::info);
//	}
	
	@Test
	public void testGetListWithPaging() {
		boardMapper.getListWithPaging(new Criteria()).forEach(log::info);
		//boardMapper.getListWithPaging(new Criteria(3, 10, "TC", "; select * from dual --'")).forEach(log::info);
		// boardMapper.getListWithPaging(new Criteria(3, 10, "T", "java")).forEach(log::info);
	}
	
	@Test
	public void testInsert() {
		BoardVO vo = new BoardVO();
		
		vo.setTitle("테스트코드 작성 insert() 제목");
		vo.setContent("테스트코드 작성 insert() 내용");
		vo.setWriter("작성자");
		
		boardMapper.insert(vo);
	}
	
	@Test
	public void testInsertSelectKey() {
		BoardVO vo = new BoardVO();
		
		vo.setTitle("테스트코드 작성 insertSelectKey() 제목");
		vo.setContent("테스트코드 작성 insertSelectKey() 내용");
		vo.setWriter("작성자");
		
		boardMapper.insertSelectKey(vo);
	}
	
	@Test
	public void testRead() {
		boardMapper.read(81968L);
	}
	
	@Test
	public void testDelete() {
		Long bno = 2L;
		log.info(boardMapper.read(bno));
		log.info(boardMapper.delete(bno));
		log.info(boardMapper.read(bno));
		
	}
	
	@Test
	public void testUpdate() {
		BoardVO vo = boardMapper.read(1231L);
		
		vo.setTitle("수정된 제목");
		vo.setContent("수정된 내용");
		vo.setWriter("user00");
		
		log.info(vo);
		boardMapper.update(vo);
		
		BoardVO vo2 = boardMapper.read(1231L);
		
		log.info(vo2);
		
		
	}
	
	@Test
	public void testGetTotalCnt() {
		Criteria cri = null;
		// cri = new Criteria();
		cri = new Criteria(3, 10, "T", "1");
		
		int result = boardMapper.getTotalCnt(cri);
		
		log.info(result);
	}

}
