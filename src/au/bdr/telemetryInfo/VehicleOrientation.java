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
public class VehicleOrientation implements VehicleReset {

    public Coordinate oriX = new Coordinate();
    public Coordinate oriY = new Coordinate();
    public Coordinate oriZ = new Coordinate();
    public Coordinate localRot = new Coordinate();
    public Coordinate localRotAccel = new Coordinate();

    @Override
    public void reset() {
        ArrayList<Coordinate> coordinates = new ArrayList<>();
        coordinates.add(oriX);
        coordinates.add(oriY);
        coordinates.add(oriZ);
        coordinates.add(localRot);
        coordinates.add(localRotAccel);
        for (Coordinate co : coordinates) {
            co.reset();
        }
    }

    @Override
    public String toString() {
        return String.format("oriX: %s, oriY: %s, oriZ: %s, localRot: %s, "
                + "localRotAccel: %s", oriX, oriY, oriZ, localRot, localRotAccel);
    }
}
