package Modele;

import java.util.Observable;
import java.util.Observer;

public class Model extends Observable
        implements Runnable, ViewGetter,ControllerSetter, Observer {
    private final TetrisGame gameSolo;
    private final TetrisDuo gameDuo;
    private TetrisGame gameCurrent;

    public enum GameType {
        TETRISSOLO,TETRISDUO
    }

    public Model() {
        gameSolo = new TetrisGame();
        gameDuo = new TetrisDuo();
        gameCurrent=gameSolo;
        gameSolo.addObserver(this);
        gameDuo.addObserver(this);
    }

    private void changeCurrent(GameType gs){
        gameCurrent = switch (gs) {
            case TETRISSOLO -> gameSolo;
            case TETRISDUO -> gameDuo;
        };
    }

    @Override
    public void pieceCurrentRotation(boolean direction) {
        gameCurrent.pieceCurrentRotation(direction);
    }

    @Override
    public void pieceCurrentMove(boolean direction) {
        gameCurrent.pieceCurrentMove(direction);
    }

    @Override
    public void pieceCurrentGoDown() {
        gameCurrent.pieceCurrentGoDown();
    }

    @Override
    public void gameChangeState(TetrisGame.GameState _state) {
        gameCurrent.gameChangeState(_state);
    }

    @Override
    public void gameReset(GameType _gameType) {
        changeCurrent(_gameType);
        gameSolo.gameReset(GameType.TETRISSOLO);
        gameDuo.gameReset(GameType.TETRISDUO);
    }

    @Override
    public Grid getGrid() {
        return gameCurrent.getGrid();
    }

    @Override
    public Piece getCurrentPiece() {
        return gameCurrent.getCurrentPiece();
    }

    @Override
    public Piece getNextPiece() {
        return gameCurrent.getNextPiece();
    }

    @Override
    public int getScore() {
        return gameCurrent.getScore();
    }

    @Override
    public TetrisGame.GameState getGameState() {
        return gameCurrent.getGameState();
    }

    @Override
    public void run() {
        gameCurrent.run();
    }

    @Override
    public void update(Observable o, Object arg) {
        setChanged();
        notifyObservers();
    }

    @Override
    public void playerMove(boolean direction) {
        gameCurrent.playerMove(direction);
    }

    @Override
    public void playerJump() {
        gameCurrent.playerJump();
    }

    @Override
    public Player getPlayer() {
        return gameCurrent.getPlayer();
    }
}
