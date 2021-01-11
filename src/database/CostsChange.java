package database;

/**
 * The class contains information about monthly cost changes
 */
public final class CostsChange {
    private final int id;
    private final int infrastructureCost;
    private final int productionCost;

    public CostsChange(final int id, final int infrastructureCost,
                       final int productionCost) {
        this.id = id;
        this.infrastructureCost = infrastructureCost;
        this.productionCost = productionCost;
    }

    public int getId() {
        return id;
    }

    public int getInfrastructureCost() {
        return infrastructureCost;
    }

    public int getProductionCost() {
        return productionCost;
    }

}