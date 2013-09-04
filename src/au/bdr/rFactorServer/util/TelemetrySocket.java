/*
 * VehicleTelemetry socket class that gets the required information from the 
 * plugin. At the moment only rfactor is supported.
 */
package au.bdr.rFactorServer.util;

import au.bdr.telemetryInfo.VehicleOrientation;
import au.bdr.telemetryInfo.VehicleTelemetry;
import au.bdr.telemetryInfo.Coordinate;
import au.bdr.telemetryInfo.VehicleState;
import au.bdr.telemetryInfo.VehicleTime;
import au.bdr.telemetryInfo.VehiclePosition;
import au.bdr.telemetryInfo.VehicleStatus;
import au.bdr.telemetryInfo.VehicleMisc;
import au.bdr.telemetryInfo.VehicleWheel;
import au.bdr.telemetryInfo.VehicleDriverInput;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author Brendan Russo
 */
public class TelemetrySocket extends Thread {

    private String host;
    private MulticastSocket multicastSocket;
    private InetAddress multicastGroup;
    private DatagramPacket multicastPacket;
    private int multicastPort;
    private int port;
    private boolean DEBUG = new Debug().getDebug();
    private VehicleTelemetry vehicleTelemetry = new VehicleTelemetry();
    private ServerSocket serverSocket = null;
    private Socket clientSocket = null;

    public TelemetrySocket(String host, int port) throws IOException {
        this.host = host;
        this.port = port;
        multicastPort = 50000; 
        serverSocket = new ServerSocket(port);
    }

    public void reset() throws IOException {
        serverSocket.close();
        serverSocket = new ServerSocket(port);
    }

    @Override
    public void run() {
        
        /*String msgToSend = "I am still god";
        try {
            SetupMulticast("228.5.6.7",50001);


            multicastPacket = new DatagramPacket(msgToSend.getBytes(), msgToSend.length(), multicastGroup ,multicastPort);
            multicastSocket.send(multicastPacket);  
        } catch (IOException ie) {
        }*/
        System.out.println(vehicleTelemetry.vehicleTime);
        System.out.println(vehicleTelemetry.vehiclePosition);
        System.out.println(vehicleTelemetry.vehicleOrientation);
        System.out.println(vehicleTelemetry.vehicleStatus);
        System.out.println(vehicleTelemetry.vehicleDriverInput);
        System.out.println(vehicleTelemetry.vehicleMisc);
        System.out.println(vehicleTelemetry.vehicleState);
        for (VehicleWheel wh : vehicleTelemetry.vehicleWheels) {
            System.out.println(wh);
        }

        while (true) {
            try {
                clientSocket = serverSocket.accept();
                readClientStream();
                vehicleTelemetry.reset();
            } catch (IOException ex) {
                System.out.println(ex);
                System.out.println("Failed");
            }
        }
    }
    
    private void SetupMulticast(String ipAddress, int port) throws UnknownHostException, IOException
    {        
        multicastGroup = InetAddress.getByName("228.5.6.7");
        multicastSocket = new MulticastSocket(port);
        multicastSocket.joinGroup(multicastGroup);        
    }

    public VehicleTelemetry getTelemetry() {
        return vehicleTelemetry;
    }

    private long stringToLong(String number) {
        return Long.parseLong(number);
    }

    private float stringToFloat(String number) {
        return Float.parseFloat(number);
    }

    private Coordinate stringToCoordinate(String value) {
        String[] values = value.split(",");
        Coordinate coordinate = new Coordinate();
        if (values.length == 3) {
            coordinate.x = stringToFloat(values[0]);
            coordinate.y = stringToFloat(values[1]);
            coordinate.z = stringToFloat(values[2]);
        }
        return coordinate;
    }

    private void readClientStream() throws IOException {
        BufferedReader bin;
        String inputLine;

        bin = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        while ((inputLine = bin.readLine()) != null) {
            if (DEBUG) {
                System.out.println(inputLine);
            }
            try{
            sendMulticast(inputLine);
            }catch(IOException ex)
            {
                System.out.println(ex.toString());
            }
            setTelemetryData(inputLine);
        }
    }
    
    private void sendMulticast(String inputLine) throws IOException {
        multicastPacket.setData(inputLine.getBytes());
        multicastSocket.send(multicastPacket);
    }

