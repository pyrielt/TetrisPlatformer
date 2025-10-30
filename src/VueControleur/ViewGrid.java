package VueControleur;

import Modele.*;

import javax.swing.*;

//import org.w3c.dom.css.RGBColor;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Observable;
import java.util.Observer;

public abstract class ViewGrid extends JPanel implements Observer {
    protected final ViewGetter model;
    protected final static int SIZE = 42;
    protected final int width;
    protected final int height;
    protected Canvas canvas;

    public ViewGrid(ViewGetter _modele, int _width, int _height) {
        width=_width;
        height=_height;
        model = _modele;
        setOpaque(false);
    }

    @Override
    public void update(Observable o, Object arg) {

        BufferStrategy bs = canvas.getBufferStrategy();
        if(bs == null) {
            canvas.createBufferStrategy(2);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        canvas.paint(g);
        g.dispose();
        bs.show();
    }

    public static void paintCoord(Coordinate co, Graphics g, Colors color){
        g.drawImage(ImagesGetter.getImageCase(color),
                co.x* SIZE, co.y * SIZE,
                SIZE, SIZE,
                null);
    }

    public static void paintPiece(Graphics g, Piece p){
        Coordinate c;
        for(int i = 0;i<4;i++){
            c=p.getPartRealCoord(i);
            paintCoord(c,g, p.getPart(i).getColor());
        }
    }
}
