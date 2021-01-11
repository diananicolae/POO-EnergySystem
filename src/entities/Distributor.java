package entities;

import payment.Contract;
import payment.Payee;

import java.util.ArrayList;

/**
 * Information about a distributor
 *
 * Implements Entity and Payee interfaces
 */
public final class Distributor implements Entity, Payee {
    private final int id;
    private final int contractLength;
    private final int energyNeededKW;
    private final String producerStrategy;
    private int budget;
    private int infrastructureCost;
    private int productionCost;
    private int contractCost;
    private boolean isBankrupt;
    private final ArrayList<Contract> contracts;

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
        this.producerStrategy = producerStrategy;
        this.contracts = new ArrayList<>();
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

    public void setBankrupt(final boolean bankrupt) {
        isBankrupt = bankrupt;
    }

    public void setInfrastructureCost(final int infrastructureCost) {
        this.infrastructureCost = infrastructureCost;
    }

    public void setProductionCost(final int productionCost) {
        this.productionCost = productionCost;
    }

    public void setBudget(final int budget) {
        this.budget = budget;
    }

    public void setContractCost(final int contractCost) {
        this.contractCost = contractCost;
    }

    public int getEnergyNeededKW() {
        return energyNeededKW;
    }

    public String getProducerStrategy() {
        return producerStrategy;
    }
}
