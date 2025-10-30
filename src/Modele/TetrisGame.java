package Modele;

import java.util.Observable;


public class TetrisGame extends Observable
        implements Runnable, ViewGetter, ControllerSetter {
    public enum GameState {
        STARTING,PAUSE,PLAYING,GAMEOVER
    }
    protected GameState state= GameState.STARTING;

    protected final Grid grid;
    protected final PieceSimple pieceCurrent;
    protected Piece pieceNext = new Piece();
    protected final Timer timer;

    protected int score = 0;

    public TetrisGame(Grid _grid,Timer _timer ) {
        grid=_grid;
        pieceCurrent=new PieceSimple(grid);
        timer=_timer;
    }

    public TetrisGame() {
        this(new Grid(),new Timer(50));
    }

    public void action() {
        if(state==GameState.PLAYING){
            boolean ret = pieceCurrent.action();
            if (!ret){
                placeCurrentPiece();
                timer.updateDifficulty();
            }
            easyNotify();
        }
    }



    public void run() {
        //System.out.println("here");
        if(state== GameState.PLAYING){
            boolean res = timer.tick();
            if(res){
                action();
            }
        }
    }

    public void easyNotify(){
        setChanged();
        notifyObservers();
    }

    public void changePiece(){
        if(grid.canPosePiece(pieceNext)){
            pieceCurrent.change(pieceNext);
            pieceNext = new Piece();
        }
        else{
            gameChangeState(GameState.GAMEOVER);
        }
    }

    protected void addScore(int addScore){
        if(state== GameState.PLAYING){
            score+=addScore;
        }
    }

    protected void placeCurrentPiece(){
        int scoreadd;
        grid.placePiece(pieceCurrent);

        changePiece();
        scoreadd = grid.lookForFullLines();
        if(scoreadd>0){
            scoreadd*=(int)Math.floor(Math.sqrt(scoreadd)*10);
        }
        if(grid.doesLastRawHasCase()){
            gameChangeState(GameState.GAMEOVER);
        }
        addScore(scoreadd+1);
    }

    protected void reset(){
        grid.resetGrid();
        changePiece();
        changePiece();
        score=0;
        timer.reset();
    }

    @Override
    public void pieceCurrentRotation(boolean direction) {
        if(state== GameState.PLAYING){
            pieceCurrent.rotate(direction?-1:1, grid);
            easyNotify();
        }
    }
    @Override
    public void pieceCurrentMove(boolean direction) {
        if(state== GameState.PLAYING){
            pieceCurrent.move(new Coordinate(direction?1:-1,0),grid);
            easyNotify();
        }
    }
    @Override
    public void pieceCurrentGoDown() {
        if(state== GameState.PLAYING){
            timer.passCurrent();
            action();
        }
    }
    @Override
    public void gameChangeState(GameState _state) {
        if(state==GameState.PAUSE && _state==GameState.PAUSE){
            _state=GameState.PLAYING;
        }
        if(    (// Can't start new game if playing
                state!=GameState.PLAYING&&_state==GameState.STARTING
                // Can't pause if GAME OVER
                ||state!=GameState.GAMEOVER&&_state==GameState.PAUSE
                // Can't play if GAME OVER
                ||state!=GameState.GAMEOVER&&_state==GameState.PLAYING
                // Everything can be a GAME OVER
                ||_state==GameState.GAMEOVER)
            && (// Not same state or new game
                state!=_state||_state==GameState.STARTING)){

            state = _state;
            if(state==GameState.STARTING){
                reset();
            }
            easyNotify();
        }
    }
    @Override
    public void gameReset(Model.GameType _gameType) {
        if(state==GameState.PLAYING){
            gameChangeState(GameState.PAUSE);
        }
        gameChangeState(GameState.STARTING);
        easyNotify();
    }

    @Override
    public Grid getGrid() {
        return grid;
    }
    @Override
    public Piece getCurrentPiece() {
        return pieceCurrent;
    }
    @Override
    public Piece getNextPiece() {
        return pieceNext;
    }
    @Override
    public int getScore() {
        return score;
    }
    @Override
    public GameState getGameState() {
        return state;
    }

    @Override
    public void playerMove(boolean direction) {}

    @Override
    public void playerJump() {}

    @Override
    public Player getPlayer() {
        return null;
    }
}
