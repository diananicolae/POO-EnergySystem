package strategies;

import entities.Distributor;
import entities.Producer;

import java.util.ArrayList;

/**
 * Common interface for the Choice Strategies (Green, Price and Quantity)
 */
public interface EnergyChoiceStrategy {
    /**
     * Chooses enough producers to provide energy for a distributor
     * Each strategy sorts the producers according to their priority
     */
    void producerChoice(ArrayList<Producer> producers,
                        Distributor distributor);

    /**
     * Set the producers for a distributor
     */
    default void chooseProducers(ArrayList<Producer> producers,
                  Distributor distributor) {
        /* choose enough producers to cover the needed energy quantity */
        for (Producer producer : producers) {
            /* if the remained quantity is negative,
            * enough producers have been chosen */
            if (distributor.getRemainedEnergyNeededKW() < 0) {
                return;
            }
            distributor.addProducer(producer);
        }
    }
}
