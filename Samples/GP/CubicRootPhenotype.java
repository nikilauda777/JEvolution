/*
 * Version 0.9.8 1/25/13 4:38 PM
 */

/*
 * Copyright (c) 1/25/13 4:35 PM
 */

import java.util.List;

import evSOLve.JEvolution.Phenotype;
import evSOLve.JEvolution.chromosomes.Chromosome;

import evSOLve.JEvolution.gp.Tree;
import evSOLve.JEvolution.gp.ProgramNode;

import evSOLve.JEvolution.misc.Xml;

import org.jdom2.Element;

/** A GP phenotype for computing the cubic root of a double value.
*
* @author Caroline Atzl
* @author Helmut a. Mayer
* @since May, 2009
*
*/
public class CubicRootPhenotype implements Phenotype {

	/** The program tree. */
	private Tree programTree;
	
	/** The phenotype's fitness. */
    private double fitness;
    
/** Constructs the basic phenotype. */
	public CubicRootPhenotype() {
	}
	
    /** Constructs the phenotype from XML. If the genotype is constructed from XML, so is the phenotype (= genotype) in this case.
     *
     * @param element			a JDOM element
     *
     */		
	public CubicRootPhenotype(Element element) {
	}


/** A deep clone. The genotype is cloned by JEvolution and with GP the phenotype (program tree) is the genotype.
*
* @returns Object	the cloned phenotype
*
*/
	public Object clone() {
		try {
			CubicRootPhenotype clone = (CubicRootPhenotype)super.clone();
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

			
/** The fitness function with a number of fitness cases. The error of each cubic root calculation is squared, summed over all fitness cases,
* and transformed to the unit interval. If an illegal value is computed, the fitness is set to 0.0.
* 
*/
	public void calcFitness() {
		
		fitness = 0.0;
		boolean problem = false;
		
		for (int i = 1; i < 20; i++) {
            double x = i * i * i;
			double result = cubrt(x);
			if (Double.isInfinite(result) || Double.isNaN(result) || programTree.size() > 100) {					// some problem
				problem = true;
				break;																							// stop fitness evaluation
			}
			x = result - i;																					// error
			fitness += x * x;																				// square error
		}
		fitness = (problem ? 0.0 : 1.0 / (1.0 + fitness));									// to unit iterval, 0 if problems
    }
    

/** Access to the fitness of the Phenotype. */
    public double getFitness() {
        return fitness;
    }


/** The String representation of the Phenotype. */
	public String toString() {

		String s = "Tree: " + programTree;
		s += '\n' + "cubrt(1.0) = " + cubrt(1.0);
		s += '\n' + "cubrt(125.0) = " + cubrt(125.0);
		s += '\n' + "cubrt(1000.0) = " + cubrt(1000.0);

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


/** Returns the evolved cubic root fucntion value.
*
* @param x		to be rooted
*
* @return			the evolved value
*
 */
    private double cubrt(double x) {

		ProgramNode.addValue((Double)x);												// set variable value            
		ProgramNode root = (ProgramNode)programTree.getRoot();
		double result = (Double)root.eval();												// evaluate the program
		ProgramNode.clearValues();															// clear variable value

		return result;
    }
//
}
