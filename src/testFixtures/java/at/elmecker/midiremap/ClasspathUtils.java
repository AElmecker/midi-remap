package at.elmecker.midiremap;

import java.net.URISyntaxException;
import java.nio.file.Path;

public class ClasspathUtils {

    private ClasspathUtils() {
        throw new IllegalStateException("Utility class must not be instantiated");
    }

    public static Path pathOfResource(String path) {
        try {
            return Path.of(ClassLoader.getSystemResource(path).toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
