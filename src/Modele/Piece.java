package Modele;

import java.util.Iterator;

public class Piece extends Case implements Cloneable, Iterable<Case> {

    Shapes shape;

    private Case[] parts;
    private static final Coordinate DEFCOORD = new Coordinate(5,0);

    private void shapeReconstructor(){
        parts = shape.getCaseFromShape();
    }

    public Piece(Shapes s, Coordinate c) {
        super(c, s.color());
        shape = s;
        shapeReconstructor();
    }
    public Piece(Coordinate c){
        this(Shapes.getRandomValidShape(),c);
    }

    public Piece(){
        this(Coordinate.newCoordCopy(DEFCOORD));
    }

    private void rotate(int dir) { // -1 = Counter; 1 = Clockwise
        switch (shape){ // Axes: L-/R+, U-/D+
            case SQUARE:
                break;
            case T:
            case LINE:
            case L:
            case J:
            case S:
            case Z:
                Iterator<Case> iteratorCase = iterator();
                Case c;
                while (iteratorCase.hasNext()){
                    c=iteratorCase.next();
                    c.setCoordinate(new Coordinate(
                            -dir * (c.coordinate.y),
                            dir * (c.coordinate.x)));
                }
            default:
                break;
        }
    }
    public void rotate(int dir, Grid grid) {
        if(dir==0 || dir<-1 || dir>1 || grid==null){
            return;
        }

        Piece p=getClone();
        Piece p2;
        p.rotate(dir);
        p2 = p.getClone();
        Coordinate c = new Coordinate(0,0);
        int newX=0;
        int iter=0;
        boolean res = grid.canPosePiece(p2);

        while(!res && iter<4){
            iter++;
            newX*=-1;
            if(newX>=0){
                newX++;
            }

            c.change(newX,0);
            p2 = p.getClone();
            p2.move(c,grid);
            res = grid.canPosePiece(p2);
        }

        if(res){
            rotate(dir);
            move(c,grid);
        }
    }

    public void move(Coordinate coordToAdd, Grid grid) {
        if(coordToAdd==null||grid==null){
            System.exit(-1);
        }
        if(coordToAdd.x==0 && coordToAdd.y==0){
            return;
        }

        Piece p=getClone();
        p.coordinate.add(coordToAdd);
        if(grid.canPosePiece(p)){
            coordinate.add(coordToAdd);
        }
    }

    public Case getPart(int id) { return parts[id]; }
    public Coordinate getPartRealCoord(int id) {
        Coordinate c = Coordinate.newCoordCopy(parts[id].coordinate);
        return c.add(coordinate);
    }
    @Override
	public Object clone() throws CloneNotSupportedException
    {
        Piece p = null;
        try {
            p = (Piece) super.clone();
        } catch(CloneNotSupportedException cnse) {
            cnse.printStackTrace(System.err);
        }

        if(p == null) {
            System.exit(-1);
        }

        if(parts!=null){
            p.parts = new Case[4];
            Iterator<Case> iteratorCase = p.iterator();
            int i=0;
            while (iteratorCase.hasNext()){
                iteratorCase.next();
                if(parts[i]!=null){
                    p.parts[i] = (Case) parts[i].clone();
                }
                else{
                    p.parts[i] = null;
                }
                i++;
            }
        }

	    return p;
    }

    public Piece getClone(){
        Piece p=null;
        try {
            p = (Piece) clone();
        } catch(CloneNotSupportedException cnse) {
            cnse.printStackTrace(System.err);
        }

        if(p == null) {
            System.exit(-1);
        }
        return p;
    }

    public void change(Piece pNew){
        if(!Piece.isValid(pNew)){
            return;
        }
        super.change(pNew);
        parts = pNew.parts;
        shape = pNew.shape;
    }

    public boolean isValid(){
        boolean ret = super.isValid();
        Iterator<Case> iteratorCase = iterator();
        Case c;
        while (iteratorCase.hasNext()){
            c=iteratorCase.next();
            ret= Case.isValid(c);
        }
        return ret;
    }

    @Override
    public Iterator<Case> iterator() {
        Piece t = this;
        return new Iterator<>() {
            private int current = -1;
            private final Piece piece = t;

            @Override
            public boolean hasNext() {
                return current < piece.parts.length - 1;
            }

            @Override
            public Case next() {
                current++;
                return piece.getPart(current);
            }
        };
    }
}
