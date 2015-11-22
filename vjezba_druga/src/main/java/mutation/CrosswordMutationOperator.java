package mutation;

import base.CrosswordMain;
import org.jgap.*;

import java.util.List;
import java.util.Random;

/**
 * Created by marko on 11/21/15.
 */
public class CrosswordMutationOperator implements GeneticOperator {

    private static final int POPULATION_SIZE = 2000;

    @Override
    public void operate(Population a_population, List a_candidateChromosomes) {
        Random random = new Random();
//        for (int i = 0; i < a_population.size(); i++) {
            int chromosomeOrdinal = random.nextInt(CrosswordMain.POPULATION_SIZE);
            IChromosome chromosome = a_population.getChromosome(chromosomeOrdinal);
            Gene[] genes = chromosome.getGenes();
            int numberOfGenes = genes.length;
            int firstGeneOrdinal = random.nextInt(numberOfGenes);
            int secondGeneOrdinal = random.nextInt(numberOfGenes);
            Gene firstGene = chromosome.getGene(firstGeneOrdinal);
            Gene secondGene = chromosome.getGene(secondGeneOrdinal);
            genes[firstGeneOrdinal] = secondGene;
            genes[secondGeneOrdinal] = firstGene;
            try {
                chromosome.setGenes(genes);
            } catch (InvalidConfigurationException e) {
                e.printStackTrace();
            }
            a_candidateChromosomes.add(chromosome);
//        }
    }
}