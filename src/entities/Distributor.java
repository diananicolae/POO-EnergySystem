package entities;

import database.Contract;
import payment.Payee;
import strategies.EnergyChoiceStrategyType;
import utils.Constants;
import utils.Utils;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Information about a distributor
 *
 * Implements Entity and Payee interfaces
 */
public final class Distributor implements Entity, Payee, Observer {
    private final int id;
    private final int contractLength;
    private final int energyNeededKW;
    private int remainedEnergyNeededKW;
    private final EnergyChoiceStrategyType producerStrategy;
    private int budget;
    private int infrastructureCost;
    private int productionCost;
    private int contractCost;
    private boolean isBankrupt;
    private final ArrayList<Contract> contracts;
    private ArrayList<Producer> producers;
    private boolean activeProducerChanges;

    public Distributor(final int id, final int contractLength,
                       final int initialBudget,
                       final int initialInfrastructureCost,
                       final int energyNeededKW,
                       final String producerStrategy) {
        this.id = id;
        this.contractLength = contractLength;
        this.budget = initialBudget;
        this.infrastructureCost = initialInfrastructureCost;
        this.energyNeededKW = energyNeededKW;
        this.remainedEnergyNeededKW = energyNeededKW;
        this.producerStrategy = Utils.stringToStrategyType(producerStrategy);
        this.contracts = new ArrayList<>();
        this.producers = new ArrayList<>();
    }

    /**
     * Returns the total number of clients
     */
    public int getClientsNumber() {
        return contracts.size();
    }

    /**
     * Add a new contract to contracts list
     */
    public void addContract(final Contract contract) {
        contracts.add(contract);
    }

    /**
     * Remove a contract from contracts list
     */
    public void removeContract(final Contract contract) {
        contracts.remove(contract);
    }

    public int getId() {
        return id;
    }

    public int getContractLength() {
        return contractLength;
    }

    public int getBudget() {
        return budget;
    }

    public int getInfrastructureCost() {
        return infrastructureCost;
    }

    public int getProductionCost() {
        return productionCost;
    }

    public int getContractCost() {
        return contractCost;
    }

    public boolean isBankrupt() {
        return isBankrupt;
    }

    public int getMonthlyIncome() {
        return 0;
    }

    public ArrayList<Contract> getContracts() {
        return contracts;
    }

    public int getEnergyNeededKW() {
        return energyNeededKW;
    }

    public EnergyChoiceStrategyType getProducerStrategy() {
        return producerStrategy;
    }

    public int getRemainedEnergyNeededKW() {
        return remainedEnergyNeededKW;
    }

    public ArrayList<Producer> getProducers() {
        return producers;
    }

    public void setActiveProducerChanges(final boolean activeProducerChanges) {
        this.activeProducerChanges = activeProducerChanges;
    }

    public boolean hasActiveProducerChanges() {
        return activeProducerChanges;
    }

    public void setBankrupt(final boolean bankrupt) {
        isBankrupt = bankrupt;
    }

    public void setInfrastructureCost(final int infrastructureCost) {
        this.infrastructureCost = infrastructureCost;
    }

    public void setBudget(final int budget) {
        this.budget = budget;
    }

    public void setContractCost(final int contractCost) {
        this.contractCost = contractCost;
    }

    public void addProducer(final Producer producer) {
        producers.add(producer);
        remainedEnergyNeededKW -= producer.getEnergyPerDistributor();
        producer.addDistributor(this);
    }

    public void removeAllProducers() {
        for (Producer producer : producers) {
            producer.removeDistributor(this);
        }
        remainedEnergyNeededKW = energyNeededKW;
        producers.clear();
    }

    public void determineProductionCost() {
        double cost = 0;
        for (Producer producer : producers) {
            cost += producer.getEnergyPerDistributor() * producer.getPriceKW();
        }
        productionCost = (int) Math.round(Math.floor(cost / Constants.COST_PERCENT));
    }

    @Override
    public void update(final Observable producer, final Object obj) {
        activeProducerChanges = true;
    }
}
