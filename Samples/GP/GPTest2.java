/*
 * JEvolution Version 0.9.8 at 1/25/13 5:04 PM
 */

import evSOLve.JEvolution.JEvolution;
import evSOLve.JEvolution.JEvolutionException;
import evSOLve.JEvolution.JEvolutionReporter;
import evSOLve.JEvolution.gp.TreeChromosome;
import evSOLve.JEvolution.gp.ProgramNode;

import evSOLve.JEvolution.gp.nodes.AndBoolean;
import evSOLve.JEvolution.gp.nodes.ConstBoolean;
import evSOLve.JEvolution.gp.nodes.ConstDouble;
import evSOLve.JEvolution.gp.nodes.LessEqualBoolean;
import evSOLve.JEvolution.gp.nodes.VarDouble;

/**
 * A test program for Genetic Programming with JEvolution.
 *
 * $Id: GPTest2.java 559 2014-04-18 18:45:37Z helmut $
 *
 * @author Helmut A. Mayer
 * @since March, 2012
 */
public class GPTest2 {

/**
 * To run JEvolution, you only need the following steps
 *
 * - get the JEvolution singleton
 * - get the associated reporter
 * - create the Chromosome(s) needed
 * - pass the Chromosome(s) to JEvolution
 * - pass your Phenotype class to JEvolution (see ParaboloidPhenotype.java)
 * - run it
 *
 * These necessary steps are indicated by `+` in the comments,
 * the `-' lines are optional (the 'o' lines depend on the nature of the problem).
 * Note that all parameters have default values
 * which you can (and should) change easily. Some examples for setting
 * parameters are included under comment characters.
 */
public static void main(String[] args) {

	JEvolution GP = JEvolution.getInstance();                                                                                    //+ call it a GP
// 		GP.setMaximization(false);																											//o minimization problem
	JEvolutionReporter GPReporter = (JEvolutionReporter)GP.getReporter();		//- get the reporter
	TreeChromosome chrom = new TreeChromosome(4, ProgramNode.TYPE_BOOLEAN);  	//+ create a tree chromosome with depth 4 and result type
	chrom.setNodeMutation(true);
		
		/* Add function and terminal nodes sufficient for the unit interval problem. */
// 		chrom.addNode(new IfBoolean());								// if function
	chrom.addNode(new AndBoolean());                                // and function
// 		chrom.addNode(new OrBoolean());								// or function
// 		chrom.addNode(new NotBoolean());							// not function
	chrom.addNode(new LessEqualBoolean());                  	  // <= function
	chrom.addNode(new ConstBoolean());                            // boolean constant, terminal
	chrom.addNode(new VarDouble());                         	   // variable, terminal
	chrom.addNode(new ConstDouble(-1.0, 1.0));                		// constant in unit interval, terminal

	try {
		chrom.setSoupType(TreeChromosome.FULL);          			  //- only set to justify try statement..;-)
		chrom.setMutationRate(0.1);
// 			chrom.setCrossoverRate(0.5);
// 			Utilities.setRandomSeed(42);

		GP.addChromosome(chrom);                                 	//+ tell GP about your chromosome
		GP.setPhenotype(new UnitIntervalPhenotype());              	//+ tell GP about your Phenotype class
// 			GP.setSelection(new TournamentSelection(4));
		GP.setPopulationSize(10000);
		GP.setMaximalGenerations(100);                                                        //o

		GPReporter.setReportLevel(JEvolutionReporter.BRIEF);
// 			GPReporter.useFitnessRepository(true);

	} catch (JEvolutionException e) {
		System.out.println(e.toString());
		System.out.println("Continuing with default values.");
	}
//		while (GP.doEvolve(1) != 0)									// single step generation
//			;
	GP.doEvolve();                                  		        // + evolution run

//	Individual best = GPReporter.getBestIndividual();
//	best.toFile("bestResult.xml");
//	Individual bestFromFile = new Individual("bestResult.xml");
//
//	System.out.println(best.getGenotype().equals(bestFromFile.getGenotype()));
}
}
