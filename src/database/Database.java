package database;

import entities.Consumer;
import entities.Distributor;
import entities.Entity;
import entities.Producer;
import io.SingletonInput;

import java.util.ArrayList;

/**
 * The class contains information about the database used during processing
 * Singleton instance of database
 */
public final class Database {
    private static Database database = null;

    private ArrayList<Consumer> consumers;
    private ArrayList<Distributor> distributors;
    private ArrayList<Producer> producers;
    private ArrayList<Update> updates;
    private int numberOfTurns;

    private Database(final ArrayList<Consumer> consumers,
                     final ArrayList<Distributor> distributors,
                     final ArrayList<Producer> producers,
                     final ArrayList<Update> updates,
                     final int numberOfTurns) {
        this.consumers = consumers;
        this.distributors = distributors;
        this.producers = producers;
        this.updates = updates;
        this.numberOfTurns = numberOfTurns;
    }

    /**
     * Returns the singleton instance of database
     * */
    public static Database getInstance() {
        if (database == null) {
            throw new AssertionError("Database not initialized.");
        }
        return database;
    }

    /**
     * Transform input objects to working objects
     * */
    public static void init() {
        SingletonInput input = SingletonInput.getInstance();
        ArrayList<Consumer> consumers =
                (ArrayList<Consumer>) Entity.transformInput(input.getConsumers());
        ArrayList<Distributor> distributors =
                (ArrayList<Distributor>) Entity.transformInput(input.getDistributors());
        ArrayList<Producer> producers =
                (ArrayList<Producer>) Entity.transformInput(input.getProducers());
        ArrayList<Update> updates = input.getMonthlyUpdates();
        int numberOfTurns = input.getNumberOfTurns() + 1;
        database = new Database(consumers, distributors, producers, updates, numberOfTurns);
    }

    /**
     * Add new consumers to database
     * */
    public void addNewConsumers(final Update update) {
        ArrayList<Consumer> newConsumers =
                (ArrayList<Consumer>) Entity.transformInput(update.getNewConsumers());
        consumers.addAll(newConsumers);
    }

    public ArrayList<Consumer> getConsumers() {
        return consumers;
    }

    public ArrayList<Distributor> getDistributors() {
        return distributors;
    }

    public ArrayList<Producer> getProducers() {
        return producers;
    }

    public ArrayList<Update> getUpdates() {
        return updates;
    }

    public int getNumberOfTurns() {
        return numberOfTurns;
    }

}
