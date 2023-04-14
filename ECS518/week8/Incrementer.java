import java.util.concurrent.ThreadLocalRandom;

public class Incrementer extends Thread {
    private static final int numIncrements = 200;
    private String name;
    static int counter = 0;

    public Incrementer(int index) {
        this.name = "Thread " + index;
    }

    public void run() {
        // Does nothing useful for now
        // Make it increase the common counter 
        for (int i = 0; i < numIncrements; i++) {
            System.out.println(this.name + " " + i);
            increase(i);
            int randomNum = ThreadLocalRandom.current().nextInt(10, 100 + 1);
            try {
                Thread.sleep(randomNum);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void increase(int i) {
        //int currCount = counter;
        System.out.printf("%s %d increase\n", name, i);
        //counter = currCount + 1;
        
        synchronized(this) {
            counter++;
        }
    }
}
