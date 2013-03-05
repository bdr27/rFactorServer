/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package au.bdr.rFactorServer.GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Brendan Russo
 */
public class NavPanel extends JPanel{
    private static final String author = "Written by: Brendan Russo";
    ArrayList<JButton> navButtons = new ArrayList<>();
    GridLayout navLayout;
    int gridWidth = 0;
    
    public NavPanel(){
        navButtons.add(new JButton("Home"));
        navButtons.add(new JButton("Telemetry"));
        navButtons.add(new JButton("Settings"));
        gridWidth = navButtons.size();
        navLayout = new GridLayout();
        for(int i = 0; i < gridWidth; i++){
            this.add(navButtons.get(i));
        }        
    }
    
    public ArrayList<JButton> getNavButtons(){
        return navButtons;
    }
    
    @Override
    protected void paintComponent(Graphics graphic) {
        super.paintComponent(graphic);
        Graphics2D g = (Graphics2D) graphic;
        g.setColor(Color.BLACK);
        g.setFont(new Font("Menlo", Font.BOLD, 10));
        g.drawString(author, 0, 10);
    }
}
