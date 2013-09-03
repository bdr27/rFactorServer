/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package au.bdr.telemetryInfo;

/**
 *
 * @author Brendan
 */
public class VehicleTime implements ITelemetry {
    public float delta = 0.0f;
    public long lapNumber = 0;
    public float lapStartET = 0.0f;

    @Override
    public void reset() {
        delta = 0.0f;
        lapNumber = 0;
        lapStartET = 0.0f;
    }

    @Override
    public String toString() {
        return String.format("delta: %.4f, lapNumber: %d, lapStartET: %.4f", 
                delta, lapNumber, lapStartET);
    }

    @Override
    public boolean checkValue(String input) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
