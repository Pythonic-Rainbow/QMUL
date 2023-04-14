import java.util.Random;

/**
 * Lab 9: Thread Barrier
 * 
 * Main class
 * 
 */
public class Main {

    private static int threadCount;
    private static final int barrierSize = 10 ;
    private static final int sleepTime = 500 ;
	
    /**
    * Main program
    *    Create a barrier
    *    Create multiple instances of Process and run them in threads.
    *	 threadCount should be provided as command line argument when you run Main
    * @param args
    */
    public static void main(String[] args) {
        threadCount = Integer.parseInt(args[0]);
		                
        // Create a random source for randomly setting the sleep time of the 
        //  process instances
        Random r = new Random() ;
 
        // Print out the number of threads
        System.out.println("Number of threads = " + threadCount);
 
        // Create the barrier
        Barrier barrier = new Barrier(barrierSize) ;

        // Create and start the process threads
        // There are threadCount threads
        
        // Add code here
        for (int i = 0; i < threadCount; i++) {
            new Thread(new Process(barrier, i+1, r.nextInt(sleepTime))).start();
        }
    }
}
