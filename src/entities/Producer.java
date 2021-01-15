package entities;

import utils.Utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Observable;

public final class Producer extends Observable implements Entity {
    private final int id;
    private final EnergyType energyType;
    private final int maxDistributors;
    private final double priceKW;
    private int energyPerDistributor;
    private ArrayList<Distributor> distributors;
    private HashMap<Integer, ArrayList<Integer>> monthlyStats;

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

    public int getId() {
        return id;
    }

    @Override
    public int getBudget() {
        return 0;
    }

    @Override
    public void setBudget(int budget) {

    }

    @Override
    public boolean isBankrupt() {
        return false;
    }

    @Override
    public void setBankrupt(boolean bankrupt) {

    }

    @Override
    public int getMonthlyIncome() {
        return 0;
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

    public void addDistributor(final Distributor distributor) {
        addObserver(distributor);
        distributors.add(distributor);
        distributors.sort(Comparator.comparingInt(Distributor::getId));
    }

    public void removeDistributor(final Distributor distributor) {
        deleteObserver(distributor);
        distributors.remove(distributor);
    }

    public void setMonthlyStat(final int month) {
        ArrayList<Integer> distributorIds = new ArrayList<>();
        for (Distributor distributor : distributors) {
            distributorIds.add(distributor.getId());
        }
        monthlyStats.put(month, distributorIds);
    }

    public void notifyDistributors() {
        setChanged();
        notifyObservers();
    }

}
