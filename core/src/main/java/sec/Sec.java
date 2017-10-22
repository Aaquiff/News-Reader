package sec;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Sec {

    public static void main(String[] args) {
         Scanner scanner = new Scanner(System.in);
        ArrayList<NewsPlugin> plugins = new ArrayList<>();
        PluginLoader pl = new PluginLoader();

        System.out.println("Enter plugins to load (0 to exit) : ");
        
        try {
//            String pluginName = scanner.next();
//            NewsPlugin newsPlugin = null;
//            while (!pluginName.equals("n")) {                
//                newsPlugin = pl.loadPlugin(pluginName);
//                plugins.add(newsPlugin);
//                System.out.println(newsPlugin.update().size());
//                pluginName = scanner.next();
//            }
            plugins.add(pl.loadPlugin("Bbc"));
            plugins.add(pl.loadPlugin("Nytimes"));
            plugins.add(pl.loadPlugin("Arstechnica"));          
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Sec.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("*******************************");
        for (NewsPlugin np : plugins) {
            System.out.println("Loaded : " + np.GetURL());
        }
        System.out.println("*******************************");
        
        MainWindow mainWindow = new MainWindow(plugins);
        mainWindow.setVisible(true);
    }
}
