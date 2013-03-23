/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package au.bdr.rFactorServer.util;

/**
 *
 * @author Brendan
 */
public class RpmGuage {
    //
    public int amountDrawn;
    private int rpmLength = 4;
    public double offset = 1.8;
    public double currentRpm;
    public double maxRpm;
    public double[] xLocations;
    public double[] yLocations;
    public double[] height;
    public double[] width;
    
    public RpmGuage(int amountDrawn){
        this.amountDrawn = amountDrawn;
    }
    
    public String formatedRpm(){
        String rpm = "" + (int) currentRpm;
        
        for(int i = rpm.length(); i < rpmLength; i++){
            rpm = "0" + rpm;
        }
        return rpm;
    }
    
    public void calculateRpmLength(){
        if(maxRpm >= 10000){
            rpmLength = 5;
            offset = .9;
        }else{
            rpmLength = 4;
            offset = 1.8;
        }
    }
}
