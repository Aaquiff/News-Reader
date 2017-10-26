package sec;

import java.util.ArrayList;
import java.util.concurrent.CancellationException;


public class NewsDownloaderThread implements Runnable {

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

            //Update the plugin and get News objects returned
            ArrayList<News> newsList = plugin.update();

            
            System.out.println("Test");
            if (Thread.interrupted()) {
                throw new InterruptedException();
            }
            
            newsController.updateHeadlinesList(newsList);

        } catch (InterruptedException ex) {
            System.out.println(Thread.currentThread().getId() + " Interrupted");
        } catch (CancellationException ex) {
            System.out.println("Cancelled");
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
        }
        finally {
            //Update Currently Downloading list
            newsController.RemoveDownloading(plugin.GetURL());
        }
    }

}
