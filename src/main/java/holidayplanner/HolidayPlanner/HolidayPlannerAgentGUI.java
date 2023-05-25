package holidayplanner.HolidayPlanner;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;


public class HolidayPlannerAgentGUI  extends JFrame{
	
	public HolidayPlannerAgentGUI (HolidayPlannerAgent agent) {
		
		Container cp = getContentPane();
		
		//JTextField toppingName = new JTextField();
		JTextField toppingNameTF = new JTextField();
		
		JButton searchButton = new JButton("Search");
		
		searchButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String toppingName = toppingNameTF.getText();
				
				if(toppingName != null && toppingName.length() > 0) {
					//TODO: search stuff
				}
				
			}
		});
		
		Box content = Box.createHorizontalBox();
		
		content.add(Box.createRigidArea(new Dimension(5,1)));
		content.add(toppingNameTF);
		content.add(Box.createRigidArea(new Dimension(5,1)));
		content.add(searchButton);
		content.add(Box.createRigidArea(new Dimension(5,1)));
		
		cp.add(content);
		
		this.setSize(300, 100);
		setVisible(true);
	}

}
