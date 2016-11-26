
import javax.swing.*;
import java.awt.*;

/**
 * Created by luckruns0ut on 02/05/15.
 */
public class Window extends JFrame {
    GameStub game;
    public static void main(String[] args) {
        new Window();
    }

    public Window() {
        new MapViewer();
        Loader.load();
        game = new GameStub();
        game.start();
        setLayout(new BorderLayout());
        add(game.getApplet());
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        setTitle("ScapeRuneScape");

        Object o = game.getApplet().getComponentAt(20, 20);

        while (o == null) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
