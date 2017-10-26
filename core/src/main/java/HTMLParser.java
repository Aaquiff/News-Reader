package sec;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

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
        
        ArrayList<News> n = Parse1(source,html,"h2");
        return n;
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

