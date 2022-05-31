import evSOLve.JEvolution.Phenotype;
import evSOLve.JEvolution.chromosomes.IntChromosome;
import evSOLve.JEvolution.misc.Utilities;
import evSOLve.JEvolution.misc.Xml;
import java.util.List;
import org.jdom2.Element;

/**
 * A Agent Phenotype for the simple toy problem
 * using two Chromosomes (x, y). The task is to find the best territory with the simple strategy.
 *
 *
 * @author Nikita Semkin
 */

public class AgentPhenotype implements Phenotype {
    /** The food value. */
    private int food;

    /** The water value. */
    private int water;

    /** The fitness. */
    private int fitness;


    /** Constructs the basic phenotype. */
    public AgentPhenotype() {

    }

    /**
     * Constructs the phenotype from XML.
     *
     * @param element a JDOM element
     */
    public AgentPhenotype(Element element) {

        food = Xml.getProperty(element, "food", 5);
        water = Xml.getProperty(element, "water", 5);
    }

    /**
     * A proper clone. Here the phenotype is simply a double, so super.clone() handles
     * everything just fine.
     *
     * @return Object  the cloned phenotype
     */

    public Object clone() {

        try {
            AgentPhenotype clone = (AgentPhenotype)super.clone();
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
            IntChromosome bc = (IntChromosome) genotype.get(i);        // get single Chromosome

            double v = Utilities.nextIntegerInRange( bc.getLow()*10, bc.getHigh()*10);    // v in [0, 99]
            if (i == 0) {
                food = (int) v;
                //System.out.println("Food: " +food);
            }
            else
                water = (int) v;
        }
    }

    /**
     * The fitness function of strategy just based on simple strategy for one resource in this case "Water".
     * We actually check the territories and choose one where more water as a food
     */

    // не ебу как это сделать что я написал выше
    public void calcFitness() {

      fitness = Math.max(water + water, food+food);
    }

    /** Access to the fitness of the Phenotype. */

    public double getFitness() {

        return fitness;
    }


    /** The String representation of the Phenotype values x, y. */

    public String toString() {

        return ("food: " + food*10 + " water: " + water*10);
    }

    /**
     * Saves the phenotype to XML.
     *
     * @param element a JDOM element
     */
    public void toXml(Element element) {

        Xml.addChildTo(element, "x", String.valueOf(food));
        Xml.addChildTo(element, "y", String.valueOf(water));
    }
//
}
