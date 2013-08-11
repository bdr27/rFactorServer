/*
 * Telemetry socket class that gets the required information from the 
 * plugin. At the moment only rfactor is supported.
 */
package au.bdr.rFactorServer.util;

import au.bdr.rFactorServer.util.Telemetry.VehicleStatus;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Brendan Russo
 */
public class TelemetrySocket extends Thread {

    private String host;
    private int port;
    private boolean DEBUG = new Debug().getDebug();
    private Telemetry telemetry = new Telemetry();
    private ServerSocket serverSocket = null;
    private Socket clientSocket = null;

    public TelemetrySocket(String host, int port) throws IOException {
        this.host = host;
        this.port = port;
        serverSocket = new ServerSocket(port);
    }

    public void reset() throws IOException {
        serverSocket.close();
        serverSocket = new ServerSocket(port);
    }

    @Override
    public void run() {
        while (true) {
            try {
                clientSocket = serverSocket.accept();
                readClientStream();
                telemetry.reset();
            } catch (IOException ex) {
                System.out.println("Failed");
            }
        }
    }

    public Telemetry getTelemetry() {
        return telemetry;
    }

    private long stringToLong(String number) {
        return Long.parseLong(number);
    }

    private float stringToFloat(String number) {
        return Float.parseFloat(number);
    }

    private void readClientStream() throws IOException {
        BufferedReader bin;
        String inputLine;

        bin = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        while ((inputLine = bin.readLine()) != null) {
            if (DEBUG) {
                System.out.println(inputLine);
            }
            setTelemetryData(inputLine);
            //setTelemetryDataOld(inputLine);
        }
    }

    @Deprecated
    private void setTelemetryDataOld(String inputLine) {
        String[] nameValue = inputLine.split("=");
        switch (nameValue[0].toLowerCase()) {
            case "speed":
                telemetry.setMeterPerSec(stringToFloat(nameValue[1]));
                break;
            case "rpm":
                telemetry.setEngineRpm(stringToFloat(nameValue[1]));
                break;
            case "gear":
                telemetry.setGear(stringToLong(nameValue[1]));
                break;
            case "maxrpm":
                telemetry.setEngineRpmMax(stringToFloat(nameValue[1]));
                telemetry.maxRpmChange = true;
                break;
            case "water":
                telemetry.setWater(stringToFloat(nameValue[1]));
                break;
            case "oil":
                telemetry.setOil(stringToFloat(nameValue[1]));
                break;
            case "fuel":
                telemetry.setFuel(stringToFloat(nameValue[1]));
                break;
            case "updatescreen":
                if (nameValue[1].equals("true")) {
                    telemetry.setDisplay(true);
                } else {
                    telemetry.reset();
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
                 break;
            case "2":
                 break;
            case "3":
                break;
            case "4":
                setTelemetryStatus(types, nameValue[1]);
                break;
            case "5":
                 break;
            case "6":
                 break;
            case "7":
                 break;
            case "8":
                 break;
            default:
                if(DEBUG)
                {
                    System.out.println("Unknown message type");
                }
                break;
        }
    }

    private void setTelemetryStatus(String[] types, String value) {
        VehicleStatus vehicleStatus = telemetry.vehicleStatus;
        switch (types[1]){
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
                System.out.println(telemetry.vehicleStatus.toString());
                break;
        }
    }
}

/*
Telemetry class values.
* rFactor info
* Lap - 1
*   delta - 1
*   lap number - 2
*   lap start - 3*   
* 
* Vehicle Velocity / Acceleration / position - 2
*   position - 1
*       x,y,z
*   velocity - 2
*       x,y,z
*   accel - 3
*       x,y,z
* 
* Vehicle VehicleStatus - 3
* 
* Driver Inputs - 4
* 
* Pit - 5
* 
* Wheel - 6
* 
* Misc - 7
*   Vehicle Name - 1
*   Track Name - 2
* 
* Ori / rot / rot acel - 8
*   ori - x,y,z
*       x,y,z
*   rot - 1
*       x,y,z
*   rot acel - 2
*       x,y,z
* */