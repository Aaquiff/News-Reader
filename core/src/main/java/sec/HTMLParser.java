/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sec;

import models.News;
import java.awt.List;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;

/**
 *
 * @author aaralk
 */
public class HTMLParser {
    public static ArrayList<News> Parse(String source, String html)
    {
        ArrayList<News> news = new ArrayList<News>();
        
        ArrayList<News> n1 = Parse1(source,html,"h1");
        ArrayList<News> n2 = Parse1(source,html,"h2");
        ArrayList<News> n3 = Parse1(source,html,"h3");
        
        for(News n : n1)
        {
            news.add(n);
        }
        for(News n : n2)
        {
            news.add(n);
        }
        for(News n : n3)
        {
            news.add(n);
        }
        
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

