
/**
 * Lab 9: Thread Barrier
 * Barrier Class
 *
 * Processes join the barrier and are held until barrierSize processes have
 * joined.
 *
 *
 */
public class Barrier {

    /**
     * Size of the barrier, which is the minimum number of processes to proceed.
     */
    private int barrierSize;
    private int threadCount = 0;
    private int releaseCount = -1;

    /**
     * Create a barrier of a given size
     *
     * @param size
     */
    public Barrier(int size) {
        barrierSize = size;
        System.out.println("Barrier size = " + barrierSize);
    }

    /**
     * Processes join at barrier and either wait or are allowed past.
     *
     * @param p The process joining
     */
    public void joinBarrier(Process p) {

        // add code here
        try {
            synchronized (this) {
                // Set Process ID
                p.id = threadCount;
                System.out.println(p.getName() + " waiting on barrier " + p.id);
                threadCount++;
   
                if (threadCount == barrierSize) {
                    // Start opening the barrier once there is barrierSize amount of threads in the barrier
                    releaseCount++;
                    notifyAll();
                } 

                // Block thread until received barrier pass signal
                while (releaseCount != p.id) {
                    wait();
                }

                // Received barrier pass signal
                System.out.println(p.getName() + " passed the barrier " + p.id);
                releaseCount++;
                notifyAll();

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
