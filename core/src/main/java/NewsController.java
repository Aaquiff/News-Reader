package sec;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.SwingUtilities;

/**
 * Responsible for downloading headlines periodically and manually by invoking
 * the plugins.
 */
public class NewsController {

    private final Object monitor = new Object();

    JList jListHeadlines, jListCurrentDownloads;
    ArrayList<NewsPlugin> plugins;

    MyExecutorService executorService = new MyExecutorService();
    DefaultListModel currentlyDownloadingListModel = new DefaultListModel();
    ArrayList<News> newsList = new ArrayList<>();

    /**
     * Overloaded Constructor. Constructor with 3 arguments
     *
     * @param pJListHeadlines JList that lists the headlines
     * @param pJListCurrentDownloads JList that lists the current downloads
     * @param pPlugins list of plugins that were loaded at startup
     */
    public NewsController(JList pJListHeadlines, JList pJListCurrentDownloads, ArrayList<NewsPlugin> pPlugins) {
        this.jListHeadlines = pJListHeadlines;
        this.jListCurrentDownloads = pJListCurrentDownloads;
        plugins = pPlugins;

        ScheduledUpdate();
    }

    /**
     * Start threads for each plugin. These threads will update periodically
     * according to the interval given in the plugin argument is a specifier
     * that is relative to the url argument.
     */
    public void ScheduledUpdate() {
        Timer timer = new Timer(true);
        //Iterate through each plugins loaded
        for (NewsPlugin plugin : plugins) {

            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    UpdatePlugin(plugin);
                }
            };
            timer.scheduleAtFixedRate(task, 0, plugin.GetUpdateFrequency() * 60000);
        }
    }

    /**
     * Manual UpdateAllPlugins all plugins. This method will schedule an update on all
     * plugins now
     */
    public void UpdateAllPlugins() {
        for (NewsPlugin plugin : plugins) {
            UpdatePlugin(plugin);
        }
    }

    public void UpdatePlugin(NewsPlugin plugin) {
        NewsDownloaderThread newsDownloaderThread
                = new NewsDownloaderThread(plugin, this);
        executorService.submit(newsDownloaderThread);
    }

    /**
     * Interrupt Downloads. Interrupt all the threads currently executing in the
 executorService service
     */
    public void Cancel() {
        executorService.interruptAllTasks();
    }

    /**
     * UpdateAllPlugins headlines JList. Takes an array list as an argument, update the
     * existing news array
     */
    public synchronized void updateHeadlinesList(ArrayList<News> pNewsList) {
        DefaultListModel dlm = new DefaultListModel();
        synchronized (monitor) {
            //Iterate and add only non existent news
            for (News n1 : pNewsList) {
                //Check if the news already exists
                boolean newsAlreadyExists = false;
                for (News n2 : newsList) {
                    if (n2.news.equals(n1.news) && n2.source.equals(n1.source)) {
                        newsAlreadyExists = true;
                    }
                }
                //News doesn't exist so add it to the list
                if (!newsAlreadyExists) {
                        newsList.add(n1);
                }
            }

            //Iterate and remove the non existing headlines
            for (News currentNews : newsList) {
                if (currentNews.source.equals(pNewsList.get(0).source)) {
                    boolean newsNoLongerAvailable = true;
                    for (News webNews : pNewsList) {
                        if (currentNews.news.equals(webNews.news)) {
                            newsNoLongerAvailable = false;
                            break;
                        }
                    }
                    if (newsNoLongerAvailable) {
                        newsList.remove(currentNews);
                    }
                }
            }

            Collections.sort(newsList);
            
            for (News n : newsList) {
                dlm.addElement(n);
            }
            UpdateSwingList(jListHeadlines, dlm);
        }
    }

    /**
     * SetDownloading.
     * Set the Url in the currently downloading list
     * @param url 
     */
    public void SetDownloading(String url) {
        //Update Currently Downloading list
        currentlyDownloadingListModel.addElement(url);
        UpdateSwingList(jListCurrentDownloads, currentlyDownloadingListModel);
    }

     /**
     * RemoveDownloading.
     * Remove Url from the currently downloading list
     * @param url 
     */
    public void RemoveDownloading(String url) {
        //Update Currently Downloading list
        currentlyDownloadingListModel.removeElement(url);
        UpdateSwingList(jListCurrentDownloads, currentlyDownloadingListModel);
    }

    /**
     * UpdateAllPlugins the model of the JList swing component. UpdateAllPlugins the swing JList
 control using SwingUtilities static class
     */
    private void UpdateSwingList(final JList theList, final DefaultListModel lm) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                theList.setModel(lm);
            }
        }
        );

    }

}
