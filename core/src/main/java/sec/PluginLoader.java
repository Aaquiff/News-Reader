package sec;

import java.nio.file.*;

/*REFERENCE TAKEN FROM PRACTICAL SHEETS*/

public class PluginLoader extends ClassLoader {

    public static void main(String[] args) {
        try {
            PluginLoader pl = new PluginLoader();
            NewsPlugin a = pl.loadPlugin("AddPlugin");
            a.update();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    public NewsPlugin loadPlugin(String fname) throws ClassNotFoundException {
        try {
            
            String fileName = "classes/"+fname+".class";
            System.out.println("Path : " + Paths.get(fileName));
            
            System.out.println(fileName);
            byte[] classData = Files.readAllBytes(Paths.get(fileName));
            Class<?> cls = defineClass(null, classData, 0, classData.length);
            return (NewsPlugin) cls.newInstance();
        } catch (Exception e) {
            // Slightly naughty, but there's a huge range of potential exceptions.
            throw new ClassNotFoundException(
                    String.format("Could not load '%s': %s", fname, e.getMessage()), e);
        }
    }

}
