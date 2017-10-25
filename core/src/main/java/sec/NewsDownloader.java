package sec;


import models.News;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.SwingUtilities;

/**
 * Responsible for downloading headlines periodically and manually by invoking
 * the plugins.
 */
public class NewsDownloader {

    JList jListHeadlines, jListCurrentDownloads;
    ArrayList<NewsPlugin> plugins;
    private final Object mutex = new Object();
    ExecutorService executor = Executors.newCachedThreadPool();
    ArrayList<Future<ArrayList<News>>> futureNewsArray = new ArrayList<>();
    DefaultListModel currentlyDownloadingListModel = new DefaultListModel();
    ArrayList<News> newsList = new ArrayList<>();

    /**
     * Overloaded Constructor. Constructor with 3 arguments
     *
     * @param pJListHeadlines JList that lists the headlines
     * @param pJListCurrentDownloads JList that lists the current downloads
     * @param pPlugins list of plugins that were loaded at startup
     */
    public NewsDownloader(JList pJListHeadlines, JList pJListCurrentDownloads, ArrayList<NewsPlugin> pPlugins) {
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
        for (NewsPlugin plugin : plugins) {
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        //Update Currently Downloading list
                        currentlyDownloadingListModel.addElement(plugin.GetURL());
                        UpdateSwingList(jListCurrentDownloads, currentlyDownloadingListModel);

                        //Update the plugin and get News objects returned
                        ArrayList<News> newsList = plugin.update();

                        updateHeadlinesList(newsList);

                        //Update Currently Downloading list
                        currentlyDownloadingListModel.removeElement(plugin.GetURL());
                        UpdateSwingList(jListCurrentDownloads, currentlyDownloadingListModel);

                        try {
                            Thread.sleep(plugin.GetUpdateFrequency() * 600);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(NewsDownloader.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            });
        }
    }

    /**
     * Manual Update all plugins. This method will schedule an update on all
     * plugins now
     */
    public void Update() {
        for (NewsPlugin plugin : plugins) {
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    //Update Currently Downloading list
                    currentlyDownloadingListModel.addElement(plugin.GetURL());
                    UpdateSwingList(jListCurrentDownloads, currentlyDownloadingListModel);

                    //Update the plugin and get News objects returned
                    ArrayList<News> newsList = plugin.update();

                    updateHeadlinesList(newsList);;

                    //Update Currently Downloading list
                    currentlyDownloadingListModel.removeElement(plugin.GetURL());
                    UpdateSwingList(jListCurrentDownloads, currentlyDownloadingListModel);
                }

            });
        }
    }

    /**
     * Interrupt Downloads. Interrupt all the threads currently executing in the
     * executor service
     */
    public void Cancel() {
        List<Runnable> shutdownNow = executor.shutdownNow();
    }

    /**
     * Update headlines JList. Takes an array list as an argument, update the
     * existing news array
     */
    private synchronized void updateHeadlinesList(ArrayList<News> pNewsList) {
        DefaultListModel dlm = new DefaultListModel();

        for (News n : pNewsList) {
            newsList.add(n);
        }

        for (News n : newsList) {
            dlm.addElement(n);
        }
        UpdateSwingList(jListHeadlines, dlm);
    }

    /**
     * Update the model of the JList swing component. Update the swing JList
     * control using SwingUtilities static class
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
