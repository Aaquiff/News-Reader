package sec;

import java.nio.file.*;

/**
 * Load plugin classes. Used to load plugin classes to be used.
 */
public class PluginLoader extends ClassLoader {

    /**
     * Load plugin from class files. Loads a NewsPlugin type of class by reading
     * the .class files of the plugins specified
     *
     * @param fname name of the plugin class
     * @return NewsPlugin class
     * @throws ClassNotFoundException
     */
    public NewsPlugin loadPlugin(String fname) throws ClassNotFoundException {
        try {

            String fileName = "classes/" + fname + ".class";
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
