/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package au.bdr.telemetryInfo;

/**
 *
 * @author Brendan
 */
public class VehicleMisc implements ITelemetry {

    public float steeringArmForce = 0.0f;
    public String vehicleName = "";
    public String trackName = "";

    @Override
    public void reset() {
        steeringArmForce = 0.0f;
        vehicleName = "";
        trackName = "";
    }

    @Override
    public String toString() {
        return String.format("steeringArmForce: %f, vehicleName: %s, trackName: %s", steeringArmForce, vehicleName, trackName);
    }

    @Override
    public boolean checkValue(String input) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
