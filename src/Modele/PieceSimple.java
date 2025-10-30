package Modele;

public class PieceSimple extends Piece {

    private static final int DY = 1;

    private final Grid grid;

    public PieceSimple(Grid _grille) {
        super();
        grid = _grille;
    }
    public boolean action() {
        boolean ret = validateNextPos();
        if (ret) {
            move(new Coordinate(0, DY), grid);
        }
        return ret;
    }

    private boolean validateNextPos(){
        Piece nextPiece = getClone();
        nextPiece.coordinate.add(new Coordinate(0, DY));
        return grid.canPosePiece(nextPiece);
    }
}