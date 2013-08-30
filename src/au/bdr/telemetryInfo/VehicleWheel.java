/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package au.bdr.telemetryInfo;

/**
 *
 * @author Brendan
 */
public class VehicleWheel implements VehicleReset {
    
    public float rotation = 0.0f;
    public float suspenstionDeflection = 0.0f;
    public float rideHeight = 0.0f;
    public float tireLoad = 0.0f;
    public float lateralForce = 0.0f;
    public float gripFract = 0.0f;
    public float brakeTemp = 0.0f;
    public float pressure = 0.0f;
    public Coordinate temperature = new Coordinate();
    public float wear = 0.0f;
    public String terrainName = "";
    public char surfaceType = '\0';
    public boolean flat = false;
    public boolean detached = false;

    @Override
    public void reset() {
        rotation = 0.0f;
        suspenstionDeflection = 0.0f;
        rideHeight = 0.0f;
        tireLoad = 0.0f;
        lateralForce = 0.0f;
        gripFract = 0.0f;
        brakeTemp = 0.0f;
        pressure = 0.0f;
        temperature = new Coordinate();
        wear = 0.0f;
        terrainName = "";
        surfaceType = '\0';
        flat = false;
        detached = false;
    }

    @Override
    public String toString() {
        return String.format("rotation: %s, suspensionDeflection: %s, "
                + "rideHeigh: %f, tireLoad: %f, lateralForce: %f, gripFract: %f,"
                + " brakeTemp %f, pressure: %f, tempature: %s, wear: %f, "
                + "terrainName: %s, surfaceType: %s, flat %s, detached %s ", 
                rotation, suspenstionDeflection, rideHeight, tireLoad, 
                lateralForce, gripFract, brakeTemp, pressure, temperature, wear, 
                terrainName, surfaceType, flat, detached);
    }
}
