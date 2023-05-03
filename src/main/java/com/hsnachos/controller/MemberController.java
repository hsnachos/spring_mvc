package com.hsnachos.controller;

import com.hsnachos.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * packageName    : com.hsnachos.controller
 * fileName       : MemberController
 * author         : banghansol
 * date           : 2023/04/11
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/04/11        banghansol       최초 생성
 */
@Controller
@RequestMapping("member")
@Log4j
@AllArgsConstructor
public class MemberController {
    private MemberService memberService;
/*
    @GetMapping("chat")
    public void chat() {}
*/

    @GetMapping("login")
    public void login() {}
/*
    @PostMapping("login")
    public String login(MemberVO vo, HttpSession session, RedirectAttributes rttr) {
        log.info(vo);
        MemberVO memberVO = memberService.get(vo);

        if(memberVO == null) {
            rttr.addFlashAttribute("msg", "로그인 실패");
        }else {
            session.setAttribute("member", memberVO);
            log.info("로그인 성공");
        }

            return "redirect:/member/login";
    }

    @RequestMapping("logout")
    public String logout(HttpSession session) {
        log.info("로그아웃 처리");
        session.invalidate();

        return "redirect:/member/login";
    }

    @GetMapping("getList")
    @ResponseBody
    public List<MemberVO> getList() {
        return memberService.getList();
    }
    */
}