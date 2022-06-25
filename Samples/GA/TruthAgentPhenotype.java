/*
 * JEvolution Version 0.9.8 at 1/25/13 5:04 PM
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import evSOLve.JEvolution.Phenotype;

import java.util.Map.Entry;
import java.util.Random;
import org.jdom2.Element;

public class TruthAgentPhenotype implements Phenotype {


    protected double fitness;
    protected int food;
    protected int water;
    ArrayList<MyPair> territories = new ArrayList<MyPair>(); // List of pairs

    protected int nBases;								// the number of elements


    public TruthAgentPhenotype() {
    }


    /** A proper clone. Here the phenotype is simply an integer, so super.clone() handles
     * everything just fine.
     *
     * @return Object							The clone.
     *
     */

    public Object clone() {
        try {
            TruthAgentPhenotype clone = (TruthAgentPhenotype) super.clone();
            return(clone);
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e.toString());			//should not happen
        }
    }


    /** The genotype-phenotype mapper just counts the number of correct
     * permutation elements.
     */

    public void doOntogeny(List genotype) {
        TerritoryChromosome chrom = (TerritoryChromosome) genotype.get(0);
        List perm = (List) chrom.getBases();
        nBases = chrom.getLength(); // 6 with 3 territories
        Integer index;
        for (int i = 0; i < nBases; i++) {
            index = (Integer) perm.get(i);

            MyPair pair = new MyPair(); // Object of pairs
            pair.setFood(index);
            pair.setWater( (Integer) perm.get(
                i + 1));
            territories.add(pair);   // add all available territories
            }
        }
    /** Here the fitness is simply the percentage of correct elements. */
    public void calcFitness() {

        ArrayList<Integer> resources = new ArrayList<>();
        if(territories.size() > 0){
            int i = 0;
            while( i < territories.size()){
                int sum;
                food = (int) territories.get(i).getFood();
                water = (int) territories.get(i).getWater();
                sum = food + water;
                resources.add(sum);
                i += 2;
            }
            // find the maximum
            double cost = 0;
            int sum = resources.get(0);
            for (int j = 0; j < resources.size(); j++) {
                if (sum < resources.get(j))
                    sum = resources.get(j);
                    cost += j;
            }

            fitness = sum - (int) (Math.log(cost*100) / Math.log(2));

        }
    }


    /** Access to the fitness of the Phenotype. */

    public double getFitness() {
      return fitness;
    }


    /** A String representation of the Phenotype. */

    public String toString() {
        return( " The values for truth agent, food: " + food + " water: " + water);
    }

    /** Saves the phenotype to XML. */
    public void toXml(Element element) {
    }
//
}


