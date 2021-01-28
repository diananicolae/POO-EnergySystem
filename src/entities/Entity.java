package entities;

import io.EntityInput;
import java.util.ArrayList;

/**
 * Common interface between Consumer, Distributor and Producer
 */
public interface Entity {
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
