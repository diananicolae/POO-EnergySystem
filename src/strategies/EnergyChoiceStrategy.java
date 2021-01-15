package strategies;

import entities.Distributor;
import entities.Producer;

import java.util.ArrayList;

public interface EnergyChoiceStrategy {
    void producerChoice(ArrayList<Producer> producers,
                        Distributor distributor);

}
