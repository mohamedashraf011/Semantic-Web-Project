package com.mycompany.semantic_web_project;

import java.io.File;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.HermiT.ReasonerFactory;
import org.semanticweb.owlapi.reasoner.*;

public class Semantic_Web_Project {

    public static void main(String[] args) throws Exception {
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        File file = new File("src/main/resources/library.owl");
        OWLOntology ontology = manager.loadOntologyFromOntologyDocument(file);
        System.out.println("Ontology Loaded: " + ontology.getOntologyID());

        OWLReasonerFactory reasonerFactory = new ReasonerFactory();
        OWLReasoner reasoner = reasonerFactory.createReasoner(ontology);

        reasoner.precomputeInferences(InferenceType.CLASS_HIERARCHY);
        System.out.println("Is ontology consistent? " + reasoner.isConsistent());

        OWLDataFactory factory = manager.getOWLDataFactory();
<<<<<<< HEAD
        
        System.out.println("Hello World!");
    
        // List all individuals in class ProgrammingBook
        OWLClass programmingBookClass = factory.getOWLClass(
        IRI.create("http://www.semanticweb.org/mo7am/ontologies/2025/3/library#ProgrammingBook")
=======
        String ns = "http://www.semanticweb.org/mo7am/ontologies/2025/3/library#";

        // 1. hasAuthor some Author AND recommendedFor some DifficultyLevel
        OWLObjectProperty hasAuthor = factory.getOWLObjectProperty(IRI.create(ns + "hasAuthor"));
        OWLObjectProperty recommendedFor = factory.getOWLObjectProperty(IRI.create(ns + "recommendedFor"));
        OWLClass authorClass = factory.getOWLClass(IRI.create(ns + "Author"));
        OWLClass difficultyClass = factory.getOWLClass(IRI.create(ns + "DifficultyLevel"));
        OWLClassExpression expr1 = factory.getOWLObjectIntersectionOf(
            factory.getOWLObjectSomeValuesFrom(hasAuthor, authorClass),
            factory.getOWLObjectSomeValuesFrom(recommendedFor, difficultyClass)
>>>>>>> 57914bfc5d9e242885466c4bf4cfd2b4fc037bea
        );
        printResults(reasoner, expr1, "1. hasAuthor some Author AND recommendedFor some DifficultyLevel");

        // 2. specializesIn some ProgrammingLanguage
        OWLObjectProperty specializesIn = factory.getOWLObjectProperty(IRI.create(ns + "specializesIn"));
        OWLClass progLang = factory.getOWLClass(IRI.create(ns + "ProgrammingLanguage"));
        OWLClassExpression expr2 = factory.getOWLObjectSomeValuesFrom(specializesIn, progLang);
        printResults(reasoner, expr2, "2. specializesIn some ProgrammingLanguage");

        // 3. teachesLanguage some ProgrammingLanguage
        OWLObjectProperty teachesLanguage = factory.getOWLObjectProperty(IRI.create(ns + "teachesLanguage"));
        OWLClassExpression expr3 = factory.getOWLObjectSomeValuesFrom(teachesLanguage, progLang);
        printResults(reasoner, expr3, "3. teachesLanguage some ProgrammingLanguage");

        // 4. hasDifficultyLevel value Advanced
        OWLObjectProperty hasDifficulty = factory.getOWLObjectProperty(IRI.create(ns + "hasDifficultyLevel"));
        OWLNamedIndividual advanced = factory.getOWLNamedIndividual(IRI.create(ns + "Advanced"));
        OWLClassExpression expr4 = factory.getOWLObjectHasValue(hasDifficulty, advanced);
        printResults(reasoner, expr4, "4. hasDifficultyLevel value Advanced");

        // 5. recommendedFor value Beginner
        OWLNamedIndividual beginner = factory.getOWLNamedIndividual(IRI.create(ns + "Beginner"));
        OWLClassExpression expr5 = factory.getOWLObjectHasValue(recommendedFor, beginner);
        printResults(reasoner, expr5, "5. recommendedFor value Beginner");

        // 6. publishedBy value Mohamed
        OWLObjectProperty publishedBy = factory.getOWLObjectProperty(IRI.create(ns + "publishedBy"));
        OWLNamedIndividual mohamed = factory.getOWLNamedIndividual(IRI.create(ns + "Mohamed"));
        OWLClassExpression expr6 = factory.getOWLObjectHasValue(publishedBy, mohamed);
        printResults(reasoner, expr6, "6. publishedBy value Mohamed");

        // 7. hasAuthor value Bahaa
        OWLNamedIndividual bahaa = factory.getOWLNamedIndividual(IRI.create(ns + "Bahaa"));
        OWLClassExpression expr7 = factory.getOWLObjectHasValue(hasAuthor, bahaa);
        printResults(reasoner, expr7, "7. hasAuthor value Bahaa");

        // 8. relatedTopic some Topic
        OWLObjectProperty relatedTopic = factory.getOWLObjectProperty(IRI.create(ns + "relatedTopic"));
        OWLClass topicClass = factory.getOWLClass(IRI.create(ns + "Topic"));
        OWLClassExpression expr8 = factory.getOWLObjectSomeValuesFrom(relatedTopic, topicClass);
        printResults(reasoner, expr8, "8. relatedTopic some Topic");

        // 9. ProgrammingBook and coversTopic value Algorithms
        OWLClass programmingBook = factory.getOWLClass(IRI.create(ns + "ProgrammingBook"));
        OWLObjectProperty coversTopic = factory.getOWLObjectProperty(IRI.create(ns + "coversTopic"));
        OWLNamedIndividual algorithms = factory.getOWLNamedIndividual(IRI.create(ns + "Algorithms"));
        OWLClassExpression expr9 = factory.getOWLObjectIntersectionOf(
            programmingBook,
            factory.getOWLObjectHasValue(coversTopic, algorithms)
        );
        printResults(reasoner, expr9, "9. ProgrammingBook and coversTopic value Algorithms");
    }

    public static void printResults(OWLReasoner reasoner, OWLClassExpression expr, String label) {
        System.out.println("=".repeat(60));
        System.out.println("Query: " + label);
        //System.out.println("-".repeat(60));

        NodeSet<OWLNamedIndividual> individuals = reasoner.getInstances(expr, false);
        if (individuals.isEmpty()) {
            System.out.println("No matching individuals found.");
        } else {
            int count = 1;
            for (OWLNamedIndividual ind : individuals.getFlattened()) {
                System.out.println("Result " + count + ": " + ind.getIRI().getShortForm());
                count++;
            }
        }
        
        //  ------------------------------------------------------------------------------------

<<<<<<< HEAD
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
        
=======
        System.out.println("=".repeat(60) + "\n");
>>>>>>> 57914bfc5d9e242885466c4bf4cfd2b4fc037bea
    }
}
