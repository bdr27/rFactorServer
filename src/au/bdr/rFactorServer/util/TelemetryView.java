/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package au.bdr.rFactorServer.util;

/**
 *
 * @author Brendan
 */
public class TelemetryView {

    private static final boolean DEBUG = new Debug().getDebug();
    private Telemetry telemetry;
    private int screenWidth = 0;
    private int screenHeight = 0;
    private int rpmSteps = 10;
    private double rpmSizeX = 0.07;
    private double rpmGapX = 0.05;
    private double rpmSizeY = .15;
    private double[] rpmXLocations;
    private double[] rpmYLocations;
    private double[] rpmHeight;
    private double[] rpmWidth;
    private double[] rpmStepValues;

    public TelemetryView(Telemetry telemetry) {
        this.telemetry = telemetry;
        // Potential load config file at some point
    }
    
    /*
     * Method that will check weather any value for the Telemetry view has 
     * changed. Will be called everytime before the object is redrawn to make
     * sure the max rpm hasn't changed or screen size changed ect.
     */
    public void checkTelemetryView(int width, int height){
        resizeScreen(width, height);
        if(telemetry.checkMaxRpm()){
            //Change the rpmStepValues
            rpmStepValues = findRpmSteps(simultaneousEquationSolver(2.5, 10, telemetry.getMaxRpm()/2, telemetry.getMaxRpm()),rpmSteps);
        }
    }

    private void resizeScreen(int width, int height) {
        if (this.screenWidth != width || this.screenHeight != height) {
            this.screenWidth = width;
            this.screenHeight = height;
            resizeRpmGuage();
        }
    }

    private void resizeRpmGuage() {
        rpmXLocations = new double[rpmSteps];
        rpmYLocations = new double[rpmSteps];
        rpmWidth = new double[rpmSteps];
        rpmHeight = new double[rpmSteps];        

        for (int i = 0; i < rpmSteps; ++i) {
            rpmWidth[i] = (screenWidth * rpmSizeX);
            rpmXLocations[i] = (screenWidth * rpmGapX + i * screenWidth * rpmSizeX);
            rpmYLocations[i] = screenHeight * rpmSizeY;
            rpmHeight[i] = screenHeight * rpmSizeY;
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
    
    public double[] getRpmXLocations(){
        return rpmXLocations;
    }
    
    public double[] getRpmYLocations(){
        return rpmYLocations;
    }
    
    public double[] getRpmHeight(){
        return rpmHeight;
    }
    
    public double[] getRpmWidth(){
        return rpmWidth;
    }
    
    public double[] getRpmStepValues(){
        return rpmStepValues;
    }
}