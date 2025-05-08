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
    
        // List all individuals in class ProgrammingBook
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
        
        //  ------------------------------------------------------------------------------------

        // Step 1: Define the required IRIs
        IRI hasAuthorIRI = IRI.create("http://www.semanticweb.org/mo7am/ontologies/2025/3/library#hasAuthor");
        IRI recommendedForIRI = IRI.create("http://www.semanticweb.org/mo7am/ontologies/2025/3/library#recommendedFor");
        IRI authorIRI = IRI.create("http://www.semanticweb.org/mo7am/ontologies/2025/3/library#Author");
        IRI difficultyIRI = IRI.create("http://www.semanticweb.org/mo7am/ontologies/2025/3/library#DifficultyLevel");

        // Step 2: Create object properties
        OWLObjectProperty hasAuthor = factory.getOWLObjectProperty(hasAuthorIRI);
        OWLObjectProperty recommendedFor = factory.getOWLObjectProperty(recommendedForIRI);

        // Step 3: Create class expressions for the DL query
        OWLClass authorClass = factory.getOWLClass(authorIRI);
        OWLClass difficultyClass = factory.getOWLClass(difficultyIRI);

        OWLClassExpression hasAuthorSomeAuthor = factory.getOWLObjectSomeValuesFrom(hasAuthor, authorClass);
        OWLClassExpression recommendedForSomeDifficulty = factory.getOWLObjectSomeValuesFrom(recommendedFor, difficultyClass);

        // Step 4: Combine both with AND (intersection)
        OWLClassExpression combinedQuery = factory.getOWLObjectIntersectionOf(hasAuthorSomeAuthor, recommendedForSomeDifficulty);

        // Step 5: Use reasoner to find all individuals matching the DL query
        NodeSet<OWLNamedIndividual> results = reasoner.getInstances(combinedQuery, false);

        // Step 6: Print results
        System.out.println("\n🔍 Individuals matching: hasAuthor some Author AND recommendedFor some DifficultyLevel");
        for (OWLNamedIndividual ind : results.getFlattened()) {
            System.out.println(" - " + ind.getIRI().getShortForm());
        }
        
    }
}
