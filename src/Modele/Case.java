package Modele;

public class Case implements Cloneable{
    protected Coordinate coordinate;
    protected Colors color;

    public Case(Coordinate _coordinate, Colors _color) {
        color = _color;
        coordinate = _coordinate;
    }
    public Colors getColor() {
        return color;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate c) {
        coordinate.change(c);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Case c = null;

        try {
            c = (Case) super.clone();
        } catch(CloneNotSupportedException cnse) {
            cnse.printStackTrace(System.err);
        }

        if(!Case.isValid(c)) {
            System.exit(-1);
        }

        c.coordinate = Coordinate.newCoordCopy(coordinate);

        return c;
    }

    public boolean isValid(){
        return color.isColorBlocking() && coordinate != null;
    }

    public void change(Case cNew){
        if(!Case.isValid(cNew)){
            return;
        }
        color = cNew.color;
        coordinate.change(cNew.coordinate);
    }

    public static boolean isValid(Case c){
        if(c==null){
            return false;
        }
        return c.isValid();
    }
}
