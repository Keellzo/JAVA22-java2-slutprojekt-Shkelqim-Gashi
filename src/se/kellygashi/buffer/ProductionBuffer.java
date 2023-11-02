package se.kellygashi.model;

import java.util.LinkedList;
import java.util.Queue;

public class ProductionBuffer {
    private final Queue<Integer> queue = new LinkedList<>();
    private long totalUnits = 0;
    private long unitCount = 0;

    public synchronized void addItem(int item) {
        queue.add(item);
        totalUnits += item;
        unitCount++;
        notifyAll();
    }

    public long getTotalUnits() {
        return totalUnits;
    }

    public long getUnitCount() {
        return unitCount;
    }

    public synchronized int removeItem() {
        while (queue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        int removedItem = queue.remove();
        totalUnits -= removedItem;
        unitCount--;
        notifyAll();
        return removedItem;
    }


    public synchronized int size() {
        return queue.size();
    }

    public synchronized double getAverage() {
        if (unitCount == 0) {
            return 0.0;
        }
        System.out.println((double) totalUnits / unitCount);
        return (double) totalUnits / unitCount;
    }
}
