package sec;

import java.time.Instant;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

/**
 * ClockThread.
 * Clock thread is used to update the clock in the GUI
 */
public class ClockThread implements Runnable {

    /**
     * Represents the clock label in the GUI.
     */
    JLabel label;

    /**
     * Constructor.
     * @param label the clock label which shows the time. 
     */
    public ClockThread(JLabel label) {
        this.label = label;
    }

    @Override
    public void run() {
        while (true) {
            Date date = Date.from(Instant.now());
            updateLabel(label, date.toString());
            try {
                //Thread.sleep(60000);
                 Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    /**
     * Update the clock label.
     * Uses SwingUtilities static class to update the clock label using the Event Dispatcher Thread(EDT)
     * @param theLabel the clock label to be updated
     * @param newMessage the new time to be updated to the list
     */
    public void updateLabel(final JLabel theLabel, final String newMessage) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                theLabel.setText(newMessage);
            }
        }
        );

    }
}
