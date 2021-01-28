package strategies;

import entities.Distributor;
import entities.Producer;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Class for the Quantity Energy Strategy Choice
 */
public final class QuantityStrategy implements EnergyChoiceStrategy {
    public QuantityStrategy() {
    }

    /**
     * Chooses enough producers to provide energy for a distributor
     * Producers with a higher quantity of energy are prioritized
     */
    public void producerChoice(final ArrayList<Producer> producers,
                               final Distributor distributor) {
        ArrayList<Producer> orderedProducers =  new ArrayList<>(producers);

        /* remove producers with the maximum number of distributors */
        orderedProducers.removeIf(producer -> producer.getMaxDistributors()
                == producer.getDistributorsNumber());

        /* sort desc by energy quantity */
        orderedProducers.sort(Comparator
                .comparingInt(Producer::getEnergyPerDistributor).reversed());

        chooseProducers(orderedProducers, distributor);
    }
}
