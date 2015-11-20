package base;

import entities.Word;
import fitness.WordDiffFitnessFunction;
import org.jgap.*;
import org.jgap.impl.CrossoverOperator;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.StringGene;

import java.util.Scanner;

/**
 * Created by marko on 10/31/15.
 */
public class WordGuessingGame {

    public static final int NUMBER_OF_EVOLUTIONS = 50;
    public static final int POPULATION_SIZE = 50000;

    public static void main(String[] args) {

        Configuration conf = new DefaultConfiguration();

        Scanner input = new Scanner(System.in);

        Word inputWord = getWordData(input);

        input.close();

        FitnessFunction fitnessFunction = new WordDiffFitnessFunction(inputWord);

        try {
            conf.setFitnessFunction(fitnessFunction);
            conf.addGeneticOperator(new CrossoverOperator(conf));

            int chromosomeSize = inputWord.getLength();

            Gene[] sampleGenes = new Gene[chromosomeSize];

            int from = 1;
            int to = 1;
            for (int i = 0; i < chromosomeSize; i++) {
                sampleGenes[i] = new StringGene(conf, from, to, StringGene.ALPHABET_CHARACTERS_LOWER);
            }

            Chromosome sampleChromosome = new Chromosome(conf, sampleGenes);

            conf.setSampleChromosome(sampleChromosome);
            conf.setPopulationSize(POPULATION_SIZE);

            Genotype population = Genotype.randomInitialGenotype(conf);

            long start = System.currentTimeMillis();
            boolean found = false;

            for (int i = 0; i < NUMBER_OF_EVOLUTIONS; i++) {

                IChromosome fittestChromosome = population.getFittestChromosome();

                String chromosomeValue = getValue(fittestChromosome);
                System.out.println(String.format("Nakon %d. generacije, naljbolji rezultat je: %s", i + 1, chromosomeValue));
                System.out.println("dobrota: " + fittestChromosome.getFitnessValue());
                System.out.println();

                found = chromosomeValue.equals(inputWord.getContent());
                if (found) break;

                population.evolve();
            }
            long end = System.currentTimeMillis();

            System.out.printf("Vaša riječ " + (found ? "je" : "nije") + " pronađena u %s sec, hvala što ste igrali! (Sljedeći put se potrudite malo više)", (((double) end - start) / 1000));

        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }

    }

    private static String getValue(IChromosome fittestChromosome) {
        String content = "";
        for (Gene gene : fittestChromosome.getGenes()) {
            content += gene.getAllele();
        }
        return content;
    }

    private static Word getWordData(Scanner input) {
        String inputWord;
        boolean inputCorrect = false;

        System.out.println("Word Guessing Game");
        System.out.println("Unesena rijec mora se sastojati od malih slova engleske abecede.");
        System.out.println("Unesite rijec za pogadjanje: ");

        do {
            inputWord = input.nextLine();

            for (char ch : inputWord.toCharArray()) {
                if (Character.isUpperCase(ch) || Character.isDigit(ch) || Character.isSpaceChar(ch)) {
                    System.out.println("Pogresan unos trazene rijeci, pokusajte ponovno:");
                    break;
                }
                inputCorrect = true;
            }
        } while (inputCorrect == false);

        return new Word(inputWord);
    }
}
