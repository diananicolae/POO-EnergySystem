package strategies;

import database.CostsChange;
import database.Update;
import payment.Contract;
import entities.Consumer;
import entities.Distributor;
import io.Constants;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * The class contains every strategy needed for the game process
 */
public final class Strategy {
    public Strategy() {
    }

    /**
     * Update and change the costs for each distributor
     */
    public void updateDistributorCosts(final ArrayList<Distributor> distributors,
                                       final Update update) {
        for (CostsChange change : update.getCostsChanges()) {
            Distributor distributor = distributors.get(change.getId());
            if (distributor != null && !distributor.isBankrupt()) {
                distributor.setInfrastructureCost(change.getInfrastructureCost());
                distributor.setProductionCost(change.getProductionCost());
            }
        }
    }
    /**
     * Determine and set contract cost for each distributor
     */
    public void determineDistributorsContractCosts(final ArrayList<Distributor> distributors) {
        for (Distributor distributor : distributors) {
            distributor.determineContractCost();
            /* remove expired contracts */
            distributor.getContracts().removeIf(contract ->
                    contract.getRemainedContractMonths() == 0);
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
            }
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
