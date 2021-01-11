package io;

/**
 * Information about an entity retrieved from parsing the input files
 */
public final class EntityInput {
    private final String type;
    private final int id;
    private final int initialBudget;
    private int monthlyIncome;
    private int contractLength;
    private int initialInfrastructureCost;
    private int initialProductionCost;

    /**
     * Constructor for Distributor Input object
     */
    public EntityInput(final String type, final int id,
                       final int contractLength,
                       final int initialBudget,
                       final int initialInfrastructureCost,
                       final int initialProductionCost) {
        this.type = type;
        this.id = id;
        this.contractLength = contractLength;
        this.initialBudget = initialBudget;
        this.initialInfrastructureCost = initialInfrastructureCost;
        this.initialProductionCost = initialProductionCost;
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

    public int getInitialProductionCost() {
        return initialProductionCost;
    }
}
