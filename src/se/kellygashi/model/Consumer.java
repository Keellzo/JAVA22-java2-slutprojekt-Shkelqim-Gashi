package se.kellygashi.model;

import se.kellygashi.buffer.ProductionBuffer;

public class Consumer implements Runnable {
    private final ProductionBuffer buffer;
    private final int interval;
    private Boolean isRunning = true;

    /**
     * Consumer constructor injection that takes the buffer object and an interval as parameters.
     *
     * @param buffer   to update the units queue.
     * @param interval of how long the interval of consuming is.
     */
    public Consumer(ProductionBuffer buffer, int interval) {
        this.buffer = buffer;
        this.interval = interval;
    }

    /**
     * Thread is removing units and then sleeps based on the interval of the consumer object.
     */
    @Override
    public void run() {
        while (isRunning) {
            buffer.removeUnit();
            try {
                Thread.sleep(interval * 1000);
            } catch (InterruptedException e) {
                isRunning = false;
                throw new RuntimeException(e);
            }
        }
    }
}