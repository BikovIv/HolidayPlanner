package holidayplanner.HolidayPlanner;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class ClientAgentGUI extends JFrame {

	
	public ClientAgentGUI(ClientAgent agent) {
		
		Container cp = getContentPane();
		
		JTextField townNameTF = new JTextField();
		
		JButton searchButton = new JButton("Search");
		JButton getTownsButton = new JButton("Available towns");
		
		searchButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String townName = townNameTF.getText();
				
				if(townName != null && 
						townName.length() > 0) {
					agent.setsearchedTown(townName);
				}
				
			}
		});
		
		getTownsButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String townName = "***all_towns***";
				
				if(townName != null && 
						townName.length() > 0) {
					agent.setsearchedTown(townName);
					
				}
				
			}
		});
		
		Box content = Box.createHorizontalBox();
		
		content.add(Box.createRigidArea(new Dimension(5, 1)));
		content.add(townNameTF);
		content.add(Box.createRigidArea(new Dimension(5, 1)));
		content.add(searchButton);
		content.add(getTownsButton);
		content.add(Box.createRigidArea(new Dimension(5, 1)));
		
		cp.add(content);
		
		this.setSize(300, 100);
		setVisible(true);
	}
	
}
