import java.io.*;
import java.util.FormatFlagsConversionMismatchException;

/**
 * Created by smele on 03.06.2017.
 */
public class Main {
    public static Object obj = new Object();
    public static char turn = 'A';

    public static int printCount = 0;
    public static int scanCount = 0;

    public static Object printLock = new Object();
    public static Object scanLock = new Object();


    public static void main(String[] args) {

        Thread aThread = new Thread(() -> {
            try {

                for (int i = 0; i < 5; i++) aMethod();

                for (int i = 0; i < 10; i++) {
                    writeFile("ThreadA " + i + "\n");
                    Thread.sleep(20);
                }

                printer(10, "Отдел А");
                scanner(5, "Отдел А");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread bThread = new Thread(() -> {
            try {

                for (int i = 0; i < 5; i++) bMethod();

                for (int i = 0; i < 10; i++) {
                    writeFile("ThreadB " + i + "\n");
                    Thread.sleep(20);
                }

                printer(5, "Отдел Б");
                scanner(15, "Отдел Б");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread cThread = new Thread(() -> {
            try {

                for (int i = 0; i < 5; i++) cMethod();

                for (int i = 0; i < 10; i++) {
                    writeFile("ThreadC " + i + "\n");
                    Thread.sleep(20);
                }

                printer(10, "Отдел В");
                scanner(20, "Отдел В");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        aThread.start();
        bThread.start();
        cThread.start();
    }

// По памяти получилось как в методичке.

    public static void aMethod() {
        synchronized (obj) {
            try {

                while (turn != 'A') obj.wait();
                System.out.println("A");
                turn = 'B';
                obj.notifyAll();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void bMethod() {
        synchronized (obj) {
            try {

                while (turn != 'B') obj.wait();
                System.out.println("B");
                obj.notifyAll();
                turn = 'C';

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void cMethod() {
        synchronized (obj) {
            try {

                while (turn != 'C') obj.wait();
                System.out.println("C");
                turn = 'A';
                obj.notifyAll();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void writeFile(String outText) {
        try (OutputStreamWriter outWriter = new OutputStreamWriter(new FileOutputStream("test.txt", true), "UTF-8")) {

            outWriter.write(outText);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printer(int pages, String o) {
        synchronized (printLock) {
            try {
                for (int i = 0; i < pages; i++) {
                    printCount++;
                    System.out.println("Отпечатано " + printCount + " страниц. Документ отдела " + o);
                    Thread.sleep(50);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void scanner(int pages, String o) {
        synchronized (scanLock) {
            try {
                for (int i = 0; i < pages; i++) {
                    scanCount++;
                    System.out.println("Отсканированно " + scanCount + " страниц. Документ отдела " + o);
                    Thread.sleep(50);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
