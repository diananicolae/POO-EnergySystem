package io;

import database.Update;

import java.util.ArrayList;

/**
 * The class contains singleton instance of the database created from input
 */
public final class Input {
    private static Input input = null;

    private final int numberOfTurns;
    private final ArrayList<EntityInput> consumers;
    private final ArrayList<EntityInput> distributors;
    private final ArrayList<EntityInput> producers;
    private final ArrayList<Update> monthlyUpdates;

    private Input(final int numberOfTurns,
                  final ArrayList<EntityInput> consumers,
                  final ArrayList<EntityInput> distributors,
                  final ArrayList<EntityInput> producers,
                  final ArrayList<Update> monthlyUpdates) {
        this.numberOfTurns = numberOfTurns;
        this.consumers = consumers;
        this.distributors = distributors;
        this.producers = producers;
        this.monthlyUpdates = monthlyUpdates;
    }

    /**
     * Returns the singleton instance of input
     */
    public static Input getInstance() {
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
                            final ArrayList<EntityInput> producers,
                            final ArrayList<Update> monthlyUpdates) {
        input = new Input(numberOfTurns, consumers,
                distributors, producers, monthlyUpdates);
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

    public ArrayList<EntityInput> getProducers() {
        return producers;
    }

    public ArrayList<Update> getMonthlyUpdates() {
        return monthlyUpdates;
    }
}
