package database;

import io.EntityInput;

import java.util.ArrayList;

/**
 * The class contains information about monthly updates
 */
public final class Update {
    private final ArrayList<EntityInput> newConsumers;
    private final ArrayList<CostsChange> costsChanges;

    public Update(final ArrayList<EntityInput> newConsumers,
                  final ArrayList<CostsChange> costsChanges) {
        this.newConsumers = newConsumers;
        this.costsChanges = costsChanges;
    }

    public ArrayList<EntityInput> getNewConsumers() {
        return newConsumers;
    }

    public ArrayList<CostsChange> getCostsChanges() {
        return costsChanges;
    }

}
