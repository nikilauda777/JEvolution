/*
 * JEvolution Version 0.9.8 at 1/25/13 5:04 PM
 */

import java.util.ArrayList;
import java.util.List;

import evSOLve.JEvolution.Phenotype;

import java.util.Map.Entry;
import java.util.Random;
import org.jdom2.Element;

public class TruthVsSimple implements Phenotype {


    protected double fitnessSimple;
    protected double fitnessTruth;
    protected double fitness;
    protected int food;
    protected int water;
    protected int checkFood = 50;
    protected Entry<Integer,Integer> globalList; // here we will write a values of one territory

    ArrayList<MyPair> green = new ArrayList<MyPair>(); // List of pairs
    ArrayList<MyPair> red = new ArrayList<MyPair>(); // List of pairs
    ArrayList<MyPair> territories = new ArrayList<MyPair>(); // List of pairs

    protected int nBases;								// the number of elements


    public TruthVsSimple() {
    }


    /** A proper clone. Here the phenotype is simply an integer, so super.clone() handles
     * everything just fine.
     *
     * @return Object							The clone.
     *
     */

    public Object clone() {
        try {
            TruthVsSimple clone = (TruthVsSimple) super.clone();
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
        for (int i = 0; i <= nBases; i++) {
            index = (Integer) perm.get(i);
            if (i % 2 != 0) { // even numbers where we have planed the position of territory
                if(checkFood <= index) {  // 50 >= Food check for parameter food
                    /** Add Green Territory **/
                    MyPair pair = new MyPair(); // Object of pairs
                    pair.setFood(index);
                    pair.setWater( (Integer) perm.get(
                        i - 1));
                    green.add(pair); // add the green territories to our list of pair [water,food]
                    territories.add(pair);   // add all available territories
                }
                /**Add Red Territory */
                else{
                    MyPair pair2 = new MyPair(); // Object of pairs
                    pair2.setFood(index);
                    pair2.setWater( (Integer) perm.get(
                        i - 1));
                    red.add(pair2);
                    territories.add(pair2);   // add all available territories
                }
            }
        }
    }
    /** Here the fitness is simply the percentage of correct elements. */
    public void calcFitness() {
        calcSimple();
        calcTruth(territories);

        if(fitnessTruth > fitnessSimple){
            fitness = fitnessTruth;
            System.out.println("Truth strategy wins");
        }
        else if (fitnessTruth < fitnessSimple) {
            fitness = fitnessSimple;
            System.out.println("Simple strategy wins");
        } else {
            System.out.println("Two strategies stably coexist.");
        }
    }

    public double calcSimple(){
        Random rand = new Random();
        // check if doesnt have green
        if(green.size() <= 0) {
            for (int i = 0; i < red.size(); i++) {
                int randomIndex = rand.nextInt(red.size()); // generate Random number
                food = (int) red.get(randomIndex).getFood();
                water = (int) red.get(randomIndex).getWater();
                fitnessSimple = food + water; // the utility of the territory becames agents fitness
            }
        }
        // chose randomly the Green territories
        else {
            for (int i = 0; i < green.size(); i++) {
                int randomIndex = rand.nextInt(green.size()); // generate Random number
                food = (int) green.get(randomIndex).getFood();
                water = (int) green.get(randomIndex).getWater();
                fitnessSimple = food + water; // the utility of the territory becames agents fitness
            }
        }
        return fitnessSimple;
    }

    public double calcTruth (ArrayList<MyPair> territories) {

        ArrayList<Integer> resources = new ArrayList<>();
        // delete the territory which was choosen by simple agent
        territories.removeIf(pair -> pair.getFood() == food && pair.getWater() == water);

        if(territories.size() > 0){
            int i = 0;
            while( i < territories.size()){
                int sum;
                food = (int) territories.get(i).getFood();
                water = (int) territories.get(i).getWater();
                sum = food + water;
                resources.add(sum);
                i += 1;
            }
            // find the maximum
            double cost = 0;
            int sum = resources.get(0);
            for (int j = 0; j < resources.size(); j++) {
                if (sum < resources.get(j))
                    sum = resources.get(j);
                cost += j;
            }
            fitnessTruth = sum - (int) (Math.log(cost*100) / Math.log(2));
        }
        return fitnessTruth;
    }



    /** Access to the fitness of the Phenotype. */

    public double getFitness() {
        return fitness;
    }


    /** A String representation of the Phenotype. */

    public String toString() {
        return( "The values for simple agent, food: " + food + " water: " + water);
    }

    /** Saves the phenotype to XML. */
    public void toXml(Element element) {
    }
//
}


