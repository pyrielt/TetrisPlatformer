package Modele;

public class  OrdonnanceurSimple extends Thread {

    public Runnable myRunnable;
    private int delay;



    public OrdonnanceurSimple(Runnable _monRunnable, int _delay) {
        myRunnable = _monRunnable;
        delay = _delay;

    }

    public void changeDelay(int newDelay){
        delay = newDelay;
    }

    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myRunnable.run();
        }
    }
}
