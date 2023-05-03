package com.hsnachos.mapper;

import com.hsnachos.domain.MemberVO_old;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * packageName    : com.hsnachos.mapper
 * fileName       : MemberMapperTests
 * author         : banghansol
 * date           : 2023/04/11
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/04/11        banghansol       최초 생성
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class MemberMapperTests {
    @Autowired
    private MemberMapper_old mapper;

    @Test
    public void testGetList() {
        mapper.getList().forEach(log::info);
    }

    @Test
    public void testLogin() {
        MemberVO_old vo = new MemberVO_old();
        vo.setId("id1");
        vo.setPw("1234");

        log.info(mapper.login(vo));
    }
}
