package com.hsnachos.controller;

import com.hsnachos.domain.NoteVO;
import com.hsnachos.service.NoteService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * packageName    : com.hsnachos.controller
 * fileName       : NoteController
 * author         : banghansol
 * date           : 2023/04/11
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/04/11        banghansol       최초 생성
 */
@RestController
@Log4j
@RequestMapping("note")
@AllArgsConstructor
public class NoteController {
    private NoteService service;

    @PostMapping("new")
    public int send(@RequestBody NoteVO vo){
        log.info(vo);

        return service.send(vo);
    }

    @GetMapping("{noteno}")
    public NoteVO getNote(@PathVariable Long noteno){
        log.info(noteno);
        return service.get(noteno);
    }

    @PutMapping("{noteno}")
    public int receive(@PathVariable Long noteno){
        log.info(noteno);
        return service.receive(noteno);
    }

    @DeleteMapping("{noteno}")
    public int delete(@PathVariable Long noteno){
        log.info(noteno);
        return service.remove(noteno);
    }

    @GetMapping("s/{id}")
    public List<NoteVO> getSendList(@PathVariable String id) {
        log.info(id);
        return service.getSendList(id);
    }

    @GetMapping("r/{id}")
    public List<NoteVO> getReceiveList(@PathVariable String id) {
        log.info(id);
        return service.getReceiveList(id);
    }

    @GetMapping("r/c/{id}")
    public List<NoteVO> getReceiveCheckedList(@PathVariable String id) {
        log.info(id);
        return service.getReceiveUnCheckedList(id);
    }
}
