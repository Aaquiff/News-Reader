package sec;

public class Bbc extends NewsPlugin {
    
    @Override
    public String GetURL() {
        return "http://www.bbc.co.uk/news";
    }
    
    @Override
    public int GetUpdateFrequency() {
        return 10;
    }

}
