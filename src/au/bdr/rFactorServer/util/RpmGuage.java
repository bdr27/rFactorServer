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
    public int amount;
    public double[] xLocations;
    public double[] yLocations;
    public double[] height;
    public double[] width;
    
    public RpmGuage(int amount){
        this.amount = amount;
    }
}
