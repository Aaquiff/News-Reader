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

public abstract class NewsPlugin {

    public abstract String GetURL();
    
    public abstract int GetUpdateFrequency();

    public ArrayList<News> update() {
        String content = ReadHTMLPage(GetURL());
        return HTMLParser.Parse(GetURL(), content);
    }

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
