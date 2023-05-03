package com.hsnachos.crawl;

import com.hsnachos.mapper.CrawlMapper;
import lombok.extern.log4j.Log4j;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class CGVParserTests {
    @Autowired
    private CrawlMapper crawlMapper;

    @Test
    public void testExist(){
        assertNotNull(crawlMapper);
    }

    @Test
    public void parse() throws IOException {
        Document doc = Jsoup.parse(new URL("http://www.cgv.co.kr/movies/"), 2000);
        // System.out.println("doc = " + doc);

        Element element = doc.selectFirst(".sect-movie-chart");

        Elements lis = element.select("li");

        for (Element e: lis) {

            String href = e.selectFirst("a").attr("href");
            String midx = href.substring(href.lastIndexOf("=")+1);
            String date = e.selectFirst(".txt-info").text().replaceAll("개봉", "").trim();
            String imgSrc = e.selectFirst(".thumb-image img").attr("src");
            String imgAlt = e.selectFirst(".thumb-image img").attr("alt");
            String age = e.selectFirst("i.cgvIcon").text();
            String title = e.selectFirst(".box-contents strong.title").text();

            Map<String, Object> map = new HashMap<>();
            map.put("href", href);
            map.put("midx", midx);
            map.put("date", date);
            map.put("thumb", imgSrc);
            map.put("thumb_alt", imgAlt);
            map.put("age", age);
            map.put("title", title);

            crawlMapper.insert(map);

        }
    }

    @Test
    public void testList(){
        crawlMapper.getList().forEach(log::info);
    }

    @Test
    public void testDownloadThumbs() throws IOException {
        //InputStream inputStream = new URL("https://img.cgv.co.kr/Movie/Thumbnail/Poster/000086/86911/86911_320.jpg").openStream();

        // FileUtils.copyInputStreamToFile(inputStream, new File("d:/tmp.png"));

        //FileUtils.copyInputStreamToFile(inputStream, new File("/Users/banghansol/crawling"));


        List<Map<String, Object>> thumbs = crawlMapper.getList();
        for (Map<String, Object> m : thumbs) {
            String thumb = m.get("thumb").toString();
            InputStream is = new URL(thumb).openStream();
            File file = new File("/Users/banghansol/crawling", m.get("pidx").toString());
            if(!file.exists()){
                file.mkdirs();
            }

            file = new File(file, "index" + thumb.substring(thumb.lastIndexOf(".")));
            FileUtils.copyInputStreamToFile(is, file);
        }
    }

    @Test
    public void testInsertPerson() {
        Map<String, String> map = new HashMap<>();

        map.put("pidx", "734");
        map.put("name", "휴고 위빙");
        map.put("href", "/movies/persons/?pidx=734");


        crawlMapper.insertPerson(map);
    }

    @Test
    public void testInsertPerson1() {
        Map<String, String> map = new HashMap<>();

        map.put("pidx", "1978");
        map.put("name", "벤 애플렉");
        map.put("href", "/movies/persons/?pidx=1978");


        crawlMapper.insertPerson(map);
    }

    @Test
    public void testInsertDirector() {
        HashMap<String, String> map = new HashMap<>();

        map.put("midx", "86916");
        map.put("pidx", "1978");

        crawlMapper.insertDirector(map);
    }

    @Test
    public void crawlDetail() throws IOException {
        List<Map<String, Object>> list = crawlMapper.getList();
        for(Map<String, Object> m : list){

            String url = "http://www.cgv.co.kr" + m.get("href");
            Document doc = Jsoup.parse(new URL(url), 2000);

            String engtitle = doc.selectFirst(".sect-base-movie .title p").text();
            System.out.println("titlep = " + engtitle);
            String info = doc.selectFirst(".sect-story-movie").text();
            System.out.println("info = " + info);
            // doc.selectFirst(".select-first-movie");

            Element element = doc.selectFirst("#ctl00_PlaceHolderContent_Section_Still_Cut");
            Elements select = doc.select(".sect-base-movie .spec dt");
            for (Element e : select) {
                Elements es = e.getElementsContainingText("감독").next().select("a");
                Map<String, String> m2 = new HashMap<>();

                for (Element e2 : es){
                    Map<String, String> map = new HashMap<>();

                    String director = e2.text();
                    String directorHref = e2.attr("href");
                    String pidx = directorHref.substring(directorHref.lastIndexOf("=") + 1);
                    String midx = m.get("midx").toString();

                    map.put("pidx", pidx);
                    map.put("name", director);
                    map.put("href", directorHref);


                    crawlMapper.insertPerson(map);

                    map = new HashMap<>();

                    System.out.println("director = " + director);
                    System.out.println("directorHref = " + directorHref);
                    System.out.println("pidx = " + pidx);

                    map.put("midx", midx);
                    map.put("pidx", pidx);

                    crawlMapper.insertDirector(map);

                }

                System.out.println("======================================================");

                Elements es1 = e.getElementsContainingText("배우").next().select("a");
                for (Element e2 : es1){
                    String actor = e2.text();
                    String actorHref = e2.attr("href");
                    String pidx = actorHref.substring(actorHref.lastIndexOf("=") + 1);

                    System.out.println("actor = " + actor);
                    System.out.println("actorHref = " + actorHref);
                    System.out.println("pidx = " + pidx);
                }

                Elements es2 = e.getElementsContainingText("장르");
                for (Element e2 : es2){
                    String genre = e2.text();

                    System.out.println("genre = " + genre);
                    m2.put("genre", genre);
                }

                Elements es3 = e.getElementsContainingText("기본").next();
                for (Element e2 : es3){
                    String runningTime = e2.text().split(", ")[1];
                    String nation = e2.text().split(", ")[2];

                    System.out.println("runningTime = " + runningTime);
                    System.out.println("nation = " + nation);

                }

                crawlMapper.updateCGV(m2);

                break;
            }

        }

    }
    @Test
    public void testDownloadStillCut() throws IOException {
        List<Map<String, Object>> list = crawlMapper.getList();
        for(Map<String, Object> m : list) {

            String url = "http://www.cgv.co.kr" + m.get("href");
//            Document doc = Jsoup.parse(new URL(url), 2000);
            // 위 코드는 Underlying input stream returned zero bytes 에러가 발생
            Document doc = Jsoup.connect(url).get();

            String midx = m.get("midx").toString();

            Element element = doc.selectFirst("#ctl00_PlaceHolderContent_Section_Still_Cut");

            System.out.println("midx = " + midx);

            String engtitle = doc.selectFirst(".sect-base-movie .title p").text();
            System.out.println("titlep = " + engtitle);
            String info = doc.selectFirst(".sect-story-movie").text();
            System.out.println("info = " + info);

            Elements lis = element.select("img");

            // File file = new File("d:/img", midx);
            File file = new File("/Users/banghansol/crawling", midx);
            if(!file.exists()){
                file.mkdirs();
            }

            int idx = 1;
            for(Element e : lis){
                String src = e.attr("data-src");
                System.out.println(src);

                // 이미지 파일시스템에 다운로드
                String ext = src.substring(src.lastIndexOf("."));
                File file2 = new File(file, idx + ext);

                URL url2 = new URL(src);
                FileUtils.copyURLToFile(url2, file2);

                // 데이터베이스에 정의
                Map<String, String> map = new HashMap<>();
                map.put("odr", idx+"");
                map.put("midx", midx);

                crawlMapper.insertAttach(map);

                idx++;

            }
        }
    }
}
