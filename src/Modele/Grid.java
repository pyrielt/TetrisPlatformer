package Modele;

import java.util.Iterator;

public class Grid {
    protected final Colors[][] grid;
    public static int WIDTH =10;
    public static int HEIGHT=20;

    public Grid() {
        grid = new Colors[WIDTH][HEIGHT];
        resetGrid();
    }

    public static boolean isCoordCorrect(Coordinate c){
        return c.x>=0 && c.y>=0 && c.x< WIDTH && c.y<HEIGHT;
    }

    public Colors getCase(Coordinate c){
        if(isCoordCorrect(c)){
            return grid[c.x][c.y];
        }
        return Colors.ERROR;
    }

    public boolean isOccupied(Coordinate c){
        return getCase(c).isColorBlocking();
    }

    public boolean isLineFull(int y) {
        Coordinate coord = new Coordinate(0, 0);
        int i=0;
        boolean ret = true;
        while (i < WIDTH && ret){
            ret=canBeDeleted(coord.change(i, y));
            i++;
        }
        return ret;
    }

    public int lookForFullLines() {
        int rowNbDeleted=0;
        for(int i=0;i < HEIGHT;i++) {
            if(isLineFull(i)) {
                removeLine(i);
                for(int j = 0; j < i; j++) {
                    moveLine(i - j - 1, i - j);
                }
                rowNbDeleted++;
            }
        }
        return  rowNbDeleted;
    }

    protected void setCase (Colors color, Coordinate co){
        if(isCoordCorrect(co)){
            grid[co.x][co.y]=color;
        }
    }

    private void removeCase(Coordinate c){
        if(isOccupied(c)){
            setCase(Colors.NONE,c);
        }
    }

    private void removeLine (int height){
        Coordinate c = new Coordinate(0,height);
        if(isCoordCorrect(c)){
            for (int i = 0; i < WIDTH; i++) {
                removeCase(c.change(i,height));
            }
        }
    }
    private void moveLine(int oldHeight, int newHeight){
        Coordinate oCo = new Coordinate(0,oldHeight);
        Coordinate nCo = new Coordinate(0,newHeight);
        Colors ca;
        if(isCoordCorrect(oCo) && isCoordCorrect(nCo)){
            for (int i = 0; i < WIDTH; i++) {
                oCo.x=i;
                nCo.x=i;
                ca = getCase(oCo);
                setCase(ca,nCo);
                removeCase(oCo);
            }
        }
    }
    public boolean isEmptyAndValid(Coordinate _next) {
        return isCoordCorrect(_next) && !isOccupied(_next);
    }
    public boolean canBeDeleted(Coordinate _next) {
        return isCoordCorrect(_next) && getCase(_next).canRowDelete();
    }



    public boolean canPosePiece(Piece one_piece){
        if(one_piece==null)
        {
            return false;
        }
        Iterator<Case> iteratorCase = one_piece.iterator();
        int i=0;
        boolean ret = true;
        while (iteratorCase.hasNext() && ret) {
            iteratorCase.next();
            ret = isEmptyAndValid( one_piece.getPartRealCoord(i));
            i++;
        }
        return ret;
    }

    public void placePiece (Piece p){
        if(canPosePiece(p)){
            Iterator<Case> iteratorCase = p.iterator();
            int i=0;
            while (iteratorCase.hasNext()) {
                iteratorCase.next();
                setCase(p.getPart(i).color, p.getPartRealCoord(i));
                i++;
            }
        }
    }

    public boolean doesLastRawHasCase(){
        Coordinate c = new Coordinate(0,0);
        int i = 0;
        boolean ret = false;
        while (i<WIDTH && !ret) {
            c.x=i;
            ret=!isEmptyAndValid(c);
            i++;
        }
        return ret;
    }

    public void resetGrid(){
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                grid[i][j]=Colors.NONE;
            }
        }
    }
}
