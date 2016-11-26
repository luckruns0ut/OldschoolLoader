import javax.swing.*;
import java.awt.*;

/**
 * Created by jordan on 14/10/15.
 */
public class MapViewer extends JFrame {
    private MapCanvas mapCanvas;

    public MapViewer() {
        super("Map viewer");
        setLayout(new BorderLayout());
        mapCanvas = new MapCanvas();
        add(mapCanvas);
        pack();
        setVisible(true);
    }
}
