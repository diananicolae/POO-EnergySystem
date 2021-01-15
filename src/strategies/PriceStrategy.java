package strategies;

import entities.Distributor;
import entities.Producer;

import java.util.ArrayList;

public class PriceStrategy implements EnergyChoiceStrategy {
    public PriceStrategy() {
    }

    public void producerChoice(final ArrayList<Producer> producers,
                               final Distributor distributor) {
        ArrayList<Producer> orderedProducers =  new ArrayList<>(producers);
        orderedProducers.removeIf(producer -> producer.getMaxDistributors()
                == producer.getDistributorsNumber());

        orderedProducers.sort((p1, p2) -> {
            if (p1.getPriceKW() == p2.getPriceKW()) {
                return Integer.compare(p2.getEnergyPerDistributor(), p1.getEnergyPerDistributor());
            } else {
                return Double.compare(p1.getPriceKW(), p2.getPriceKW());
            }
        });

        for (Producer producer : orderedProducers) {
            if (distributor.getRemainedEnergyNeededKW() < 0) {
                return;
            }
            distributor.addProducer(producer);
        }
    }
}
