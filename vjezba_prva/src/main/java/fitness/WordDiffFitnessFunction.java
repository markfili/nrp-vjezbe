package fitness;

import entities.Word;
import org.jgap.FitnessFunction;
import org.jgap.IChromosome;

/**
 * Created by marko on 10/31/15.
 */
public class WordDiffFitnessFunction extends FitnessFunction {

    public static double MAXIMUM_FITNESS = 1000000;
    private static double FITNESS_MULTIPLIER = 100;
    private Word inputWord;

    public WordDiffFitnessFunction(Word targetWord) {
        this.inputWord = targetWord;
    }

    @Override
    protected double evaluate(IChromosome a_subject) {

        Word guessingWord = guessingWord(a_subject);

        double fitness = 0;

        for (int i = 0; i < inputWord.getLength(); i++) {
            fitness += Math.pow((guessingWord.getContent().charAt(i) - (int) inputWord.getContent().charAt(i)), 2);
        }

        fitness = MAXIMUM_FITNESS - Math.pow(fitness, 2);

        if (fitness < 0) {
            fitness = 0;
        }

        if (guessingWord.getContent().equals(inputWord.getContent())) {
            fitness += inputWord.getLength() * FITNESS_MULTIPLIER;
        }

        return fitness;
    }

    private Word guessingWord(IChromosome a_subject) {
        String geneValues = "";
        for (int i = 0; i < a_subject.size(); i++) {
            geneValues += (String) a_subject.getGene(i).getAllele();
        }
        return new Word(geneValues);
    }
}
