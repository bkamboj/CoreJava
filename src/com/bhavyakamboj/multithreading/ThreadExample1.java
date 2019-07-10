package com.bhavyakamboj.multithreading;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

class CustomRunnable implements Runnable {
    private final long countUntil;
    private Logger logger;

    CustomRunnable(long countUntil,Logger logger) {
        this.countUntil = countUntil;
        this.logger = logger;
    }

    @Override
    public void run() {
        long sum = 0;
        for (long i = 1; i < countUntil; i++) {
            sum += i;
        }
        System.out.println(sum);
        logger.info("Sum "+sum);
    }
}
public class ThreadExample1 {

    public static void main(String[] args) throws SecurityException, IOException {
    	boolean append = true;
        FileHandler handler = new FileHandler("default.log", append);
 
        Logger logger = Logger.getLogger("multithreading");
        logger.addHandler(handler);
        // We will store the threads so that we can check if they are done
        List<Thread> threads = new ArrayList<Thread>();
        // We will create 500 threads
        for (int i = 0; i < 500; i++) {
            Runnable task = new CustomRunnable(i,logger);
            Thread worker = new Thread(task);
            // We can set the name of the thread
            worker.setName(String.valueOf(i));
            // Start the thread, never call method run() direct
            worker.start();
            logger.info("Added worker "+i);
            // Remember the thread for later usage
            threads.add(worker);
        }
        int running = 0;
        do {
            running = 0;
            for (Thread thread : threads) {
                if (thread.isAlive()) {
                    running++;
                }
            }
            logger.info("We have " + running + " running threads. ");
            System.out.println("We have " + running + " running threads. ");
        } while (running > 0);

    }
}