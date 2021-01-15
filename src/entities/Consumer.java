package entities;

import database.Contract;
import payment.Payer;

/**
 * Information about a consumer
 *
 * Implements Entity and Payer interfaces
 */
public final class Consumer implements Entity, Payer {
    private final int id;
    private final int monthlyIncome;
    private int budget;
    private boolean isBankrupt;
    private Contract contract;
    private boolean isIndebted;
    private int debt;

    public Consumer(final int id, final int monthlyIncome,
                    final int initialBudget) {
        this.id = id;
        this.monthlyIncome = monthlyIncome;
        this.budget = initialBudget;
    }

    /**
     * Set a contract between consumer and distributor
     */
    public void setContract(final Distributor distributor) {
        if (isBankrupt) {
            return;
        }
        contract = new Contract(distributor.getId(), id,
                                distributor.getContractCost(),
                                distributor.getContractLength());
        distributor.addContract(contract);
    }

    /**
     * Remove a consumer's contract
     */
    public void removeContract() {
        contract = null;
    }

    public int getId() {
        return id;
    }

    public int getMonthlyIncome() {
        return monthlyIncome;
    }

    public int getBudget() {
        return budget;
    }

    public boolean isBankrupt() {
        return isBankrupt;
    }

    public Contract getContract() {
        return contract;
    }

    public int getDebt() {
        return debt;
    }

    public boolean isIndebted() {
        return isIndebted;
    }

    public void setBudget(final int budget) {
        this.budget = budget;
    }

    public void setBankrupt(final boolean bankrupt) {
        isBankrupt = bankrupt;
    }

    public void setIndebted(final boolean indebted) {
        isIndebted = indebted;
    }

    public void setDebt(final int debt) {
        this.debt = debt;
    }
}
