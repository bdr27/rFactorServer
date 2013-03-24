/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package au.bdr.rFactorServer.Guage;

/**
 *
 * @author Brendan
 */
public class RpmGuage extends Guage{
    //
    public int amountDrawn;
    public double offset = 1.8;
    public double currentRpm;
    public double maxRpm;
    public double[] xLocations;
    public double[] yLocations;
    public double[] height;
    public double[] width;
    
    public RpmGuage(int amountDrawn, int length){
        super(length);
        this.amountDrawn = amountDrawn;
    }
    
//    public String formatedRpm(){
//        String rpm = "" + (int) currentRpm;
//        
//        for(int i = rpm.length(); i < length; i++){
//            rpm = "0" + rpm;
//        }
//        return rpm;
//    }
    
    public void calculateRpmLength(){
        if(maxRpm >= 10000){
            length = 5;
            offset = .9;
        }else{
            length = 4;
            offset = 1.8;
        }
    }
}
