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

        String pluginName = scanner.next();
        try {
            NewsPlugin newsPlugin = null;
            while (!pluginName.equals("N")) {                
                newsPlugin = pl.loadPlugin(pluginName);
                plugins.add(newsPlugin);
                System.out.println(newsPlugin.update().size());
                break;
                //pluginName = scanner.next();
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Sec.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (NewsPlugin np : plugins) {
            System.out.println("Loaded : " + np.GetURL());
        }

        MainWindow mainWindow = new MainWindow(plugins);
        mainWindow.setVisible(true);
    }
}
