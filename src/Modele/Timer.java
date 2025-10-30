package Modele;

public class Timer {
    private int initialMaxTime;
    private int currentMaxTime;
    private int currentTime;
    public Timer(int _initialMaxTime) {
        reset(_initialMaxTime);
    }
    public void reset(int newInitialMaxTime){
        initialMaxTime=newInitialMaxTime;
        currentMaxTime=initialMaxTime;
        currentTime=0;
    }

    public void reset(){
        reset(initialMaxTime);
    }

    public boolean tick(){
        boolean ret=false;
        currentTime++;
        if(isOutOfTime()){
            passCurrent();
            ret=true;
        }
        return ret;
    }
    public void passCurrent(){
        currentTime=0;
    }

    private boolean isOutOfTime(){
        return currentTime>=currentMaxTime;
    }

    public void updateDifficulty(){
        //if we want to change the difficulty, it can be done here
    }
}
