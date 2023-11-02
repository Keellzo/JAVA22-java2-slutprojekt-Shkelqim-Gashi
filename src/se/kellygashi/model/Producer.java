package se.kellygashi.model;


import se.kellygashi.buffer.ProductionBuffer;

public class Producer implements Runnable {
    private final ProductionBuffer buffer;
    private int interval;
    private boolean isRunning = true;


    /**
     * Producer constructor that takes the buffer object and an interval as parameters.
     *
     * @param buffer   to update the units queue.
     * @param interval of how long the interval of producer is.
     */
    public Producer(ProductionBuffer buffer, int interval) {
        this.buffer = buffer;
        this.interval = interval;
    }

    /**
     * Thread is adding always one unit and then sleeps based on the interval of the producer object.
     */
    @Override
    public void run() {
        while (isRunning) {
            buffer.addUnit(1);
            try {
                Thread.sleep(interval * 1000);
            } catch (InterruptedException e) {
                isRunning = false;
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Getting the interval of current producer.
     *
     * @return returns the interval value.
     */
    public int getInterval() {
        return interval;
    }
}
