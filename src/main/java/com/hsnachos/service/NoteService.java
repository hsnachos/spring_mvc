package com.hsnachos.service;

import com.hsnachos.domain.NoteVO;

import java.util.List;

/**
 * packageName    : com.hsnachos.service
 * fileName       : NoteService
 * author         : banghansol
 * date           : 2023/04/11
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/04/11        banghansol       최초 생성
 */
public interface NoteService {
    int send(NoteVO vo);

    NoteVO get(long noteno);

    int receive(Long noteno);

    int remove(Long noteno);

    List<NoteVO> getSendList(String id);

    List<NoteVO> getReceiveList(String id);

    List<NoteVO> getReceiveUnCheckedList(String id)
;}
