/*
 * JEvolution Version 0.9.8 at 1/25/13 5:03 PM
 */

import evSOLve.JEvolution.*;
import evSOLve.JEvolution.chromosomes.BitChromosome;
import evSOLve.JEvolution.selection.TournamentSelection;

/** A test bed for the package evSOLve.JEvolution.
*
* $Id: GATest.java 559 2014-04-18 18:45:37Z helmut $
* @author Helmut A. Mayer
* @since June 8, 2001
*
*/
public class GATest {

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

		JEvolution GA = JEvolution.getInstance();								//+ call it a GA
		GA.setMaximization(false);												//o minimization problem
		JEvolutionReporter GAReporter = (JEvolutionReporter)GA.getReporter();	//- get the reporter
		BitChromosome chromX = new BitChromosome();								//+ create a chromosome

		try {
			chromX.setLength(12);										//- only set to justify try statement..;-)
//			chromX.setMutationRate(0.0);
//			chromX.setSoupType(Chromosome.LAPLACE);
//			chromX.setCrossoverPoints(2);
// 			Utilities.setRandomSeed(88);

			BitChromosome chromY = (BitChromosome)chromX.clone();		//- second chromosome, demonstration purpose

			GA.addChromosome(chromX);									//+ tell GA about your chromosome
			GA.addChromosome(chromY);									//- second one
			GA.setPhenotype(new ParaboloidPhenotype());					//+ tell GA about your Phenotype class
			GA.setSelection(new TournamentSelection(3));
// 			GA.setPopulationSize(25, 50);
			GA.setFitnessThreshold(0.00001);				//o may cause earlier termination, hence save time
			GA.setMaximalGenerations(100);					//o
// 			GA.setSubPopulations(10);						//o

			GAReporter.setReportLevel(JEvolutionReporter.BRIEF);
// 			GAReporter.useFitnessRepository(true);

		} catch (JEvolutionException e) {
			System.out.println(e.toString());
			System.out.println("Continuing with default values.");
		}
//		while (GA.doEvolve(1) != 0)                                   // single step generation
//			;
		GA.doEvolve();																						// + evolution run
		
/*		Individual best = GAReporter.getBestIndividual();
		best.toFile("bestResult.xml");
		Individual bestFromFile = new Individual("bestResult.xml");*/
		
// 		System.out.println(best.getGenotype().equals(bestFromFile.getGenotype()));
	}
}
