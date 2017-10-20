package sec;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.html.HTMLDocument;

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
