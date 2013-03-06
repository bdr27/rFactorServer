/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package au.bdr.rFactorServer.GUI;

import au.bdr.rFactorServer.util.Debug;
import au.bdr.rFactorServer.util.Telemetry;
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

    private final static boolean DEBUG = new Debug().getDebug();
    private static final Font DEBUG_FONT = new Font("Menlo", Font.BOLD, 10);
    private Font telemetryFont;
    private Dimension panelSize;
    private Telemetry telemetry;
    private Timer timer;
    private double[] rpmStep = new double[1];
    private int test = 0;
    private double currentMaxRpm = 0;
    private int numOfSteps = 10;

    public TelemetryPanel(Telemetry telemetry) {
        this.telemetry = telemetry;
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
            g.setFont(telemetryFont);
            
            //New code
            if(telemetry.checkMaxRpm()){
                telemetry.setRpmStep();
            }
            
            //Old code
            //Need to shift this to the telemetry class
            if (telemetry.getMaxRpm() != 0 && telemetry.getMaxRpm() != currentMaxRpm) {
                currentMaxRpm = telemetry.getMaxRpm();
                rpmStep = findRpmSteps(simultaneousEquationSolver(3.5, 10, currentMaxRpm / 2, currentMaxRpm), numOfSteps);
            }

            drawRevGuage(g, panelSize, rpmStep, telemetry.getRpm(), numOfSteps);
            drawSpeed(g, panelSize, telemetry.getSpeed());

            if (DEBUG) {
                System.out.println(telemetry);
                System.out.println(panelSize);
            }
        }
    }

    private void drawRevGuage(Graphics2D g, Dimension panelSize, double[] rpmSteps, double currentRpm, int steps) {
        //Needs to be in a seperate function, possible a draw string function
        g.drawString("" + (int) currentRpm, (float) panelSize.width / 2f, (float) panelSize.height * .1f);
        for (int i = 0; i < steps; i++) {
            int width = (int) (panelSize.width * .07);
            int height = (int) (panelSize.height * .3);
            int x = (int) (panelSize.width * .05 + i * panelSize.width * 0.09);
            int y = (int) (panelSize.height * .15);

            if (rpmSteps[0] != 0 && rpmSteps[i] < currentRpm) {
                g.fillRect(x, y, width, height);
            } else {
                g.drawRect(x, y, width, height);
            }
        }
    }

    @Deprecated
    private void oldDrawRevGuage(Graphics2D g, Dimension panelSize, double rpmStep, double currentRpm) {
        g.drawString("" + (int) currentRpm, (float) panelSize.width / 2f, (float) panelSize.height * .1f);
        for (int i = 0; i < 10; i++) {
            int width = (int) (panelSize.width * .07);
            int height = (int) (panelSize.height * .3);
            int x = (int) (panelSize.width * .05 + i * panelSize.width * 0.09);
            int y = (int) (panelSize.height * .15);

            //Works with drawing of the rev guage but potential needs to be non linear
            //Weird glitch while ai has control this doesn't work
            if (rpmStep < 1 || rpmStep * (i + 1) > currentRpm) {
                g.drawRect(x, y, width, height);
            } else {
                g.fillRect(x, y, width, height);
            }
        }
    }

    private void drawSpeed(Graphics2D g, Dimension panelSize, double speed) {
        g.drawString("" + (int) speed, (int) (panelSize.width / 2), (int) (panelSize.height * .55));
    }

    private double[] findRpmSteps(double[] missingValues, int steps) {
        double a = missingValues[0];
        double b = missingValues[1];
        if (DEBUG) {
            System.out.println("value of a: " + a);
            System.out.println("value of b: " + b);
        }
        double[] localRpmStep = new double[steps];

        for (int i = 1; i <= steps; i++) {
            localRpmStep[i - 1] = (a * i * i + b * i) * .98;
            //Reduces by 2% so final one will hopefully light up
            if (DEBUG) {
                System.out.println("Rpm step: " + localRpmStep[i - 1]);
            }
        }

        return localRpmStep;
    }

    private double[] simultaneousEquationSolver(double x1, double x2, double y1, double y2) {

        double a = ((y2 / x2) - (y1 / x1)) / (x2 - x1);
        double b = (y1 / x1) - a * x1;
        double[] missingValues = {a, b};
        return missingValues;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}
