package sec;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.html.HTMLDocument;

public class Bbc extends NewsPlugin {
    
    @Override
    public String GetURL() {
        return "http://www.bbc.co.uk/news";
    }
    
    @Override
    public int GetUpdateFrequency() {
        return 10;
    }

    /*
    public void ParseHTMLUsingDOM(String html) {
        HTMLDocument doc = new HTMLDocument();
        
        try 
        {
            doc.setInnerHTML(doc.getDefaultRootElement(), html);
            //doc.insertString(0, html, new SimpleAttributeSet());
        } 
        catch (BadLocationException ex) {
            Logger.getLogger(Bbc.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Bbc.class.getName()).log(Level.SEVERE, null, ex);
        }
        Element rootElement = doc.getDefaultRootElement();
        
        int count = 0;
        ParseHTMLTree(rootElement,count);
    }

    public void ParseHTMLTree(Element element, int count) {
        String name = element.getName();
        int x = count + 1;
        System.out.println("Level " + count + " , Name : " + name);
        for (int i = 0; i < element.getElementCount(); i++) {
            Element el = element.getElement(i);
            ParseHTMLTree(el,x);
        }
    }

    public void ParseHTMLUsingStringMatching(String html) {
        String tag = "h1";

        while (html.contains("<h2")) {
            String temp = html.substring(html.indexOf("<h2"), html.indexOf("</h2"));
            temp = temp.substring(temp.indexOf(">") + 1, temp.length());
            System.out.println(temp);
            html = html.substring(html.indexOf("</h2") + 1, html.length());
        }
    }

    public void WriteStringToFile(String content) {
        BufferedWriter bw;
        try {
            bw = new BufferedWriter(new FileWriter("output.txt"));
            bw.write(content);
            System.out.println("Page Written");
        } catch (IOException ex) {
            Logger.getLogger(Bbc.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    */
}
