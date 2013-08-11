/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TelemetryInfo;

/**
 *
 * @author Brendan
 */
public class VehicleDriverInput implements VehicleReset {

    public float unfilteredThrottle = 0.0f;
    public float unfilteredBrake = 0.0f;
    public float unfilteredSteering = 0.0f;
    public float unfilteredClutch = 0.0f;

    @Override
    public void reset() {
        unfilteredBrake = 0.0f;
        unfilteredClutch = 0.0f;
        unfilteredSteering = 0.0f;
        unfilteredThrottle = 0.0f;
    }

    @Override
    public String toString() {
        return String.format("unfilteredThrottle: %s, unfilteredBrake: %s, "
                + "unfilteredSteering: %s, unfilteredClutch: %s, ", 
                unfilteredThrottle, unfilteredBrake, unfilteredSteering, 
                unfilteredClutch);
    }
}
