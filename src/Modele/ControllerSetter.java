package Modele;

public interface ControllerSetter {
    void pieceCurrentRotation(boolean direction); // 0 = counterclockwise; 1 = clockwise;
    void pieceCurrentMove(boolean direction); // 0 = left; 1 = right;
    void pieceCurrentGoDown(); // fall down 1 case

    void playerMove(boolean direction);
    void playerJump();
    void gameChangeState(TetrisGame.GameState _state);
    void gameReset(Model.GameType _gameType);
}
