/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package au.bdr.rFactorServer.util;

/**
 *
 * @author Brendan
 */
public class SpeedGuage {
    public double speed;
    public double xLocation;
    public double yLocation;
    public double size;
    
    @Override
    public String toString(){
        String currentSpeed = "" + (int) speed;
        for(int i = currentSpeed.length(); i < 3; i++){
            currentSpeed = "0" + currentSpeed;
        }
        return currentSpeed;
    }
}
