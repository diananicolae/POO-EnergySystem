package strategies;

import entities.Distributor;
import entities.Producer;

import java.util.ArrayList;

/**
 * Class for the Price Energy Strategy Choice
 */
public final class PriceStrategy implements EnergyChoiceStrategy {
    public PriceStrategy() {
    }

    /**
     * Chooses enough producers to provide energy for a distributor
     * Producers with a cheaper energy price are prioritized
     */
    public void producerChoice(final ArrayList<Producer> producers,
                               final Distributor distributor) {
        ArrayList<Producer> orderedProducers =  new ArrayList<>(producers);

        /* remove producers with the maximum number of distributors */
        orderedProducers.removeIf(producer -> producer.getMaxDistributors()
                == producer.getDistributorsNumber());

        orderedProducers.sort((p1, p2) -> {
            /* sort asc by energy price */
            if (p1.getPriceKW() != p2.getPriceKW()) {
                return Double.compare(p1.getPriceKW(), p2.getPriceKW());
            } else {
                /* then sort desc by energy quantity */
                return Integer.compare(p2.getEnergyPerDistributor(), p1.getEnergyPerDistributor());
            }
        });

        chooseProducers(orderedProducers, distributor);
    }
}
