/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package au.bdr.rFactorServer.util;

import java.io.IOException;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Brendan Russo
 */
public class LanReachable extends Thread{
    private static String subnet = "192.168.0";
    private static int timeout = 60000;
    private int address;
    private String host;
    
    
    public LanReachable(int address){
        this.address = address;
        host= subnet + "." + address;
    }
    
    @Override
    public void run(){
        try {
            if (InetAddress.getByName(host).isReachable(timeout)){
               System.out.println(host + " is reachable");
                System.out.println("Name: " + InetAddress.getByName(host).getHostName());
           }else{
            }
        } catch (IOException ex) {
            Logger.getLogger(LanReachable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setSubnet(String subnet){
        LanReachable.subnet = subnet;
    }
    
    public void setTimeout(int timeout){
        LanReachable.timeout = timeout;
    }
}
