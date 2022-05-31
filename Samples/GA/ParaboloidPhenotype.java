/*
 * JEvolution Version 0.9.8 at 1/25/13 5:04 PM
 */

import evSOLve.JEvolution.JEvolution;
import evSOLve.JEvolution.Phenotype;
import evSOLve.JEvolution.chromosomes.BitChromosome;
import evSOLve.JEvolution.misc.Utilities;
import evSOLve.JEvolution.misc.Xml;
import org.jdom2.Element;

import java.util.List;

/**
 * A test Phenotype for the simple paraboloid problem f(x, y) = x^2 + y^2
 * using two Chromosomes (x, y). The task is to find the minimum.
 *
 * $Id: ParaboloidPhenotype.java 537 2014-03-28 18:03:05Z helmut $
 *
 * @author Helmut A. Mayer
 * @since June 2, 2000
 */
public class ParaboloidPhenotype implements Phenotype {

	/** The x value. */
	private double x;

	/** The y value. */
	private double y;

	/** The fitness. */
	private double fitness;


	/** Constructs the basic phenotype. */
	public ParaboloidPhenotype() {

	}


	/**
	 * Constructs the phenotype from XML.
	 *
	 * @param element a JDOM element
	 */
	public ParaboloidPhenotype(Element element) {

		x = Xml.getProperty(element, "x", 0.0);
		y = Xml.getProperty(element, "y", 0.0);
	}


	/**
	 * A proper clone. Here the phenotype is simply a double, so super.clone() handles
	 * everything just fine.
	 *
	 * @return Object    the cloned phenotype
	 */

	public Object clone() {

		try {
			ParaboloidPhenotype clone = (ParaboloidPhenotype)super.clone();
			return (clone);
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e.toString());                        // should not happen
		}
	}


	/**
	 * The genotype-phenotype mapper converts the two BitChromosomes to
	 * the phenotype values x, y.
	 *
	 * @param genotype the complete set of chromosomes
	 */
	public void doOntogeny(List genotype) {

//		if (JEvolution.getInstance().getJEvolutionReporter().inGenerationGap())
//			System.out.println("In generation gap.");

		for (int i = 0; i < 2; i++) {                                // two Chromosomes
			BitChromosome bc = (BitChromosome)genotype.get(i);        // get single Chromosome
			List<Boolean> bits = (List<Boolean>)bc.getBases();        // access bases

			double v = Utilities.express(bits, 0, bc.getLength(), 0.0, 1.0);    // v in [0, 1]
			if (i == 0)
				x = v;
			else
				y = v;
		}
	}


	/**
	 * The fitness function x^2 + y^2. Note that JEvolution has been set to minimization mode,
	 * hence we do not have to tune the fitness function.
	 */

	public void calcFitness() {

		fitness = x * x + y * y;
	}


	/** Access to the fitness of the Phenotype. */

	public double getFitness() {

		return fitness;
	}


	/** The String representation of the Phenotype values x, y. */

	public String toString() {

		return ("x: " + x + " y: " + y);
	}


	/**
	 * Saves the phenotype to XML.
	 *
	 * @param element a JDOM element
	 */
	public void toXml(Element element) {

		Xml.addChildTo(element, "x", String.valueOf(x));
		Xml.addChildTo(element, "y", String.valueOf(y));
	}
//
}
