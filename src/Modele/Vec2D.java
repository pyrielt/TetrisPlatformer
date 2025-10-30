package Modele;

public class Vec2D {
    public double x, y;

    public Vec2D(double _x, double _y)
    { x = _x; y = _y; }

    public Vec2D(Vec2D v) {
        x = v.x;
        y = v.y;
    }

    public Vec2D add(Vec2D vec){
        x += vec.x;
        y += vec.y;
        return this;
    }

    public Vec2D mult(double factor){
        x *= factor;
        y *= factor;
        return this;
    }

    public Vec2D mult(double fx, double fy){
        x *= fx;
        y *= fy;
        return this;
    }

    public Vec2D getCopy(){
        return new Vec2D(x,y);
    }

    @Override
    public boolean equals(Object obj) {
        boolean ret = obj instanceof Vec2D;
        if(ret){
            ret = ((Vec2D) obj).x == x;
            ret = ret && ((Vec2D) obj).y == y;
        }
        return ret;
    }

    @Override
    public String toString()
    {
        return ("X: " + x + "  |  Y: " + y);
    }
}
