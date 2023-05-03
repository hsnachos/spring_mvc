package com.hsnachos.task;

import com.hsnachos.mapper.AttachMapper;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * packageName    : com.hsnachos.task
 * fileName       : AttachMapperTests
 * author         : banghansol
 * date           : 2023/04/18
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/04/18        banghansol       최초 생성
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class AttachMapperTests {
    @Autowired
    private FileCheckTask fileCheckTask;

    @Test
    public void testCheckFiles(){
        fileCheckTask.checkFiles();
    }
}
