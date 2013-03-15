/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package au.bdr.rFactorServer.util;

import java.awt.Dimension;

/**
 *
 * @author Brendan
 */
public class TelemetryView {
    Telemetry telemetry;
    int width = 0;
    int height = 0;
    int steps = 10;
    
    public TelemetryView(Telemetry telemetry){
        this.telemetry = telemetry;
        // Potential load config file at some point
    }
    
    public void resizeScreen(int width, int height){
        if(this.width != width){
            this.width = width;
            //Resize width
        }
        if(this.height != height){
            this.height = height;
            //Resize height
        }
    }
    
}
