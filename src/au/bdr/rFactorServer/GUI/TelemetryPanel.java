/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package au.bdr.rFactorServer.GUI;

import au.bdr.rFactorServer.util.Debug;
import au.bdr.rFactorServer.util.RpmGuage;
import au.bdr.rFactorServer.util.Telemetry;
import au.bdr.rFactorServer.util.TelemetryView;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Brendan Russo
 */
public class TelemetryPanel extends JPanel implements ActionListener {

    private final boolean DEBUG = new Debug().getDebug();
    private final Font DEBUG_FONT = new Font("Menlo", Font.BOLD, 10);
    private Font telemetryFont;
    private Dimension panelSize;
    private TelemetryView telemetryView;
    private Timer timer;

    public TelemetryPanel(Telemetry telemetry) {
        this.telemetryView = new TelemetryView(telemetry);
        timer = new Timer(100, this);
    }

    public void startTimer() {
        timer.start();
        if (DEBUG) {
            System.out.println("Starting Timer");
        }

    }

    public void stopTimer() {
        timer.stop();
        if (DEBUG) {
            System.out.println("Stopping Timer");
        }
    }
    
    

    /*
     * Draw the telemetry stuff that I'll need
     */
    @Override
    protected void paintComponent(Graphics graphic) {
        super.paintComponent(graphic);

        synchronized (this) {
            Graphics2D g = (Graphics2D) graphic;
            panelSize = this.getSize();
            telemetryFont = new Font("Menlo", Font.BOLD, panelSize.height / 10);

            g.setColor(Color.BLACK);            
            telemetryView.checkTelemetryView(panelSize.width, panelSize.height);
            
            int emptyGuage = telemetryView.startEmptyGuage();
            RpmGuage rpmGuage = telemetryView.getRpmGuage();
            
            for(int i = 0; i < rpmGuage.amountDrawn; ++i){
                if(i > emptyGuage){
                    g.drawRect((int) rpmGuage.xLocations[i], (int) rpmGuage.yLocations[i], (int) rpmGuage.width[i], (int) rpmGuage.height[i]);
                }else{
                     g.fillRect((int) rpmGuage.xLocations[i], (int) rpmGuage.yLocations[i], (int) rpmGuage.width[i], (int) rpmGuage.height[i]);
                }
            }
            
            
            //New code
         //   telemetry.draw(g, panelSize);
        //    drawSpeed(g, panelSize, telemetry.getSpeed());

            if (DEBUG) {
       //         System.out.println(telemetry);
                System.out.println(panelSize);
            }
            g.dispose();
        }
    }

//    private void drawSpeed(Graphics2D g, Dimension panelSize, double speed) {
//        g.drawString("" + (int) speed, (int) (panelSize.width / 2), (int) (panelSize.height * .55));
//    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}
