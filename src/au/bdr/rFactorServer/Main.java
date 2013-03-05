/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package au.bdr.rFactorServer;

import au.bdr.rFactorServer.GUI.MainPanel;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;

/**
 *
 * @author Brendan Russo
 */
public class Main {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(800,600));
        frame.setVisible(true);
        frame.setResizable(false);
        MainPanel mainPanel = new MainPanel();
        frame.add(mainPanel);
        frame.pack();
    }
}
