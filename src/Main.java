import java.util.FormatFlagsConversionMismatchException;

/**
 * Created by smele on 03.06.2017.
 */
public class Main {
    public static Object obj = new Object();

    public static char turn = 'A';


    public static void main(String[] args) {

        Thread aThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    aMethod();
                }
            }
        });
        Thread bThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    bMethod();
                }
            }
        });
        Thread cThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    cMethod();
                }
            }
        });
        aThread.start();
        bThread.start();
        cThread.start();

    }

    public static void aMethod() {
        synchronized (obj) {
            try {
                if (turn != 'A') obj.wait();
                System.out.println("A");
                Thread.sleep(1000);
                obj.notifyAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            turn = 'B';
        }
    }

    public static void bMethod() {
        synchronized (obj) {
            try {
                while (turn != 'B') obj.wait();
                System.out.println("B");
                Thread.sleep(1000);
                obj.notifyAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            turn = 'C';
        }
    }

    public static void cMethod() {
        synchronized (obj) {
            try {
                while (turn != 'C') obj.wait();
                System.out.println("C");
                Thread.sleep(1000);
                obj.notifyAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            turn = 'A';
        }
    }
}
