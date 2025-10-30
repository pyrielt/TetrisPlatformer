package Modele;

public class GridDuo extends Grid{

    public boolean placePlayer(Vec2D v){
        boolean ret = true;
        Vec2D size;
        Vec2D vs;
        Coordinate cs;

        for (int i = 0; i < 4; i++) {
            size = Player.SIZE().mult(0.5);
            vs = size.mult((i%2)*2-1,(i-(i%2))-1).add(v);
            cs = new Coordinate(vs);
            if(!isOccupied(cs)){
                setCase(Colors.PLAYER,cs);
            }else{
                ret=false;
            }
        }
        return ret;
    }

    @Override
    public int lookForFullLines() {
        int ret = super.lookForFullLines();
        removePlayer();
        return ret;
    }

    public void removePlayer(){
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if(grid[i][j]==Colors.PLAYER){
                    grid[i][j]=Colors.NONE;
                }
            }
        }
    }
    public boolean[][] getBox9(Vec2D v){
        Coordinate c = new Coordinate(v);
        Coordinate add = new Coordinate(0,0);
        boolean[][] ret = new boolean[5][5];
        for (int i = -2; i <= 2; i++) {
            for (int j = -2; j <= 2; j++) {
                add.change(i,j);
                ret[i+2][j+2] = getCase(add.add(c)).isColorBlocking();
            }
        }
        return  ret;
    }
}
