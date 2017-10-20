/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sec;

import java.time.Instant;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class ClockThread implements Runnable {

    JLabel label;

    public ClockThread(JLabel label) {
        this.label = label;
    }

    @Override
    public void run() {
        while (true) {
            Date date = Date.from(Instant.now());
            updateLabel(label, date.toString());
            try {
                Thread.sleep(60000);

            } catch (InterruptedException ex) {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

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
