package strategies;

import entities.Distributor;
import entities.Producer;

import java.util.ArrayList;

/**
 * Class for the Green Energy Strategy Choice
 */
public final class GreenStrategy implements EnergyChoiceStrategy {
    public GreenStrategy() {
    }

    /**
     * Chooses enough producers to provide energy for a distributor
     * Producers with a renewable energy source are prioritized
     */
    public void producerChoice(final ArrayList<Producer> producers,
                               final Distributor distributor) {
        ArrayList<Producer> orderedProducers =  new ArrayList<>(producers);

        /* remove producers with the maximum number of distributors */
        orderedProducers.removeIf(producer -> producer.getMaxDistributors()
                == producer.getDistributorsNumber());

        orderedProducers.sort((p1, p2) -> {
            /* move producers with renewable energy to the top */
            if (p1.getEnergyType().isRenewable() && !p2.getEnergyType().isRenewable()) {
                return -1;
            }
            /* move producers with non renewable energy to the bottom */
            if (p2.getEnergyType().isRenewable() && !p1.getEnergyType().isRenewable()) {
                return 1;
            }
            /* then sort asc by energy price */
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
