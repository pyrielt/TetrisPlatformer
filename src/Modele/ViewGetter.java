package Modele;

public interface ViewGetter {
    Grid getGrid();
    Piece getCurrentPiece();
    Piece getNextPiece();

    Player getPlayer();
    int getScore();
    TetrisGame.GameState getGameState();
}
