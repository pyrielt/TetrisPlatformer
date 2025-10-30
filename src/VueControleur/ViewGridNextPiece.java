package VueControleur;

import Modele.Coordinate;
import Modele.Piece;
import Modele.ViewGetter;

import java.awt.*;

public class ViewGridNextPiece extends ViewGrid{

    protected void defaultBackground(Graphics g, Dimension dim){
        g.drawImage(ImagesGetter.getImageBackGround(1),
                0, 0, dim.width, dim.height, null);
    }

    public ViewGridNextPiece(ViewGetter _modele) {
        super(_modele, 5, 5);
        Dimension dim = new Dimension(SIZE *width, SIZE *height);
        setSize(dim);
        setMaximumSize(dim);

        Dimension dimf = dim;
        canvas = new Canvas() {
            private final Dimension dimgrid = dimf;
            public void paint(Graphics g) {
                defaultBackground(g,dimgrid);
                Piece p = model.getNextPiece().getClone();
                p.setCoordinate(new Coordinate(2,2));
                paintPiece(g,p);
            }
        };

        canvas.setPreferredSize(dim);
        add(canvas);
    }
}
