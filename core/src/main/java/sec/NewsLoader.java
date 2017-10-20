/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sec;

import models.News;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.SwingUtilities;


public class NewsLoader implements Runnable {
    JList list;
    ArrayList<News> news;
    ArrayList<NewsPlugin> plugins;

    public NewsLoader(JList jl, ArrayList<NewsPlugin> pp) {
        this.list = jl;
        news = new ArrayList<>();
        plugins = pp;
    }
    
    public void Update() {
        news.clear();
            
        for (NewsPlugin plugin : plugins) {
            ArrayList<News> allNews = plugin.update();
            for(News n : allNews) {
                news.add(n);
            }
        }
        
        DefaultListModel lm = new DefaultListModel();
        for (News n : news) {
            System.out.println(n.source);
            lm.addElement(n.source + ": " + n.news + " (" + n.time.toString() + ")");
        }
        updateLabel(list, lm);
    }

    @Override
    public void run() {
        Update();
    }

    private void updateLabel(final JList theList, final DefaultListModel lm) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                theList.setModel(lm);
            }
        }
        );

    }

}
