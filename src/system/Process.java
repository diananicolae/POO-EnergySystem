package system;

import database.Update;
import database.Database;

/**
 * The class contains methods used for monthly processing
 */
public final class Process {
    private final ProcessStrategy processStrategy;
    private final Database database;

    public Process(final Database database) {
        this.processStrategy = new ProcessStrategy();
        this.database = database;
    }

    public void processFirstMonthPayments() {
        /* distributors choose producers */
        processStrategy.setProducers(database.getDistributors(),
                database.getProducers());
        /* determine production cost for each distributor */
        processStrategy.determineProductionCosts(database.getDistributors());
        /* determine contract cost for each distributor */
        processStrategy.determineContractsCosts(database.getDistributors());
        /* consumers get paid */
        processStrategy.receiveMonthlyIncome(database.getConsumers());
        /* consumers choose contracts */
        processStrategy.setContract(database.getDistributors(),
                database.getConsumers());
        /* players pay monthly costs */
        processStrategy.payCosts(database.getConsumers(),
                database.getDistributors());
    }

    /**
     * Process monthly payments
     * @return true if game has ended
     */
    public boolean processPayments(final int month) {
        Update update = database.getUpdates().get(month - 1);
        /* update costs for distributors */
        processStrategy.updateDistributorCosts(database.getDistributors(), update);
        /* determine new contract and production cost for each distributor */
        processStrategy.determineContractsCosts(database.getDistributors());
        /* update and add new consumers */
        database.addNewConsumers(update);
        /* consumers get paid */
        processStrategy.receiveMonthlyIncome(database.getConsumers());
        /* consumers choose contracts */
        processStrategy.setContract(database.getDistributors(),
                database.getConsumers());
        /* players pay monthly costs */
        processStrategy.payCosts(database.getConsumers(),
                database.getDistributors());
        /* determine bankrupt players */
        processStrategy.determineBankruptcy(database.getConsumers(),
                database.getDistributors());
        /* update costs for producers */
        processStrategy.updateProducerCosts(database.getProducers(), update);
        /* distributors choose producers */
        processStrategy.setProducers(database.getDistributors(),
                database.getProducers());
        /* determine production cost for each distributor */
        processStrategy.determineProductionCosts(database.getDistributors());

        processStrategy.determineMonthlyStats(database.getProducers(), month);
        /* determine and return game status */
        return processStrategy.determineGameStatus(database.getDistributors());
    }
}
