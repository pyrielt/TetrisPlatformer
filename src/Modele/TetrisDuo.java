package Modele;

public class TetrisDuo extends TetrisGame {
    private Player player;
    private final GridDuo gridDuo;
    public TetrisDuo() {
        super(new GridDuo(), new Timer(50));
        gridDuo = (GridDuo) grid;
        player= new Player(gridDuo);
    }

    public void checkPlayer(){
        if(!player.isPositionValid()){
            gameChangeState(GameState.GAMEOVER);
        }
    }
    @Override
    public void  placeCurrentPiece() {
        gridDuo.placePlayer(player.getPos());
        super.placeCurrentPiece();
        checkPlayer();
    }

    @Override
    public void run() {
        super.run();
        if(state==GameState.PLAYING){
            player.update();
            checkPlayer();
        }
        easyNotify();
    }

    @Override
    protected void reset() {
        super.reset();
        player = new Player(gridDuo);
    }

    @Override
    public void playerMove(boolean direction) {
        if(state==GameState.PLAYING){
            player.move(direction);
            easyNotify();
        }
    }

    @Override
    public void playerJump() {
        if(state==GameState.PLAYING){
            player.jump();
            easyNotify();
        }
    }

    @Override
    public Player getPlayer() {
        return player;
    }
}
