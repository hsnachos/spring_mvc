package com.hsnachos.service;

import com.hsnachos.mapper.MemberMapper_old;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * packageName    : com.hsnachos.service
 * fileName       : MemberServiceImpl
 * author         : banghansol
 * date           : 2023/04/11
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/04/11        banghansol       최초 생성
 */
@AllArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {
    private MemberMapper_old memberMapper;
/*
    @Override
    public List<MemberVO> getList() {
        return memberMapper.getList();
    }

    @Override
    public MemberVO get(String id) {
        return memberMapper.read(id);
    }

    @Override
    public MemberVO get(MemberVO vo) {
        return memberMapper.login(vo);
    }*/
}
