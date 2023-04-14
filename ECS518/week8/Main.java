public class Main {

    /**
     * This program should create a number of Incrementer threads
     * at the same time.  All Incrementer threads should be modifying the same
     * shared Counter object. Because they are all running at the
     * same time, we should see some thread interference (race conditions) 
     * as they try to modify the same Counter.
     **/
    public static void main(String args[]) throws InterruptedException {
        int numThreads = Integer.parseInt(args[0]);        
        int numAdds = 200;
        Incrementer[] tasks = new Incrementer[numThreads];
        

        for (int i = 0; i < numThreads; i++) {
            tasks[i] = new Incrementer(i+1);
            tasks[i].start();
        }
        for (Incrementer task : tasks) {
            task.join();
        }

        int expectedCount = numAdds * numThreads;
        System.out.println("Expected count: " + expectedCount);
        System.out.println("Actual count: " + Incrementer.counter);
        System.out.println("Race condition: " + (expectedCount != Incrementer.counter ? "Yes" : "No"));
    }
}

