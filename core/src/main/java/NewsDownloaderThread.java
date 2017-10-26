package sec;

import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.concurrent.CancellationException;
import java.util.logging.Level;


public class NewsDownloaderThread implements Runnable {

    private final static Logger logger = Logger.getLogger(NewsDownloaderThread.class.getName());
    NewsController newsController;
    NewsPlugin plugin;

    public NewsDownloaderThread(NewsPlugin pPlugin, NewsController nc) {
        newsController = nc;
        plugin = pPlugin;
    }

    @Override
    public void run() {
        try {

            //Update Currently Downloading list
            newsController.SetDownloading(plugin.GetURL());

            logger.log(Level.INFO, "Starting Download ");
            
            //Update the plugin and get News objects returned
            ArrayList<News> newsList = plugin.update();
            
            logger.log(Level.INFO, "Downloaded ");
            
            if (Thread.interrupted()) {
                throw new InterruptedException();
            }
            
            newsController.updateHeadlinesList(newsList);

        } catch (InterruptedException ex) {
            logger.log(Level.INFO, "Thread Interrupted ");
        } catch (CancellationException ex) {
            logger.log(Level.INFO, "Thread Cancelled ");
        } catch(Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage());
        }
        finally {
            //Update Currently Downloading list
            newsController.RemoveDownloading(plugin.GetURL());
        }
    }

}
