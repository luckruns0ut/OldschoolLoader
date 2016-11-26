import java.io.IOException;

/**
 * Created by luckruns0ut on 09/06/15.
 */
public class Stats {
    /*
     * @param username The username of the player to look up.
     * @return a long[] containing all of the highscore data for a given username.
     */
    public static long[] getHighscoreData(String username) throws IOException {
        long[] data = new long[48];
        String response = WebUtils.getPageSource("http://services.runescape.com/m=hiscore_oldschool/index_lite.ws?player=" + username.toLowerCase());
        response = response.replaceAll("\n", "");
        String[] split = response.split(",");
        for(int i = 0; i < 48; i++) {
            data[i] = Long.parseLong(split[i]);
        }
        return data;
    }

    /*
     * @param username The username of the player to look up.
     * @return an int[] containing the levels of every stat for a given username.
     */
    public static int[] getHighscoreStats(String username) throws IOException {
        int[] stats = new int[23];
        long[] data = getHighscoreData(username);
        int index = 0;
        for(int i = 3; i < 48; i += 2) {
            stats[index] = (int)data[i];
            index++;
        }
        return stats;
    }

    /*
     * @param username The username of the player to look up.
     * @param stat The specific stat to check the level of
     * @return the level of a given stat for a given player
     */
    public static int getHighscoreStat(String username, Stat stat) throws IOException {
        int[] stats = getHighscoreStats(username);
        return stats[stat.getIndex()];
    }
}