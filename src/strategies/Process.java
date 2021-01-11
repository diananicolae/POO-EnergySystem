package strategies;

import database.Update;
import database.Database;

/**
 * The class contains methods used for monthly processing
 */
public final class Process {
    private final Strategy strategy;
    private final Database database;

    public Process(final Database database) {
        this.strategy = new Strategy();
        this.database = database;
    }

    /**
     * Process monthly update and modify database
     */
    public void processUpdate(final int index) {
        Update update = database.getUpdates().get(index - 1);
        database.addNewConsumers(update);

        /* update costs for distributors */
        strategy.updateDistributorCosts(database.getDistributors(), update);
    }

    /**
     * Process monthly payments
     * @return true if game has ended
     */
    public boolean processPayments() {
        /* determine new contract cost for each distributor */
        strategy.determineDistributorsContractCosts(database.getDistributors());
        /* consumers get paid */
        strategy.receiveMonthlyIncome(database.getConsumers());
        /* consumers choose contracts */
        strategy.setContract(database.getDistributors(),
                database.getConsumers());
        /* players pay monthly costs */
        strategy.payCosts(database.getConsumers(),
                database.getDistributors());
        /* determine bankrupt players */
        strategy.determineBankruptcy(database.getConsumers(),
                database.getDistributors());
        /* determine and return game status */
        return strategy.determineGameStatus(database.getDistributors());
    }
}
