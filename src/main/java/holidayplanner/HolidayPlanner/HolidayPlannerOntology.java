package holidayplanner.HolidayPlanner;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.coode.owlapi.rdfxml.parser.DataQualifiedCardinalityTranslator;
import org.semanticweb.HermiT.Reasoner.ReasonerFactory;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLNamedObject;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;
import org.semanticweb.owlapi.util.OWLEntityRemover;
import org.semanticweb.owlapi.util.OWLEntityRenamer;
import org.semanticweb.owlapi.util.OWLObjectVisitorAdapter;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

public class HolidayPlannerOntology {

	private OWLOntologyManager ontoManager;
	private OWLOntology holidayPlannerOntology;
	private OWLDataFactory dataFactory;
	//private OWLReasoner reasoner;
	
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
		//File ontoFile = new File("src/main/java/OntologyFiles/20230521HolidayPlannerOntology.owl");
		File ontoFile = new File("src/main/java/OntologyFiles/2023-08-21 - HolidayPlannerOntology.owl");
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

	//method ok readed data in web
public ArrayList<Holiday> getHolidayByUserParameters(String tripType, String tripSubType, String placeToStayType, String tripTownType, double placeToStayPrice){
	
	System.out.println(tripType + ", " + placeToStayType + ", " + tripTownType + ", " + placeToStayPrice);
	
	boolean containPropertyTown = false;
	boolean containPropertyPlace = false;
	ArrayList<Holiday> result = new ArrayList<>();
	ArrayList<Holiday> tempResult = new ArrayList<>();
	ArrayList<Holiday> tempResult2 = new ArrayList<>();
	HashSet<Holiday> set = null; //= new HashSet<>(list1);
	
	OWLObjectProperty hasPlaceToStay = dataFactory
			.getOWLObjectProperty(
					IRI.create(ontologyIRIStr + "hasPlaceToStay"));
	
	OWLObjectProperty hasTown = dataFactory
			.getOWLObjectProperty(
					IRI.create(ontologyIRIStr + "hasTown"));
	
	OWLClass townClass = dataFactory.getOWLClass(
			IRI.create(ontologyIRIStr + tripType));
	
	OWLClass placeToStayTypeClass = dataFactory.getOWLClass(
			IRI.create(ontologyIRIStr + "Hotel"));
	
	OWLClass tripTownTypeClass = dataFactory.getOWLClass(
			IRI.create(ontologyIRIStr + "SeaTown"));
		
	System.out.println("townClass" + townClass);
	System.out.println("placeToStayTypeClass" + placeToStayTypeClass);
	System.out.println("tripTownTypeClass" + tripTownTypeClass);
	
	for(OWLClassExpression subCls : 
		townClass.getSubClasses(holidayPlannerOntology)) {
		
		tempResult.clear();
		tempResult2.clear();
		containPropertyTown = false;
		containPropertyPlace = false;
		
		for(OWLAxiom axiom : 
			subCls.asOWLClass().getReferencingAxioms(holidayPlannerOntology)) {
			
			for(OWLObjectProperty op: 
				axiom.getObjectPropertiesInSignature()) {				

				System.out.println("op1: " + op);
				System.out.println("op.getIRI()1: " + op.getIRI());
				System.out.println("hasPlaceToStay.getIRI()1: " + hasPlaceToStay.getIRI());
				
				if(hasPlaceToStay.getIRI().equals(op.getIRI())){
					containPropertyPlace = true;
					
					for(Holiday h : tempResult) { System.out.println("tempResult-1: " + h.getId()); }
					
					if(tempResult.size() == 0) {
						tempResult.addAll(getAllClasses(axiom, placeToStayTypeClass, subCls, tempResult));//tempResult.retainAll(tempResult);
					}else {
						
						tempResult2.addAll(getAllClasses(axiom, placeToStayTypeClass, subCls, tempResult));
						
						 set = new HashSet<>(tempResult2);
						
						if(set.size() > 0) {
						 for (Holiday element : tempResult) {
					            if (set.contains(element)) {
					            	result.add(element);
					            }
					        }
						}else {
							tempResult.clear();
						}
					}
					
					for(Holiday h : tempResult) { System.out.println("tempResult-1.1: " + h.getId()); }
					
					System.out.println("axiom1: " + axiom);
					System.out.println("placeToStayTypeClass1: " + placeToStayTypeClass);
					System.out.println("subCls1: " + subCls);
				}
				
				System.out.println("op2: " + op);
				System.out.println("op.getIRI()2: " + op.getIRI());
				System.out.println("hasTown.getIRI()2: " + hasTown.getIRI());
				
				if(hasTown.getIRI().equals(op.getIRI())) {
					containPropertyTown = true;
					
					for(Holiday h : tempResult) {	System.out.println("tempResult-2: " + h.getId());}
				
					if(tempResult.size() == 0) {
						tempResult.addAll(getAllClasses(axiom, tripTownTypeClass, subCls, tempResult));//tempResult.retainAll(tempResult);
					}else {
						
						tempResult2.addAll(getAllClasses(axiom, tripTownTypeClass, subCls, tempResult));
						
						 set = new HashSet<>(tempResult2);			
						
						if(set.size() > 0) {
						 for (Holiday element : tempResult) {
					            if (set.contains(element)) {
					            	result.add(element);
					            }
					       }
						}else {
							tempResult.clear();
						}
						
					}
					
					for(Holiday h : tempResult) {System.out.println("tempResult-2.2: " + h.getId());}
						
					System.out.println("axiom2: " + axiom);
					System.out.println("placeToStayTypeClass2: " + tripTownTypeClass);
					System.out.println("subCls2: " + subCls);
				}
				
				for(Holiday h : result) {	System.out.println("h5: " + h.getId());}

				}			
			}
		
		System.out.println("containPropertyPlace: " + containPropertyPlace);
		System.out.println("containPropertyTown: " + containPropertyTown);
		if(containPropertyPlace && containPropertyTown) {
			result.addAll(unifyArray(tempResult));
		}
	}
	
	/*for (Holiday element :  tempResult) { System.out.println("tempResult: " + element.getId());}
	  for (Holiday element : result) { System.out.println("result: " + element.getId());}*/
	
	return result;
	}

public Collection<? extends Holiday> getAllClasses(OWLAxiom axiom, OWLClass comparedClass, OWLClassExpression classToFind,
													ArrayList<Holiday> startData){
	
	ArrayList<Holiday> result = new ArrayList<>();
	
	 OWLSubClassOfAxiom subClassAxiomPlaceToStay = (OWLSubClassOfAxiom) axiom;
		OWLClassExpression superClassPlaceToStay = subClassAxiomPlaceToStay.getSuperClass();
		OWLObjectSomeValuesFrom objectSomeValuesFromPlaceToStay = (OWLObjectSomeValuesFrom) superClassPlaceToStay;

		for(OWLClass classInAxiom: 
			objectSomeValuesFromPlaceToStay.getClassesInSignature()) {
			//System.out.println("classInAxiom: " + classInAxiom);
			for(OWLClassExpression ex : 
				classInAxiom.getSuperClasses(holidayPlannerOntology)) {
				
				//System.out.println("ex: " + ex);
				//System.out.println("ex: " + ex.isClassExpressionLiteral()); //tuk sa samo jelanite tipove mesta za otsqdane
				if(ex.isClassExpressionLiteral() && ex.equals(comparedClass)) { //samo ako e klasa na mestoto za otsqdane hotel ili motel
					
					Holiday p = new Holiday();
					p.setId(classToFind.toString());
					
					result.add(p);
					
					//System.out.println("Holiday p: " + p.getId());
				}	
			}
		}
		
		return result;
	}

public ArrayList<Holiday> unifyArray(Collection<? extends Holiday> collection){
	
ArrayList<Holiday> startData = new ArrayList<Holiday>(collection);	
ArrayList<Holiday> results = new ArrayList<Holiday>();
Set<String> ids = new HashSet<String>();

for(Holiday item : collection) {
    if(ids.add(item.getId())) {
    	results.add(item);
    }
}

results.retainAll(collection);

return results;
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
/*//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////*/
public List<Holiday> getAllTripTypesFromOntology(String tripTypeInput, String tripSubTypeInput) {
	
	Holiday trip;// = new Holiday();
	
	List<Holiday> results = new ArrayList<>();
	ReasonerFactory reasonerFactory = new ReasonerFactory();
	OWLReasoner reasoner = reasonerFactory.createReasoner(holidayPlannerOntology);
	
	//OWLClass tripTypeClass = dataFactory.getOWLClass(IRI.create(ontologyIRIStr + tripTypeInput));
	OWLClass tripSubTypeClass = dataFactory.getOWLClass(IRI.create(ontologyIRIStr + tripSubTypeInput));
	 
	reasoner.precomputeInferences();

	NodeSet<OWLNamedIndividual> individuals = reasoner.getInstances(tripSubTypeClass, false);
	
	Node<OWLObjectPropertyExpression> topObjectProperties = reasoner.getTopObjectPropertyNode();
	NodeSet<OWLNamedIndividual> objectPropertyValues;
	Set<OWLAnnotation> annotations;
	
	OWLObjectProperty objectPropertyTown = ontoManager.getOWLDataFactory().getOWLObjectProperty(IRI.create(ontologyIRIStr + "hasTown"));
	OWLObjectProperty objectPropertyPlaceToStay = ontoManager.getOWLDataFactory().getOWLObjectProperty(IRI.create(ontologyIRIStr + "hasPlaceToStay"));
	OWLObjectProperty objectPropertyTripType = ontoManager.getOWLDataFactory().getOWLObjectProperty(IRI.create(ontologyIRIStr + "hasTripType"));
	OWLObjectProperty objectPropertyDestination = ontoManager.getOWLDataFactory().getOWLObjectProperty(IRI.create(ontologyIRIStr + "hasDestination"));
	OWLObjectProperty objectPropertySeason = ontoManager.getOWLDataFactory().getOWLObjectProperty(IRI.create(ontologyIRIStr + "hasSeason"));
	OWLObjectProperty objectPropertyTransportation = ontoManager.getOWLDataFactory().getOWLObjectProperty(IRI.create(ontologyIRIStr + "hasTransportation"));
	 
	for (OWLNamedIndividual individual : individuals.getFlattened()) {
		
		annotations = individual.getAnnotations(holidayPlannerOntology);
		
	    System.out.println("Current Individual: " + getClassFriendlyName(individual.getIRI().toString()));
	    
	    trip = new Holiday();
	    
	    trip.setIri(individual.getIRI().toString());
	    trip.setId(individual.getIRI().toString());
	
	    for (OWLObjectPropertyExpression objectPropertyExpression : topObjectProperties.getEntities()) {
	    	
	    	for (OWLObjectProperty OWLobjectProperty : holidayPlannerOntology.getObjectPropertiesInSignature()) {
	    	    // Get object property values for the individual
	    	    objectPropertyValues = reasoner.getObjectPropertyValues(individual, OWLobjectProperty);

	    	    
	    	    // Check if there are any values related to the individual through this property
	    	    if (!objectPropertyValues.isEmpty() && OWLobjectProperty.equals(objectPropertyTown)) {
	    	        //System.out.println("Object Property IRI: " + OWLobjectProperty.getIRI().toString());
	    	       
	    	        // Iterate through the individuals related to the individual through the object property
	    	        for (Node<OWLNamedIndividual> value : objectPropertyValues) {
	    	            for (OWLNamedIndividual relatedIndividual : value.getEntities()) {
	    	                //System.out.println("Related Individual: " + relatedIndividual.getIRI());
	    	                trip.setTown(getClassFriendlyName(relatedIndividual.getIRI().toString()));
	    	            }
	    	        }
	    	    }
	    	    
	    	    if (!objectPropertyValues.isEmpty() && OWLobjectProperty.equals(objectPropertyPlaceToStay)) {
	    	        System.out.println("Object Property IRI: " + OWLobjectProperty.getIRI().toString());

	    	        for (Node<OWLNamedIndividual> value : objectPropertyValues) {
	    	            for (OWLNamedIndividual relatedIndividual : value.getEntities()) {
	    	                //System.out.println("Related Individual: " + relatedIndividual.getIRI());
	    	                trip.setPlaceToStay(getClassFriendlyName(relatedIndividual.getIRI().toString()));
	    	            }
	    	        }
	    	    }
	    	    
	    	    if (!objectPropertyValues.isEmpty() && OWLobjectProperty.equals(objectPropertyTripType)) {
	    	        //System.out.println("Object Property IRI: " + OWLobjectProperty.getIRI().toString());

	    	        for (Node<OWLNamedIndividual> value : objectPropertyValues) {
	    	            for (OWLNamedIndividual relatedIndividual : value.getEntities()) {
	    	                //System.out.println("Related Individual: " + relatedIndividual.getIRI());
	    	                trip.setType(getClassFriendlyName(relatedIndividual.getIRI().toString()));
	    	            }
	    	        }
	    	    }
	    	    
	    	    if (!objectPropertyValues.isEmpty() && OWLobjectProperty.equals(objectPropertyDestination)) {
	    	        System.out.println("Object Property IRI: " + OWLobjectProperty.getIRI().toString());

	    	        for (Node<OWLNamedIndividual> value : objectPropertyValues) {
	    	            for (OWLNamedIndividual relatedIndividual : value.getEntities()) {
	    	                //System.out.println("Related Individual: " + relatedIndividual.getIRI());
	    	                trip.setDestination(getClassFriendlyName(relatedIndividual.getIRI().toString()));
	    	            }
	    	        }
	    	    }	 
	    	}
		}
	    
	    for (OWLAnnotation annotation : annotations) {
	    	if(annotation.getValue().toString().contains("@en")) {
	    		
	    		trip.setDescription(annotation.getValue().toString().replaceAll("\"", ""));
	    		 System.out.println("Annotation Property: " + annotation.getProperty());

	 	        System.out.println("Annotation Value: " + annotation.getValue());
	    	}
	    	
	       
	        
	        
	    }
	    
	    for (OWLDataPropertyAssertionAxiom dataPropertyAssertion : holidayPlannerOntology.getDataPropertyAssertionAxioms(individual)) {
	        OWLDataProperty property = (OWLDataProperty) dataPropertyAssertion.getProperty();
	        OWLLiteral value = dataPropertyAssertion.getObject();
	        
	        System.out.println("Data Property: " + getClassFriendlyName(property.toString()));
	        System.out.println("Value: " + value.getLiteral());
	        
	        if(getClassFriendlyName(property.toString()).equals("name") ) {
	        	trip.setName(getClassFriendlyName(value.getLiteral().toString()));
	        }
	        
	        if(getClassFriendlyName(property.toString()).equals("inSeason") ) {
	        	trip.setSeason(value.getLiteral().toString());
	        }
	        
	        if(getClassFriendlyName(property.toString()).equals("travelBy") ) {
	        	trip.setTransportation(value.getLiteral().toString());
	        }
	    }
	    
	    results.add(trip);
	}
	
	// Dispose of the reasoner resources
	reasoner.dispose();
	
	return results;
}

		public Holiday getTripByIRI(String tripIRI) {
			
			Holiday trip = new Holiday();
			NodeSet<OWLNamedIndividual> objectPropertyValues;
			ReasonerFactory reasonerFactory = new ReasonerFactory();
			OWLReasoner reasoner = reasonerFactory.createReasoner(holidayPlannerOntology);
			
			OWLObjectProperty objectPropertyTown = ontoManager.getOWLDataFactory().getOWLObjectProperty(IRI.create(ontologyIRIStr + "hasTown"));
			OWLObjectProperty objectPropertyPlaceToStay = ontoManager.getOWLDataFactory().getOWLObjectProperty(IRI.create(ontologyIRIStr + "hasPlaceToStay"));
			OWLObjectProperty objectPropertyTripType = ontoManager.getOWLDataFactory().getOWLObjectProperty(IRI.create(ontologyIRIStr + "hasTripType"));
			OWLObjectProperty objectPropertyDestination = ontoManager.getOWLDataFactory().getOWLObjectProperty(IRI.create(ontologyIRIStr + "hasDestination"));
			OWLObjectProperty objectPropertySeason = ontoManager.getOWLDataFactory().getOWLObjectProperty(IRI.create(ontologyIRIStr + "hasSeason"));
			OWLObjectProperty objectPropertyTransportation = ontoManager.getOWLDataFactory().getOWLObjectProperty(IRI.create(ontologyIRIStr + "hasTransportation"));
			 
			
			
	
	        //OWLOntologyManager manager = OWLManager.createOWLOntologyManager();

	        // Create an IRI object from the input string
			IRI individualIRI = IRI.create(tripIRI);

			// Use the dataFactory to get the OWLNamedIndividual
			OWLNamedIndividual individual = dataFactory.getOWLNamedIndividual(individualIRI);
			
			for (OWLObjectProperty OWLobjectProperty : holidayPlannerOntology.getObjectPropertiesInSignature()) {
			
			objectPropertyValues = reasoner.getObjectPropertyValues(individual, OWLobjectProperty);
			
			//for (OWLObjectProperty OWLobjectProperty : holidayPlannerOntology.getObjectPropertiesInSignature()) {
	    	    // Get object property values for the individual
	    	    //objectPropertyValues = reasoner.getObjectPropertyValues(individual, OWLobjectProperty);

	    	    
	    	    // Check if there are any values related to the individual through this property
	    	    if (!objectPropertyValues.isEmpty() && OWLobjectProperty.equals(objectPropertyTown)) {
	    	        //System.out.println("Object Property IRI: " + OWLobjectProperty.getIRI().toString());
	    	       
	    	        // Iterate through the individuals related to the individual through the object property
	    	        for (Node<OWLNamedIndividual> value : objectPropertyValues) {
	    	            for (OWLNamedIndividual relatedIndividual : value.getEntities()) {
	    	                //System.out.println("Related Individual: " + relatedIndividual.getIRI());
	    	                trip.setTown(getClassFriendlyName(relatedIndividual.getIRI().toString()));
	    	            }
	    	        }
	    	    }
	    	    
	    	    if (!objectPropertyValues.isEmpty() && OWLobjectProperty.equals(objectPropertyPlaceToStay)) {
	    	        System.out.println("Object Property IRI: " + OWLobjectProperty.getIRI().toString());

	    	        for (Node<OWLNamedIndividual> value : objectPropertyValues) {
	    	            for (OWLNamedIndividual relatedIndividual : value.getEntities()) {
	    	                //System.out.println("Related Individual: " + relatedIndividual.getIRI());
	    	                trip.setPlaceToStay(getClassFriendlyName(relatedIndividual.getIRI().toString()));
	    	            }
	    	        }
	    	    }
	    	    
	    	    if (!objectPropertyValues.isEmpty() && OWLobjectProperty.equals(objectPropertyTripType)) {
	    	        //System.out.println("Object Property IRI: " + OWLobjectProperty.getIRI().toString());

	    	        for (Node<OWLNamedIndividual> value : objectPropertyValues) {
	    	            for (OWLNamedIndividual relatedIndividual : value.getEntities()) {
	    	                //System.out.println("Related Individual: " + relatedIndividual.getIRI());
	    	                trip.setType(getClassFriendlyName(relatedIndividual.getIRI().toString()));
	    	            }
	    	        }
	    	    }
	    	    
	    	    if (!objectPropertyValues.isEmpty() && OWLobjectProperty.equals(objectPropertyDestination)) {
	    	        //System.out.println("Object Property IRI: " + OWLobjectProperty.getIRI().toString());

	    	        for (Node<OWLNamedIndividual> value : objectPropertyValues) {
	    	            for (OWLNamedIndividual relatedIndividual : value.getEntities()) {
	    	                //System.out.println("Related Individual: " + relatedIndividual.getIRI());
	    	                trip.setDestination(getClassFriendlyName(relatedIndividual.getIRI().toString()));
	    	            }
	    	        }
	    	    }
	    	    
	    	    
	   }
			

			return trip;
	    }
	
	public List<String> getAllTripTypes(String tripType) {
		
		if(tripType == null){
			tripType = "Trip";
		}
		
		List<String> results = new ArrayList<>();

		OWLClass tripTypeClass = dataFactory.getOWLClass(IRI.create(ontologyIRIStr + tripType));
		System.out.println("tripTypeClass: " + tripTypeClass.getSubClasses(holidayPlannerOntology));
		System.out.println("trip_type: " + tripType);
		//OWLClass tripTypeClass = dataFactory.getOWLClass(IRI.create(ontologyIRIStr + "TripTypes"));

		for(OWLClassExpression tripTypeOWL : 
			tripTypeClass.getSubClasses(holidayPlannerOntology)) {

			results.add(getClassFriendlyName(tripTypeOWL.toString()));
		}
		
		/*for(OWLIndividual tripType : 
			tripTypeClass.getIndividuals(holidayPlannerOntology)) {

			results.add(getClassFriendlyName(tripType.toString()));
		}*/
		
		return results;
		
		/*List<String> results = new ArrayList<>();

		OWLClass tripTypeClass = dataFactory.getOWLClass(IRI.create(ontologyIRIStr + "TripTypes"));
	
		for(OWLIndividual tripType : 
			tripTypeClass.getIndividuals(holidayPlannerOntology)) {

			results.add(getClassFriendlyName(tripType.toString()));
		}
		
		return results;*/
	}
	
	public ArrayList<TripType> getAllTripTypesArray() {
		
			ArrayList<TripType> result = new ArrayList<>();
			
			OWLClass tripTypeClass = dataFactory.getOWLClass(IRI.create(ontologyIRIStr + "TripTypes"));
			
			for(OWLIndividual tripType : 
				tripTypeClass.getIndividuals(holidayPlannerOntology)) {
				
				TripType p = new TripType();
				//p.setName(getClassFriendlyName(classInAxiom));
				p.setId(((OWLNamedObject) tripType).getIRI().toQuotedString());
				
				result.add(p);	

			}
				
			return result;
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
	
	public ArrayList<TripTownType> getAllTownTypesArray() {
		
		ArrayList<TripTownType> result = new ArrayList<>();

		OWLClass townTypeClass = dataFactory.getOWLClass(IRI.create(ontologyIRIStr + "TripTown"));
	
		for(OWLClassExpression townType : 
			townTypeClass.getSubClasses(holidayPlannerOntology)) {
			//result.add(getClassFriendlyName(townType.toString()));
			
			TripTownType p = new TripTownType();
			p.setId(((OWLNamedObject) townType).getIRI().toQuotedString());
			
			result.add(p);
		}
		
		return result;
	}
	
	public ArrayList<PlaceToStayType> getAllPlaceToStayTypesArray() {
		
		ArrayList<PlaceToStayType> result = new ArrayList<>();
		
		OWLClass placeToStayTypeClass = dataFactory.getOWLClass(IRI.create(ontologyIRIStr + "PlaceToStay"));
		
		for(OWLClassExpression place : 
			placeToStayTypeClass.getSubClasses(holidayPlannerOntology)) {
			//System.out.println("place: " + place);
			//System.out.println("place: " + place.containsEntityInSignature(placeToStayTypeClass));
			//System.out.println("place: " + place.getNestedClassExpressions());
			
			PlaceToStayType p = new PlaceToStayType();
			p.setId(((OWLNamedObject) place).getIRI().toQuotedString());
			
			result.add(p);
		}
		
	/*	for(OWLClass place : placeToStayType.getClassesInSignature()) {
			System.out.println(place);
			PlaceToStayType p = new PlaceToStayType();
			p.setId(place.getIRI().toQuotedString());*/
			
			
			//result.add(p);	

		//}
			
		return result;
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
	
	
	public void addTrip(String tripType, String tripName, String town, String townType, String placeToStayType,
						String placeToStayName, double costByNight) {
		
		OWLClass tripClass = dataFactory.getOWLClass(
				IRI.create(ontologyIRIStr + tripName));
		//System.out.println("Onto - addTrip - tripClass - " + tripClass);
		
		OWLClass townClass = dataFactory.getOWLClass(
				IRI.create(ontologyIRIStr + town));
		//System.out.println("Onto - addTrip - townClassIRI - " + IRI.create(ontologyIRIStr + town));
		
		OWLClass placeToStayClass = dataFactory.getOWLClass(
				IRI.create(ontologyIRIStr + placeToStayName));
		
		//ako izbraniq grad ne sashtestvuva go dobavqme kam saotvetnata kategoriq
		if(!holidayPlannerOntology.containsClassInSignature(IRI.create(ontologyIRIStr + town))) {
			//System.out.println(townClass + " doens not exist and will be added first");
			
			//System.out.println("addTown("+townType+", "+town+"); will be executed" );
			addTown(townType, town);
		}
		
		//ako izbranoto mqsto za otsqdane ne sashtestvuva go dobavqme kam saotvetnata kategoriq
				if(!holidayPlannerOntology.containsClassInSignature(IRI.create(ontologyIRIStr + placeToStayName))) {
					//System.out.println(townClass + " doens not exist and will be added first");
					
					//System.out.println("addTown("+townType+", "+town+"); will be executed" );
					addPlaceToStay( placeToStayType/*Hotel/Motel*/, placeToStayName, costByNight);
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
		
		OWLObjectProperty hasPlaceToStay = dataFactory.
				getOWLObjectProperty(IRI.create(
						ontologyIRIStr + "hasPlaceToStay"));
		
		OWLClassExpression clssExpressionPlaceToStay = dataFactory.
				getOWLObjectSomeValuesFrom(hasPlaceToStay, placeToStayClass);
		
		OWLSubClassOfAxiom newAxiomPlace = dataFactory. //????
				getOWLSubClassOfAxiom(tripClass, clssExpressionPlaceToStay);
		
		OWLClass paretClassplc = dataFactory.getOWLClass(
				IRI.create(ontologyIRIStr + placeToStayType));
	
		//System.out.println("Onto - addTrip - paretClass - " + paretClass);
		
		OWLSubClassOfAxiom subClassOfplace = dataFactory.
				getOWLSubClassOfAxiom(placeToStayClass, paretClassplc);
		
		

		AddAxiom axiom = new AddAxiom(holidayPlannerOntology, subClassOf);
		AddAxiom addAxiom = new AddAxiom(holidayPlannerOntology, newAxiom);
		AddAxiom addAxiompls = new AddAxiom(holidayPlannerOntology, newAxiomPlace);
		
		AddAxiom addAxiomplc = new AddAxiom(holidayPlannerOntology, subClassOfplace);
		
		
		//System.out.println("Onto - addTrip - axiom - " + axiom);
		
		List<AddAxiom> changes = new ArrayList<>(); ;
		changes.add(axiom);
		changes.add(addAxiom);
		changes.add(addAxiompls);
		
		changes.add(addAxiomplc);
		
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

	public void addPlaceToStay(String placeToStayType/*Hotel/Motel*/, String placeToStayName, double costByNight) {

		OWLClass placeToStayClass = dataFactory.getOWLClass(
				IRI.create(ontologyIRIStr + placeToStayName));

		OWLClass paretClass = dataFactory.getOWLClass(
				IRI.create(ontologyIRIStr + placeToStayType));
		
		OWLSubClassOfAxiom subClassOf = dataFactory.
				getOWLSubClassOfAxiom(placeToStayClass, paretClass);
		
		AddAxiom axiom = new AddAxiom(holidayPlannerOntology, subClassOf);
		
        // Get the data property
        OWLDataProperty dataProperty = dataFactory.getOWLDataProperty(IRI.create(ontologyIRIStr + "hasCostByNight"));

        // Create the data property value
        OWLLiteral dataPropertyValue = dataFactory.getOWLLiteral(costByNight);

        // Create the class restriction
        OWLClassExpression classRestriction = dataFactory.getOWLDataHasValue(dataProperty, dataPropertyValue);

        // Create the subclass axiom
        OWLAxiom classAxiom = dataFactory.getOWLSubClassOfAxiom(placeToStayClass, classRestriction);
        AddAxiom axiom2 = new AddAxiom(holidayPlannerOntology, classAxiom);
        
        List<AddAxiom> changes = new ArrayList<>(); ;
		changes.add(axiom);
		changes.add(axiom2);
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
