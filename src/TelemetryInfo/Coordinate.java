/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TelemetryInfo;

/**
 *
 * @author Brendan
 */
public class Coordinate implements VehicleReset{
    public float x = 0.0f;
    public float y = 0.0f;
    public float z = 0.0f;

    @Override
    public String toString() {
        return String.format("x:%f, y:%f, z:%f", x, y, z);
    }

    @Override
    public void reset() {
        x = 0.0f;
        y = 0.0f;
        z = 0.0f;
    }
}
