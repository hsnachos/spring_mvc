package com.hsnachos.service;

import com.hsnachos.domain.ReplyVO;
import com.hsnachos.mapper.BoardMapper;
import com.hsnachos.mapper.ReplyMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@AllArgsConstructor
@Service
public class ReplyServiceImpl implements ReplyService{
    private BoardMapper boardMapper;
    private ReplyMapper replyMapper;

    @Override
    @Transactional
    public int register(ReplyVO vo) {
        boardMapper.updateReplyCnt(vo.getBno(), 1);
        return replyMapper.insert(vo);
    }

    @Override
    public ReplyVO get(Long rno) {
        return replyMapper.read(rno);
    }

    @Override
    public List<ReplyVO> getList(Long bno, Long rno) {
        return replyMapper.getList(bno, rno);
    }

    @Override
    public int modify(ReplyVO vo) {
        return replyMapper.update(vo);
    }

    @Override
    @Transactional
    public int remove(Long rno) {
        boardMapper.updateReplyCnt(get(rno).getBno(), -1);
        return replyMapper.delete(rno);
    }
}
