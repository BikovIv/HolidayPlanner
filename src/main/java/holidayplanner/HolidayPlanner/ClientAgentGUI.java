package holidayplanner.HolidayPlanner;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ClientAgentGUI extends JFrame {
	
	ClientAgent myAgent = new ClientAgent();

	JList<String> holidaysList = new JList<>();
	String[] data = {"one", "two", "three", "four"};
	JComboBox<String> tripTypeCB = new JComboBox<>();
	JComboBox<String> placeToStayTypeCB = new JComboBox<>();
	JComboBox<String> tripTownTypeCB = new JComboBox<>();
	
	
	public ClientAgentGUI(ClientAgent agent) {
			
			// Create and set up a frame window
	        JFrame.setDefaultLookAndFeelDecorated(true);
	        JFrame frame = new JFrame("Layout");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setPreferredSize(new Dimension(700, 700));
	        
	        // Define the panel to hold the components  
	        JPanel panel = new JPanel();
	        SpringLayout layout = new SpringLayout();
	         
	        JLabel label = new JLabel("Trip Type: ");
	        
	        JLabel placeToStayTypeLabel = new JLabel("Place To Stay: ");
	        //JComboBox<String> placeToStayTypeCB = new JComboBox<>();
	        
	        JLabel tripTownTypeLabel = new JLabel("Trip Town Type: ");
	  		//JComboBox<String> tripTownTypeCB = new JComboBox<>();
	  		
	  		JLabel placeToStayPriceLabel = new JLabel("Price by night: ");
	  		JTextField placeToStayPriceCB = new JTextField(10);
	       
			JButton searchButton = new JButton("Search");
			
			//result fields begin	
			JLabel tripNameLabel = new JLabel("Trip Name: ");
			JTextField tripNameTF = new JTextField(10);	
			JLabel tripTownLabel = new JLabel("Trip Town: ");
			JTextField tripTownTF = new JTextField(10);	
			JLabel placeToStayNameLabel = new JLabel("Place To Stay: ");
			JTextField placeToStayNameTF = new JTextField(10);
			JLabel costByNightLabel = new JLabel("Cost by night: ");
			JTextField costByNightTF =  new JTextField(10);
			JScrollPane holidaysListScroll = new JScrollPane(holidaysList);
			JButton addButton = new JButton("Add holiday");
			
			//for(String tripType : myAgent.getAllTripTypes()) {
			//	tripTypeCB.addItem(tripType);
			//}
			
			//Image begin
			BufferedImage myPicture = null;
			try {
				myPicture = ImageIO.read(new File("src/main/webapp/uploads/131_summer-index-img.jpg"));
				//System.out.println(myPicture);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			JLabel picLabel = new JLabel(new ImageIcon(myPicture));
			//picLabel.setPreferredSize(new Dimension(1000, 1000));
			//Image end
			//resulst fields end
			
	        panel.setLayout(layout);
	        
	        //search fields begin
	        panel.add(label);
	        panel.add(tripTypeCB);
	        panel.add(placeToStayTypeLabel);
	        panel.add(placeToStayTypeCB);
	        panel.add(tripTownTypeLabel);
	        panel.add(tripTownTypeCB);
	        panel.add(placeToStayPriceLabel);
	        panel.add(placeToStayPriceCB);
	        //panel.add();
	        
	        panel.add(searchButton);
	        //search filds end
	        
	        //results begin
	        holidaysList.setPreferredSize(new Dimension(200, 300));
	        panel.add(holidaysListScroll);
	        holidaysList.setListData(data);
	        panel.add(picLabel);
	        
	        panel.add(tripNameLabel);
	        panel.add(tripNameTF);
	        panel.add(tripTownLabel);
	        panel.add(tripTownTF);
	        panel.add(placeToStayNameLabel);
	        panel.add(placeToStayNameTF);
	        panel.add(costByNightLabel);
	        panel.add(costByNightTF);
	        panel.add(addButton);
	        //results end
	        
	        //Search start
	        layout.putConstraint(SpringLayout.WEST, label, 5, SpringLayout.WEST, panel);
	        layout.putConstraint(SpringLayout.NORTH, label, 5, SpringLayout.NORTH, panel);
	        layout.putConstraint(SpringLayout.WEST, tripTypeCB, 5, SpringLayout.EAST, label);
	        layout.putConstraint(SpringLayout.NORTH, tripTypeCB, 5, SpringLayout.NORTH, panel);
	        
	        layout.putConstraint(SpringLayout.WEST, placeToStayTypeLabel, 8, SpringLayout.WEST, panel);
	        layout.putConstraint(SpringLayout.NORTH, placeToStayTypeLabel, 8, SpringLayout.SOUTH, tripTypeCB);
	        layout.putConstraint(SpringLayout.WEST, placeToStayTypeCB, 5, SpringLayout.EAST, placeToStayTypeLabel);
	        layout.putConstraint(SpringLayout.NORTH, placeToStayTypeCB, 5, SpringLayout.SOUTH, tripTypeCB);
	        
	        layout.putConstraint(SpringLayout.WEST, tripTownTypeLabel, 8, SpringLayout.WEST, panel);
	        layout.putConstraint(SpringLayout.NORTH, tripTownTypeLabel, 8, SpringLayout.SOUTH, placeToStayTypeCB);
	        layout.putConstraint(SpringLayout.WEST, tripTownTypeCB, 5, SpringLayout.EAST, tripTownTypeLabel);
	        layout.putConstraint(SpringLayout.NORTH, tripTownTypeCB, 5, SpringLayout.SOUTH, placeToStayTypeCB);
	        
	        layout.putConstraint(SpringLayout.WEST, placeToStayPriceLabel, 8, SpringLayout.WEST, panel);
	        layout.putConstraint(SpringLayout.NORTH, placeToStayPriceLabel, 8, SpringLayout.SOUTH, tripTownTypeCB);
	        layout.putConstraint(SpringLayout.WEST, placeToStayPriceCB, 5, SpringLayout.EAST, tripTownTypeLabel);
	        layout.putConstraint(SpringLayout.NORTH, placeToStayPriceCB, 5, SpringLayout.SOUTH, tripTownTypeCB);
	        
	        layout.putConstraint(SpringLayout.NORTH, searchButton, 5, SpringLayout.SOUTH, placeToStayPriceCB);
	        layout.putConstraint(SpringLayout.WEST, searchButton, 5, SpringLayout.WEST, panel);
	        //search end
	        
	        //results start
	        layout.putConstraint(SpringLayout.WEST, holidaysListScroll, 5, SpringLayout.EAST, tripTownTypeCB);
	        layout.putConstraint(SpringLayout.EAST, holidaysListScroll, 5, SpringLayout.EAST, panel);
	        layout.putConstraint(SpringLayout.NORTH, holidaysListScroll, 5, SpringLayout.NORTH, panel);
	        layout.putConstraint(SpringLayout.SOUTH, holidaysListScroll, 5, SpringLayout.SOUTH, searchButton);
	        
	        layout.putConstraint(SpringLayout.SOUTH, picLabel, 5, SpringLayout.SOUTH, panel);
	        layout.putConstraint(SpringLayout.WEST, picLabel, 5, SpringLayout.WEST, panel);
	        layout.putConstraint(SpringLayout.NORTH, picLabel, 5, SpringLayout.SOUTH, holidaysListScroll);
	        
	        layout.putConstraint(SpringLayout.WEST, tripNameLabel, 5, SpringLayout.EAST, picLabel);
	        layout.putConstraint(SpringLayout.NORTH, tripNameLabel, 5, SpringLayout.NORTH, picLabel);
	        layout.putConstraint(SpringLayout.WEST, tripNameTF, 5, SpringLayout.EAST, tripNameLabel);
	        layout.putConstraint(SpringLayout.NORTH, tripNameTF, 5, SpringLayout.NORTH, picLabel);
	        
	        layout.putConstraint(SpringLayout.WEST, tripTownLabel, 5, SpringLayout.EAST, picLabel);
	        layout.putConstraint(SpringLayout.NORTH, tripTownLabel, 5, SpringLayout.SOUTH, tripNameTF);
	        layout.putConstraint(SpringLayout.WEST, tripTownTF, 5, SpringLayout.EAST, tripNameLabel);
	        layout.putConstraint(SpringLayout.NORTH, tripTownTF, 5, SpringLayout.SOUTH, tripNameTF);
	        
	        layout.putConstraint(SpringLayout.WEST, placeToStayNameLabel, 5, SpringLayout.EAST, picLabel);
	        layout.putConstraint(SpringLayout.NORTH, placeToStayNameLabel, 5, SpringLayout.SOUTH, tripTownTF);
	        layout.putConstraint(SpringLayout.WEST, placeToStayNameTF, 5, SpringLayout.EAST, placeToStayNameLabel);
	        layout.putConstraint(SpringLayout.NORTH, placeToStayNameTF, 5, SpringLayout.SOUTH, tripTownTF);
	        
	        layout.putConstraint(SpringLayout.WEST, costByNightLabel, 5, SpringLayout.EAST, picLabel);
	        layout.putConstraint(SpringLayout.NORTH, costByNightLabel, 5, SpringLayout.SOUTH, placeToStayNameTF);
	        layout.putConstraint(SpringLayout.WEST, costByNightTF, 5, SpringLayout.EAST, costByNightLabel);
	        layout.putConstraint(SpringLayout.NORTH, costByNightTF, 5, SpringLayout.SOUTH, placeToStayNameTF);
	        
	        layout.putConstraint(SpringLayout.WEST, addButton, 5, SpringLayout.EAST, picLabel);
	        layout.putConstraint(SpringLayout.NORTH, addButton, 5, SpringLayout.SOUTH, costByNightTF);
	        
	        //results end
	        
	        // Set the window to be visible as the default to be false
	        frame.add(panel);
	        frame.pack();
	       // frame.setVisible(true);
 
	        searchButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					String townName = "townNameTF.getText()";
					
					String tripType = tripTypeCB.getSelectedItem().toString();
					String placeToStayType = placeToStayTypeCB.getSelectedItem().toString();
					String tripTownType = tripTownTypeCB.getSelectedItem().toString();
					double placeToStayPrice = Double.parseDouble(placeToStayPriceCB.getText());
					
					
					if(townName != null && townName.length() > 0 &&
						placeToStayType != null && placeToStayType.length() > 0 &&
						tripTownType != null && tripTownType.length() > 0 &&
						placeToStayPrice > 0) {
						
						//agent.setsearchedTown(townName);	
						agent.setsearchedTripType(tripType);		
						agent.setsearchedPlaceToStayType(placeToStayType);		
						agent.setsearchedTripTownType(tripTownType);		
						agent.setsearchedPlaceToStayPrice(placeToStayPrice);		
					}		
				}
			});
	        
	        holidaysList.addListSelectionListener(new ListSelectionListener() {
				
				@Override
				public void valueChanged(ListSelectionEvent e) {
					tripNameTF.setText(holidaysList.getSelectedValue());
				}
			});
	        
	        addButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					String townName = "townNameTF.getText()";
					
					if(townName != null && 
							townName.length() > 0) {
						//agent.setsearchedTown(townName);			
					}		
				}
			});
	        
		}
}
