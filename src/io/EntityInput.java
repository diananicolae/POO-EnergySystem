package io;

/**
 * Information about an entity retrieved from parsing the input files
 */
public final class EntityInput {
    private final String type;
    private final int id;
    private int initialBudget;
    private int monthlyIncome;
    private int contractLength;
    private int initialInfrastructureCost;
    private int energyNeededKW;
    private String producerStrategy;
    private String energyType;
    private int maxDistributors;
    private double priceKW;
    private int energyPerDistributor;

    /**
     * Constructor for Distributor Input object
     */
    public EntityInput(final String type, final int id,
                       final int contractLength,
                       final int initialBudget,
                       final int initialInfrastructureCost,
                       final int energyNeededKW,
                       final String producerStrategy) {
        this.type = type;
        this.id = id;
        this.contractLength = contractLength;
        this.initialBudget = initialBudget;
        this.initialInfrastructureCost = initialInfrastructureCost;
        this.energyNeededKW = energyNeededKW;
        this.producerStrategy = producerStrategy;
    }

    /**
     * Constructor for Consumer Input object
     */
    public EntityInput(final String type, final int id,
                       final int initialBudget,
                       final int monthlyIncome) {
        this.type = type;
        this.id = id;
        this.initialBudget = initialBudget;
        this.monthlyIncome = monthlyIncome;
    }

    /**
     * Constructor for Producer Input object
     */
    public EntityInput(final String type, final int id,
                       final String energyType,
                       final int maxDistributors,
                       final double priceKW,
                       final int energyPerDistributor) {
        this.type = type;
        this.id = id;
        this.energyType = energyType;
        this.maxDistributors = maxDistributors;
        this.priceKW = priceKW;
        this.energyPerDistributor = energyPerDistributor;
    }



    public int getId() {
        return id;
    }

    public int getInitialBudget() {
        return initialBudget;
    }

    public String getType() {
        return type;
    }

    public int getMonthlyIncome() {
        return monthlyIncome;
    }

    public int getContractLength() {
        return contractLength;
    }

    public int getInitialInfrastructureCost() {
        return initialInfrastructureCost;
    }

    public int getEnergyNeededKW() {
        return energyNeededKW;
    }

    public String getProducerStrategy() {
        return producerStrategy;
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
}
