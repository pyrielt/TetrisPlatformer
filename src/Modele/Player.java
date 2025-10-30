package Modele;

import static java.lang.Math.*;

public class Player {
    // Position
    private final Vec2D pos;
    // Speed
    private final Vec2D speed;

    private static final double SPEED_ACCEL_X = 0.05;
    private static final double SPEED_DECEL_X = 0.01;
    
    public static final Vec2D MAX_SPEED = new Vec2D(0.05, 0.122);
    public static final double JUMP_STRENGTH = 0.122;
    public static final double GRAVITY = 0.0025;

    private static final Vec2D SIZE = new Vec2D(0.5, 0.5);
    public static Vec2D SIZE(){
        return SIZE.getCopy();
    }

    private boolean isOnGround;
    private final GridDuo grid;


    public Player(GridDuo _grid) {
        grid = _grid;
        pos = new Vec2D(2,2);
        speed = new Vec2D(0,0);
        isOnGround = false;
    }

    public boolean isPositionValid(){
        boolean ret = grid.placePlayer(getPos());
        grid.removePlayer();
        return ret;
    }

    public Vec2D getPos() {
        return pos;
    }

    public void move(boolean dir){
        speed.x = (dir ?
                Math.min(speed.x + SPEED_ACCEL_X,
                        MAX_SPEED.x)
                : Math.max(speed.x - SPEED_ACCEL_X,
                        - MAX_SPEED.x));
    }

    public void jump(){
        if(isOnGround)
        {
            isOnGround = false;
            speed.y = -JUMP_STRENGTH;
        }
    }

    public void update(){
        if(speed.x!=0){
            int dir = speed.x>0?1:-1;
            if(isOnGround){
                if(dir*speed.x> SPEED_DECEL_X){
                    speed.x -= SPEED_DECEL_X *dir;
                }else{
                    speed.x=0;
                }
            }
            if(dir*speed.x>MAX_SPEED.x){
                speed.x = dir*MAX_SPEED.x;
            }
        }

        if(speed.y!=0){
            int dir = speed.y>0?1:-1;
            if(dir*speed.y>MAX_SPEED.y){
                speed.y = dir*MAX_SPEED.y;
            }
        }

        handleCollision();
        pos.add(speed);
        if(speed.y!=0){
            isOnGround=false;
        }
        speed.add(new Vec2D(0,GRAVITY));
    }

    //COLLISION
    private double maxTimeMouvment(double pos, double size, double speed){
        double limit = 0.0000005;
        if(abs(speed)<=limit){
            return -1;
        }
        if(floor(pos)!=floor(pos+size+limit)||floor(pos)!=floor(pos-size-limit))
        {
            return -2;
        }

        double border = speed>0?ceil(pos)-size:floor(pos)+size;
        double ret = abs((border-pos)/speed);
        if(ret>1){
            ret = -3;
        }
        return ret;
    }


    private void changePositionSpeedCollision(boolean direction){//0 = ver
        boolean mul = (direction&&speed.x>0) || (!direction&&speed.y>0);
        if(direction){
            if(speed.x!=0){
                pos.x = mul?ceil(pos.x):floor(pos.x);
                pos.x-=(SIZE().x/2)*(mul?1:-1);
                pos.x-=mul?0.01:-0.01;
                speed.x = 0;
            }
        }else{
            if(speed.y!=0) {
                if(speed.y>0){
                    isOnGround=true;
                }
                pos.y = mul ? ceil(pos.y) : floor(pos.y);
                pos.y -= (SIZE().y / 2) * (mul ? 1 : -1);
                pos.y -= mul ? 0.01 : -0.01;
                speed.y = 0;
            }
        }
    }


    private boolean isNextInBloc(boolean[] bloc, boolean dir){
        double position = dir?pos.x:pos.y;
        double velocity = dir?speed.x:speed.y;
        double size = dir?SIZE().x:SIZE().y;
        size = size/2;
        boolean ret = false;
        int direction = (velocity >= 0) ? 0 : 1;

        if(bloc[direction]){
            direction = -direction*2 +1;
            double maxP = position + direction*size;
            int currentP = (int) floor(maxP);
            ret = (currentP!=(int) floor(maxP + velocity)
                    || currentP!=(int) floor(position));
        }
        return ret;
    }

    private boolean isPlayerInSingleCase(double pos, double size){
        return ((int) floor(pos-size/2) == (int) floor(pos+size/2));
    }

    public void handleCollision(){
        boolean[][] bGrid = grid.getBox9(pos);

        boolean hor = isPlayerInSingleCase(pos.x,SIZE.x);
        boolean ver = isPlayerInSingleCase(pos.y,SIZE.y);

        boolean horCol = false;
        boolean verCol = false;

        if(speed.x!=0&&hor){
            horCol = isNextInBloc
                    (new boolean[]{bGrid[3][2],bGrid[1][2]},
                true);
            if(horCol){
                changePositionSpeedCollision(true);
            }else if(!ver){
                int dir = (pos.y-((int)floor(pos.y))-0.5)>0?1:-1;
                horCol = isNextInBloc
                        (new boolean[]{bGrid[3][2+dir],bGrid[1][2+dir]},
                    true);
                if(horCol){
                    changePositionSpeedCollision(true);
                }
            }
        }

        if(speed.y!=0&&ver){
            verCol = isNextInBloc
                    (new boolean[]{bGrid[2][3],bGrid[2][1]},
                false);
            if(verCol){
                changePositionSpeedCollision(false);
            }else if(!hor){
                int dir = (pos.x-((int)floor(pos.x))-0.5)>0?1:-1;
                verCol = isNextInBloc
                        (new boolean[]{bGrid[2+dir][3],bGrid[2+dir][1]},
                    false);
                if(verCol){
                    changePositionSpeedCollision(false);
                }
            }
        }

        if(hor && ver && !verCol && !horCol){
            if(bGrid[2+(speed.x>0?1:-1)][2+(speed.y>0?1:-1)]){
                double limitX, limitY;
                limitX = maxTimeMouvment(pos.x,SIZE().x/2,speed.x);
                limitY = maxTimeMouvment(pos.y,SIZE().y/2,speed.y);
                if((limitX>limitY)&&limitY>=-0.5){
                    changePositionSpeedCollision(true);
                } else if((limitX<limitY)&&limitX>=-0.5){
                    changePositionSpeedCollision(false);
                }
            }
        }
    }
}
