package com.hsnachos.mapper;

import com.hsnachos.domain.NoteVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * packageName    : com.hsnachos.mapper
 * fileName       : NoteMapper
 * author         : banghansol
 * date           : 2023/04/11
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/04/11        banghansol       최초 생성
 */
public interface NoteMapper {
    // CRUD
    //
    @Insert("INSERT INTO TBL_NOTE(NOTENO, SENDER, RECEIVER, MESSAGE) VALUES(SEQ_NOTE.NEXTVAL, #{sender}, #{receiver}, #{message})")
    int insert(NoteVO vo);

    @Select("SELECT * FROM TBL_NOTE WHERE NOTENO=#{noteno}")
    NoteVO selectOne(Long noteno);

    @Update("UPDATE TBL_NOTE SET RDATE = SYSDATE WHERE NOTENO = #{noteno}")
    int update(Long noteno);

    @Delete("DELETE FROM TBL_NOTE WHERE NOTENO = #{noteno}")
    int delete (Long noteno);

    // 보낸거
    @Select("SELECT * FROM TBL_NOTE WHERE NOTENO > 0 AND SENDER = #{sender} ORDER BY 1 DESC")
    List<NoteVO> sendList(String sender);

    // 받은거
    @Select("SELECT * FROM TBL_NOTE WHERE NOTENO > 0 AND RECEIVER = #{receiver} ORDER BY 1 DESC")
    List<NoteVO> receiveList(String receiver);
    // 받았는데 확인 안한거
    @Select("SELECT * FROM TBL_NOTE WHERE NOTENO > 0 AND RECEIVER = #{receiver} AND RDATE IS NULL ORDER BY 1 DESC")
    List<NoteVO> receiveUncheckedList(String receiver);
}
