package entities;

import io.EntityInput;
import utils.Constants;

/**
 * Factory for classes implementing the Entity interface
 *
 * Creates new instances of Consumer or Distributor
 */
public final class EntityFactory {
    private EntityFactory() {
    }

    /**
     * Creates new entity depending on type
     *
     * @param entity object containing information from input
     * @return new entity instance
     * */
    public static Entity getEntity(final EntityInput entity) {
        switch (entity.getType()) {
            case Constants.CONSUMER -> {
                return new Consumer(entity.getId(),
                        entity.getMonthlyIncome(),
                        entity.getInitialBudget());
            }
            case Constants.DISTRIBUTOR -> {
                return new Distributor(entity.getId(),
                        entity.getContractLength(),
                        entity.getInitialBudget(),
                        entity.getInitialInfrastructureCost(),
                        entity.getEnergyNeededKW(),
                        entity.getProducerStrategy());
            }
            case Constants.PRODUCER -> {
                return new Producer(entity.getId(),
                        entity.getEnergyType(),
                        entity.getMaxDistributors(),
                        entity.getPriceKW(),
                        entity.getEnergyPerDistributor());
            }
            default -> {
                return null;
            }
        }
    }
}
