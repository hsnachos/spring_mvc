package com.hsnachos.domain;

import lombok.Data;

import java.util.Date;

/**
 * packageName    : com.hsnachos.domain
 * fileName       : NoteVO
 * author         : banghansol
 * date           : 2023/04/11
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/04/11        banghansol       최초 생성
 */
@Data
public class NoteVO {
    private long noteno;
    private String sender;
    private String receiver;
    private Date sdate;
    private Date rdate;
    private String message;
}
