package strategies;

public final class StrategyFactory {
    private StrategyFactory() {
    }

    /**
     *
     * @param strategyType
     * @return
     */
    public static EnergyChoiceStrategy createStrategy(final EnergyChoiceStrategyType strategyType) {
        switch (strategyType) {
            case GREEN -> {
                return new GreenStrategy();
            }
            case PRICE -> {
                return new PriceStrategy();
            }
            case QUANTITY -> {
                return new QuantityStrategy();
            }
            default -> {
                return null;
            }
        }
    }
}
