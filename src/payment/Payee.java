package payment;

import entities.Producer;
import utils.Constants;

import java.util.ArrayList;

/**
 * Interface for entities that receive monthly payments
 */
public interface Payee {
    /**
     * Returns budget
     */
    int getBudget();
    /**
     * Sets budget
     */
    void setBudget(int budget);
    /**
     * Returns infrastructure cost
     */
    int getInfrastructureCost();
    /**
     * Returns production cost
     */
    int getProductionCost();
    /**
     * Returns the current number of clients
     */
    int getClientsNumber();
    /**
     * Sets the price of the contract
     */
    void setContractCost(int contractCost);
    /**
     *
     */
    void setProductionCost(int productionCost);
    /**
     *
     */
    ArrayList<Producer> getProducers();
    /**
     * Returns if entity is bankrupt
     */
    boolean isBankrupt();
    /**
     * Sets bankruptcy
     */
    void setBankrupt(boolean bankrupt);

    /**
     * Determine payee's profit
     */
    default int getProfit() {
        return (int) Math.round(Math.floor(Constants.PROFIT_PERCENT
                * getProductionCost()));
    }

    /**
     * Determine payee's monthly costs
     */
    default int getMonthlyCosts() {
        return getInfrastructureCost()
                + getProductionCost() * getClientsNumber();
    }

    /**
     * Payee gets paid
     */
    default void getPaid(final int cost) {
        int budget = getBudget() + cost;
        setBudget(budget);
    }

    /**
     * Payee pays costs
     */
    default void payCosts() {
        int budget = getBudget();
        if (budget < 0) {
            setBankrupt(true);
            return;
        }
        /* subtract amount from payee's budget */
        setBudget(budget - getMonthlyCosts());
    }

    /**
     * Determine the payee's final contract cost
     */
    default void determineContractCost() {
        /* bankrupt payee is ignored */
        if (isBankrupt()) {
            return;
        }

        int contractCost;
        if (getClientsNumber() == 0) {
            contractCost = getInfrastructureCost()
                    + getProductionCost() + getProfit();
        } else {
            contractCost = (int) Math.round(Math.floor(
                    (double) getInfrastructureCost() / getClientsNumber()
                    + getProductionCost() + getProfit()));
        }
        setContractCost(contractCost);
    }

    /**
     *
     */
    default void determineProductionCost() {
        double cost = 0;
        for (Producer producer : getProducers()) {
            cost += producer.getEnergyPerDistributor() * producer.getPriceKW();
        }
        setProductionCost((int) Math.round(Math.floor(cost / Constants.COST_PERCENT)));
    }
}
