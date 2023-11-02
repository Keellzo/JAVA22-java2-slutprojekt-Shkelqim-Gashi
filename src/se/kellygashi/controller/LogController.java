package se.kellygashi.controller;

import se.kellygashi.logger.EventLog;
import se.kellygashi.buffer.ProductionBuffer;

import java.util.List;

public class LogController {
    private EventLog eventLog;
    private Controller controller;
    private long lastWarningTime = 0;
    private ProductionBuffer productionBuffer;


    /**
     * Constructor injection for LogController specified for EventLog, Controller and ProductionBuffer.
     *
     * @param eventLog         instance for logging events
     * @param controller       instance for accessing functionality
     * @param productionBuffer instance for managing production units
     */
    public LogController(EventLog eventLog, Controller controller, ProductionBuffer productionBuffer) {
        this.eventLog = eventLog;
        this.controller = controller;
        this.productionBuffer = productionBuffer;
    }

    /**
     * Logs the average units.
     * If statement to always make sure I get the latest average calculation.
     *
     * @return the latest saved average units.
     */
    public String logAverageUnits() {
        List<Integer> savedAverages = controller.getMonitor().getAverageUnits();
        String log = "";

        if (!savedAverages.isEmpty()) {
            double lastAverage = savedAverages.get(savedAverages.size() - 1);
            log = "Average units: " + lastAverage;
        } else {
            log = "No average units available.";
        }
        return eventLog.append(log);
    }


    /**
     * Logs the added producer with the producers' interval.
     * Logs also total producers.
     *
     * @return returning the log of above-mentioned
     */
    public String logAddedProducer() {
        int producerCount = controller.producers.size();
        int lastInterval = controller.producers.get(producerCount - 1).getInterval();
        return eventLog.append("Producer added with interval: " + lastInterval + "\n" + "Total Producers: " + producerCount + "\n");
    }

    /**
     * Logs the removed producer and total producers.
     *
     * @return returning the log of above-mentioned.
     */
    public String logRemovedProducer() {
        int producerCount = controller.producers.size();
        return eventLog.append("Producer removed." + "\n" + "Total Producers: " + producerCount + "\n");
    }


    /**
     * Checking the percentage of the units to log if the percentage is equal or under 10 or equal or above 90 to 100.
     * Making sure to print this every 5 seconds to the log, so it doesn't get bombarded.
     *
     * @return returning the log based on the condition.
     * NOTE: the return null will not get called since it has a null check in my view.
     */
    public String logUnits() {
        int maxUnits = 100;
        int availableUnits = controller.getAllUnits();
        double percentage = ((double) availableUnits / maxUnits) * 100;
        percentage = Math.min(percentage, 100);

        long currentTime = System.currentTimeMillis();

        if ((currentTime - lastWarningTime) >= 5000) {

            if (percentage <= 10) {
                lastWarningTime = currentTime;
                return eventLog.append("Warning: Units too low, at " + percentage + "%");
            } else if (percentage >= 90 && percentage <= 100) {
                lastWarningTime = currentTime;
                return eventLog.append("Warning: Units too high, at " + percentage + "%");
            }
        }
        return null;
    }

}
