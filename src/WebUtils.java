import java.util.HashMap;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

/**
 * Created by luckruns0ut on 02/05/15.
 */
public class WebUtils {
    /*
     * Downloads the data found at the given URL;
     * @return The bytes which were downloaded.
     */
    public static byte[] downloadBytes(String url) throws IOException {
        URLConnection connection = new URL(url).openConnection();
        ByteArrayOutputStream outputStream;

        try (InputStream inputStream = connection.getInputStream()) {
            outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[2048];

            int read;

            while ((read = inputStream.read(buffer, 0, buffer.length)) != -1) {
                outputStream.write(buffer, 0, read);
            }
        }

        outputStream.flush();
        return outputStream.toByteArray();
    }

    /*
     * Parses the parameters found at the given webpage.
     * @param source The source of the webpage which is to be parsed.
     * @return A HashMap containing the parameters found.
     */
    public static HashMap<String, String> getParams(String source) {
        String[] lines = source.split("\n");
        String paramPrefix = "param name=";
        String valuePrefix = "value=";
        HashMap<String, String> params = new HashMap<>();

        for (String line : lines) {
            if (!line.contains(paramPrefix))
                continue;

            int start = line.indexOf(paramPrefix) + paramPrefix.length() + 1;
            int end = line.indexOf('"', start);
            String name = line.substring(start, end);
            start = line.indexOf(valuePrefix) + valuePrefix.length() + 1;
            end = line.indexOf('"', start);
            String val = line.substring(start, end);
            params.put(name, val);
        }

        return params;
    }

    /*
     * Reads the source of a webpage.
     * @param url The URL from which to obtain the webpage source.
     * @return The source for the webpage found at the given URL.
     */
    public static String getPageSource(String url) throws IOException {
        String source;

        URL page = new URL(url);
        try (Scanner scanner = new Scanner(page.openStream())) {
            source = "";
            while (scanner.hasNext()) {
                source += scanner.nextLine() + '\n';
            }
        }
        return source;
    }
}
