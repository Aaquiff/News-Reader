package sec;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ClosedByInterruptException;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;
import models.News;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Abstract classes to be inherited by the plugin. To implement a plugin,
 * inherit from this abstract classes and implement the methods to get the URL
 * and the Update frequency.
 */
public abstract class NewsPlugin {

    /**
     * Get the URL of the website the plugin points.
     *
     * @return the URL of the plugin
     */
    public abstract String GetURL();

    /**
     * Get the update frequency of the plugin.
     *
     * @return update frequency of the plugin
     */
    public abstract int GetUpdateFrequency();

    /**
     * Update the plugin. Update the plugin and get the news from the website.
     *
     * @return ArrayList of the news from the plugin website
     */
    public ArrayList<News> update() {
        String content = ReadHTMLPage(GetURL());
        return HTMLParser.Parse(GetURL(), content);
    }

    /**
     * Download and read the html page into a string.
     *
     * @param urlString URL of the website for the plugin
     * @return html content of the website
     */
    public String ReadHTMLPage(String urlString) {
        StringBuilder sb = new StringBuilder("");

        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException ex) {
            Logger.getLogger(NewsPlugin.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (ReadableByteChannel chan = Channels.newChannel(url.openStream())) {
            ByteBuffer buf = ByteBuffer.allocate(65536);
            byte[] array = buf.array();

            int bytesRead = chan.read(buf);
            while (bytesRead != -1) {
                byte[] temp = new byte[bytesRead];
                temp = Arrays.copyOfRange(array, 0, bytesRead - 1);
                sb.append(new String(temp, Charset.forName("UTF-8")));

                buf.clear();
                bytesRead = chan.read(buf);
            }

        } catch (ClosedByInterruptException ex) {

        } catch (IOException ex) {
            Logger.getLogger(NewsPlugin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sb.toString();
    }
}
