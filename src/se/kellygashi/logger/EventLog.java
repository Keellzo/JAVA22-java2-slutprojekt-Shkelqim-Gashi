package se.kellygashi.logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.EmptyStackException;
import java.util.Stack;


/**
 * Singleton class for logging some specific events to the log.
 */
public class EventLog {
    private static EventLog instance;
    private Stack<String> events = new Stack<>();


    /**
     * Private to enforce the singleton pattern.
     */
    private EventLog() {}

    /**
     * @return singleton object.
     */
    public static EventLog getInstance() {
        if (instance == null) {
            instance = new EventLog();
        }
        return instance;
    }



    /**
     * Appending new event to the log and writes the entire log to the file.
     * Uses stack to add log entries in descending order.
     * @param event takes the string of the event that will be logged.
     * @return the event value to the log that was passed in.
     */
    public String append(String event) {
        events.push(event);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("eventlog.log"))) {
            // Clone the stack to avoid modifying the original stack while writing to the file
            Stack<String> tempStack = (Stack<String>) events.clone();
            while (!tempStack.isEmpty()) {
                try {
                    writer.write(tempStack.pop());
                    writer.newLine();
                } catch (EmptyStackException e) {
                    // stack was empty, break
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return event;
    }
}
