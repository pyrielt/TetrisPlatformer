package VueControleur;

import Modele.*;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class VC extends JFrame implements Observer, ControllerSetter, ViewGetter {
    private final Executor ex =  Executors.newSingleThreadExecutor();
    private final KeyController keyController;
    private final View view;
    public final static int COEF = 21;
    static long lastTime = System.currentTimeMillis();

    final Model model;


    private void initFrame(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Tetris Platformer");
        setIconImage(ImagesGetter.getImageCase(Colors.getRandomValidColor()));
        setSize(33*COEF, 43*COEF);

        setContentPane(new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(ImagesGetter.getImageBackGround(2),
                        0, 0, getSize().width, getSize().height, null);
            }
        });

    }


    public VC(Model _modele) {
        initFrame();

        model = _modele;
        keyController = new KeyController(this, 5, ex);
        view = new View(this);
        add(view.createPanel(COEF));

        setLocationRelativeTo(null);
        setVisible(true);
        setFocusable(true);

        new OrdonnanceurSimple(new Runnable() {
            @Override
            public void run() {
                synchronized (model){
                    keyController.run();
                    model.run();
                }
            }
        }, 5).start();
    }

    public Executor getEx(){
        return ex;
    }

    @Override
    public void update(Observable o, Object arg) {
        SwingUtilities.invokeLater(new Runnable() {
            //@Override
            public void run() {
                view.update(o,arg);
                lastTime = System.currentTimeMillis();
            }
        });
    }

    @Override
    public void pieceCurrentRotation(boolean direction) {
        model.pieceCurrentRotation(direction);
    }
    @Override
    public void pieceCurrentMove(boolean direction) {
        model.pieceCurrentMove(direction);
    }
    @Override
    public void pieceCurrentGoDown() {
        model.pieceCurrentGoDown();
    }
    @Override
    public void gameChangeState(TetrisGame.GameState _state) {
        model.gameChangeState(_state);
    }
    @Override
    public void gameReset(Model.GameType _gameType) {
        synchronized (model){
            model.gameReset(_gameType);
        }
    }

    @Override
    public Grid getGrid() {
        return model.getGrid();
    }
    @Override
    public Piece getCurrentPiece() {
        return model.getCurrentPiece();
    }
    @Override
    public Piece getNextPiece() {
        return model.getNextPiece();
    }
    @Override
    public int getScore() {
        return model.getScore();
    }
    @Override
    public TetrisGame.GameState getGameState() {
        return model.getGameState();
    }

    @Override
    public void playerMove(boolean direction) {
        model.playerMove(direction);
    }

    @Override
    public void playerJump() {
        model.playerJump();
    }

    @Override
    public Player getPlayer() {
        return model.getPlayer();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                Model m = new Model();
                VC vc = new VC(m);
                vc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                m.addObserver(vc);
                vc.setVisible(true);
                }
            }
        );
    }
}
