package com.hsnachos.mapper;

import com.hsnachos.domain.MemberVO_old;
import com.hsnachos.domain.NoteVO;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

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
public class NoteMapperTests {
    @Autowired
    private NoteMapper mapper;
    @Autowired
    private MemberMapper_old memberMapepr;

    @Test
    public void testInsert2() {
        List<MemberVO_old> members = memberMapepr.getList();
        int i = 1;
        for(MemberVO_old vo : members){
            for(MemberVO_old vo2 : members){
                NoteVO noteVO = new NoteVO();
                noteVO.setSender(vo.getId());
                noteVO.setReceiver(vo2.getId());
                noteVO.setMessage("mapper 테스트 발송 " + i);
                mapper.insert(noteVO);
                i++;
            }
        }
    }

    @Test
    public void testInsert(){
        NoteVO noteVO = new NoteVO();
        noteVO.setSender("id1");
        noteVO.setReceiver("id2");
        noteVO.setMessage("mapper 테스트 발송");

        mapper.insert(noteVO);
    }

    @Test
    public void testSelectOne(){
        log.info(mapper.selectOne(1L));
    }

    @Test
    public void testUpdate(){
        mapper.update(1L);
    }

    @Test
    public void testDelete(){
        mapper.delete(3L);
    }

    @Test
    public void testSendList(){
        mapper.sendList("id1").forEach(log::info);
    }

    @Test
    public void testReceiveList(){
        mapper.receiveList("id1").forEach(log::info);
    }

    @Test
    public void testReceiveUncheckedList() {
        mapper.receiveUncheckedList("id1").forEach(log::info);
    }
}
