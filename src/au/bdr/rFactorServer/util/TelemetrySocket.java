/*
 * Telemetry socket class that gets the required information from the 
 * plugin. At the moment only rfactor is supported.
 */
package au.bdr.rFactorServer.util;

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

    private void setTelemetryData(String inputLine) {
        String[] nameValue = inputLine.split("=");
        switch (nameValue[0].toLowerCase()) {
            case "speed":
                telemetry.setSpeed(stringToDouble(nameValue[1]));
                break;
            case "rpm":
                telemetry.setRpm(stringToDouble(nameValue[1]));
                break;
            case "gear":
                telemetry.setGear(stringToLong(nameValue[1]));
                break;
            case "maxrpm":
                telemetry.setTempMaxRpm(stringToDouble(nameValue[1]));
                break;
            case "water":
                telemetry.setWater(stringToDouble(nameValue[1]));
                break;
            case "oil":
                telemetry.setOil(stringToDouble(nameValue[1]));
                break;
            case "fuel":
                telemetry.setFuel(stringToDouble(nameValue[1]));
                break;
            case "updatescreen":
                if (nameValue[1].equals("true")) {
                    telemetry.setDisplay(true);
                } else {
                    telemetry.reset();
                }
                break;
            default:
                System.out.println(nameValue[0] + " is an unimplemented telemetry value");
                break;
        }
    }

    private long stringToLong(String number) {
        return Long.parseLong(number);
    }

    private double stringToDouble(String number) {
        return Double.parseDouble(number);
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
        }
    }
}
