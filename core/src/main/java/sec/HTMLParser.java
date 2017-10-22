/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sec;

import models.News;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HTMLParser {
    public static ArrayList<News> Parse(String source, String html)
    {
        //ArrayList to return news
        ArrayList<News> news = new ArrayList<>();
        
        //Parse html document using Jsoup
        Document doc = Jsoup.parse(html);
        
        //Get all h1 tags and create a news elements
        Elements elements = doc.getElementsByTag("h1");
        for (Element e : elements) {
            News n = new News(source, e.text(), Date.from(Instant.now()));
            news.add(n);
            System.out.println(e.text());
        }
        
        //Get all h2 tags and create a news elements
        elements = doc.getElementsByTag("h2");
        for (Element e : elements) {
            News n = new News(source, e.text(), Date.from(Instant.now()));
            news.add(n);
            System.out.println(e.text());
        }
        
        //Get all h3 tags and create a news elements
        elements = doc.getElementsByTag("h3");
        for (Element e : elements) {
            News n = new News(source, e.text(), Date.from(Instant.now()));
            news.add(n);
            System.out.println(e.text());
        }
        
//        ArrayList<News> n1 = Parse1(source,html,"h1");
//        ArrayList<News> n2 = Parse1(source,html,"h2");
//        ArrayList<News> n3 = Parse1(source,html,"h3");
//        
//        for(News n : n1)
//        {
//            news.add(n);
//        }
//        for(News n : n2)
//        {
//            news.add(n);
//        }
//        for(News n : n3)
//        {
//            news.add(n);
//        }
        
        return news;
    }
    
    public static ArrayList<News> Parse1(String source, String html, String tag)
    {
        ArrayList<News> news = new ArrayList<>();
        
        while (html.contains("<"+tag)) {
            String temp = html.substring(html.indexOf("<"+tag), html.indexOf("</"+tag));
            temp = temp.substring(temp.indexOf(">") + 1, temp.length());
            news.add(new News(source,temp,Date.from(Instant.now())));

            html = html.substring(html.indexOf("</"+tag) + 1, html.length());
        }
        
        return news;
    }

}

