/*
 * This is the server panel that will contain all the various other panels for the server
 * At the moment there will only be a panel with list of hosts
 */
package au.bdr.rFactorServer.GUI;

import au.bdr.rFactorServer.util.Debug;
import au.bdr.rFactorServer.util.Telemetry;
import au.bdr.rFactorServer.util.TelemetrySocket;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Brendan Russo
 */
public class MainPanel extends JPanel implements ActionListener {
    private TelemetrySocket telemetrySocket;
    private Telemetry telemetry;
    
    private static boolean DEBUG = new Debug().getDebug();

    

    private enum AvaliablePages {

        HOME, SETTINGS, TELEMETRY
    };
    private AvaliablePages currentPage = AvaliablePages.HOME;
    private BorderLayout layout = new BorderLayout();
    private NavPanel navPanel;
    private ArrayList<JButton> navButtons;
    private TelemetryPanel displayPanel;
    private HomePanel homePanel;

    public MainPanel() {
        this.setLayout(layout);
        try {
            //Sets up the telemetrySocket and telemetry
            telemetrySocket = new TelemetrySocket("127.0.0.1", 27015);
            telemetrySocket.start();
            telemetry = telemetrySocket.getTelemetry();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Could not connect to the socket. Please reset the application");
        }
        //Set's up the panels
        navPanel = new NavPanel();
        displayPanel = new TelemetryPanel(telemetry);
        homePanel = new HomePanel();

        //Sets up the navigation panel and adds action listeners
        this.add(navPanel, BorderLayout.PAGE_START);
        navButtons = navPanel.getNavButtons();
        for (JButton navButton : navButtons) {
            navButton.addActionListener(this);
        }
        //Add the home panel
        this.add(homePanel, BorderLayout.CENTER);
    }

    /*        }

     * Remove the current panel from the Frame
     */
    private void removePages() {
        if (currentPage.equals(AvaliablePages.HOME)) {
            removePanel(homePanel);
            if (DEBUG) {
                System.out.println("Remove home page");
            }
        }
        if (currentPage.equals(AvaliablePages.SETTINGS)) {
            if (DEBUG) {
                System.out.println("Remove settings page");
            }
        }
        if (currentPage.equals(AvaliablePages.TELEMETRY)) {
            displayPanel.stopTimer();
            removePanel(displayPanel);
            if (DEBUG) {
                System.out.println("Remove telemetry page");
            }
        }
    }
    
    private void showPanel(JPanel panel) {
        this.add(panel, BorderLayout.CENTER);
        panel.setVisible(true);
        panel.setFocusable(true);
        this.revalidate();
        this.repaint();
    }
    
    private void removePanel(JPanel panel){
        this.remove(panel);
        this.revalidate();
        this.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand().toLowerCase()) {
            case "home":
                if (!currentPage.equals(AvaliablePages.HOME)) {
                    removePages();
                    showPanel(homePanel);
                    currentPage = AvaliablePages.HOME;
                    if (DEBUG) {
                        System.out.println("Go to home");
                    }
                }
                break;
            case "telemetry":
                if (!currentPage.equals(AvaliablePages.TELEMETRY)) {
                    removePages();
                    showPanel(displayPanel);
                    displayPanel.startTimer();
                    currentPage = AvaliablePages.TELEMETRY;
                    if (DEBUG) {
                        System.out.println("Go to telemetry");
                    }
                }
                break;
            case "settings":
                if (!currentPage.equals(AvaliablePages.SETTINGS)) {
                    removePages();
                    currentPage = AvaliablePages.SETTINGS;
                    if (DEBUG) {
                        System.out.println("Go to settings");
                    }
                }
                break;
            default:
                System.out.println("Unkown command");
        }
    }
}
