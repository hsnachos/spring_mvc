package com.hsnachos.service;

import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class SampleServiceTests {
    @Autowired
    private SampleService service;

    @Test
    public void testExist() {
        assertNotNull(service);
    }

    @Test
    public void testAddData() throws UnsupportedEncodingException {
        String data = "대법원과 각급법원의 조직은 법률로 정한다. 형사피고인은 유죄의 판결이 확정될 때까지는 무죄로 추정된다. 국회는 헌법개정안이 공고된 날로부터 60일 이내에 의결하여야 하며, 국회의 의결은 재적의원 3분의 2 이상의 찬성을 얻어야 한다.";

        byte[] bs = data.getBytes("utf-8");
        log.info(bs.length);

        byte[] bs2 = new byte[50];
        System.arraycopy(bs, 0, bs2, 0, 50);

        log.info(bs2.length);

        String str = new String(bs2, "utf-8");
        log.info(str);

        // data = "abcd";
        // log.info(data.length());
        service.addData(data);
    }
}
