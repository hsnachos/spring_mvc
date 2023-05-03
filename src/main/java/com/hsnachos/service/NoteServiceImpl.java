package com.hsnachos.service;

import com.hsnachos.domain.NoteVO;
import com.hsnachos.mapper.NoteMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * packageName    : com.hsnachos.service
 * fileName       : NoteServiceImpl
 * author         : banghansol
 * date           : 2023/04/11
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/04/11        banghansol       최초 생성
 */
@Service
@AllArgsConstructor

public class NoteServiceImpl implements NoteService{
    private NoteMapper mapper;

    @Override
    public int send(NoteVO vo) {
        return mapper.insert(vo);
    }

    @Override
    public NoteVO get(long noteno) {
        return mapper.selectOne(noteno);
    }

    @Override
    public int receive(Long noteno) {
        return mapper.update(noteno);
    }

    @Override
    public int remove(Long noteno) {
        return mapper.delete(noteno);
    }

    @Override
    public List<NoteVO> getSendList(String id) {
        return mapper.sendList(id);
    }

    @Override
    public List<NoteVO> getReceiveList(String id) {
        return mapper.receiveList(id);
    }

    @Override
    public List<NoteVO> getReceiveUnCheckedList(String id) {
        return mapper.receiveUncheckedList(id);
    }
}
