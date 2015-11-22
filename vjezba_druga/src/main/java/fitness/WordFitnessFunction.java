package fitness;

import org.apache.commons.lang.ArrayUtils;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.IChromosome;

/**
 * Created by marko on 11/21/15.
 */
public class WordFitnessFunction extends FitnessFunction {
    private static final double MAX_FITNESS = 100000;
    private Character[] content;

    @Override
    protected double evaluate(IChromosome a_subject) {

        Character[] candidateContent = getGenes(a_subject);

        double fitness = 0;

        for (int i = 0; i < content.length; i++) {
            fitness += Math.pow(content[i] - candidateContent[i], 2);
        }

        fitness = MAX_FITNESS - Math.pow(fitness, 3);

        if (fitness < 0) {
            fitness = 0;
        }

        if (content == candidateContent) fitness *= 100;

        return fitness;
    }

    private Character[] getGenes(IChromosome a_subject) {
        Gene[] genes = a_subject.getGenes();
        String candidateContent = "";
        Character[] contentArray;
        for (int i = 0; i < genes.length; i++) {
            candidateContent += (String) genes[i].getAllele();
        }

        contentArray = ArrayUtils.toObject(candidateContent.toCharArray());
        return contentArray;
    }

    public void setContent(Character[] content) {
        this.content = content;
    }
}
