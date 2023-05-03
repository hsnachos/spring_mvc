package com.hsnachos.service;

import java.io.File;
import java.util.List;

import com.hsnachos.controller.UploadController;
import com.hsnachos.domain.AttachFileDTO;
import com.hsnachos.domain.AttachVO;
import com.hsnachos.mapper.AttachMapper;
import com.hsnachos.mapper.ReplyMapper;
import lombok.Getter;
import org.springframework.stereotype.Service;

import com.hsnachos.domain.BoardVO;
import com.hsnachos.domain.Criteria;
import com.hsnachos.mapper.BoardMapper;

import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.log4j.Log4j;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
@Log4j
@ToString
public class BoardServiceImpl implements BoardService {


	//	스프링 4.3버전 부터 단일 파라미터를 받는 생성자의 경우엔 필요한 파라미터를 자동적으로 주입을 해 줄 수 있음!!!!
//	@Setter
//	@Autowired
	private final BoardMapper boardMapper;

	private final ReplyMapper replyMapper;

	private final AttachMapper attachMapper;

	@Override
	@Transactional
	public void register(BoardVO vo) {
		boardMapper.insertSelectKey(vo);
		Long bno = vo.getBno();
		List<AttachVO> list = vo.getAttachs();
		/*
		if(list == null || list.size() == 0) {
			return;
		}*/

		int idx = 0;
		for(AttachVO attach : list) {
			attach.setBno(bno);
			attach.setOdr(idx++);
			attachMapper.insert(attach);
		}
	}

	@Override
	public BoardVO get(Long bno) {
		return boardMapper.read(bno);
	}

	@Override
	@Transactional
	public boolean modify(BoardVO vo) {
		attachMapper.deleteAll(vo.getBno());

		List<AttachVO> list = vo.getAttachs();
		int idx = 0;
		for(AttachVO attach : list){
			attach.setBno(vo.getBno());
			attach.setOdr(idx++);
			attachMapper.insert(attach);
		}

		return boardMapper.update(vo) > 0;
	}

	@Override
	@Transactional
	public boolean remove(Long bno) {
		replyMapper.deleteByBno(bno);
		attachMapper.deleteAll(bno);
		return boardMapper.delete(bno) > 0;
	}

	@Override
	public List<BoardVO> getList(Criteria criteria) {
//		return boardMapper.getList();
		return boardMapper.getListWithPaging(criteria);
	}

	@Override
	public int getTotalCnt(Criteria criteria) {
		// TODO Auto-generated method stub
		return boardMapper.getTotalCnt(criteria);
	}

	@Override
	public String deleteFile(AttachFileDTO dto) {
		log.info(dto);
		String s = UploadController.getPATH() + "/" + dto.getPath() + "/" + dto.getUuid() + "_" + dto.getName();

		File file = new File(s);
		file.delete();
		if(dto.isImage()){
			s = UploadController.getPATH() + "/" + dto.getPath() + "/s_" + dto.getUuid() + "_" + dto.getName();
			file = new File(s);
			file.delete();
		}

		return null;
	}

}
