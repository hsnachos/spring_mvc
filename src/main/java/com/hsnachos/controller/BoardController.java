package com.hsnachos.controller;

import com.hsnachos.domain.*;
import oracle.jdbc.proxy.annotation.Post;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hsnachos.service.BoardService;

import lombok.Data;
import lombok.extern.log4j.Log4j;

import java.util.List;

@Controller
@Log4j
@RequestMapping("board/*")
@Data
public class BoardController {
	private final BoardService boardService;

	@GetMapping("list")
	public void list(Model model, Criteria criteria) {
		log.info("BoardController list");
		log.info(criteria);
		
		model.addAttribute("list", boardService.getList(criteria));
		model.addAttribute("page", new PageDto(boardService.getTotalCnt(criteria), criteria));
	}
	
	@GetMapping("register")
	@PreAuthorize("isAuthenticated()")
	public void register() {}
	
	@PostMapping("register")
	@PreAuthorize("isAuthenticated()")
	public String register(BoardVO vo, RedirectAttributes rttr, Criteria cri) {
		log.info("register");
		log.info(vo);
		
		boardService.register(vo);
		 
		rttr.addFlashAttribute("result", vo.getBno());
		rttr.addFlashAttribute("pageNum", 1);
		rttr.addFlashAttribute("amount", cri.getAmount());
		
		return "redirect:/board/list";
	}
	
	@PostMapping("modify")
	@PreAuthorize("isAuthenticated() and principal.username eq #vo.writer")
	public String modify(BoardVO vo, RedirectAttributes rttr, Criteria cri) {
		// 원본 리스트
		List<AttachVO> originList = boardService.get(vo.getBno()).getAttachs();


		// 수정될 리스트
		List<AttachVO> list = vo.getAttachs();
		log.info("modify");
		log.info(vo);
		log.info(cri);
		
		if(boardService.modify(vo)) {

			rttr.addFlashAttribute("result", "success");
			/*
			originList.stream().filter(v -> {
				boolean flag = false;
				for (AttachVO vo2 : list) {
					vo2.getUuid().equals(v.getUuid());
				}
				return !flag;
			}).forEach(boardService::deleteFile);
			*/
		}
//		
//		rttr.addAttribute("pageNum", cri.getPageNum());
//		rttr.addAttribute("amount", cri.getAmount());
//		
		return "redirect:/board/list" + cri.getFullQueryString();
	}
	
	@PostMapping("remove")
	public String remove(Long bno, RedirectAttributes rttr, Criteria cri) {
		log.info("remove");
		log.info(bno);

		List<AttachVO> list = boardService.get(bno).getAttachs();

		if(boardService.remove(bno)) {
			// list.forEach(boardService::deleteFile);
			for(AttachVO vo : list){
				boardService.deleteFile(vo);
			}

			rttr.addFlashAttribute("result", "success");
		}
		
		return "redirect:/board/list" + cri.getFullQueryString();
	}
	
	
	
	@GetMapping({"get", "modify"})
	public void get(Model model, Long bno, @ModelAttribute("cri") Criteria cri) {
		log.info("get() or modify");
		log.info(bno);
		BoardVO boardVO = boardService.get(bno);

		log.info("boardVO : " + boardVO);

		model.addAttribute("board", boardVO);
	}
	
	@GetMapping("{bno}")
	public String getByPath(Model model, @PathVariable Long bno) {
		log.info("get() or modify");
		log.info(bno);
		
		model.addAttribute("board", boardService.get(bno));
		
		return "board/get";
	}

	/*
	 그냥 @Controller에서는 메서드 이름 혹은 String 이름에 따른 jsp를 찾기 때문에
	 만약 @Controller에서 json을 리턴해주고 싶으면 @ResponseBody 어노테이션을 붙여줘야한다!
	*/
	@GetMapping("getSample")
	@ResponseBody
	public SampleVO getSample() {
		return new SampleVO(112, "스타", "로드");
	}

}
