/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.Date;

/**
 *
 * @author aaralk
 */
public class News {

    public String source;
    public String news;
    public Date time;
    
    public News(String source, String news, Date time) {
        this.source = source;
        this.news = news;
        this.time = time;
    }
    
}
