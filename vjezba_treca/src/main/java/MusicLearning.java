import org.encog.Encog;
import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by marko on 11/28/15.
 */
public class MusicLearning {

    private static final double C = 0.1;
    private static final double D = 0.2;
    private static final double E = 0.3;
    private static final double F = 0.4;
    private static final double G = 0.5;
    private static final double A = 0.6;
    private static final double B = 0.7;
    private static final double[][] SONG_INPUT_TESTING = {
            {B, C, E, B, D, F, D, A, C, E},
            {B, D, A, C, E, G, B, D, B, D},
            {A, B, C, D, E, A, A, F, A, B},
            {E, D, C, B, C, B, D, E, E, B},
            {C, D, G, A, G, C, D, A, A, B},
            {F, D, C, B, C, B, D, E, F, B},
            {G, D, G, A, G, C, D, D, G, G}
    };

    private static double SONG_INPUT[][] =
            {
                    {A, C, E, B, D, F, D, A, C, E},
                    {B, D, A, C, E, G, B, D, B, D},
                    {A, B, C, D, E, A, A, F, A, B},
                    {E, D, C, B, C, B, D, E, E, B},
                    {C, D, G, A, G, C, D, A, A, B},
                    {F, D, C, B, C, B, D, E, F, B},
                    {G, D, G, A, G, C, D, D, G, G}
            };

    private static double SONG_IDEAL[][] =
            {
                    {C}, {D}, {E}, {F}, {G}, {A}, {B}
            };

    private static List<String> songNames = new ArrayList<String>();


    public static void main(String[] args) {

        initValue();

        BasicNetwork network = new BasicNetwork();
        network.addLayer(new BasicLayer(null, true, SONG_INPUT[0].length));
        network.addLayer(new BasicLayer(new ActivationSigmoid(), true, 10));
        network.addLayer(new BasicLayer(new ActivationSigmoid(), false, SONG_IDEAL[0].length));
        network.getStructure().finalizeStructure();
        network.reset();

        // create training data
        MLDataSet trainingSet = new BasicMLDataSet(SONG_INPUT, SONG_IDEAL);
        MLDataSet trainingSetTesting = new BasicMLDataSet(SONG_INPUT_TESTING, SONG_IDEAL);

        // train the neural network
        final ResilientPropagation train = new ResilientPropagation(network, trainingSet);

        int epoch = 1;

        do {
            train.iteration();
            System.out.println("Epoch #" + epoch + " Error:" + train.getError());
            epoch++;
        } while (train.getError() > 0.000001);

        train.finishTraining();

        System.out.println("\nPossible solutions:\n");
        for (int i = 0; i < songNames.size(); i++) {
            System.out.println(i + 1 + ". " + songNames.get(i));
        }

        // test the neural network
        System.out.println("\nNeural Network Results:");
        for (MLDataPair pair : trainingSet) {
            final MLData output = network.compute(pair.getInput());

            double outValue = output.getData(0);
            double ideal = pair.getIdeal().getData(0);

            System.out.println("\nactual= " + outValue + ", ideal= " + ideal);

            outValue = Math.round((outValue - 0.1) * 10);
            System.out.println("Order: " + (int) (outValue + 1) + " Song title: " + songNames.get((int) (outValue)));
        }

        Encog.getInstance().shutdown();
    }

    private static void initValue() {
        songNames.addAll(Arrays.asList("Anesthesia - Sweet Histerics",
                "Clown - Eternal Laughter",
                "Decadent - The Missing Indecision",
                "Local Church - Searching For Your Ignorance",
                "Meteorologist - Raw Storm",
                "The Laxatives - American Relaxation",
                "Josef Fritzl - Hiding My Renegade"));
    }
}
