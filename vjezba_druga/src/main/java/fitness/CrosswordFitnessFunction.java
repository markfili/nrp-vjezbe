package fitness;

import org.jgap.FitnessFunction;
import org.jgap.IChromosome;

/**
 * Created by marko on 11/20/15.
 */
public class CrosswordFitnessFunction extends FitnessFunction {

    private Integer[] solutionsOrder;

    public CrosswordFitnessFunction(Integer[] solutionsOrder) {

        this.solutionsOrder = solutionsOrder;
    }

    @Override
    protected double evaluate(IChromosome a_subject) {

        Integer[] candidateOrder = getOrder(a_subject);

        double fitness = 0;

        if (!containsDoubles(candidateOrder)) {

            for (int i = 0; i < candidateOrder.length; i++) {
                fitness += Math.pow(solutionsOrder[i] - candidateOrder[i], 2);
            }

            fitness = 100000 - Math.pow(fitness, 3);

            if (fitness < 0) {
                fitness = 0;
            }

            if (solutionsOrder == candidateOrder) fitness *= 100;
        }
        return fitness;
    }

    private Integer[] getOrder(IChromosome a_subject) {
        Integer[] candidateOrder = new Integer[a_subject.size()];
        for (int i = 0; i < a_subject.size(); i++) {
            candidateOrder[i] = (Integer) a_subject.getGene(i).getAllele();
        }
        return candidateOrder;
    }

    private boolean containsDoubles(Integer[] candidateContent) {
        int count;
        for (int searchInt : candidateContent) {
            count = 0;
            for (int comparedInt : candidateContent) {
                if (searchInt == comparedInt) {
                    count++;
                    if (count > 1) return true;
                }
            }
        }
        return false;
    }

}
