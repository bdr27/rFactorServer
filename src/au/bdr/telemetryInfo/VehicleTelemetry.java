/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package au.bdr.telemetryInfo;

import au.bdr.rFactorServer.util.Debug;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Brendan Russo
 */
public class VehicleTelemetry {

    private static final boolean DEBUG = new Debug().getDebug();
    public VehicleTime vehicleTime;
    public VehiclePosition vehiclePosition;
    public VehicleOrientation vehicleOrientation; 
    public VehicleStatus vehicleStatus;
    public VehicleDriverInput vehicleDriverInput;
    public VehicleMisc vehicleMisc;
    public VehicleState vehicleState;
    public VehicleWheel[] vehicleWheels;
    public boolean maxRpmChange = false;
    private boolean display = false;
    private String filename = "E:\\Program Files (x86)\\rFactor\\ExampleInternalsTelemetryOutput.txt";

    public VehicleTelemetry() {        
        vehicleTime = new VehicleTime();
        vehiclePosition = new VehiclePosition();
        vehicleOrientation = new VehicleOrientation();
        vehicleStatus = new VehicleStatus();
        vehicleDriverInput = new VehicleDriverInput();
        vehicleMisc = new VehicleMisc();
        vehicleState = new VehicleState();
        vehicleWheels = new VehicleWheel[4];
        for(int i = 0; i < 4; i++){
            vehicleWheels[i] = new VehicleWheel();
        }
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
            Logger.getLogger(VehicleTelemetry.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void reset() {
        display = false;
        ArrayList<VehicleReset> vi = new ArrayList<>();
        vi.add(vehicleTime);
        vi.add(vehiclePosition);
        vi.add(vehicleOrientation);
        vi.add(vehicleStatus);
        vi.add(vehicleDriverInput);
        vi.add(vehicleState);
        for (VehicleReset vr : vi) {
            vr.reset();
        }
    }

    public boolean checkMaxRpm() {
        return maxRpmChange;
    }
}