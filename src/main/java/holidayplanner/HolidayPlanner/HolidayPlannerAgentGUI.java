package holidayplanner.HolidayPlanner;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.semanticweb.owlapi.model.OWLOntologyStorageException;

import holidayplanner.HolidayPlanner.activity.ActivityController;
import holidayplanner.HolidayPlanner.activity.ActivityRepository;
import holidayplanner.HolidayPlanner.activity.ActivityService;

public class HolidayPlannerAgentGUI  extends JFrame{   /*  DOBAVQNE NA ZAPISI KAM ONTOOOOOOOOOOOOOOOOOOOOOOOOO*/
	
	HolidayPlannerAgent myAgent;
	
	public HolidayPlannerAgentGUI(HolidayPlannerAgent myAgent) {
		this.myAgent = myAgent;
		
		initialize();
	}
	
	private void initialize() {
		
		myAgent = new HolidayPlannerAgent();
		
		JLabel tripTypeLabel = new JLabel("Trip Type");
		JComboBox<String> tripTypeCB = new JComboBox<>();
		JLabel tripNameLabel = new JLabel("Trip Name");
		JTextField tripNameTF = new JTextField(10);	
		JLabel tripTownLabel = new JLabel("Trip Town");
		JTextField tripTownTF = new JTextField(10);	
		JLabel tripTownTypeLabel = new JLabel("Trip Town Type");
		JComboBox<String> tripTownTypeCB = new JComboBox<>();
		JLabel placeToStayTypeLabel = new JLabel("Place To Stay Type ");
		JTextField placeToStayTypeTF = new JTextField(10);
		JLabel placeToStayNameLabel = new JLabel("Place To Stay Name");
		JTextField placeToStayNameTF = new JTextField(10);
		JLabel costByNightLabel = new JLabel("Cost by night");
		JTextField costByNightTF =  new JTextField(10);
		JButton addTripB = new JButton("Add Trip");
		
		/*try {
			for(String tripType : myAgent.getAllTripTypes()) {
				tripTypeCB.addItem(tripType);
			}
		} catch (OWLOntologyStorageException e2) {
			e2.printStackTrace();
		}*/
		
		try {
			for(String townType : myAgent.getAllTownTypes()) {
				tripTownTypeCB.addItem(townType);
			}
		} catch (OWLOntologyStorageException e2) {
			e2.printStackTrace();
		}
		
		
		//JTextField toppingNameTF = new JTextField(10);
		//JButton addToppingB = new JButton("������ �������");
		//JButton removeToppingB = new JButton("�������� �������");
		
		//JTextField oldNameToppingTF = new JTextField(10);
		//JTextField newNameToppingTF = new JTextField(10);
		//JButton editToppingNameB = new JButton("��������");
		
		//JTextField removeToppingNameTF = new JTextField(10);
		//JButton removeToppingByNameB = new JButton(
			//	"���������� �� �������");
		
		/*tripTypeLabel.setLabelFor(tripTypeCB);
		tripNameLabel.setLabelFor(tripNameTF);
		tripTownLabel.setLabelFor(tripTownTF);
		tripTownTypeLabel.setLabelFor(tripTownTypeCB);
		placeToStayTypeLabel.setLabelFor(placeToStayTypeTF);
		placeToStayNameLabel.setLabelFor(placeToStayNameTF);
		costByNightLabel.setLabelFor(costByNightTF);*/
	
		JPanel contentPane = new JPanel(new GridLayout(5,1));
		
		JPanel row1 = new JPanel();
		row1.add(tripTypeLabel);
		row1.add(tripTypeCB);
		row1.add(tripNameLabel);
		row1.add(tripNameTF);
		row1.add(tripTownLabel);
		row1.add(tripTownTF);
		row1.add(tripTownTypeLabel);
		row1.add(tripTownTypeCB);
		row1.add(placeToStayTypeLabel);
		row1.add(placeToStayTypeTF);
		row1.add(placeToStayNameLabel);
		row1.add(placeToStayNameTF);
		row1.add(costByNightLabel);
		row1.add(costByNightTF);
		
		row1.add(addTripB);
		
		contentPane.add(row1);
		
		/*JPanel row2 = new JPanel();
		row2.add(pizzaNameTF);
		row2.add(toppingNameTF);
		row2.add(addToppingB);
		row2.add(removeToppingB);
		contentPane.add(row2);
		
		JPanel row3 = new JPanel();
		row3.add(oldNameToppingTF);
		row3.add(newNameToppingTF);
		row3.add(editToppingNameB);
		contentPane.add(row3);
		
		JPanel row4 = new JPanel();
		row4.add(removeToppingNameTF);
		row4.add(removeToppingByNameB);
		contentPane.add(row4);*/
		
		setContentPane(contentPane);
		pack();
		//setVisible(true);		
				
		addTripB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			
				String tripType = tripTypeCB.getSelectedItem().toString(); //TO DO: add new trip types
				String tripName = tripNameTF.getText();
				String tripTown = tripTownTF.getText();
				String tripTownType = tripTownTypeCB.getSelectedItem().toString(); //TO DO: add new town types
				String placeToStayType = placeToStayTypeTF.getText();
				String placeToStayName = placeToStayNameTF.getText();
				double costByNight = Double.parseDouble(costByNightTF.getText());
				
				try {
					myAgent.addTrip(tripType, tripName, tripTown, tripTownType, placeToStayType, placeToStayName, costByNight);
				} catch (OWLOntologyStorageException e1) {
					e1.printStackTrace();
				}
			}	
		});	
	}
}
