package plugins;

import sec.*;

public class Nytimes extends NewsPlugin {
    
    @Override
    public String GetURL() {
        return "https://www.nytimes.com";
    }
    
    @Override
    public int GetUpdateFrequency() {
        return 20;
    }
}
