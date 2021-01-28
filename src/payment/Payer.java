package payment;

import database.Contract;
import utils.Constants;

/**
 * Interface for entities that make monthly payments
 */
public interface Payer {
    /**
     * Returns budget
     */
    int getBudget();
    /**
     * Sets budget
     */
    void setBudget(int budget);
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
     * Returns if entity is bankrupt
     */
    boolean isBankrupt();
    /**
     * Sets bankruptcy
     */
    void setBankrupt(boolean bankrupt);
    /**
     * Returns monthly income
     */
    int getMonthlyIncome();

    /**
     * Pays the contract cost to the payee
     *
     * @param payee entity which the payment goes to
     * @param contract which has to be paid
     */
     default void pay(final Payee payee, final Contract contract) {
         /* the bankrupt payer is ignored */
         if (isBankrupt()) {
             return;
         }

         int budget = getBudget();
         int cost = 0;

         if (isIndebted()) {
             /* payer is indebted but cannot pay */
             if (budget < getCostPenalty()) {
                 /* becomes bankrupt */
                 setBankrupt(true);
             } else {
                 /* payer is indebted and can pay */
                 cost = getCostPenalty();
                 /* pays and debt is erased */
                 eraseDebt();
                 setIndebted(false);
             }
         } else {
             /* payer cannot pay */
             if (budget < contract.getPrice()) {
                 /* set new debt */
                 setDebt(contract.getPrice());
                 setIndebted(true);
             } else {
                 /* payer can pay */
                 cost = contract.getPrice();
             }
         }
         /* subtract amount from payer's budget */
         setBudget(budget - cost);
         contract.decreaseContractMonths();
         /* payee receives payment amount */
         payee.getPaid(cost);
     }

    /**
     * Payer gets monthly income
     */
     default void getIncome() {
         if (!isBankrupt()) {
             int budget = getBudget() + getMonthlyIncome();
             setBudget(budget);
         }
     }

    /**
     * Determine payer's cost penalty
     */
    default int getCostPenalty() {
        return (int) Math.round(Math.floor(Constants.DEBT_PERCENT * getDebt())
                + getContractCost());
    }

    /**
     * Get the cost of the contract
     */
    default int getContractCost() {
        return getContract().getPrice();
    }

    /**
     * Erase a payer's debt
     */
    default void eraseDebt() {
        setDebt(0);
    }
}
