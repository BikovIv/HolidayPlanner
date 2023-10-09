package holidayplanner.HolidayPlanner;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;

public class ClientAgentGUI {

	ClientAgent myAgent;
 
 public ClientAgentGUI(ClientAgent myAgent) {
		this.myAgent = myAgent;
		
		initialize();
	}
 
 private void initialize() {
		
		myAgent = new ClientAgent();
		
		
		//setContentPane(contentPane);
		//pack();
		//setVisible(true);		}
	
}}
