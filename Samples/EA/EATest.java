/*
 * JEvolution Version 0.9.8 at 1/25/13 5:04 PM
 */

import evSOLve.JEvolution.*;
import evSOLve.JEvolution.chromosomes.PermChromosome;

/** A test bed for the package evSOLve.JEvolution.
*
* @author Helmut A. Mayer
* @since June 8, 2001
*
* SVN $Id: EATest.java 559 2014-04-18 18:45:37Z helmut $
*
*/
public class EATest {

/** To run JEvolution, you only need the following steps
*
* - get the JEvolution singleton
* - get the associated reporter
* - create the Chromosome(s) needed
* - pass the Chromosome(s) to JEvolution
* - pass your Phenotype class to JEvolution (see SortPhenotype.java)
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

		JEvolution EA = JEvolution.getInstance();									//+ call it an EA
// 		EA.setMaximization(false);													//o minimization problem
		  JEvolutionReporter EAReporter = (JEvolutionReporter)EA.getReporter();		//- get the reporter
		  PermChromosome chrom = new PermChromosome();								//+ create a chromosome

		try {
			chrom.setLength(10);									//- only set to justify try statement..;-)
//			chromX.setMutationRate(0.0);
//			chromX.setSoupType(Chromosome.LAPLACE);
//			chromX.setCrossoverPoints(2);
// 			Utilities.setRandomSeed(88);

			EA.addChromosome(chrom);								//+ tell EA about your chromosome
			EA.setPhenotype(new SortPhenotype());					//+ tell EA about your Phenotype class
//			EA.setSelection(new TournamentSelection(3));
// 			EA.setPopulationSize(25, 50);
			EA.setFitnessThreshold(1.0);							//o better fitness not possible
			EA.setMaximalGenerations(100);							//o

			EAReporter.setReportLevel(JEvolutionReporter.VERBOSE);
// 			EAReporter.useFitnessRepository(true);

		} catch (JEvolutionException e) {
			System.out.println(e.toString());
			System.out.println("Continuing with default values.");
		}
//		while (EA.doEvolve(1) != 0)									// single step generation
//			;
		EA.doEvolve();												// + evolution run
		
		Individual best = EAReporter.getBestIndividual();
		best.toFile("bestResult.xml");
		Individual bestFromFile = new Individual("bestResult.xml");
		
		System.out.println(best.getGenotype().equals(bestFromFile.getGenotype()));
	}
}
