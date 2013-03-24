/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package au.bdr.rFactorServer.Guage;

/**
 *
 * @author Brendan
 */
public class SpeedGuage extends Guage{
    public double speed;
    public double xLocation;
    public double yLocation;
    public double size;
    public String units = "KPH";
    
    public SpeedGuage(int length){
        super(length);
    }
}
