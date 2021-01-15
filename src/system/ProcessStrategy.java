package system;

import database.DistributorChange;
import database.ProducerChange;
import database.Update;
import entities.Producer;
import database.Contract;
import entities.Consumer;
import entities.Distributor;
import strategies.EnergyChoiceStrategy;
import strategies.StrategyFactory;
import utils.Constants;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * The class contains every strategy needed for the game process
 */
public final class ProcessStrategy {
    public ProcessStrategy() {
    }

    /**
     * Update and change the costs for each distributor
     */
    public void updateDistributorCosts(final ArrayList<Distributor> distributors,
                                       final Update update) {
        for (DistributorChange change : update.getDistributorChanges()) {
            Distributor distributor = distributors.get(change.getId());
            if (distributor != null && !distributor.isBankrupt()) {
                distributor.setInfrastructureCost(change.getInfrastructureCost());
            }
        }
    }

    /**
     * Update and change the costs for each producer
     */
    public void updateProducerCosts(final ArrayList<Producer> producers,
                                    final Update update) {
        for (ProducerChange change : update.getProducerChanges()) {
            Producer producer = producers.get(change.getId());
            if (producer != null) {
                producer.setEnergyPerDistributor(change.getEnergyPerDistributor());
                producer.notifyDistributors();
            }
        }
    }

    /**
     * Determine set contract cost for each distributor
     */
    public void determineContractsCosts(final ArrayList<Distributor> distributors) {
        for (Distributor distributor : distributors) {
            distributor.determineContractCost();
            /* remove expired contracts */
            distributor.getContracts().removeIf(contract ->
                    contract.getRemainedContractMonths() == 0);
        }
    }

    /**
     * Determine production cost for each distributor
     */
    public void determineProductionCosts(final ArrayList<Distributor> distributors) {
        for (Distributor distributor : distributors) {
            distributor.determineProductionCost();
        }
    }

    public void setProducers(final ArrayList<Distributor> distributors,
                             final ArrayList<Producer> producers) {
        for (Distributor distributor : distributors) {
            if (distributor.isBankrupt()) {
                return;
            }
            if (distributor.hasActiveProducerChanges()) {
                distributor.removeAllProducers();
                distributor.setActiveProducerChanges(false);
            }
            EnergyChoiceStrategy strategy =
                    StrategyFactory.createStrategy(distributor.getProducerStrategy());
            strategy.producerChoice(producers, distributor);
        }
    }

    /**
     * Consumers receive monthly income
     */
    public void receiveMonthlyIncome(final ArrayList<Consumer> consumers) {
        for (Consumer consumer : consumers) {
            consumer.getIncome();
        }
    }

    /**
     * Set the contracts between consumers and distributors
     */
    public void setContract(final ArrayList<Distributor> distributors,
                                    final ArrayList<Consumer> consumers) {
        ArrayList<Distributor> sortedDistributors = new ArrayList<>(distributors);
        /* remove distributors if they are bankrupt or
        * if they can't afford new clients */
        sortedDistributors.removeIf(distributor ->
                distributor.isBankrupt() || distributor.getBudget() <= 0);
        /* sort distributors by contract cost */
        sortedDistributors.sort(Comparator.comparingInt(Distributor::getContractCost));
        /* get the distributor with the lowest price */
        Distributor distributor = sortedDistributors.get(Constants.LOWEST_PRICE);

        /* set new contract if consumer doesn't have a contract
         * or the contract is expiring */
        for (Consumer consumer : consumers) {
            if (consumer.getContract() == null
                    || consumer.getContract().getRemainedContractMonths() == 0) {
                consumer.setContract(distributor);
            }
        }
    }

    /**
     * Players pay monthly costs
     */
    public void payCosts(final ArrayList<Consumer> consumers,
                                  final ArrayList<Distributor> distributors) {
        /* consumers pay their contracts */
        for (Consumer  consumer : consumers) {
            Contract contract = consumer.getContract();
            Distributor distributor = distributors.get(contract.getDistributorId());
            consumer.pay(distributor, contract);
        }

        /* distributors pay their costs */
        for (Distributor distributor : distributors) {
            distributor.payCosts();
        }
    }

    /**
     * Determine bankruptcy for each entity
     */
    public void determineBankruptcy(final ArrayList<Consumer> consumers,
                                    final ArrayList<Distributor> distributors) {
        /* if a consumer is bankrupt
        * remove contract from distributor */
        for (Consumer consumer : consumers) {
            Contract contract = consumer.getContract();
            if (consumer.isBankrupt() && contract != null) {
                Distributor distributor = distributors.get(contract.getDistributorId());
                distributor.removeContract(contract);
            }
        }

        /* if a distributor is bankrupt
        * erase their clients' debt and remove the contracts */
        for (Distributor distributor : distributors) {
            if (distributor.isBankrupt()) {
                for (Contract contract : distributor.getContracts()) {
                    Consumer consumer = consumers.get(contract.getConsumerId());
                    if (consumer != null) {
                        consumer.setIndebted(false);
                        consumer.eraseDebt();
                        consumer.removeContract();
                    }
                }
                distributor.getContracts().clear();
                distributor.removeAllProducers();

            }
        }
    }

    public void determineMonthlyStats(final ArrayList<Producer> producers,
                                      final int month) {
        for (Producer producer : producers) {
            producer.setMonthlyStat(month);
        }
    }

    /**
     * Determine current game status
     * @return true if game has ended
     */
    public boolean determineGameStatus(final ArrayList<Distributor> distributors) {
        /* count bankrupt distributors */
        int bankruptDst = 0;
        for (Distributor distributor : distributors) {
            if (distributor.isBankrupt()) {
                bankruptDst++;
            }
        }
        /* if all distributors are bankrupt, returns true and game ends */
        return bankruptDst == distributors.size();
    }
}
