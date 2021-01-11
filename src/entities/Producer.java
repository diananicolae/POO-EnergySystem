package entities;

import java.util.ArrayList;

public final class Producer implements Entity {
    private final int id;
    private final String energyType;
    private final int maxDistributors;
    private final double priceKW;
    private int energyPerDistributor;
    private ArrayList<Distributor> distributors;

    public Producer(final int id, final String energyType,
                    final int maxDistributors,
                    final double priceKW,
                    final int energyPerDistributor) {
        this.id = id;
        this.energyType = energyType;
        this.maxDistributors = maxDistributors;
        this.priceKW = priceKW;
        this.energyPerDistributor = energyPerDistributor;
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

    public String getEnergyType() {
        return energyType;
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

    public void setEnergyPerDistributor(int energyPerDistributor) {
        this.energyPerDistributor = energyPerDistributor;
    }
}
