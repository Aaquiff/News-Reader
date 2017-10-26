package sec;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * MyExecutorService.
 * Responsible for maintaining the thread pool or 
 * submitting and interrupting threads,
 */
public class MyExecutorService {
    
    ExecutorService service = Executors.newCachedThreadPool();
    private final BlockingQueue<Runnable> runnableQueue = new LinkedBlockingQueue();;
    ArrayList<Future<?>> futures = new ArrayList<>();
    
    public MyExecutorService() {
        
    }

    /**
     * submit.
     * Post a runnable task using the ExecutorService thread pool
     * @param runnable the runnable task to be run on a new thread
     * @return 
     */
    public Future<?> submit(Runnable runnable) {
        Future<?> future = service.submit(runnable);
        futures.add(future);
        return future;
    }
    
    /**
     * interruptAllTasks.
     * Interrupt all the current tasks being executed using the futures stored
     */
    public void interruptAllTasks() {
        for (Future<?> f : futures) {
            f.cancel(true);
        }
        futures.clear();
    }
}
