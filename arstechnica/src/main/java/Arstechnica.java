package sec;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.html.HTMLDocument;

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
