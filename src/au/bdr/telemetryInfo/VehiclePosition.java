/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package au.bdr.telemetryInfo;

import java.util.ArrayList;

/**
 *
 * @author Brendan
 */
public class VehiclePosition implements VehicleReset{
    public Coordinate pos = new Coordinate();
    public Coordinate localVel = new Coordinate();
    public Coordinate localAccel = new Coordinate();

    @Override
    public void reset() {
        ArrayList<Coordinate> coordinates = new ArrayList<>();
        coordinates.add(pos);
        coordinates.add(localVel);
        coordinates.add(localAccel);
        for(Coordinate co : coordinates){
            co.reset();
        }
    }
    
    @Override
    public String toString()
    {
        return String.format("pos: %s, localVel: %s, localAccel: %s", pos, 
                localVel, localAccel);
    }
    
}
