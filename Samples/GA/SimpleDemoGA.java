import java.util.Random;
import java.util.Scanner;


//Main class
public class SimpleDemoGA {

    Population population = new Population();
    Individual fittest;
    Individual secondFittest;
    int generationCount = 0;

    public static void main(String[] args) {
        double mutationrate = 0.1;

        SimpleDemoGA demo = new SimpleDemoGA();

        //Initialize population
        demo.population.initializePopulation(2); // two territories
        //Calculate fitness of each individual
        demo.population.calculateFitness();
        System.out.println("Generation: " + demo.generationCount + " Fittest: " + demo.population.fittest);

        //While population gets an individual with maximum fitness
        while (demo.population.fittest < 10) {
            ++demo.generationCount;

            //Do selection
            demo.selection();

            //Do crossover
            System.out.println(demo.population); // this is the current population
            demo.crossover();
            System.out.println(demo.population); // population after crossover

            //Add fittest offspring to population before mutating ... elitism
            demo.addFittestOffspring();

            //Do mutation under a random probability
            if (Math.random()<mutationrate) {
                demo.mutation();
                System.out.println(demo.population); // population after mutating
            }

            //Calculate new fitness value
            demo.population.calculateFitness();

            System.out.println("Generation: " + demo.generationCount + " Fittest: " + demo.population.fittest);
            if (demo.generationCount>5000) {
                System.out.println("Terminated...prevent infinite loop.");
                System.exit(-1);
            }
        }

        System.out.println("\nSolution found in generation " + demo.generationCount);
        System.out.println("Fitness: "+demo.population.getFittest().fitness);
        System.out.print("Genes: ");
        System.out.print(demo.population.getFittest());

    }

    //Selection
    void selection() {
        //Select the most fittest individual
        fittest = population.getFittest();

        //Select the second most fittest individual
        secondFittest = population.getSecondFittest();
    }

    //Crossover
    void crossover() {
        Random rn = new Random();

        //Select a random crossover point
        int crossOverPoint = rn.nextInt(Individual.GENE_LENGTH);

        //Swap values among parents
        System.out.println("Crossing two fittest at point: " + crossOverPoint);
        for (int i = 0; i < crossOverPoint; i++) {
            int temp = fittest.genes[i];
            fittest.genes[i] = secondFittest.genes[i];
            secondFittest.genes[i] = temp;
        }

    }

    //Mutation - mutate top two instead of the offspring, for whatever reason i'm not sure why author did it this way
    // although really, each round there is actually three offspring as the fittest and secondfittest are being crossovered
    // in place, then mutated. Then a copy of the fittest (after it has been mutated) is replacing the least
    // fittest member of the population. This is not how I would do this at all.
    // This approach leads to really poor genetic diversity which might be why the author decided to use extremely high mutation rate
    void mutation() {
        Random rn = new Random();

        System.out.println("mutating");

        //Select a random mutation point
        int mutationPoint = rn.nextInt(Individual.GENE_LENGTH);

        //Flip values at the mutation point
        if (fittest.genes[mutationPoint] == 0) {
            fittest.genes[mutationPoint] = 1;
        } else {
            fittest.genes[mutationPoint] = 0;
        }

        mutationPoint = rn.nextInt(Individual.GENE_LENGTH);

        if (secondFittest.genes[mutationPoint] == 0) {
            secondFittest.genes[mutationPoint] = 1;
        } else {
            secondFittest.genes[mutationPoint] = 0;
        }
    }

    //Get fittest offspring
    Individual getFittestOffspring() {
        if (fittest.fitness > secondFittest.fitness) {
            return fittest;
        }
        return secondFittest;
    }


    //Replace least fittest individual from most fittest offspring
    void addFittestOffspring() {

        //Update fitness values of offspring
        fittest.calcFitness();
        secondFittest.calcFitness();

        //Get index of least fit individual
        int leastFittestIndex = population.getLeastFittestIndex();

        //Replace least fittest individual from most fittest offspring
        population.individuals[leastFittestIndex] = new Individual(getFittestOffspring());
    }

}


