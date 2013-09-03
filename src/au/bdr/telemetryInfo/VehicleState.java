/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package au.bdr.telemetryInfo;

/**
 *
 * @author Brendan
 */
public class VehicleState implements ITelemetry {

    public boolean overheating = false;
    public boolean detached = false;
    public float[] dentSeverity = new float[8];
    public float lastImpactET = 0.0f;
    public float lastImpactMagnitude = 0.0f;
    public Coordinate lastImpactPos = new Coordinate();

    @Override
    public void reset() {
        overheating = false;
        detached = false;
        dentSeverity = new float[8];
        lastImpactET = 0.0f;
        lastImpactMagnitude = 0.0f;
        lastImpactPos = new Coordinate();
    }

    @Override
    public String toString() {
        String beginning = "overheating: " + overheating + ", "; 
        beginning = beginning + "detached: " + detached + ", ";
        beginning = beginning + "dentSeverity: ";
        for(float fl : dentSeverity){
            beginning = beginning + fl + ", ";
        }
        return String.format("%slastImpactET: %f, lastImpactMagnitude: %f, "
                + "lastImpactPos: %s", beginning, lastImpactET, 
                lastImpactMagnitude, lastImpactPos);
    }

    @Override
    public boolean checkValue(String input) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
