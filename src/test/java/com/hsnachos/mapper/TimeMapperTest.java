package com.hsnachos.mapper;

import com.hsnachos.config.RootConfig;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * packageName    : com.hsnachos.mapper
 * fileName       : TimeMapperTest
 * author         : banghansol
 * date           : 2023/04/19
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/04/19        banghansol       최초 생성
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RootConfig.class)
@Log4j
public class TimeMapperTest {
    @Autowired
    private TimeMapper mapper;

    @Test
    public void testGetTime(){
        log.warn(mapper.getTime());
    }
}
