import java.io.*;

/**
 * @author Zinah
 */
public class FileUtil {
    public static void writeToFile(Object object, String filePath) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath));
        oos.writeObject(object);
        oos.flush();
        oos.close();
    }

    public static Object readFromFile(String filePath) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath));
        Object object = ois.readObject();

        ois.close();
        return object;
    }
}