    @Deprecated
    private void setTelemetryDataOld(String inputLine) {
        String[] nameValue = inputLine.split("=");
        switch (nameValue[0].toLowerCase()) {
            case "speed":
                vehicleTelemetry.setMeterPerSec(stringToFloat(nameValue[1]));
                break;
            case "rpm":
                vehicleTelemetry.setEngineRpm(stringToFloat(nameValue[1]));
                break;
            case "gear":
                vehicleTelemetry.setGear(stringToLong(nameValue[1]));
                break;
            case "maxrpm":
                vehicleTelemetry.setEngineRpmMax(stringToFloat(nameValue[1]));
                vehicleTelemetry.maxRpmChange = true;
                break;
            case "water":
                vehicleTelemetry.setWater(stringToFloat(nameValue[1]));
                break;
            case "oil":
                vehicleTelemetry.setOil(stringToFloat(nameValue[1]));
                break;
            case "fuel":
                vehicleTelemetry.setFuel(stringToFloat(nameValue[1]));
                break;
            case "updatescreen":
                if (nameValue[1].equals("true")) {
                    vehicleTelemetry.setDisplay(true);
                } else {
                    vehicleTelemetry.reset();
                }
                break;
            default:
                // System.out.println(nameValue[0] + " is an unimplemented telemetry value");
                break;
        }
    }

    private void setTelemetryData(String inputLine) {
        String[] nameValue = inputLine.split("=");
        String[] types = nameValue[0].split(",");
        switch (types[0]) {
            case "0":
                break;
            case "1":
                setTelemetryVehicleTime(types, nameValue[1]);
                break;
            case "2":
                setTelemetryVehiclePositions(types, nameValue[1]);
                break;
            case "3":
                setTelemetryVehicleOrientation(types, nameValue[1]);
                break;
            case "4":
                setTelemetryVehicleStatus(types, nameValue[1]);
                break;
            case "5":
                setTelemetryVehicleDriverInput(types, nameValue[1]);
                break;
            case "6":
                setTelemetryVehicleMisc(types, nameValue[1]);
                break;
            case "7":
                setTelemetryVehicleState(types, nameValue[1]);
                break;
            case "8":
                setTelemetryVehicleWheel(nameValue);
                break;
            default:
                if (DEBUG) {
                    System.out.println("Unknown message type");
                }
                break;
        }
    }

    private void setTelemetryVehicleTime(String[] types, String value) {
        VehicleTime vehicleTime = vehicleTelemetry.vehicleTime;
        switch (types[1]) {
            case "0":
                vehicleTime.delta = stringToFloat(value);
                System.out.println("");
                System.out.println(vehicleTelemetry.vehicleTime);
                break;
            case "1":
                vehicleTime.lapNumber = stringToLong(value);
                break;
            case "2":
                vehicleTime.lapStartET = stringToFloat(value);
                break;
            default:
                System.out.println("Invalid Input");
        }
    }
    //TODO Change to make dependant on the classes maybe

    private void setTelemetryVehiclePositions(String[] types, String value) {
        VehiclePosition vehiclePosition = vehicleTelemetry.vehiclePosition;
        switch (types[1]) {
            case "0":
                vehiclePosition.pos = stringToCoordinate(value);
                break;
            case "1":
                vehiclePosition.localVel = stringToCoordinate(value);
                break;
            case "2":
                vehiclePosition.localAccel = stringToCoordinate(value);
                System.out.println(vehicleTelemetry.vehiclePosition);
                break;
            default:
                System.out.println("Invalid Input");
        }
    }

    private void setTelemetryVehicleOrientation(String[] types, String value) {
        VehicleOrientation vehicleOrientation = vehicleTelemetry.vehicleOrientation;
        switch (types[1]) {
            case "0":
                vehicleOrientation.oriX = stringToCoordinate(value);
                break;
            case "1":
                vehicleOrientation.oriY = stringToCoordinate(value);
                break;
            case "2":
                vehicleOrientation.oriZ = stringToCoordinate(value);
                break;
            case "3":
                vehicleOrientation.localRot = stringToCoordinate(value);
                break;
            case "4":
                vehicleOrientation.localRotAccel = stringToCoordinate(value);
                System.out.println(vehicleTelemetry.vehicleOrientation);
                break;
            default:
                System.out.println("Invalid Input");
        }
    }

