package VueControleur;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.concurrent.Executor;

public class KeyController implements Runnable {

    private final Executor ex;
    private final int delay;
    VC vc;
    KeyHandler[] keyHandlers;
    KeyActor keyActor;

    public static final int[] KEYVALID = {
            KeyEvent.VK_A,
            KeyEvent.VK_E,
            KeyEvent.VK_Q,
            KeyEvent.VK_D,
            KeyEvent.VK_S,
            KeyEvent.VK_UP,
            KeyEvent.VK_LEFT,
            KeyEvent.VK_RIGHT,
            KeyEvent.VK_ESCAPE,
            KeyEvent.VK_P};

    public KeyController(VC _vc, int _delay, Executor _ex) {
        vc = _vc;
        delay = _delay;
        ex = _ex;
        KeyboardFocusManager manager =
                KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                return keyboardClick(e);
            }
        });

        keyHandlers = new KeyHandler[KEYVALID.length];
        for (int i = 0; i < keyHandlers.length; i++) {
            keyHandlers[i] = new KeyHandler(KEYVALID[i],this);
        }
        keyActor = new KeyActor(this,vc);
    }

    public void run(){
        for (KeyHandler keyHandler: keyHandlers) {
            if(keyHandler.isActivated()){
                keyHandler.update(delay);
            }
        }
    }

    public boolean isKeyActivated(int keyCode){
        if(isKeyCodeInvalid(keyCode)){
            return false;
        }
        return keyHandlers[getIdFromKey(keyCode)].isActivated();
    }

    public static boolean isKeyCodeInvalid(int keyCode){
        return (Arrays.stream(KEYVALID).noneMatch(i -> i == keyCode));
    }

    public static int getIdFromKey(int keyCode){
        int id = -1;
        if(isKeyCodeInvalid(keyCode)){
            return id;
        }

        int i = 0;
        while (i<KEYVALID.length && id<0){
            if(KEYVALID[i]==keyCode){
                id=i;
            }
            i++;
        }
        return id;
    }

    private boolean keyPressed(int keyCode){
        int id = getIdFromKey(keyCode);
        boolean ret = false;
        if(id>=0){
            ret = keyHandlers[id].activate();
        }
        return ret;
    }

    private boolean keyReleased(int keyCode){
        int id = getIdFromKey(keyCode);
        boolean ret = false;
        if(id>=0){
            ret = keyHandlers[id].deactivate();
        }
        return ret;
    }

    public void keyAction(int key){
        keyActor.keyAction(key);
    }

    public boolean keyboardClick(KeyEvent e) {
        boolean ret = false;
        if(isKeyCodeInvalid(e.getKeyCode())){
            return ret;
        }

        switch (e.getID()){
            case KeyEvent.KEY_PRESSED:
                //ret = keyPressed(e.getKeyCode());
                ex.execute(new Runnable() {
                    @Override
                    public void run() {
                        keyPressed(e.getKeyCode());
                    }
                });
                ret = true;
                break;
            case KeyEvent.KEY_RELEASED:
                //ret = keyReleased(e.getKeyCode());
                ex.execute(new Runnable() {
                    @Override
                    public void run() {
                        keyReleased(e.getKeyCode());
                    }
                });
                ret = true;
                break;
            case KeyEvent.KEY_TYPED:
            default:
                break;
        }
        return ret;
    }

    public void deactivateAll(){
        for (KeyHandler k:keyHandlers) {
            k.deactivate();
        }
    }
}
