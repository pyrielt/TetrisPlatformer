package Modele;

public enum Colors {
    PLAYER(false,true,-3),
    ERROR(true,false,-2),
    NONE(false,false,-1),
    YELLOW(true,true,0),
    MAGENTA(true,true,1),
    CYAN(true,true,2),
    ORANGE(true,true,3),
    GREEN(true,true,4);
    private final boolean isColorBlocking;
    private final boolean canRowDelete;
    private final int id;
    public static final int NBVALIDCOLOR = getNbColorsValid();

    Colors(boolean _isColorBlocking, boolean _canRowDelete, int _id){
        isColorBlocking = _isColorBlocking;
        canRowDelete = _canRowDelete;
        id = _id;
    }

    private static int getNbColorsValid(){
        int res=0;
        for (Colors c: Colors.values()) {
            if(c.isColorBlocking){
                res++;
            }
        }
        return res;
    }

    public boolean isColorBlocking(){
        return isColorBlocking;
    }
    public boolean canRowDelete(){
        return canRowDelete;
    }

    public int id(){
        return id;
    }

    public static Colors getValidColor(int _id){
        Colors ret=ERROR;
        if(_id<0 || _id>=NBVALIDCOLOR){
            return ret;
        }
        _id++;
        for (Colors c:Colors.values()){
            if(c.isColorBlocking()){
                _id--;
            }
            if(_id==0){
                ret = c;
            }
        }
        return ret;
    }

    public static Colors getRandomValidColor(){
        return getValidColor((int) (Math.random() * NBVALIDCOLOR));
    }
}