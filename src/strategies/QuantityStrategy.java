package strategies;

import entities.Distributor;
import entities.Producer;

import java.util.ArrayList;
import java.util.Comparator;

public class QuantityStrategy implements EnergyChoiceStrategy {
    public QuantityStrategy() {
    }

    public void producerChoice(final ArrayList<Producer> producers,
                               final Distributor distributor) {
        ArrayList<Producer> orderedProducers =  new ArrayList<>(producers);
        orderedProducers.removeIf(producer -> producer.getMaxDistributors()
                == producer.getDistributorsNumber());
        orderedProducers.sort(Comparator.comparingInt(Producer::getEnergyPerDistributor).reversed());

        for (Producer producer : orderedProducers) {
            if (distributor.getRemainedEnergyNeededKW() < 0) {
                return;
            }
            distributor.addProducer(producer);
        }
    }
}
