package base;

import fitness.CrosswordFitnessFunction;
import fitness.WordFitnessFunction;
import mutation.CrosswordMutationOperator;
import org.jgap.*;
import org.jgap.impl.CrossoverOperator;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.IntegerGene;
import org.jgap.impl.StringGene;

import java.util.Arrays;

/**
 * Created by marko on 11/20/15.
 */
public class CrosswordMain {
    public static final int POPULATION_SIZE = 20;
    private static final int NUMBER_OF_EVOLUTIONS = 1000;

    static Character[][] content;
    static String[] solutions;

    public static void main(String[] args) {

        // 4.a
        // initData();
        content = new Character[][]{
                {'K', 'L', 'U', 'B'},
                {'R', 'A', 'D', 'A'},
                {'A', 'R', 'A', 'K'},
                {'J', 'A', 'V', 'A'}
        };
        solutions = new String[content.length];


        try {

            for (int i = 0; i < content.length; i++) {
                Character[] rowContent = content[i];
                Configuration.reset();

                System.out.println("setting content to calculate " + charsToString(rowContent));
                // 4.c
                Configuration conf = new DefaultConfiguration();

                // 4.d
                conf.addGeneticOperator(new CrosswordMutationOperator());
                conf.addGeneticOperator(new CrossoverOperator(conf));

                // 4.e booleans
                conf.setKeepPopulationSizeConstant(true);
                conf.setPreservFittestIndividual(true);

                // 4.b
                WordFitnessFunction wordFitnessFunction = new WordFitnessFunction();

                // 4.f
                wordFitnessFunction.setContent(rowContent);
                conf.setFitnessFunction(wordFitnessFunction);

                // 4.g sample genes
                int chromosomeSize = rowContent.length;
                Gene[] sampleGenes = new Gene[chromosomeSize];

                int letterSize = 1;
                for (int j = 0; j < chromosomeSize; j++) {
                    sampleGenes[j] = new StringGene(conf, letterSize, letterSize, StringGene.ALPHABET_CHARACTERS_UPPER);
                }

                // 4.h
                Chromosome sampleChromosome = new Chromosome(conf, sampleGenes);

                // 4.i
                conf.setSampleChromosome(sampleChromosome);
                conf.setPopulationSize(POPULATION_SIZE);

                // 4.j
                Genotype population = Genotype.randomInitialGenotype(conf);

                // 4.k

                long startTime = System.currentTimeMillis();
                for (int k = 0; k < NUMBER_OF_EVOLUTIONS; k++) {
                    IChromosome candidate = population.getFittestChromosome();
                    System.out.println("candidate: " + geneToString(candidate.getGenes()));
                    System.out.println("fitness: " + candidate.getFitnessValue());

                    if (geneToString(candidate.getGenes()).equals(charsToString(rowContent))) {
                        solutions[i] = geneToString(candidate.getGenes());
                        System.out.println(conf.toString());
                        break;
                    }
                    population.evolve();
                }
                long endTime = System.currentTimeMillis();

                System.out.println("\nTime to calculate " + (((double) endTime - startTime) / 1000) + " seconds\n");
            }

            for (String solution : solutions) {
                System.out.println(solution);
            }


            // calculate crossword order
            Configuration.reset();
            Configuration orderConfiguration = new DefaultConfiguration();

            // 4.d

            orderConfiguration.addGeneticOperator(new CrosswordMutationOperator());
            orderConfiguration.addGeneticOperator(new CrossoverOperator(orderConfiguration));

            // 4.e booleans
            orderConfiguration.setKeepPopulationSizeConstant(true);
            orderConfiguration.setPreservFittestIndividual(true);
            // 4.f


            Integer[] solutionsOrder = {1, 2, 3, 4};
            orderConfiguration.setFitnessFunction(new CrosswordFitnessFunction(solutionsOrder));

            // 4.g sample genes
            int chromosomeSize = solutions.length;
            Gene[] sampleGenes = new Gene[chromosomeSize];

            for (int j = 0; j < chromosomeSize; j++) {
                sampleGenes[j] = new IntegerGene(orderConfiguration, 1, chromosomeSize);
            }

            // 4.h
            Chromosome sampleChromosome = new Chromosome(orderConfiguration, sampleGenes);

            orderConfiguration.setSampleChromosome(sampleChromosome);
            orderConfiguration.setPopulationSize(POPULATION_SIZE);
//            orderConfiguration.setAlwaysCaculateFitness(true);

            Genotype orderPopulation = Genotype.randomInitialGenotype(orderConfiguration);

            long startTime = System.currentTimeMillis();
            for (int i = 0; i < NUMBER_OF_EVOLUTIONS; i++) {

                IChromosome orderCandidate = orderPopulation.getFittestChromosome();
                Integer[] candidateOrder = getOrderGenes(orderCandidate.getGenes());

                for (int traverse = 0; traverse < candidateOrder.length; traverse++) {
                    System.out.print(candidateOrder[traverse]);
                    if (traverse < (candidateOrder.length - 1)) {
                        System.out.print(", ");
                    } else {
                        System.out.println();
                    }
                }

                System.out.println("order fitness: " + orderCandidate.getFitnessValue());

                if (Arrays.equals(candidateOrder, solutionsOrder)) {
                    System.out.println(orderConfiguration.toString());
                    break;
                }

                orderPopulation.evolve();
            }

            long endTime = System.currentTimeMillis();

            System.out.println("\nTime to calculate " + (((double) endTime - startTime) / 1000) + " seconds");


        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }


    }

    private static Integer[] getOrderGenes(Gene[] genes) {
        Integer[] order = new Integer[genes.length];
        for (int i = 0; i < genes.length; i++) {
            order[i] = (Integer) genes[i].getAllele();
        }
        return order;
    }

    private static String geneToString(Gene[] genes) {
        String candidate = "";
        for (Gene gene : genes) {
            candidate += (String) gene.getAllele();
        }
        return candidate;
    }

    private static String charsToString(Character[] characters) {
        String content = "";
        for (Character character : characters) {
            content += character;
        }
        return content;
    }
}