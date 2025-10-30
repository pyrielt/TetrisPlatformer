package VueControleur;

public class KeyHandler {
    private final int ownKey;
    private boolean isActivated;
    private int timepassfromlast;
    private final KeyController keyController;
    public static final int DEFAULT_MAX_TIME = -1;
    public static final int[] MAX_TIME ={200,200,125,125,75,
            10,10,10};

    public static int getMaxTime(int key){
        if(KeyController.isKeyCodeInvalid(key)
                || KeyController.getIdFromKey(key)<0
                || KeyController.getIdFromKey(key)>=MAX_TIME.length){
            return DEFAULT_MAX_TIME;
        }
        return MAX_TIME[KeyController.getIdFromKey(key)];
    }

    public KeyHandler(int _key, KeyController _keyController) {
        ownKey = _key;
        keyController = _keyController;
        isActivated = false;
        timepassfromlast = 0;
    }

    private void resetTimer() {
        timepassfromlast = 0;
    }

    public boolean isActivated(){
        return isActivated;
    }

    public boolean activate(){
        boolean ret = !isActivated;
        if(ret){
            isActivated=true;
            timepassfromlast=2*getMaxTime(ownKey);
        }
        return ret;
    }

    public boolean deactivate(){
        boolean ret = isActivated;
        if(ret){
            isActivated=false;
        }
        return ret;
    }

    private void action(){
        keyController.keyAction(ownKey);
        resetTimer();
    }

    public void update(int time){
        if(!isActivated){
            return;
        }

        if(timepassfromlast>getMaxTime(ownKey) && getMaxTime(ownKey)>0 ){
            action();
        } else if (timepassfromlast<getMaxTime(ownKey) && getMaxTime(ownKey)<0 ) {
            action();
        } else {
            timepassfromlast+=time;
        }
    }
}
