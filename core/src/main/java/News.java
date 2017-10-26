package sec;

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
    
    @Override
    public String toString()
    {
        return String.format("%s %s %s", this.source, this.news, this.time.toString());
    }
    
}
