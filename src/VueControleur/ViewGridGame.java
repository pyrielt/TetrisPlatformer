package VueControleur;

import Modele.*;

import java.awt.*;

public class ViewGridGame extends ViewGrid{

    protected void defaultBackground(Graphics g, Dimension dim){
        g.drawImage(ImagesGetter.getImageBackGround(0),
                0, 0, dim.width, dim.height, null);
    }

    protected void drawGameOver(Graphics g, TetrisGame.GameState gs){
        if(gs==TetrisGame.GameState.GAMEOVER){
            g.drawImage(ImagesGetter.getImageBackGround(3),
                    0, 0, SIZE *width, SIZE *height, null);
        } else if (gs==TetrisGame.GameState.PAUSE){
            g.drawImage(ImagesGetter.getImageBackGround(6),
                    0, 0, SIZE *width, SIZE *height, null);
        }
    }

    public ViewGridGame(ViewGetter _modele, int _width, int _height) {
        super(_modele, _width, _height);
        Dimension dim = new Dimension(SIZE *width, SIZE *height);
        setSize(dim);

        Dimension finalDim = getSize();
        canvas = new Canvas() {
            private final Dimension dimgrid = finalDim;
            public void paint(Graphics g) {
                defaultBackground(g,dimgrid);
                paintPiece(g, model.getCurrentPiece());
                paintPlayer(g, model.getPlayer());
                paintGrid(g,model.getGrid());
                drawGameOver(g,model.getGameState());
            }
        };
        canvas.setPreferredSize(new Dimension(SIZE *width, SIZE *height));
        add(canvas);
    }

    private void paintPlayer(Graphics g, Player p){
        if(p != null){
            Coordinate co, ce;
            Vec2D size, pos;

            size = new Vec2D(Player.SIZE());
            pos = new Vec2D(p.getPos());

            co = new Coordinate(pos.mult(SIZE));
            ce = new Coordinate(size.mult(-SIZE *0.5));

            g.drawImage(ImagesGetter.getImageCase(Colors.PLAYER),
                    co.x + ce.x, co.y + ce.y,
                    (int) (SIZE *Player.SIZE().x), (int) (SIZE *Player.SIZE().y),
                    null);
        }
    }

    private void paintGrid(Graphics g, Grid grid){
        Coordinate c;
        g.setColor(Color.BLACK);
        c=new Coordinate(0,0);
        for (int i = 0; i < Grid.WIDTH; i++) {
            for (int j = 0; j < Grid.HEIGHT; j++) {
                c.change(i,j);
                if(grid.canBeDeleted(c) && grid.getCase(c)!=Colors.PLAYER){
                    paintCoord(c,g, model.getGrid().getCase(c));
                }
            }
        }
    }
}
