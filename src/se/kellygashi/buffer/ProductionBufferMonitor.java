package se.kellygashi.buffer;

import java.util.ArrayList;
import java.util.List;

public class ProductionBufferMonitor {
    private final ProductionBuffer buffer;
    private final List<Integer> averageUnits = new ArrayList<>();
    private static final int AVERAGE_CALCULATION_INTERVAL = 10;


    /**
     * Constructor injection for ProductionBuffer class
     * The ProductionBuffer is used for monitoring its size over time.
     * @param buffer ProductionBuffer object to monitor
     */
    public ProductionBufferMonitor(ProductionBuffer buffer) {
        this.buffer = buffer;
    }

    /**
     * Creates new thread that runs until the program is terminated
     * It monitors the buffer size every second and to calculate the average size every 10 second
     */
    public void startMonitoring() {
        new Thread(() -> {
            int sum = 0;
            int counter = 0;
            while (true) {
                try {
                    Thread.sleep(1000);
                    sum += buffer.size();
                    counter++;
                    if (counter == AVERAGE_CALCULATION_INTERVAL) {
                        int average = sum / AVERAGE_CALCULATION_INTERVAL;
                        averageUnits.add(average);
                        sum = 0;
                        counter = 0;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * Returns the list of average queue size
     * Calculated every 10sec
     * @return the list of sizeAverages
     */
    public List<Integer> getAverageUnits() {
        return averageUnits;
    }
}
