package com.hsnachos.service;

import com.hsnachos.domain.ReplyVO;

import java.util.List;

public interface ReplyService {
    int register(ReplyVO vo);

    ReplyVO get(Long rno);

    List<ReplyVO> getList(Long bno, Long rno);

    int modify(ReplyVO vo);

    int remove(Long rno);
}
