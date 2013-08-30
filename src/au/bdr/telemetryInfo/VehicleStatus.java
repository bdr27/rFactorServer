/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package au.bdr.telemetryInfo;

/**
 *
 * @author Brendan
 */
public class VehicleStatus implements VehicleReset {

    public long gear = 0;
    public float engineRpm = 0.0f;
    public float engineWaterTemp = 0.0f;
    public float engineOilTemp = 0.0f;
    public float clutchRpm = 0.0f;
    public float fuel = 0.0f;
    public float engineRpmMax = 0.0f;
    public long schedualedStops = 0;
    public float meterPerSec = 0.0f;

    @Override
    public void reset() {
        gear = 0;
        engineRpm = 0.0f;
        engineWaterTemp = 0.0f;
        engineOilTemp = 0.0f;
        clutchRpm = 0.0f;
        fuel = 0.0f;
        engineRpmMax = 0.0f;
        schedualedStops = 0;
        meterPerSec = 0.0f;
    }

    @Override
    public String toString() {
        return String.format("gear: %d, engineRpm: %.0f, engineWaterTemp: %.2f,"
                + " engineOilTemp: %.2f, clutchRpm: %.0f, fuel: %.2f,"
                + " engineRpmMax: %.0f, schedualedStops: %d, meterPerSec: %.2f", gear, engineRpm, engineWaterTemp, engineOilTemp, clutchRpm,
                fuel, engineRpmMax, schedualedStops, meterPerSec);
    }
}
