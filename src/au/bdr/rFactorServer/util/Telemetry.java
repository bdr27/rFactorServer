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
    private boolean display = false;
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
    
    public void setDisplay(boolean display){
        this.display = display;
    }
    
    public boolean getDisplay(){
        return display;
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
        if (DEBUG) {
            System.out.println("max rpm: " + maxRpm + "temp max rpm: " + tempMaxRpm);
        }
        if (maxRpm != tempMaxRpm && tempMaxRpm > 50) {
            maxRpm = tempMaxRpm;
            return true;
        }
        return false;
    }

    public void reset() {
        display = false;
        speed = 0;
        rpm = 0;
        maxRpm = 0;
        tempMaxRpm = 0;
        water = 0;
        oil = 0;
        fuel = 0;
    }
}