package configuration;

import org.jgap.Configuration;
import org.jgap.DefaultFitnessEvaluator;
import org.jgap.InvalidConfigurationException;
import org.jgap.event.EventManager;
import org.jgap.impl.BestChromosomesSelector;
import org.jgap.impl.ChromosomePool;
import org.jgap.impl.GABreeder;
import org.jgap.impl.StockRandomGenerator;

/**
 * Created by marko on 11/21/15.
 */
public class CroswordConfiguration extends Configuration {
    public CroswordConfiguration(String a_id, String a_name) {
        super("Song segments configuration", "second level");
        try {
            setBreeder(new GABreeder());
            setRandomGenerator(new StockRandomGenerator());
            setEventManager(new EventManager());
            BestChromosomesSelector bestChromsSelector = new BestChromosomesSelector(
                    this, 0.90d);
            bestChromsSelector.setDoubletteChromosomesAllowed(true);
            addNaturalSelector(bestChromsSelector, false);
            setMinimumPopSizePercent(0);
            setSelectFromPrevGen(1.0d);
            setKeepPopulationSizeConstant(true);
            Configuration.reset("Song segments configuration");
            setFitnessEvaluator(new DefaultFitnessEvaluator());
            setChromosomePool(new ChromosomePool());
//            addGeneticOperator(new SongSegmentMutationOperator());
        } catch (InvalidConfigurationException e) {
            throw new RuntimeException(
                    "Fatal error: DefaultConfiguration class could not use its "
                            + "own stock configuration values. This should never happen. "
                            + "Please report this as a bug to the JGAP team.");
        }
    }
}
