package io;

import database.Update;

import java.util.ArrayList;

/**
 * The class contains singleton instance
 * of the database created from input
 */
public final class SingletonInput {
    private static SingletonInput input = null;

    private final int numberOfTurns;
    private final ArrayList<EntityInput> consumers;
    private final ArrayList<EntityInput> distributors;
    private final ArrayList<Update> monthlyUpdates;

    private SingletonInput(final int numberOfTurns,
                          final ArrayList<EntityInput> consumers,
                          final ArrayList<EntityInput> distributors,
                          final ArrayList<Update> monthlyUpdates) {
        this.numberOfTurns = numberOfTurns;
        this.consumers = consumers;
        this.distributors = distributors;
        this.monthlyUpdates = monthlyUpdates;
    }

    /**
     * Returns the singleton instance of input
     */
    public static SingletonInput getInstance() {
        if (input == null) {
            throw new AssertionError("Input not initialized.");
        }
        return input;
    }

    /**
     * Initialize the singleton input instance
     * Used after parsing file input
     */
    public static void init(final int numberOfTurns,
                            final ArrayList<EntityInput> consumers,
                            final ArrayList<EntityInput> distributors,
                            final ArrayList<Update> monthlyUpdates) {
        input = new SingletonInput(numberOfTurns, consumers,
                distributors, monthlyUpdates);
    }

    public int getNumberOfTurns() {
        return numberOfTurns;
    }

    public ArrayList<EntityInput> getConsumers() {
        return consumers;
    }

    public ArrayList<EntityInput> getDistributors() {
        return distributors;
    }

    public ArrayList<Update> getMonthlyUpdates() {
        return monthlyUpdates;
    }
}
