package payment;

import database.Contract;
import entities.Entity;
import utils.Constants;

/**
 * Interface for entities that make monthly payments
 */
public interface Payer extends Entity {
    /**
     * Returns if payer is indebted
     */
    boolean isIndebted();
    /**
     * Returns payer's current debt
     */
    int getDebt();
    /**
     * Sets payer's debt
     */
    void setDebt(int debt);
    /**
     * Sets if payer is indebted
     */
    void setIndebted(boolean indebted);
    /**
     * Returns payer's current contract
     */
    Contract getContract();

    /**
     * Pays the contract cost to the payee
     *
     * @param payee entity which the payment goes to
     * @param contract which has to be paid
     */
     default void pay(final Payee payee, final Contract contract) {
         /* the bankrupt payer is ignored */
         if (this.isBankrupt()) {
             return;
         }

         int budget = this.getBudget();
         int cost = 0;

         if (this.isIndebted()) {
             /* payer is indebted but cannot pay */
             if (budget < this.getCostPenalty()) {
                 /* becomes bankrupt */
                 this.setBankrupt(true);
             } else {
                 /* payer is indebted and can pay */
                 cost = this.getCostPenalty();
                 /* pays and debt is erased */
                 this.eraseDebt();
                 this.setIndebted(false);
             }
         } else {
             /* payer cannot pay */
             if (budget < contract.getPrice()) {
                 /* set new debt */
                 this.setDebt(contract.getPrice());
                 this.setIndebted(true);
             } else {
                 /* payer can pay */
                 cost = contract.getPrice();
             }
         }
         /* subtract amount from payer's budget */
         this.setBudget(budget - cost);
         contract.decreaseContractMonths();
         /* payee receives payment amount */
         payee.getPaid(cost);
     }
    /**
     * Payer gets monthly income
     */
     default void getIncome() {
         if (!this.isBankrupt()) {
             int budget = this.getBudget() + this.getMonthlyIncome();
             this.setBudget(budget);
         }
     }
    /**
     * Determine payer's cost penalty
     */
    default int getCostPenalty() {
        return (int) Math.round(Math.floor(Constants.DEBT_PERCENT * this.getDebt())
                + this.getContractCost());
    }
    /**
     * Get the cost of the contract
     */
    default int getContractCost() {
        return this.getContract().getPrice();
    }
    /**
     * Erase a payer's debt
     */
    default void eraseDebt() {
        this.setDebt(0);
    }
}
