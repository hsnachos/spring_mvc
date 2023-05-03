package com.hsnachos.controller;

import com.hsnachos.domain.ReplyVO;
import com.hsnachos.service.ReplyService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("replies")
@RestController
@Log4j
@AllArgsConstructor
public class ReplyController {
    private ReplyService replyService;

    // replies/list/{bno}
    // replies/list/{bno}/{rno}

    @GetMapping({"/list/{bno}", "/list/{bno}/{rno}"})
    public List<ReplyVO> getList(
            @PathVariable Long bno,
            // @PathVariable(required = false) Long rno
            @PathVariable(required = false) Optional<Long> rno
    ) {
        log.info(bno);
        log.info(rno);

        // isPresent() : null인지 체크
        log.info(rno.isPresent() ? rno.get() : 0L);
        // 간단하게 orElse를 사용해도 상관없다!
        log.info(rno.orElse(0L));

//        if(rno == null){
//            rno = 0L;
//        }

        rno.orElseGet(() -> 0L);

        return replyService.getList(bno, rno.orElse(0L));
    }

    @PostMapping("new")
    @PreAuthorize("isAuthenticated()")
    public Long create(@RequestBody ReplyVO vo) {
        log.info(vo);
        replyService.register(vo);
        return vo.getRno();
    }

    @GetMapping("{rno}")
    public ReplyVO get(@PathVariable Long rno) {
        log.info(rno);

        return replyService.get(rno);
    }

    @PutMapping("{rno}")
    @PreAuthorize("isAuthenticated() and principal.username eq #vo.replyer")
    public int modify(@PathVariable Long rno, @RequestBody ReplyVO vo) {
        log.info(rno);

        log.info(vo);

        return replyService.modify(vo);
    }

    @DeleteMapping("{rno}")
    //@PreAuthorize("isAuthenticated()")
    @PreAuthorize("isAuthenticated() and principal.username eq #vo.replyer")
    public int remove(@PathVariable Long rno, @RequestBody ReplyVO vo) {
        log.info("rno : " + rno);
        log.info("vo : " + vo);

        return replyService.remove(rno);
    }
}
