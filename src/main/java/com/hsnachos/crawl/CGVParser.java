package com.hsnachos.crawl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class CGVParser {
    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.parse(new URL("http://www.cgv.co.kr/movies/detail-view/?midx=85541"), 2000);
        // Document doc = Jsoup.parse(new URL("http://www.cgv.co.kr/movies/detail-view/?midx=86720"), 2000);
        // System.out.println("doc = " + doc);

        // Element element = doc.selectFirst(".sect-movie-chart");

        String engtitle = doc.selectFirst(".sect-base-movie .title p").text();
        System.out.println("titlep = " + engtitle);
        String info = doc.selectFirst(".sect-story-movie").text();
        System.out.println("info = " + info);
        // doc.selectFirst(".select-first-movie");

        Element element = doc.selectFirst("#ctl00_PlaceHolderContent_Section_Still_Cut");
        Elements select = doc.select(".sect-base-movie .spec dt");
        for (Element e : select) {
            Elements es = e.getElementsContainingText("감독").next().select("a");
            for (Element e2 : es){
                String director = e2.text();
                String directorHref = e2.attr("href");
                String pidx = directorHref.substring(directorHref.lastIndexOf("=") + 1);

                System.out.println("director = " + director);
                System.out.println("directorHref = " + directorHref);
                System.out.println("pidx = " + pidx);
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
            }

            Elements es3 = e.getElementsContainingText("기본").next();
            for (Element e2 : es3){
                String runningTime = e2.text().split(", ")[1];
                String nation = e2.text().split(", ")[2];

                System.out.println("runningTime = " + runningTime);
                System.out.println("nation = " + nation);

            }

        }

        //Elements lis = element.select("li");
        Elements lis = element.select("img");
        for (Element e: lis) {
            System.out.println("e.attr(\"data-src\") = " + e.attr("data-src"));
            // System.out.println("e = " + e);
/*

            String href = e.selectFirst("a").attr("href");
            String date = e.selectFirst(".txt-info").text().replaceAll("개봉", "").trim();
            String imgSrc = e.selectFirst(".thumb-image img").attr("src");
            String imgAlt = e.selectFirst(".thumb-image img").attr("alt");
            String age = e.selectFirst("i.cgvIcon").text();
            String title = e.selectFirst(".box-contents strong.title").text();


            System.out.println("href = " + href);
            System.out.println("date = " + date);
            System.out.println("imgSrc = " + imgSrc);
            System.out.println("imgAlt = " + imgAlt);
            System.out.println("age = " + age);
            System.out.println("title = " + title);
*/

            // Document parse = Jsoup.parse(new URL("http://www.cgv.co.kr" + href), 2000);
            // System.out.println("parse = " + parse);

            // break;
        }

        /*
        Elements select = doc.select(".sect-movie-chart");
        System.out.println("select.size() = " + select.size());
        System.out.println("select = " + select);

        for (int i = 0; i < select.size(); i++) {
            Element e = select.get(i);
            System.out.println("e = " + e);
        }
        */

        // 한줄씩 str에 추가함
        /*
        URL url = new URL("http://www.cgv.co.kr/movies/");
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"));
        String str = "";
        while((str = br.readLine()) != null){
            System.out.println("str = " + str);
        }
        */
    }
}
