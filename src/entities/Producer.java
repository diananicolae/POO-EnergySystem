package entities;

import utils.Utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Observable;

/**
 * Information about a producer
 *
 * Extends Observable class and implements Entity interface
 */
public final class Producer extends Observable implements Entity {
    private final int id;
    private final double priceKW;
    private int energyPerDistributor;
    private final int maxDistributors;
    private final EnergyType energyType;
    private final ArrayList<Distributor> distributors;
    private final HashMap<Integer, ArrayList<Integer>> monthlyStats;

    public Producer(final int id, final String energyType,
                    final int maxDistributors,
                    final double priceKW,
                    final int energyPerDistributor) {
        this.id = id;
        this.energyType = Utils.stringToEnergyType(energyType);
        this.maxDistributors = maxDistributors;
        this.priceKW = priceKW;
        this.energyPerDistributor = energyPerDistributor;
        distributors = new ArrayList<>();
        monthlyStats = new HashMap<>();
    }

    /**
     * Add a new distributor as observer and to distributors list
     */
    public void addDistributor(final Distributor distributor) {
        addObserver(distributor);
        distributors.add(distributor);
        distributors.sort(Comparator.comparingInt(Distributor::getId));
    }

    /**
     * Remove distributor from distributors list and delete as observer
     */
    public void removeDistributor(final Distributor distributor) {
        deleteObserver(distributor);
        distributors.remove(distributor);
    }

    /**
     * Determine statistics for the current month and save in stats map
     */
    public void setMonthlyStat(final int month) {
        ArrayList<Integer> distributorIds = new ArrayList<>();
        for (Distributor distributor : distributors) {
            distributorIds.add(distributor.getId());
        }
        monthlyStats.put(month, distributorIds);
    }

    /**
     * Notify every observer that the producer has active changes
     */
    public void notifyDistributors() {
        setChanged();
        notifyObservers();
    }

    public int getId() {
        return id;
    }

    public int getDistributorsNumber() {
        return distributors.size();
    }

    public EnergyType getEnergyType() {
        return energyType;
    }

    public ArrayList<Distributor> getDistributors() {
        return distributors;
    }

    public int getMaxDistributors() {
        return maxDistributors;
    }

    public double getPriceKW() {
        return priceKW;
    }

    public int getEnergyPerDistributor() {
        return energyPerDistributor;
    }

    public HashMap<Integer, ArrayList<Integer>> getMonthlyStats() {
        return monthlyStats;
    }

    public void setEnergyPerDistributor(final int energyPerDistributor) {
        this.energyPerDistributor = energyPerDistributor;
    }
}
