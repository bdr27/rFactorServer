/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package au.bdr.telemetryInfo;

/**
 *
 * @author Brendan
 */
public class VehicleMisc implements VehicleReset {

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
}
