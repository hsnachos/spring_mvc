package com.hsnachos.mapper;

import com.hsnachos.domain.MemberVO_old;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * packageName    : com.hsnachos.mapper
 * fileName       : MemberMapper
 * author         : banghansol
 * date           : 2023/04/11
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/04/11        banghansol       최초 생성
 */
public interface MemberMapper_old {
    void insert(MemberVO_old vo);

    @Select("select * from tbl_member")
    List<MemberVO_old> getList();

    @Select("select * from tbl_member where id=#{id}")
    MemberVO_old read(String id);

    @Select("select * from tbl_member where id=#{id} and pw=#{pw}")
    MemberVO_old login(MemberVO_old vo);

}
