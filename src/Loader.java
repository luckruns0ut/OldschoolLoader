import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

/**
 * Created by luckruns0ut on 02/05/15.
 */
public class Loader {
    private static HashMap<String, String> params = new HashMap<>();
    private static final String GAME_URL = "http://oldschool1.runescape.com/";
    private static final String GAMEPACK_LOC = "./data/clean.jar";

    private static float downloadPercent = 0;
    private static boolean isFinished = false;

    public static void load() {
        try {
            isFinished = false;
            downloadPercent = 0;

            String src = WebUtils.getPageSource(GAME_URL);
            findParams(src);


            //byte[] gamepack = downloadGamepack(GAME_URL + getGamepackName(src));
            //saveBytes(gamepack, GAMEPACK_LOC.replace("gamepack", "clean"));

            isFinished = true;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void findParams(String source) {
        String[] lines = source.split("\n");
        String paramPrefix = "param name=";
        String valuePrefix = "value=";

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
    }

    private static byte[] downloadGamepack(String url) throws IOException {
        System.out.println("Downloading gamepack... ");
        URLConnection connection = new URL(url).openConnection();

        int content = connection.getContentLength();
        ByteArrayOutputStream out;

        try (InputStream stream = connection.getInputStream()) {
            out = new ByteArrayOutputStream();
            byte[] buffer = new byte[2048];
            if (content == -1)
                content = stream.available();

            int read;

            while ((read = stream.read(buffer, 0, buffer.length)) != -1) {
                out.write(buffer, 0, read);

                if (content < out.size())
                    content = out.size();

                downloadPercent = Math.round(((float) out.size() / (float) content) * 100);
            }
        }

        out.flush();
        System.out.println("Done.");
        return out.toByteArray();
    }

    private static void saveBytes(byte[] gamepack, String path) throws IOException {
        File temp = new File(path);
        try (FileOutputStream out = new FileOutputStream(temp)) {
            out.write(gamepack);
        }
    }

    private static String getGamepackName(String source) {
        String archive = "archive=";
        int index = source.indexOf(archive);
        return source.substring(index + archive.length(), source.indexOf('\'', index));
    }

    public static HashMap<String, String> getParams() {
        return params;
    }

    public static String getGameURL() {
        return GAME_URL;
    }

    public static String getGameLocation() {
        return GAMEPACK_LOC;
    }

    public static float getDownloadProgress() {
        return downloadPercent;
    }

    public static boolean isFinished() {
        return isFinished;
    }
}
