package se.kellygashi.view;

import se.kellygashi.controller.Controller;
import se.kellygashi.controller.LogController;
import se.kellygashi.logger.EventLog;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;

public class SwingView {
    private JProgressBar progressBar;
    LogController logController;

    public SwingView(Controller controller) {

        // Initialize the frame
        JFrame frame = new JFrame("Production Regulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        logController = new LogController(EventLog.getInstance(), controller, controller.getProductionBuffer());


        // Initialize buttons
        JButton addProducerButton = new JButton("Add Producer");
        JButton removeProducerButton = new JButton("Remove Producer");

        // Initialize text area for log
        JTextArea logTextArea = new JTextArea(5, 20);
        logTextArea.setEditable(false);
        DefaultCaret caret = (DefaultCaret) logTextArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
        JScrollPane scroll = new JScrollPane(logTextArea,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        // Initialize panel
        JPanel buttonPanel = new JPanel(new FlowLayout());

        // Add buttons to buttonPanel
        buttonPanel.add(addProducerButton);
        buttonPanel.add(removeProducerButton);

        // Initialize progress bar
        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);

        // Add action listeners to buttons
        addProducerButton.addActionListener(e -> {
            controller.addProducer();
            logTextArea.insert(logController.logAddedProducer(), 0);

            JScrollBar vertical = scroll.getVerticalScrollBar();
            vertical.setValue(0);
        });


        removeProducerButton.addActionListener(e -> {
            controller.removeProducer();
            logTextArea.insert(logController.logRemovedProducer(), 0);

            JScrollBar vertical = scroll.getVerticalScrollBar();
            vertical.setValue(0);
        });

        // Set constraints and add components to frame
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1.0;
        constraints.weighty = 0.1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        frame.add(buttonPanel, constraints);

        constraints.gridy = 1;
        frame.add(progressBar, constraints);

        constraints.gridy = 2;
        constraints.weighty = 0.8;
        constraints.fill = GridBagConstraints.BOTH;
        scroll.setPreferredSize(new Dimension(450, 200));
        frame.add(scroll, constraints);

        // Make frame visible
        frame.setVisible(true);


        // Set the new value every 100 milliseconds to the progressbar
        Timer timer = new Timer(100, e -> {
            int percentage = controller.getAndUpdateProgressBarValue(100);
            progressBar.setValue(percentage);


            String logUpdate = logController.logUnits();
            if (logUpdate != null) {
                controller.getAllUnits();
                logTextArea.insert(logUpdate + "\n", 0);
                JScrollBar vertical = scroll.getVerticalScrollBar();
                vertical.setValue(0);
            }
            updateProgressBarColor(progressBar, percentage);
        });

        // Log the average units every 10sec
        Timer avgTimer = new Timer(10000, e -> {
            String averageLog = logController.logAverageUnits();
            if (averageLog != null) {
                logTextArea.insert(averageLog + "\n", 0);
                JScrollBar vertical = scroll.getVerticalScrollBar();
                vertical.setValue(0);

            }
        });


        timer.start();
        avgTimer.start();
    }


    private void updateProgressBarColor(JProgressBar progressBar, int percentage) {
        if (percentage <= 10) {
            progressBar.setBackground(Color.red);
        } else if (percentage >= 90) {
            progressBar.setBackground(Color.green);
        }
        else{
            progressBar.setBackground(Color.lightGray);
        }
    }


    public static void main(String[] args) {
        Controller controller = new Controller();
        SwingUtilities.invokeLater(() -> new SwingView(controller));
    }
}
