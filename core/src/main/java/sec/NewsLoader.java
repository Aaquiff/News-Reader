/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sec;

import models.News;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;

/*
*   NewsLoader class is responsible for updating headlines periodically by invoking the plugins
*/
public class NewsLoader implements Runnable {

    JList jListHeadlines, jListCurrentDownloads;
    ArrayList<NewsPlugin> plugins;
    ExecutorService executor = Executors.newCachedThreadPool();
    ArrayList<Future<ArrayList<News>>> futureNewsArray = new ArrayList<Future<ArrayList<News>>>();
    ArrayList<String> currentlyDownloadingURLs = new ArrayList<String>();

    public NewsLoader(JList pJListHeadlines, JList pJListCurrentDownloads, ArrayList<NewsPlugin> pp) {
        this.jListHeadlines = pJListHeadlines;
        this.jListCurrentDownloads = pJListCurrentDownloads;
        plugins = pp;
    }

    /*
    *   Update all plugins 
    */
    public void Update() {
        ArrayList<News> news = new ArrayList<>();
        for (NewsPlugin plugin : plugins) {
            
            
            
            futureNewsArray.add(executor.submit(new Callable<ArrayList<News>>() {
                public ArrayList<News> call() {
                    System.out.println("Starting Thread");
                    
                    //Update Currently Downloading list
                    currentlyDownloadingURLs.add(plugin.GetURL());
                    DefaultListModel model = new DefaultListModel();
                    for (String url : currentlyDownloadingURLs)
                        model.addElement(url);
                    updateLabel(jListCurrentDownloads,model);
                    
                    //Update the plugin and get News objects returned
                    ArrayList<News> newsList = plugin.update();
                    
                    currentlyDownloadingURLs.remove(plugin.GetURL());
                    DefaultListModel model2 = new DefaultListModel();
                    for (String url : currentlyDownloadingURLs)
                        model2.addElement(url);
                    updateLabel(jListCurrentDownloads,model2);
                    
                    System.out.println("Ending Thread");
                    return newsList;
                }
            }));
            
        }

        for (Future<ArrayList<News>> f : futureNewsArray) {
            try {
                System.out.println("Result : " + f.get().size());
                for (News n : f.get()) {
                    news.add(n);
                }
            } catch (CancellationException ex) {
                System.out.println("CancellationException: Thread Cancelled");
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
                Logger.getLogger(NewsLoader.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExecutionException ex) {
                System.out.println(ex.getMessage());
                Logger.getLogger(NewsLoader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        DefaultListModel lm = new DefaultListModel();
        for (News n : news) {
            lm.addElement(n.source + ": " + n.news + " (" + n.time.toString() + ")");
        }
        updateLabel(jListHeadlines, lm);
        futureNewsArray.clear();
    }

    public void Cancel() {
        for (Future<ArrayList<News>> f : futureNewsArray) {
            f.cancel(true);
        }
    }

    public void UpdateOld() {
        ArrayList<News> news = new ArrayList<>();       
        
        for (NewsPlugin plugin : plugins) {
            ArrayList<News> allNews = plugin.update();
            for(News n : allNews) {
                news.add(n);
            }
        }
        
        DefaultListModel lm = new DefaultListModel();
        for (News n : news) {
            lm.addElement(n.source + ": " + n.news + " (" + n.time.toString() + ")");
        }
        updateLabel(jListHeadlines, lm);
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
