/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package au.bdr.rFactorServer.util;

import au.bdr.telemetryInfo.VehicleTelemetry;
import au.bdr.rFactorServer.Guage.RpmGuage;
import au.bdr.rFactorServer.Guage.OilGuage;
import au.bdr.rFactorServer.Guage.WaterGuage;
import au.bdr.rFactorServer.Guage.SpeedGuage;
import au.bdr.rFactorServer.Guage.FuelGuage;
import au.bdr.rFactorServer.Guage.GearNumber;

/**
 *
 * @author Brendan
 */
public class TelemetryView {

    private final boolean DEBUG = new Debug().getDebug();
    private VehicleTelemetry telemetry;
    private int screenWidth = 0;
    private int screenHeight = 0;
    private int rpmSteps = 10;
    private double rpmSizeX = 0.05;
    private double rpmGapX = 0.02;
    private double rpmSizeY = .50;
    private double rpmXOffset = .02;
    private double speedGuageX = .8;
    private double speedGuageY = .1;
    private double speedFontSize = 10;
    private double[] rpmStepValues = new double[rpmSteps];
    private RpmGuage rpmGuage = new RpmGuage(rpmSteps, 4);
    private SpeedGuage speedGuage = new SpeedGuage(3);
    private WaterGuage waterGuage = new WaterGuage(3);
    private OilGuage oilGuage = new OilGuage(3);
    private FuelGuage fuelGuage = new FuelGuage(4);
    private GearNumber gearNumber = new GearNumber();

    public TelemetryView(VehicleTelemetry telemetry) {
        this.telemetry = telemetry;
        // Potential load config file at some point
    }

    /*
     * Method that will check weather any value for the VehicleTelemetry view has 
     * changed. Will be called everytime before the object is redrawn to make
     * sure the max rpm hasn't changed or screen size changed ect.
     */
    public void checkTelemetryView(int width, int height) {
        resizeScreen(width, height);
        if (telemetry.checkMaxRpm()) {
            //Change the rpmStepValues
            rpmGuage.maxRpm = telemetry.getEngineRpmMax();
            rpmGuage.calculateRpmLength();
            rpmStepValues = new double[rpmSteps];
            rpmStepValues = findRpmSteps(simultaneousEquationSolver(3.5, 10, telemetry.getEngineRpmMax() / 2, telemetry.getEngineRpmMax()), rpmSteps);
            telemetry.maxRpmChange = false;
        }
        speedGuage.speed = telemetry.getMeterPerSec();
        rpmGuage.currentRpm = telemetry.getEngineRpm();
        waterGuage.temp = telemetry.getOil();
        oilGuage.temp = telemetry.getWater();
        fuelGuage.amount = telemetry.getFuel();
        gearNumber.gear = telemetry.getGear();
    }

    public double getMaxRpm() {
        return telemetry.getEngineRpmMax();
    }
    
    public GearNumber getGearNumber(){
        return gearNumber;
    }

    private void resizeScreen(int width, int height) {
        if (this.screenWidth != width || this.screenHeight != height) {
            this.screenWidth = width;
            this.screenHeight = height;
            resizeRpmGuage();
            resizeSpeed();
        }
    }

    private void resizeRpmGuage() {
        rpmGuage.amountDrawn = rpmSteps;
        rpmGuage.xLocations = new double[rpmGuage.amountDrawn];
        rpmGuage.yLocations = new double[rpmGuage.amountDrawn];
        rpmGuage.width = new double[rpmGuage.amountDrawn];
        rpmGuage.height = new double[rpmGuage.amountDrawn];

        for (int i = 0; i < rpmGuage.amountDrawn; ++i) {
            rpmGuage.width[i] = screenWidth * rpmSizeX;
            rpmGuage.height[i] = screenHeight * rpmSizeY;
            rpmGuage.xLocations[i] = screenWidth * rpmXOffset +  i * screenWidth * (rpmSizeX + rpmGapX);
            rpmGuage.yLocations[i] = 10;
        }
    }

    private double[] simultaneousEquationSolver(double x1, double x2, double y1, double y2) {

        double a = ((y2 / x2) - (y1 / x1)) / (x2 - x1);
        double b = (y1 / x1) - a * x1;
        double[] missingValues = {a, b};
        return missingValues;
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

    public int startEmptyGuage() {
        int emptyGuage = -1;
        if (DEBUG) {
            System.out.println("Amount of rpmSteps: " + rpmSteps);
        }
        for (int i = 0; i < rpmSteps; i++) {
            if (DEBUG) {
                System.out.println("current rpmStepValue: " + rpmStepValues[i] + "current rpm: " + telemetry.getEngineRpm());
            }
            if (rpmStepValues[i] * .98 < telemetry.getEngineRpm()) {
                emptyGuage = i;
            }
        }
        return emptyGuage;
    }

    //Resizes the Speed font so that it adjusts with the layout
    private void resizeSpeed() {
        speedGuage.xLocation = screenWidth * speedGuageX;
        speedGuage.yLocation = screenHeight * speedGuageY;
        if (screenHeight < screenWidth) {
            speedGuage.size = screenHeight / speedFontSize;
        } else {
            speedGuage.size = screenWidth / speedFontSize;
        }
    }
    public boolean getDisplay(){
        return telemetry.getDisplay();
    }

    public SpeedGuage getSpeedGuage() {
        return speedGuage;
    }
    
    public RpmGuage getRpmGuage() {
        return rpmGuage;
    }

    public WaterGuage getWaterGuage() {
        return waterGuage;
    }

    public OilGuage getOilGuage() {
        return oilGuage;
    }

    public FuelGuage getFuelGuage() {
        return fuelGuage;
    }

    public double[] getRpmStepValues() {
        return rpmStepValues;
    }
   
}
