/*
 * JEvolution Version 0.9.8 at 1/25/13 5:04 PM
 */

import java.util.List;

import evSOLve.JEvolution.Phenotype;
import evSOLve.JEvolution.chromosomes.Chromosome;

import evSOLve.JEvolution.gp.Tree;
import evSOLve.JEvolution.gp.ProgramNode;

import evSOLve.JEvolution.misc.Xml;

import org.jdom2.Element;

/** A GP phenotype for determining if a double value x is in the unit interval.
*
* @author Helmut A. Mayer
* @since March, 2012
*
*/
public class UnitIntervalPhenotype implements Phenotype {

	/** The program tree. */
	private Tree programTree;
	
	/** The phenotype's fitness. */
    private double fitness;
    
/** Constructs the basic phenotype. */
	public UnitIntervalPhenotype() {
	}
	
    /** Constructs the phenotype from XML. If the genotype is constructed from XML, so is the phenotype (= genotype) in this case.
     *
     * @param element			a JDOM element
     *
     */		
	public UnitIntervalPhenotype(Element element) {
	}


/** A deep clone. The genotype is cloned by JEvolution and with GP the phenotype (program tree) is the genotype.
*
* @returns Object	the cloned phenotype
*
*/
	public Object clone() {
		try {
			UnitIntervalPhenotype clone = (UnitIntervalPhenotype)super.clone();
			return(clone);
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e.toString());						// should not happen
		}
	}
	
	
/** The genotype-phenotype mapper only has to obtain the program tree, which is genotype and phenotype.
*
* @param genotype		the tree chromosome
*
*/	
    public void doOntogeny(List<Chromosome> genotype) {
		programTree = (Tree)genotype.get(0).getBases();
	}

			
/** The fitness function with a number of fitness cases being double values. It returns the accuracy of unit interval classification.
*/
	public void calcFitness() {
		
		int correct = 0;
		int count = 0;
		boolean problem = false;
		double x = -1.0;																	// test case

		while (x < 2.0) {
			if (programTree.size() > 20) {										// limit tree size
				problem = true;
				break;																			// stop fitness evaluation
			}
			++count;
			if ((x >= 0.0 && x <= 1.0) == inInterval(x))				// check if program is correct for x
				++correct;
			x += 0.05;																		// step size to next value
		}
		fitness = (problem ? 0.0 : (double)correct / (double)count);				
    }
    

/** Access to the fitness of the Phenotype. */
    public double getFitness() {
        return fitness;
    }


/** The String representation of the Phenotype. */
	public String toString() {

		String s = "Tree: " + programTree;
		s += '\n' + "inInterval(0.0) = " + inInterval(0.0);
		s += '\n' + "inInterval(0.5) = " + inInterval(0.5);
		s += '\n' + "inInterval(1.0) = " + inInterval(1.0);

		return s;
	}
	
	
/** Saves the phenotype to XML.
*
* @param element			a JDOM element
*
*/
    public void toXml(Element element) {
		Xml.addChildTo(element, "Size", String.valueOf(programTree.size()));
	}


/** Checks if the value x is in the unit interval.
*
* @param x		to be checked
*
* @return			true, if x in unit interval
*
 */
    private boolean inInterval(double x) {

		ProgramNode.addValue((Double)x);												// set variable value            
		ProgramNode root = (ProgramNode)programTree.getRoot();
		boolean result = (Boolean)root.eval();											// evaluate the program
		ProgramNode.clearValues();															// clear variable value

		return result;
    }
//
}
