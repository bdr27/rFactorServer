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
    public VehicleStatus vehicleStatus;
    public boolean maxRpmChange = false;
    private boolean display = false;
    private String filename = "E:\\Program Files (x86)\\rFactor\\ExampleInternalsTelemetryOutput.txt";

    public Telemetry() {
        vehicleStatus = new VehicleStatus();
    }

    @Deprecated
    public long getGear() {
        return vehicleStatus.gear;
    }

    @Deprecated
    public void setGear(long gear) {
        vehicleStatus.gear = gear;
    }

    @Deprecated
    public double getEngineRpm() {
        return vehicleStatus.engineRpm;
    }

    @Deprecated
    public void setEngineRpm(float engineRpm) {
        vehicleStatus.engineRpm = engineRpm;
    }

    @Deprecated
    public double getEngineRpmMax() {
        return vehicleStatus.engineRpmMax;
    }

    @Deprecated
    public void setEngineRpmMax(float engineRpmMax) {
        vehicleStatus.engineRpmMax = engineRpmMax;
    }

    @Deprecated
    public float getWater() {
        return vehicleStatus.engineWaterTemp;
    }

    @Deprecated
    public void setWater(float water) {
        vehicleStatus.engineWaterTemp = water;
    }

    @Deprecated
    public float getOil() {
        return vehicleStatus.engineOilTemp;
    }

    @Deprecated
    public void setOil(float oil) {
        vehicleStatus.engineOilTemp = oil;
    }

    @Deprecated
    public float getFuel() {
        return vehicleStatus.fuel;
    }

    @Deprecated
    public void setFuel(float fuel) {
        vehicleStatus.fuel = fuel;
    }

    @Deprecated
    public void setMeterPerSec(float speed) {
        vehicleStatus.meterPerSec = speed;
    }

    @Deprecated
    public float getMeterPerSec() {
        return vehicleStatus.meterPerSec;
    }

    @Deprecated
    public void setDisplay(boolean display) {
        this.display = display;
    }

    @Deprecated
    public boolean getDisplay() {
        return display;
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
                    vehicleStatus.meterPerSec = Float.parseFloat(temp.split(" ")[2]);
                }
            }
            System.out.println("Speed: " + vehicleStatus.meterPerSec);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Telemetry.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void reset() {
        display = false;
        vehicleStatus.reset();
    }

    boolean checkMaxRpm() {
        return maxRpmChange;
    }

    public class VehicleStatus {

        public long gear = 0;
        public float engineRpm = 0.0f;
        public float engineWaterTemp = 0.0f;
        public float engineOilTemp = 0.0f;
        public float clutchRpm = 0.0f;
        public float fuel = 0.0f;
        public float engineRpmMax = 0.0f;
        public long schedualedStops = 0;
        public float meterPerSec = 0.0f;

        public void reset() {
            gear = 0;
            engineRpm = 0.0f;
            engineWaterTemp = 0.0f;
            engineOilTemp = 0.0f;
            clutchRpm = 0.0f;
            fuel = 0.0f;
            engineRpmMax = 0.0f;
            schedualedStops = 0;
            meterPerSec = 0.0f;
        }
        
        @Override
        public String toString()
        {
            return String.format("GEAR: %d, ENGINE RPM: %.0f", gear, engineRpm);
        }
    }
}