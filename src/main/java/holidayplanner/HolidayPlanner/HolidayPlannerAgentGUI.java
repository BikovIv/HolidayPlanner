package holidayplanner.HolidayPlanner;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.semanticweb.owlapi.model.OWLOntologyStorageException;

public class HolidayPlannerAgentGUI  extends JFrame{
	
	HolidayPlannerAgent myAgent;
			
	public HolidayPlannerAgentGUI(HolidayPlannerAgent myAgent) {
		this.myAgent = myAgent;
		
		initialize();
	}
	
	private void initialize() {
		
		myAgent = new HolidayPlannerAgent();
		
		JComboBox<String> tripTypeCB = new JComboBox<>();
		JTextField tripNameTF = new JTextField(10);
		JTextField tripTownTF = new JTextField(10);
		//JTextField tripTownTypeTF = new JTextField(10);
		JComboBox<String> tripTownTypeCB = new JComboBox<>();
		JButton addTripB = new JButton("Add Trip");
		
		try {
			for(String tripType : myAgent.getAllTripTypes()) {
				tripTypeCB.addItem(tripType);
			}
		} catch (OWLOntologyStorageException e2) {
			e2.printStackTrace();
		}
		
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
		
		JPanel contentPane = new JPanel(new GridLayout(5,1));
		
		JPanel row1 = new JPanel();
		row1.add(tripTypeCB);
		row1.add(tripNameTF);
		row1.add(tripTownTF);
		row1.add(tripTownTypeCB);
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
		setVisible(true);		
				
		addTripB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			
				String tripType = tripTypeCB.getSelectedItem().toString(); //TO DO: add new trip types
				String tripName = tripNameTF.getText();
				String tripTown = tripTownTF.getText();
				String tripTownType = tripTownTypeCB.getSelectedItem().toString(); //TO DO: add new town types
				
				try {
					myAgent.addTrip(tripType, tripName, tripTown, tripTownType);
				} catch (OWLOntologyStorageException e1) {
					e1.printStackTrace();
				}
			}
		});	
	}
}
