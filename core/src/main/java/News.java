package sec;

import java.util.Date;

/**
 *
 * @author aaralk
 */
public class News implements Comparable<News>{

    public String source;
    public String news;
    public Date time;
    
    public News(String source, String news, Date time) {
        this.source = source;
        this.news = news;
        this.time = time;
    }
    
    @Override
    public String toString() {
        return String.format("%s - %s - %s", this.source, this.news, this.time.toString());
    }

    @Override
    public int compareTo(News o) {
        if(time.after(o.time))
            return -1;
        else if (time.before(o.time))
            return 1;
        else
            return 0;
            
    }

    
}
