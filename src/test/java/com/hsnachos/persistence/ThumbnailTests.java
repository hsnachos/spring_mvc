package com.hsnachos.persistence;

import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;

/**
 * packageName    : com.hsnachos.persistence
 * fileName       : ThumbnailTests
 * author         : banghansol
 * date           : 2023/04/07
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/04/07        banghansol       최초 생성
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class ThumbnailTests {
    @Test
    public void testThumbnail() throws IOException {
        File file = new File("/Users/banghansol/file/upload/스크린샷 2023-03-30 오후 4.02.22.png");
        File file2 = new File("/Users/banghansol/file/upload/결과.png");
        Thumbnails.of(file).crop(Positions.CENTER).size(200, 200).toFile(file2);
    }
}
