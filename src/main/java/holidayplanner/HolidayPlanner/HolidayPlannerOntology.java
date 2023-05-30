package holidayplanner.HolidayPlanner;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.coode.owlapi.rdfxml.parser.DataQualifiedCardinalityTranslator;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.util.OWLEntityRemover;
import org.semanticweb.owlapi.util.OWLEntityRenamer;

public class HolidayPlannerOntology {

	private OWLOntologyManager ontoManager;
	private OWLOntology holidayPlannerOntology;
	private OWLDataFactory dataFactory;
	private OWLReasoner reasoner;
	
	private String ontologyIRIStr;
	private boolean contains = false; 
	
	public HolidayPlannerOntology() {
		ontoManager = OWLManager.createOWLOntologyManager();
		dataFactory = ontoManager.getOWLDataFactory();
		
		loadOntologyFromFile();
		
		ontologyIRIStr = holidayPlannerOntology.getOntologyID()
				.getOntologyIRI().toString() + "#";
		
	}
	
	private void loadOntologyFromFile() {
		File ontoFile = new File("src/main/java/OntologyFiles/20230521HolidayPlannerOntology.owl");
		
		try {
			holidayPlannerOntology = ontoManager
					.loadOntologyFromOntologyDocument(ontoFile);
		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Holiday> getHolidayByTown(String town){
		
		ArrayList<Holiday> result = new ArrayList<>();
		
		OWLObjectProperty hasTown = dataFactory
				.getOWLObjectProperty(
						IRI.create(ontologyIRIStr + "hasTown"));
		
		OWLClass townClass = dataFactory.getOWLClass(
				IRI.create(ontologyIRIStr + town));
		
		//��������� ������ ������� �� �����
		for(OWLAxiom axiom : 
			townClass.getReferencingAxioms(holidayPlannerOntology)) {
			
			//������� ��� �������� ���� ������� ����� �� ��� ����
			if(axiom.getAxiomType() == AxiomType.SUBCLASS_OF) {

				//������� ������ �������� �� ������������ �������
				for(OWLObjectProperty op: 
					axiom.getObjectPropertiesInSignature()) {
					
					//����������� ��� �������� IRI � ���� ����� ������
					if(op.getIRI().equals(hasTown.getIRI())) {
						
						//������� ������ ������� �� ���������
						for(OWLClass classInAxiom: 
							axiom.getClassesInSignature()) {
							
							if(containsSuperClass(
									classInAxiom.getSuperClasses(holidayPlannerOntology),
									dataFactory.getOWLClass(
											IRI.create(ontologyIRIStr + "HolidayPlanner")))) {
								
								contains = false;
								
								Holiday p = new Holiday();
								//p.setName(getClassFriendlyName(classInAxiom));
								p.setId(classInAxiom.getIRI().toQuotedString());
								
								//p.setToppings(getAllToppings(classInAxiom
									//	, hasTopping));
								
								result.add(p);							
							}
							
						}
					}
					
				}
				
			}
			
		}
		
		return result;
	}
/*	
public ArrayList<Town> getTownByName(String town){
		
		ArrayList<Town> result = new ArrayList<>();
		
		//WLObjectProperty hasTown = dataFactory
			//	.getOWLObjectProperty(
					//	IRI.create(ontologyIRIStr + "hasTown"));
		
		OWLClass townClass = dataFactory.getOWLClass(
				IRI.create(ontologyIRIStr + town));
		
		//��������� ������ ������� �� �����
		for(OWLAxiom axiom : 
			townClass.getReferencingAxioms(holidayPlannerOntology)) {
			
			//������� ��� �������� ���� ������� ����� �� ��� ����
			if(axiom.getAxiomType() == AxiomType.SUBCLASS_OF) {

				//������� ������ �������� �� ������������ �������
				for(OWLObjectProperty op: 
					axiom.getObjectPropertiesInSignature()) {
					
					//����������� ��� �������� IRI � ���� ����� ������
					if(op.getIRI().equals(hasTown.getIRI())) {
						
						//������� ������ ������� �� ���������
						for(OWLClass classInAxiom: 
							axiom.getClassesInSignature()) {
							
							if(containsSuperClass(
									classInAxiom.getSuperClasses(holidayPlannerOntology),
									dataFactory.getOWLClass(
											IRI.create(ontologyIRIStr + "HolidayPlanner")))) {
								
								contains = false;
								
								Holiday p = new Holiday();
								//p.setName(getClassFriendlyName(classInAxiom));
								p.setId(classInAxiom.getIRI().toQuotedString());
								
								//p.setToppings(getAllToppings(classInAxiom
									//	, hasTopping));
								
								result.add(p);							
							}
							
						}
					}
					
				}
				
			}
			
		}
		
		return result;
	}*/
	
	public List<String> getAllTripTypes() {
		
		List<String> results = new ArrayList<>();

		OWLClass tripTypeClass = dataFactory.getOWLClass(IRI.create(ontologyIRIStr + "TripTypes"));
	
		for(OWLIndividual tripType : 
			tripTypeClass.getIndividuals(holidayPlannerOntology)) {

			results.add(getClassFriendlyName(tripType.toString()));
		}
		
		return results;
	}
	
	public List<String> getAllTownTypes() {
		
		List<String> results = new ArrayList<>();

		OWLClass townTypeClass = dataFactory.getOWLClass(IRI.create(ontologyIRIStr + "TripTown"));
	
		for(OWLClassExpression townType : 
			townTypeClass.getSubClasses(holidayPlannerOntology)) {
			results.add(getClassFriendlyName(townType.toString()));
		}
		
		return results;
	}
	
	public List<String> getAllTowns() { //BY TYPE
		
		List<String> towns = new ArrayList<>();
		
		OWLClass townClass = dataFactory.getOWLClass(IRI.create(ontologyIRIStr + "TripTown"));
		
		for(OWLClassExpression town :
			townClass.getSubClasses(holidayPlannerOntology)) {
			//System.out.println("townClas - " + getClassFriendlyName(townClas));	
			towns.add(getClassFriendlyName(town));
		}	
		return towns;
	}
	
	private boolean containsSuperClass(
			Set<OWLClassExpression> setOfClasses,
			OWLClass superClass) {
		
		for(OWLClassExpression clsExps : setOfClasses) {
			
			for(OWLClass cls : clsExps.getClassesInSignature()) {
				if(cls.getIRI().equals(superClass.getIRI())) {
					contains = true;
				}else {
					if(cls.getSubClasses(holidayPlannerOntology).size() > 0) {
						containsSuperClass(
								cls.getSuperClasses(holidayPlannerOntology), 
								superClass);
					}
				}
			}
			
		}	
		return contains;
	}
	
	private String getClassFriendlyName(OWLClass cls) {
		
		String label = cls.getIRI().toString();
		label = label.substring(label.indexOf('#') + 1);
		
		return label;		
	}
	
	private String getClassFriendlyName(OWLClassExpression cls) {
			
			String label = cls.toString();
			label = label.substring(label.indexOf('#') + 1).replace(">", "");
			
			return label;		
		}
	
	private String getClassFriendlyName(String text) {
		
		String label = text.toString();
		label = label.substring(label.indexOf('#') + 1).replace(">", "");
		
		return label;		
	}
	
	private void saveOntology() {
		try {
			ontoManager.saveOntology(holidayPlannerOntology);
		} catch (OWLOntologyStorageException e) {
			e.printStackTrace();
		}
	}
	
	
	public void addTrip(String tripType, String tripName, String town, String townType) {
		
		OWLClass tripClass = dataFactory.getOWLClass(
				IRI.create(ontologyIRIStr + tripName));
		//System.out.println("Onto - addTrip - tripClass - " + tripClass);
		
		OWLClass townClass = dataFactory.getOWLClass(
				IRI.create(ontologyIRIStr + town));
		//System.out.println("Onto - addTrip - townClassIRI - " + IRI.create(ontologyIRIStr + town));
		
		//ako izbraniq grad ne sashtestvuva go dobavqme kam saotvetnata kategoriq
		if(!holidayPlannerOntology.containsClassInSignature(IRI.create(ontologyIRIStr + town))) {
			//System.out.println(townClass + " doens not exist and will be added first");
			
			//System.out.println("addTown("+townType+", "+town+"); will be executed" );
			addTown(townType, town);
		}
		
		OWLObjectProperty hasTown = dataFactory.
				getOWLObjectProperty(IRI.create(
						ontologyIRIStr + "hasTown"));
		
		OWLClassExpression clssExpression = dataFactory.
				getOWLObjectSomeValuesFrom(hasTown, townClass);
		
		OWLSubClassOfAxiom newAxiom = dataFactory.
				getOWLSubClassOfAxiom(tripClass, clssExpression);
		
		OWLClass paretClass = dataFactory.getOWLClass(
				IRI.create(ontologyIRIStr + tripType));
	
		//System.out.println("Onto - addTrip - paretClass - " + paretClass);
		
		OWLSubClassOfAxiom subClassOf = dataFactory.
				getOWLSubClassOfAxiom(tripClass, paretClass);
		
		//System.out.println("Onto - addTrip - subClassOf - " + subClassOf);
		

		AddAxiom axiom = new AddAxiom(holidayPlannerOntology, subClassOf);
		AddAxiom addAxiom = new AddAxiom(holidayPlannerOntology, newAxiom);
		
		//System.out.println("Onto - addTrip - axiom - " + axiom);
		
		List<AddAxiom> changes = new ArrayList<>(); ;
		changes.add(axiom);
		changes.add(addAxiom);
		
		//ontoManager.applyChange(axiom);
		ontoManager.applyChanges(changes);
		saveOntology();		
	}
	

	public void addTown(String townType, String townName) {

		OWLClass townClass = dataFactory.getOWLClass(
				IRI.create(ontologyIRIStr + townName));
		//System.out.println("Onto - addTrip - tripClass - " + townClass);
		
		OWLClass paretClass = dataFactory.getOWLClass(
				IRI.create(ontologyIRIStr + townType));
	
		//System.out.println("Onto - addTrip - paretClass - " + paretClass);
		
		OWLSubClassOfAxiom subClassOf = dataFactory.
				getOWLSubClassOfAxiom(townClass, paretClass);
		
		//System.out.println("Onto - addTrip - subClassOf - " + subClassOf);		
		
		AddAxiom axiom = new AddAxiom(holidayPlannerOntology, subClassOf);
		
		//System.out.println("Onto - addTrip - axiom - " + axiom);
		List<AddAxiom> changes = new ArrayList<>(); ;
		changes.add(axiom);
		
		//ontoManager.applyChange(axiom);
		ontoManager.applyChanges(changes);

		saveOntology();		
	}
	
	/*public void addToppingToPizza(String pizzaTypeName,
			String toppingName) {
		//������ ��� ����� �� ������ ������
		OWLClass pizzaCls = dataFactory.getOWLClass(
				IRI.create(ontologyIRIStr + pizzaTypeName));
		//������ ����� ����� �� ����� �� ������
		OWLClass toppingCls = dataFactory.getOWLClass(
				IRI.create(ontologyIRIStr + toppingName));
		
		//���������� ���������� ����� ������� ������� � ������
		OWLObjectProperty hasTopping = dataFactory.
				getOWLObjectProperty(IRI.create(
						ontologyIRIStr + "hasTopping"));
		//������ ���������� �� ������������
		OWLClassExpression clssExpression = dataFactory.
				getOWLObjectSomeValuesFrom(hasTopping, toppingCls);
		
		//������ ������� ������
		OWLSubClassOfAxiom axiom = dataFactory.
				getOWLSubClassOfAxiom(pizzaCls,clssExpression);
		
		//��������� ������ �� ��������
		AddAxiom addAxiom = new AddAxiom(holidayPlannerOntology, axiom);
		//�������� �������
		ontoManager.applyChange(addAxiom);
		//��������� �����������
		saveOntology();
		
		
	}
	
	public void renameTopping(String oldName, String newName) {
		//������� ��������� �� ����� ����� �� ������������
		OWLClass oldClass = dataFactory.getOWLClass(
				IRI.create(ontologyIRIStr + oldName));
		
		//��������� renamer ����� �� �� �������� �� ������������
		OWLEntityRenamer renamer = 
				new OWLEntityRenamer(ontoManager, 
						Collections.singleton(holidayPlannerOntology));
		
		//������� ������� IRI
		IRI oldIRI = oldClass.getIRI();
		//��������� ������ IRI
		IRI newIRI = IRI.create(oldIRI.getNamespace(), newName);
		
		//���������� �������������� � ������� ��������� �� ������ �������
		List<OWLOntologyChange> changes = renamer.changeIRI(oldIRI, newIRI);
		//���������� �����������
		ontoManager.applyChanges(changes);
		//��������� ���������
		saveOntology();
	}
	
	public void removePizza(String name) {
		//������ ����� �� ���������
		OWLClass pizzaToRemove = dataFactory.getOWLClass(
				IRI.create(ontologyIRIStr + name));
		//��������� remover
		OWLEntityRemover remover = 
				new OWLEntityRemover
					(ontoManager, Collections.singleton(holidayPlannerOntology));
	
		//������ �� ����������
		pizzaToRemove.accept(remover);
		
		//���������� ������������
		ontoManager.applyChanges(remover.getChanges());
		
		//��������� ���������
		saveOntology();
	}*/
}
