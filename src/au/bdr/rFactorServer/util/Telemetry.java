/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package au.bdr.rFactorServer.util;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Brendan Russo
 */
public class Telemetry {

    private static final boolean DEBUG = new Debug().getDebug();
    private double speed = 0;
    private double rpm = 0;
    private double maxRpm = 0;
    private double tempMaxRpm = 0;
    private double water = 0;
    private double oil = 0;
    private double fuel = 0;
    private String filename = "E:\\Program Files (x86)\\rFactor\\ExampleInternalsTelemetryOutput.txt";

    public Telemetry() {
    }

    public double getRpm() {
        return rpm;
    }

    public void setRpm(double rpm) {
        this.rpm = rpm;
    }

    public double getMaxRpm() {
        return maxRpm;
    }

    public void setMaxRpm(double maxRpm) {
        this.maxRpm = maxRpm;
    }

    public void setTempMaxRpm(double tempMaxRpm) {
        this.tempMaxRpm = tempMaxRpm;
    }

    public double getWater() {
        return water;
    }

    public void setWater(double water) {
        this.water = water;
    }

    public double getOil() {
        return oil;
    }

    public void setOil(double oil) {
        this.oil = oil;
    }

    public double getFuel() {
        return fuel;
    }

    public void setFuel(double fuel) {
        this.fuel = fuel;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getSpeed() {
        return speed;
    }

    @Override
    public String toString() {
        return String.format("Speed=%f%nRpm=%f%nMaxRmp=%f%nWater=%f%nOil=%f%nFuel=%f%n", speed, rpm, maxRpm, water, oil, fuel);

    }

    /*
     * Old function for when it was loaded through text files
     */
    @Deprecated
    public void loadTelemetry() {
        try {
            File file = new File(filename);
            Scanner fin = new Scanner(file);
            while (fin.hasNextLine()) {
                String temp = fin.nextLine().toLowerCase();
                if (temp.contains("speed")) {
                    speed = Double.parseDouble(temp.split(" ")[2]);
                }
            }
            System.out.println("Speed: " + speed);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Telemetry.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean checkMaxRpm() {
        System.out.println("max rpm: " + maxRpm + "temp max rpm: " + tempMaxRpm);
        if(maxRpm != tempMaxRpm && tempMaxRpm != 0){
            maxRpm = tempMaxRpm;
            return true;
        }
        return false;
    }

    public void setRpmStep() {
        System.out.println(maxRpm);
    }

    public void setRpmGuage() {
        System.out.println("I set it up");
    }

//    public void draw(Graphics2D g, Dimension panelSize) {
//        if (checkMaxRpm()) {
//            rpmSteps = findRpmSteps(simultaneousEquationSolver(3.5, 10, maxRpm / 2, maxRpm), steps);
//        }
//        drawRevGuage(g, panelSize);
//    }
//
//    private void drawRevGuage(Graphics2D g, Dimension panelSize) {
//        //Needs to be in a seperate function, possible a draw string function
//        g.drawString("" + (int) rpm, (float) panelSize.width / 2f, (float) panelSize.height * .1f);
//        for (int i = 0; i < steps; i++) {
//            int width = (int) (panelSize.width * .07);
//            int height = (int) (panelSize.height * .3);
//            int x = (int) (panelSize.width * .05 + i * panelSize.width * 0.07);
//            int y = (int) (panelSize.height * .15);
//
//            if (rpmSteps[0] != 0 && rpmSteps[i] < rpm) {
//                g.fillRect(x, y, width, height);
//            } else {
//                g.drawRect(x, y, width, height);
//            }
//        }
//    }
    
//    private double[] simultaneousEquationSolver(double x1, double x2, double y1, double y2) {
//
//        double a = ((y2 / x2) - (y1 / x1)) / (x2 - x1);
//        double b = (y1 / x1) - a * x1;
//        double[] missingValues = {a, b};
//        return missingValues;
//    }
//    
//    private double[] findRpmSteps(double[] missingValues, int steps) {
//        double a = missingValues[0];
//        double b = missingValues[1];
//        if (DEBUG) {
//            System.out.println("value of a: " + a);
//            System.out.println("value of b: " + b);
//        }
//        double[] localRpmStep = new double[steps];
//
//        for (int i = 1; i <= steps; i++) {
//            localRpmStep[i - 1] = (a * i * i + b * i) * .98;
//            //Reduces by 2% so final one will hopefully light up
//            if (DEBUG) {
//                System.out.println("Rpm step: " + localRpmStep[i - 1]);
//            }
//        }
//
//        return localRpmStep;
//    }
}
