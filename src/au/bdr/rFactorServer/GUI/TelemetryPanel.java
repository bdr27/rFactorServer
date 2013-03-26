/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package au.bdr.rFactorServer.GUI;

import au.bdr.rFactorServer.Guage.FuelGuage;
import au.bdr.rFactorServer.Guage.GearNumber;
import au.bdr.rFactorServer.Guage.OilGuage;
import au.bdr.rFactorServer.util.Debug;
import au.bdr.rFactorServer.Guage.RpmGuage;
import au.bdr.rFactorServer.Guage.SpeedGuage;
import au.bdr.rFactorServer.Guage.WaterGuage;
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
    private boolean updateScreen = false;
    private boolean resetScreen = false;
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
        synchronized (this) {
            super.paintComponent(graphic);
            panelSize = this.getSize();
            telemetryView.checkTelemetryView(panelSize.width, panelSize.height);
            //Font for the first bit
            telemetryFont = new Font("Menlo", Font.BOLD, panelSize.height / 10);
            Graphics2D g = (Graphics2D) graphic;

            g.setColor(Color.BLACK);
            g.setFont(telemetryFont);

            int emptyGuage = telemetryView.startEmptyGuage();

            RpmGuage rpmGuage = telemetryView.getRpmGuage();
            SpeedGuage speedGuage = telemetryView.getSpeedGuage();
            OilGuage oilGuage = telemetryView.getOilGuage();
            FuelGuage fuelGuage = telemetryView.getFuelGuage();
            WaterGuage waterGuage = telemetryView.getWaterGuage();
            GearNumber gearNumber = telemetryView.getGearNumber();

            if (telemetryView.getMaxRpm() < 0) {
                emptyGuage = rpmGuage.amountDrawn;
            }
            for (int i = 0; i < rpmGuage.amountDrawn; ++i) {
                if (i > emptyGuage) {
                    g.drawRect((int) rpmGuage.xLocations[i], (int) rpmGuage.yLocations[i], (int) rpmGuage.width[i], (int) rpmGuage.height[i]);
                } else {
                    g.fillRect((int) rpmGuage.xLocations[i], (int) rpmGuage.yLocations[i], (int) rpmGuage.width[i], (int) rpmGuage.height[i]);
                }
            }


            Font speedGuageFont = new Font("Menlo", Font.BOLD, (int) speedGuage.size);
            g.setFont(speedGuageFont);

            if (DEBUG) {
                System.out.println(speedGuage.xLocation + " " + speedGuage.yLocation);
            }

            //Draws all the guages maybe make a function
            g.drawString(speedGuage.formatedIntGuage(speedGuage.speed), (int) speedGuage.xLocation, (int) speedGuage.yLocation);
            g.drawString(rpmGuage.formatedIntGuage(rpmGuage.currentRpm), (int) (speedGuage.xLocation - speedGuageFont.getSize() / rpmGuage.offset), (int) speedGuage.yLocation + speedGuageFont.getSize());
            g.drawString(oilGuage.formatedIntGuage(oilGuage.temp), (int) speedGuage.xLocation, (int) speedGuage.yLocation + speedGuageFont.getSize() * 2);
            g.drawString(waterGuage.formatedIntGuage(waterGuage.temp), (int) speedGuage.xLocation, (int) speedGuage.yLocation + speedGuageFont.getSize() * 3);
            g.drawString(fuelGuage.formatedDoubleGuage(fuelGuage.amount), (int) (speedGuage.xLocation - speedGuageFont.getSize() / 1.23), (int) speedGuage.yLocation + speedGuageFont.getSize() * 4);
            g.drawString("" + gearNumber.gear, (int) speedGuage.xLocation, (int) (speedGuage.yLocation + speedGuageFont.getSize() * 5));
            g.drawString("" + telemetryView.getDisplay(), 8, speedGuageFont.getSize() * 6);
            
            Font speedGuageUnitFont = new Font("Menlo", Font.BOLD, (int) speedGuage.size / 3);
            g.setFont(speedGuageUnitFont);
            g.drawString(speedGuage.units, (int) (speedGuage.xLocation + speedGuageFont.getSize2D() * 1.7), (int) speedGuage.yLocation);
            g.drawString("RPM", (int) (speedGuage.xLocation + speedGuageFont.getSize2D() * 1.7), (int) speedGuage.yLocation + speedGuageFont.getSize());
            g.drawString("OIL", (int) (speedGuage.xLocation + speedGuageFont.getSize2D() * 1.7), (int) speedGuage.yLocation + speedGuageFont.getSize() * 2);
            g.drawString("WATER", (int) (speedGuage.xLocation + speedGuageFont.getSize2D() * 1.7), (int) speedGuage.yLocation + speedGuageFont.getSize() * 3);
            g.drawString("FUEL", (int) (speedGuage.xLocation + speedGuageFont.getSize2D() * 1.7), (int) speedGuage.yLocation + speedGuageFont.getSize() * 4);
            

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
        updateScreen = telemetryView.getDisplay();
        if (updateScreen){
            resetScreen = true;
            repaint();
        }else if(resetScreen){
            repaint();
            resetScreen = false;
        }
    }
}
