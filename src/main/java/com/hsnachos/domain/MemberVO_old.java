package com.hsnachos.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * packageName    : com.hsnachos.domain
 * fileName       : MemberVO
 * author         : banghansol
 * date           : 2023/04/11
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/04/11        banghansol       최초 생성
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberVO_old {
    private String id;
    private String pw;
    private String name;
    private Date regdate;
}
