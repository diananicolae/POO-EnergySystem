package entities;

import io.EntityInput;
import java.util.ArrayList;

/**
 * Common interface between Consumer and Distributor classes
 */
public interface Entity {
    /**
     * Returns id
     */
    int getId();
    /**
     * Returns budget
     */
    int getBudget();
    /**
     * Sets budget
     */
    void setBudget(int budget);
    /**
     * Returns if entity is bankrupt
     */
    boolean isBankrupt();
    /**
     * Sets bankruptcy
     */
    void setBankrupt(boolean bankrupt);
    /**
     * Returns monthly income
     */
    int getMonthlyIncome();
    /**
     * Transforms input to list of working entities
     *
     * @param input list of input entities
     * @return list of Consumer/Distributor
     */
    static ArrayList<? extends Entity> transformInput(final ArrayList<EntityInput> input) {
        ArrayList<Entity> list = new ArrayList<>();

        for (EntityInput entityInput : input) {
            list.add(EntityFactory.getEntity(entityInput));
        }
        return list;
    }
}
