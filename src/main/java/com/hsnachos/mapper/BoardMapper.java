package com.hsnachos.mapper;

import java.util.List;

import com.hsnachos.domain.BoardVO;
import com.hsnachos.domain.Criteria;
import org.apache.ibatis.annotations.Param;

public interface BoardMapper {
	// 목록 조회
	//public List<Map<String, Object>> memberList();
	public List<BoardVO> getList();
	
	public List<BoardVO> getListWithPaging(Criteria criteria);
	
	// 글 등록
	void insert(BoardVO vo);
	
	// 글 등록
	void insertSelectKey(BoardVO vo);
	
	// 조회
	BoardVO read(Long bno);
	
	// 삭제
	int delete(Long bno);
	
	// 수정
	int update(BoardVO vo);
	
	int getTotalCnt(Criteria criteria);

	// 댓글 갯수 반영
	void updateReplyCnt (@Param("bno") Long bno, @Param("amount") int amount);
}
