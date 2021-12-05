import java.io.*;

/**
 * @author Zinah
 */
public class FileUtil {
    public static void writeToFile(Object object, String filePath) throws IOException {
//        try {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath));
        oos.writeObject(object);
        oos.flush();
        oos.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public static Object readFromFile(String filePath) throws IOException, ClassNotFoundException {
//        try {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath));
        Object object = ois.readObject();

        ois.close();
        return object;
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//            return null;
//        }
    }
}
