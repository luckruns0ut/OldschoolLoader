import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by jordan on 14/10/15.
 */
public class MapCanvas extends Canvas implements MouseMotionListener, MouseListener, KeyListener {
    private Image map = null;

    public MapCanvas() {
        setPreferredSize(new Dimension(640, 480));
        try {
            map = ImageIO.read(new File("./data/map.png"));
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        addMouseMotionListener(this);
        addMouseListener(this);
        addKeyListener(this);
    }

    public int mapX = 0;
    public int mapY = 0;

    @Override
    public void paint(Graphics g) {
        doPaint(g);
    }

    @Override
    public void repaint() {
        doPaint(super.getGraphics());
    }

    private void doPaint(Graphics g) {
        if(g != null && map != null) {
            //g.fillRect(0,0, getWidth(), getHeight());
            g.drawImage(map, mapX, mapY, null);

            int relX = lastX - mapX;
            int relY = (5376 - lastY) + mapY;

            int calcX = relX/3 + 1983;
            int calcY = relY/3 + 2432;

            g.setColor(Color.yellow);

            for(Point p : points)
                g.drawRect(p.x + mapX, p.y + mapY, 3, 3);

            g.drawString("Mouse position: (" + (calcX) + ", " + ( calcY), 50, 50);
        }
    }

    public int lastX;
    public int lastY;
    public boolean mouseDown;

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        lastX = mouseEvent.getX();
        lastY = mouseEvent.getY();
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        mouseDown = true;
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        mouseDown = false;
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
        lastX = mouseEvent.getX();
        lastY = mouseEvent.getY();
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
        lastX = mouseEvent.getX();
        lastY = mouseEvent.getY();
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        mapX -= lastX - mouseEvent.getX();
        mapY -= lastY - mouseEvent.getY();

        lastX = mouseEvent.getX();
        lastY = mouseEvent.getY();
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        lastX = mouseEvent.getX();
        lastY = mouseEvent.getY();
        if(mouseEvent.isControlDown()) {
            points.add(new Point(lastX - lastX%3 - mapX, lastY - lastY%3 - mapY));
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    ArrayList<Point> points = new ArrayList<>();

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if(keyEvent.isControlDown()) {
            points.clear();
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
