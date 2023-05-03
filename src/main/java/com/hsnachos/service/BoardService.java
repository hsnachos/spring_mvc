package com.hsnachos.service;

import java.util.List;

import com.hsnachos.domain.AttachFileDTO;
import com.hsnachos.domain.BoardVO;
import com.hsnachos.domain.Criteria;

public interface BoardService {
	void register(BoardVO vo);
	
	BoardVO get (Long bno);
	
	boolean modify(BoardVO vo);
	
	boolean remove(Long bno);
	
	List<BoardVO> getList(Criteria criteria);
	
	int getTotalCnt(Criteria criteria);

	String deleteFile(AttachFileDTO dto);
}
