/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package au.bdr.telemetryInfo;

/**
 *
 * @author Brendan
 */
public interface ITelemetry {
    public void reset();
    public boolean checkValue(String input);
}
