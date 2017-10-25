package plugins;

import sec.*;


public class Arstechnica extends NewsPlugin {
    
    @Override
    public String GetURL() {
        return "https://arstechnica.com/";
    }
    
    @Override
    public int GetUpdateFrequency() {
        return 15;
    }
}
