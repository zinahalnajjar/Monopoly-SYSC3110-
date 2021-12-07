import java.io.*;

/**
 * @author Zinah
 */

/**
 * method to write the status of the game into a file
 */
public class FileUtil {
    public static void writeToFile(Object object, String filePath) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath));
        oos.writeObject(object);
        oos.flush();
        oos.close();
    }

    /**
     * method to read the saved status from the file
     * @param filePath
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */

    public static Object readFromFile(String filePath) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath));
        Object object = ois.readObject();

        ois.close();
        return object;
    }
}
