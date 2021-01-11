package payment;

import entities.Entity;
import io.Constants;

/**
 * Interface for entities that receive monthly payments
 */
public interface Payee extends Entity {
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
     * Determine payee's profit
     */
    default int getProfit() {
        return (int) Math.round(Math.floor(Constants.PROFIT_PERCENT
                * this.getProductionCost()));
    }
    /**
     * Determine payee's monthly costs
     */
    default int getMonthlyCosts() {
        return this.getInfrastructureCost()
                + this.getProductionCost() * this.getClientsNumber();
    }
    /**
     * Payee gets paid
     */
    default void getPaid(final int cost) {
        int budget = this.getBudget() + cost;
        this.setBudget(budget);
    }
    /**
     * Payee pays costs
     */
    default void payCosts() {
        int budget = this.getBudget();
        if (budget < 0) {
            this.setBankrupt(true);
            return;
        }
        /* subtract amount from payee's budget */
        this.setBudget(budget - this.getMonthlyCosts());
    }
    /**
     * Determine the payee's final contract cost
     */
    default void determineContractCost() {
        /* bankrupt payee is ignored */
        if (this.isBankrupt()) {
            return;
        }

        int contractCost;
        if (this.getClientsNumber() == 0) {
            contractCost = this.getInfrastructureCost()
                    + this.getProductionCost() + this.getProfit();
        } else {
            contractCost = (int) Math.round(Math.floor(
                    (double) this.getInfrastructureCost() / this.getClientsNumber())
                    + this.getProductionCost() + this.getProfit());
        }
        this.setContractCost(contractCost);
    }
}