    private void setTelemetryVehicleStatus(String[] types, String value) {
        VehicleStatus vehicleStatus = vehicleTelemetry.vehicleStatus;
        switch (types[1]) {
            case "0":
                vehicleStatus.gear = stringToLong(value);
                break;
            case "1":
                vehicleStatus.engineRpm = stringToFloat(value);
                break;
            case "2":
                vehicleStatus.engineWaterTemp = stringToFloat(value);
                break;
            case "3":
                vehicleStatus.engineOilTemp = stringToFloat(value);
                break;
            case "4":
                vehicleStatus.clutchRpm = stringToFloat(value);
                break;
            case "5":
                vehicleStatus.fuel = stringToFloat(value);
                break;
            case "6":
                vehicleStatus.engineRpmMax = stringToFloat(value);
                break;
            case "7":
                vehicleStatus.schedualedStops = stringToLong(value);
                break;
            case "8":
                vehicleStatus.meterPerSec = stringToFloat(value);
                System.out.println(vehicleTelemetry.vehicleStatus);
                break;
            default:
                System.out.println("Invalid Input");
        }
    }

    private void setTelemetryVehicleDriverInput(String[] types, String value) {
        VehicleDriverInput vehicleDriverInput = vehicleTelemetry.vehicleDriverInput;
        switch (types[1]) {
            case "0":
                vehicleDriverInput.unfilteredThrottle = stringToFloat(value);
                break;
            case "1":
                vehicleDriverInput.unfilteredBrake = stringToFloat(value);
                break;
            case "2":
                vehicleDriverInput.unfilteredSteering = stringToFloat(value);
                break;
            case "3":
                vehicleDriverInput.unfilteredClutch = stringToFloat(value);
                System.out.println(vehicleTelemetry.vehicleDriverInput);
                break;
        }
    }

    private void setTelemetryVehicleMisc(String[] types, String value) {
        VehicleMisc vehicleMisc = vehicleTelemetry.vehicleMisc;
        switch (types[1]) {
            case "0":
                vehicleMisc.steeringArmForce = stringToFloat(value);
                System.out.println(vehicleTelemetry.vehicleMisc);
                break;
            case "1":
                vehicleMisc.trackName = value;
                break;
            case "2":
                vehicleMisc.vehicleName = value;
                break;
        }
    }

    private void setTelemetryVehicleState(String[] types, String value) {
        VehicleState vehicleState = vehicleTelemetry.vehicleState;
        switch (types[1]) {
            case "0":
                vehicleState.overheating = stringToBool(value);
                break;
            case "1":
                vehicleState.detached = stringToBool(value);
                break;
            case "2":
                setDentSeverity(types, value, vehicleState.dentSeverity);
                break;
            case "3":
                vehicleState.lastImpactET = stringToFloat(value);
                break;
            case "4":
                vehicleState.lastImpactMagnitude = stringToFloat(value);
                break;
            case "5":
                vehicleState.lastImpactPos = stringToCoordinate(value);
                System.out.println(vehicleTelemetry.vehicleState);
                break;
        }
    }

    private void setTelemetryVehicleWheel(String[] nameValue) {
        String[] types = nameValue[0].split(",");
        String value = nameValue[1];
        int wheel = Integer.parseInt(types[2]);
        VehicleWheel vehicleWheel = vehicleTelemetry.vehicleWheels[wheel];
        switch (types[1]) {
            case "0":
                vehicleWheel.rotation = stringToFloat(value);
                break;
            case "1":
                vehicleWheel.suspenstionDeflection = stringToFloat(value);
                break;
            case "2":
                vehicleWheel.rideHeight = stringToFloat(value);
                break;
            case "3":
                vehicleWheel.tireLoad = stringToFloat(value);
                break;
            case "4":
                vehicleWheel.lateralForce = stringToFloat(value);
                break;
            case "5":
                vehicleWheel.gripFract = stringToFloat(value);
                break;
            case "6":
                vehicleWheel.brakeTemp = stringToFloat(value);
                break;
            case "7":
                vehicleWheel.pressure = stringToFloat(value);
                break;
            case "8":
                vehicleWheel.temperature = stringToCoordinate(value);
                break;
            case "9":
                vehicleWheel.wear = stringToFloat(value);
                break;
            case "10":
                vehicleWheel.terrainName = value;
                break;
            case "11":
                vehicleWheel.surfaceType = value.charAt(0);
                break;
            case "12":
                vehicleWheel.flat = stringToBool(value);
                break;
            case "13":
                vehicleWheel.detached = stringToBool(value);
                System.out.println(vehicleTelemetry.vehicleWheels[wheel]);
                break;
        }
    }

    private boolean stringToBool(String value) {
        if (value.contains("1")) {
            return true;
        }
        return false;
    }

    private void setDentSeverity(String[] types, String value, float[] dentSeverity) {
        int pos = Integer.parseInt(types[2]);
        dentSeverity[pos] = stringToFloat(value);
    }
}