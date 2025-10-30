package VueControleur;

import Modele.TetrisGame;

import java.awt.event.KeyEvent;

public class KeyActor {
    private final KeyController keyController;
    private final VC vc;

    public KeyActor(KeyController _keyController, VC _vc) {
        keyController = _keyController;
        vc = _vc;
    }

    public static int getReverse(int key){
        int keyReverse = -1;
        switch (key){
            case KeyEvent.VK_A:
                keyReverse=KeyEvent.VK_E;
                break;
            case KeyEvent.VK_E:
                keyReverse=KeyEvent.VK_A;
                break;

            case KeyEvent.VK_Q:
                keyReverse=KeyEvent.VK_D;
                break;
            case KeyEvent.VK_D:
                keyReverse=KeyEvent.VK_Q;
                break;

            case KeyEvent.VK_LEFT:
                keyReverse=KeyEvent.VK_RIGHT;
                break;
            case KeyEvent.VK_RIGHT:
                keyReverse=KeyEvent.VK_LEFT;
                break;

            default:
                break;
        }
        return keyReverse;
    }

    public void keyAction(int key){
        startGame(key);
        int keyReverse = getReverse(key);
        if(keyReverse>=0) {
            if (keyController.isKeyActivated(keyReverse)) {
                return;
            }
        }
        keySwitchAction(key);
    }

    private void startGame(int key){
        if(vc.getGameState()!= TetrisGame.GameState.PLAYING){
            switch (key) {
                case KeyEvent.VK_A:
                case KeyEvent.VK_E:
                case KeyEvent.VK_Q:
                case KeyEvent.VK_D:
                case KeyEvent.VK_S:
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_UP:
                    vc.gameChangeState(TetrisGame.GameState.PLAYING);
                    break;
                default:
                    break;
            }
        }
    }

    private void keySwitchAction(int key){
        switch (key){
            case KeyEvent.VK_ESCAPE:
            case KeyEvent.VK_P:
                //System.out.println("pause");
                vc.gameChangeState(TetrisGame.GameState.PAUSE);
                keyController.deactivateAll();
                break;
            case KeyEvent.VK_A:
                vc.pieceCurrentRotation(false);
                break;
            case KeyEvent.VK_E:
                vc.pieceCurrentRotation(true);
                break;

            case KeyEvent.VK_Q:
                vc.pieceCurrentMove(false);
                break;
            case KeyEvent.VK_D:
                vc.pieceCurrentMove(true);
                break;

            case KeyEvent.VK_S:
                vc.pieceCurrentGoDown();
                break;

            case KeyEvent.VK_LEFT:
                vc.playerMove(false);
                break;
            case KeyEvent.VK_RIGHT:
                vc.playerMove(true);
                break;

            case KeyEvent.VK_UP:
                vc.playerJump();
                break;

            default:
                break;
        }
    }
}
