package database;

/**
 * Information about a contract between distributor and consumer
 */
public final class Contract {
    private final int distributorId;
    private final int consumerId;
    private final int price;
    private int remainedContractMonths;

    public Contract(final int distributorId, final int consumerId,
                    final int price, final int remainedContractMonths) {
        this.distributorId = distributorId;
        this.consumerId = consumerId;
        this.price = price;
        this.remainedContractMonths = remainedContractMonths;
    }

    /**
     * Decreases number of remaining contract months
     */
    public void decreaseContractMonths() {
        remainedContractMonths--;
    }

    public int getConsumerId() {
        return consumerId;
    }

    public int getDistributorId() {
        return distributorId;
    }

    public int getPrice() {
        return price;
    }

    public int getRemainedContractMonths() {
        return remainedContractMonths;
    }
}
