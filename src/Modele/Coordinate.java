package Modele;

public class Coordinate {
    public int x;
    public int y;

    public static Coordinate newCoordCopy (Coordinate c){
        Coordinate ret = null;
        if(Coordinate.isValid(c)){
            ret = new Coordinate(c.x,c.y);
        }
        return ret;
    }

    public Coordinate(int _x, int _y){
        x=_x;
        y=_y;
    }

    public Coordinate(Vec2D v){
        x+=(int) v.x;
        y+=(int) v.y;
    }

    public Coordinate change(Coordinate c){
        if(c!=null){
            x=c.x;
            y=c.y;
        }
        return this;
    }

    public Coordinate change(int _x, int _y){
        x=_x;
        y=_y;
        return this;
    }

    public Coordinate add(Coordinate c){
        x+=c.x;
        y+=c.y;
        return this;
    }

    public static boolean isValid(Coordinate c){
        return c!=null;
    }
}
