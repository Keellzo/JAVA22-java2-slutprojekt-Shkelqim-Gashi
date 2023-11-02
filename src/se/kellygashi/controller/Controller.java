package se.kellygashi.controller;

import se.kellygashi.buffer.ProductionBufferMonitor;
import se.kellygashi.logger.EventLog;
import se.kellygashi.model.Consumer;
import se.kellygashi.model.Producer;
import se.kellygashi.buffer.ProductionBuffer;

import java.util.*;

public class Controller {
    private ProductionBuffer productionBuffer;
    private List<Thread> producerThreads;
    private List<Thread> consumerThreads;
    List<Producer> producers;
    private LogController logController;
    private ProductionBufferMonitor monitor;

    /**
     * Controller constructor for initializing fields and some methods.
     */
    public Controller() {
        this.productionBuffer = new ProductionBuffer();
        this.producerThreads = new ArrayList<>();
        this.consumerThreads = new ArrayList<>();
        this.producers = new ArrayList<>();
        this.monitor = new ProductionBufferMonitor(productionBuffer);
        monitor.startMonitoring();
        logController = new LogController(EventLog.getInstance(), this, productionBuffer);
        addConsumers();
    }

    /**
     * Getter for getting production buffer object.
     *
     * @return production buffer object
     */
    public ProductionBuffer getProductionBuffer() {
        return productionBuffer;
    }

    /**
     * Getter for getting the monitor object
     *
     * @return monitor object
     */
    public ProductionBufferMonitor getMonitor() {
        return monitor;
    }

    /**
     * Adding a producer to the queue and with a random interval,
     * then adds the producer to the producers list,
     * then creates a thread with the producer as argument,
     * then adds the thread to the threads list.
     * Starts the thread and adds it the to the log.
     */
    public synchronized void addProducer() {
        int interval = getRandomInterval();
        Producer producer = new Producer(productionBuffer, interval);
        producers.add(producer);
        Thread thread = new Thread(producer);
        producerThreads.add(thread);

        thread.start();
    }


    /**
     * Removes the last index of the threads list and producers list,
     * then interrupts the last thread and logs the removed producer.
     */
    public synchronized void removeProducer() {
        if (!producerThreads.isEmpty()) {
            Thread lastProducerThread = producerThreads.get(producerThreads.size() - 1);
            producerThreads.remove(lastProducerThread);
            if (!producers.isEmpty()) {
                producers.remove(producers.size() - 1);
            }
            lastProducerThread.interrupt();
        }
    }

    /**
     *  Getting all units in the queue and calculate it to percentage.
     * @param maxUnits param for setting the max units of the calculation
     * @return the percentage
     */
    public int getAndUpdateProgressBarValue(int maxUnits) {
        int units = getAllUnits();
        int percentage = (int) (((double) units / maxUnits) * 100);

        return percentage;
    }

    /**
     * Adding consumers to the queue that consumes units based on random consumers and random intervals on each consumer.
     */
    public void addConsumers() {
        Random random = new Random();
        int numConsumers = random.nextInt(13) + 3;
        for (int i = 0; i < numConsumers; i++) {
            int interval = random.nextInt(10) + 1;
            Consumer consumer = new Consumer(productionBuffer, interval);
            Thread thread = new Thread(consumer);
            consumerThreads.add(thread);
            thread.start();
        }
    }

    /**
     * Gets the synchronized size of the queue.
     * @return all units.
     */
    public int getAllUnits() {
        return productionBuffer.size();
    }


    /**
     *  Random interval between 1-10.
     * @return the value of the above-mentioned.
     */
    private int getRandomInterval() {
        Random random = new Random();
        return random.nextInt(10) + 1;
    }
}