//Individual class
class Individual {

    int fitness = 0;
    public static final int GENE_LENGTH = 6;
    int[] genes = new int[GENE_LENGTH];

    public Individual() {
        Random rn = new Random();

        //Set genes randomly for each individual
        for (int i = 0; i < genes.length; i++) {
            // genes[i] = rn.nextInt(2); // generates either a 0 or 1
            genes[i] = 0;
        }

        fitness = 0;
    }

    public Individual(Individual other) {
        for (int i = 0; i < genes.length; i++) {
            genes[i] = other.genes[i];
        }
    }

    //Calculate fitness
    public void calcFitness() {
        fitness = 0;
        for (int i = 0; i < genes.length; i++) {
            if (genes[i] == 1) {
                ++fitness;
            }
        }
    }

    public String toString() {
        String out = "";
        for (int i = 0; i < genes.length; i++) {
            out += genes[i];
        }
        return out;
    }

}

//Population class
class Population {

    Individual[] individuals;   // must be initialized by a call to initializePopulation
    int popSize;
    int fittest = 0;            // the fitness of the current fittest member of the population

    //Initialize population
    public void initializePopulation(int size) {
        popSize = size;
        individuals = new Individual[popSize];
        for (int i = 0; i < individuals.length; i++) {
            individuals[i] = new Individual();
        }
    }

    //Get the fittest individual
    public Individual getFittest() {
        int maxFit = Integer.MIN_VALUE;
        int maxFitIndex = 0;
        for (int i = 0; i < individuals.length; i++) {
            if (maxFit <= individuals[i].fitness) {
                maxFit = individuals[i].fitness;
                maxFitIndex = i;
            }
        }
        fittest = individuals[maxFitIndex].fitness;
        return individuals[maxFitIndex];
    }

    //Get the second most fittest individual
    // Edit - Fixed bug where if the maximum fitness is the first member of the population and
    //      - all the others are less, than the first member of the population is returned as both
    //      - the fittest (from the other function) and the second fittest (from this function).
    //      - The original algorithm had benefit of single pass through fitnesses but had bug. O(n)
    //      - My approach requires two passes but is bug free. O(2*n)
    //      - Sorting would be O(n^2) and is not necessary
    public Individual getSecondFittest() {

        Individual firstbest = getFittest();

        Individual secondbest = individuals[0]!=firstbest?individuals[0]:individuals[1]; // init the secondbest to the first one unless the max was the first one
        for (int i = 1; i < individuals.length; i++) {
            if(individuals[i].fitness>secondbest.fitness && individuals[i]!=firstbest) {
                secondbest = individuals[i];
            }
        }

        return secondbest;
    }

    //Get index of least fittest individual
    public int getLeastFittestIndex() {
        int minFitVal = Integer.MAX_VALUE;
        int minFitIndex = 0;
        for (int i = 0; i < individuals.length; i++) {
            if (minFitVal >= individuals[i].fitness) {
                minFitVal = individuals[i].fitness;
                minFitIndex = i;
            }
        }
        return minFitIndex;
    }

    //Calculate fitness of each individual
    public void calculateFitness() {

        for (int i = 0; i < individuals.length; i++) {
            individuals[i].calcFitness();
        }
        getFittest();
    }

    public String toString() {
        Individual fittest = getFittest();
        Individual secondFittest = getSecondFittest();
        String out = "";
        for (int i = 0; i < individuals.length; i++) {
            if (individuals[i]==fittest) {
                out += "********** ";
            } else if (individuals[i]==secondFittest) {
                out += "++++++++++ ";
            } else {
                out += "           ";
            }
        }
        out += "\n";
        for (int i = 0; i < individuals.length; i++) {
            out += individuals[i] + " ";
        }
        return out;
    }


}