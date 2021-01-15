package strategies;

public class StrategyFactory {
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
