package se.kellygashi.buffer;

import java.util.LinkedList;

import java.util.Queue;

public class ProductionBuffer {
    private final Queue<Integer> queue = new LinkedList<>();

    /**
     * Adds a unit to the queue and then notifying all threads that is waiting
     * Notification is important to wake up threads to consume units
     * @param units the unit to be added
     */
    public synchronized void addUnit(int units) {
        queue.add(units);
        notifyAll();
    }

    /**
     * Removes units from the queue, if the queue is empty it waits
     * Then we notify all waiting threads
     */
    public synchronized void removeUnit() {
        while (queue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        queue.remove();
        notifyAll();
    }

    /**
     * Returning the size of the queue that is synchronized, so we always get the correct current data
     * @return returning the current size of the queue
     */
    public synchronized int size() {
        return queue.size();
    }
}
