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

/**
 * Uses Jsoup library to handle HTML parsing related functions.
 */
public class HTMLParser {
    
    /**
     * Parse HTML and create news objects.
     * Takes a url and the html and parses the html and returns ArrayList of News objects.
     * source is required to create the news objects.
     * Downloaded time is assumed to be the time the news object was created,
     * It may differ to the original downloading time but not by much.
     * @param source url of the website needed to create a news
     * @param html html content of the webpage to be parsed
     * @return List of news created from the parsed HTML
     */
    public static ArrayList<News> Parse(String source, String html){
        //ArrayList to return news
        ArrayList<News> news = new ArrayList<>();
        
        //Parse html document using Jsoup
        Document doc = Jsoup.parse(html);
        
        //Get all h1 tags and create a news elements
        GetNewsFromTag(doc,source,news,"h1");
        GetNewsFromTag(doc,source,news,"h2");
        GetNewsFromTag(doc,source,news,"h3");
        GetNewsFromTag(doc,source,news,"h4");
        GetNewsFromTag(doc,source,news,"h5");
        GetNewsFromTag(doc,source,news,"h6");
        return news;
    }
    
    /**
     * Adds news to the list by reading the html document using JSoup.
     * @param doc HTML Document
     * @param source url of the website
     * @param news List of news to be appended to
     * @param tag Tag to search for and extract text in the Document
     */
    private static void GetNewsFromTag(Document doc, String source, ArrayList<News> news, String tag) {
        Elements elements = doc.getElementsByTag(tag);
        for (Element e : elements) {
            News n = new News(source, e.text(), Date.from(Instant.now()));
            news.add(n);
        }
    }

}

