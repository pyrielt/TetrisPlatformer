package Modele;

public enum Shapes {
    ERROR(false,Colors.ERROR),
    NONE(false, Colors.NONE),
    SQUARE(true, Colors.YELLOW),
    T(true, Colors.MAGENTA),
    LINE(true, Colors.CYAN),
    L(true,Colors.ORANGE),
    J(true,Colors.ORANGE),
    S(true, Colors.GREEN),
    Z(true, Colors.GREEN);

    private final boolean isShapeValid;
    private final Colors color;
    public static final int NBVALIDSHAPE = getNbShapesValid();

    private static int getNbShapesValid(){
        int res=0;
        for (Shapes s: Shapes.values()) {
            if(s.isShapeValid){
                res++;
            }
        }
        return res;
    }

    Shapes(boolean _isGoodShape, Colors _color) {
        isShapeValid = _isGoodShape;
        color = _color;
    }

    public Colors color(){
        return color;
    }

    public static Shapes getValidShape(int _id){
        Shapes ret=ERROR;
        if(_id<0 || _id>=NBVALIDSHAPE){
            return ret;
        }
        _id++;
        for (Shapes s:Shapes.values()){
            if(s.isShapeValid){
                _id--;
            }
            if(_id==0){
                ret = s;
            }
        }
        return ret;
    }

    public static Shapes getRandomValidShape(){
        return getValidShape((int) (Math.random() * NBVALIDSHAPE));
    }

    public Case[] getCaseFromShape() {
        if(this==ERROR||this==NONE){
            return null;
        }
        Case[] parts = new Case[4];
        switch (this) { // Axes: L-/R+, U-/D+
            case SQUARE:
                parts[0] = new Case(new Coordinate(0, 0), color);
                parts[1] = new Case(new Coordinate(0, 1), color);
                parts[2] = new Case(new Coordinate(1, 1), color);
                parts[3] = new Case(new Coordinate(1, 0), color);
                break;
            case T:
                parts[0] = new Case(new Coordinate(-1, 0),color);
                parts[1] = new Case(new Coordinate(0, 0), color);
                parts[2] = new Case(new Coordinate(1, 0), color);
                parts[3] = new Case(new Coordinate(0, 1), color);
                break;
            case LINE:
                parts[0] = new Case(new Coordinate(-1, 0),color);
                parts[1] = new Case(new Coordinate(0, 0), color);
                parts[2] = new Case(new Coordinate(1, 0), color);
                parts[3] = new Case(new Coordinate(2, 0), color);
                break;
            case L:
                parts[0] = new Case(new Coordinate(0, 0), color);
                parts[1] = new Case(new Coordinate(0, 1), color);
                parts[2] = new Case(new Coordinate(0, 2), color);
                parts[3] = new Case(new Coordinate(1, 2), color);
                break;
            case J:
                parts[0] = new Case(new Coordinate(0, 0), color);
                parts[1] = new Case(new Coordinate(0, 1), color);
                parts[2] = new Case(new Coordinate(0, 2), color);
                parts[3] = new Case(new Coordinate(-1, 2),color);
                break;
            case S:
                parts[0] = new Case(new Coordinate(-1, 1),color);
                parts[1] = new Case(new Coordinate(0, 1), color);
                parts[2] = new Case(new Coordinate(0, 0), color);
                parts[3] = new Case(new Coordinate(1, 0), color);
                break;
            case Z:
                parts[0] = new Case(new Coordinate(-1, 0),color);
                parts[1] = new Case(new Coordinate(0, 0), color);
                parts[2] = new Case(new Coordinate(0, 1), color);
                parts[3] = new Case(new Coordinate(1, 1), color);
                break;
        }
        return parts;
    }
}
