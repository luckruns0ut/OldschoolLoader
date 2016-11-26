import java.applet.Applet;
import java.applet.AppletContext;
import java.applet.AppletStub;
import java.awt.*;
import java.io.File;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by luckruns0ut on 02/05/15.
 */
public class GameStub implements AppletStub {
    private ClassLoader classLoader;
    private Applet applet;

    public void start() {
        try {
            Class c = loadClient();
            applet = (Applet)c.newInstance();
            applet.setStub(this);
            applet.init();
            applet.start();
            applet.setPreferredSize(new Dimension(765, 503));
            applet.setBounds(0,0,765,503);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private Class loadClient() throws MalformedURLException, ClassNotFoundException {
        classLoader = new URLClassLoader(new URL[] { new File(Loader.getGameLocation()).toURI().toURL()});
        return classLoader.loadClass("client");
    }

    public Applet getApplet() {
        return applet;
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public URL getDocumentBase() {
        try {
            return new URL(Loader.getGameURL());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public URL getCodeBase() {
        try {
            return new URL(Loader.getGameURL());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public String getParameter(String s) {
        return Loader.getParams().get(s);
    }

    @Override
    public AppletContext getAppletContext() {
        return null;
    }

    @Override
    public void appletResize(int i, int i1) {

    }
}
