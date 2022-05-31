/*
 * JEvolution Version 0.9.8 at 1/25/13 5:06 PM
 */

import evSOLve.JEvolution.JEvolution;
import evSOLve.JEvolution.JEvolutionException;
import evSOLve.JEvolution.JEvolutionReporter;
import evSOLve.JEvolution.gp.ProgramNode;
import evSOLve.JEvolution.gp.TreeChromosome;
import evSOLve.JEvolution.gp.nodes.*;
import evSOLve.JEvolution.selection.TournamentSelection;

public class GPTest {

/** To run JEvolution, you only need the following steps
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
*
*/
	public static void main(String[] args) {

		JEvolution GP = JEvolution.getInstance();																					//+ call it a GP
// 		GP.setMaximization(false);																											//o minimization problem
		JEvolutionReporter GPReporter = (JEvolutionReporter)GP.getReporter();	//- get the reporter
		TreeChromosome chrom = new TreeChromosome(4,ProgramNode.TYPE_DOUBLE);	//+ create a tree chromosome with depth 4 and result type
		chrom.setNodeMutation(true);
		
		/* Add function and terminal nodes sufficient for the cubic root problem. */
		chrom.addNode(new AddDouble());							// add function
		chrom.addNode(new SubDouble());							// sub function
		chrom.addNode(new MultDouble());						// mult function
		chrom.addNode(new DivDouble());							// div function
		chrom.addNode(new VarDouble());							// variable, terminal
		chrom.addNode(new ConstDouble(0.0, 1.0));				// constant in unit interval

		try {
			chrom.setSoupType(TreeChromosome.GROW);				//- only set to justify try statement..;-)
			chrom.setMutationRate(0.01);
// 			chrom.setCrossoverRate(0.5);
// 			Utilities.setRandomSeed(42);

			GP.addChromosome(chrom);							//+ tell GP about your chromosome
			GP.setPhenotype(new CubicRootPhenotype());			//+ tell GP about your Phenotype class
			GP.setSelection(new TournamentSelection(4));
			GP.setPopulationSize(10000);
			GP.setMaximalGenerations(10);						//o , maybe use 5,000 generations for a good solution
//			GP.setTimeLimit(0.5);
// 			GA.setSubPopulations(10);							//o

			GPReporter.setReportLevel(JEvolutionReporter.BRIEF);

		} catch (JEvolutionException e) {
			System.out.println(e.toString());
			System.out.println("Continuing with default values.");
		}
//		while (GP.doEvolve(1) != 0)										// single step generation
//			;
		GP.doEvolve();													// + evolution run
		
//		Individual best = GPReporter.getBestIndividual();
//		best.toFile("bestResult.xml");
//		Individual bestFromFile = new Individual("bestResult.xml");
//
//		System.out.println("equals() returns: " + best.getGenotype().equals(bestFromFile.getGenotype()));
//		System.out.println(best.getGenotype().get(0));
//		System.out.println(bestFromFile.getGenotype().get(0));
	}
}
