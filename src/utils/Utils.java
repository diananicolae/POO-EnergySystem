package utils;

import entities.EnergyType;
import strategies.EnergyChoiceStrategyType;

public class Utils {

    /**
     * Transforms a string into an enum
     * @param energyType for producers
     * @return an EnergyType Enum
     */
    public static EnergyType stringToEnergyType(final String energyType) {
        return switch (energyType) {
            case Constants.WIND -> EnergyType.WIND;
            case Constants.SOLAR -> EnergyType.SOLAR;
            case Constants.HYDRO -> EnergyType.HYDRO;
            case Constants.COAL -> EnergyType.COAL;
            case Constants.NUCLEAR -> EnergyType.NUCLEAR;
            default -> null;
        };
    }

    /**
     * Transforms a string into an enum
     * @param strategy for type of strategy
     * @return an EnergyChoiceStrategyType Enum
     */
    public static EnergyChoiceStrategyType stringToStrategyType(final String strategy) {
        return switch (strategy) {
            case "GREEN" -> EnergyChoiceStrategyType.GREEN;
            case "PRICE" -> EnergyChoiceStrategyType.PRICE;
            case "QUANTITY" -> EnergyChoiceStrategyType.QUANTITY;
            default -> null;
        };
    }

}
