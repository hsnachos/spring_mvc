package com.hsnachos.mapper;

import com.hsnachos.domain.AttachVO;

import java.util.List;

/**
 * packageName    : com.hsnachos.mapper
 * fileName       : AttachMapper
 * author         : banghansol
 * date           : 2023/04/13
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/04/13        banghansol       최초 생성
 */
public interface AttachMapper {
    void insert(AttachVO vo);

    void delete(String uuid);

    List<AttachVO> findBy(Long bno);

    void deleteAll(Long bno);

    List<AttachVO> getOldFiles();
}
