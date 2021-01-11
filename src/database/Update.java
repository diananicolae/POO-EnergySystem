package database;

import io.EntityInput;

import java.util.ArrayList;

/**
 * The class contains information about monthly updates
 */
public final class Update {
    private final ArrayList<EntityInput> newConsumers;
    private final ArrayList<DistributorChange> distributorChanges;
    private final ArrayList<ProducerChange> producerChanges;

    public Update(final ArrayList<EntityInput> newConsumers,
                  final ArrayList<DistributorChange> distributorChanges,
                  final ArrayList<ProducerChange> producerChanges) {
        this.newConsumers = newConsumers;
        this.distributorChanges = distributorChanges;
        this.producerChanges = producerChanges;
    }

    public ArrayList<EntityInput> getNewConsumers() {
        return newConsumers;
    }

    public ArrayList<DistributorChange> getDistributorChanges() {
        return distributorChanges;
    }

    public ArrayList<ProducerChange> getProducerChanges() {
        return producerChanges;
    }
}
