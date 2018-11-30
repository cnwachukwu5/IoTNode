import java.io.File;

public class FileReadFromResourceDir {

    public File getFile(String filename) throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource(filename).getFile());
    }

}