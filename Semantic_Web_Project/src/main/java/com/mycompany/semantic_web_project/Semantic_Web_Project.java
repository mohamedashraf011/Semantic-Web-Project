package com.mycompany.semantic_web_project;

import java.io.File;
import java.io.InputStream;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.HermiT.ReasonerFactory;
import org.semanticweb.owlapi.reasoner.*;

public class Semantic_Web_Project 
{

    public static void main(String[] args) throws Exception
    {
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        File file = new File("src/main/resources/library.rdf");
        OWLOntology ontology = manager.loadOntologyFromOntologyDocument(file);
        System.out.println("Ontology Loaded: " + ontology.getOntologyID());
        
        OWLReasonerFactory reasonerFactory = new ReasonerFactory();
        OWLReasoner reasoner = reasonerFactory.createReasoner(ontology);
        
        reasoner.precomputeInferences(InferenceType.CLASS_HIERARCHY);
        System.out.println("Is ontology consistent? " + reasoner.isConsistent());
        
        OWLDataFactory factory = manager.getOWLDataFactory();
        
        System.out.println("Hello World!");
    
        // Define the IRI for the class ProgrammingBook
        OWLClass programmingBookClass = factory.getOWLClass(
        IRI.create("http://www.semanticweb.org/mo7am/ontologies/2025/3/library#ProgrammingBook")
        );

        // Ask the reasoner for all instances of ProgrammingBook
        NodeSet<OWLNamedIndividual> individuals = reasoner.getInstances(programmingBookClass,false);

        // Print the individuals
        System.out.println("\n📚 List of ProgrammingBooks:");
        for (OWLNamedIndividual individual : individuals.getFlattened()) {
        System.out.println(" - " + individual.getIRI().getShortForm());
        }

    }
}
