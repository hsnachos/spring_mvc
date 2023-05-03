package com.hsnachos.domain;

import lombok.Data;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

/**
 * packageName    : com.hsnachos.domain
 * fileName       : AttachVO
 * author         : banghansol
 * date           : 2023/04/13
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/04/13        banghansol       최초 생성
 */
@Data
@ToString(callSuper = true)
@Alias("attach")
public class AttachVO extends AttachFileDTO{
    private Long bno;
}
