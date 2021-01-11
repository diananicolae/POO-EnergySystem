package io;

import database.DistributorChange;
import database.ProducerChange;
import database.Update;
import entities.Entity;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The class reads and parses the data from input
 */
public final class Parser {
    private final String inputPath;

    public Parser(final String inputPath) {
        this.inputPath = inputPath;
    }

    /**
     * Reads input and creates singleton input database
     */
    public void readData() {
        JSONParser jsonParser = new JSONParser();

        int numberOfTurns = 0;
        ArrayList<Update> monthlyUpdates = null;
        ArrayList<EntityInput> consumers = new ArrayList<>();
        ArrayList<EntityInput> distributors = new ArrayList<>();
        ArrayList<EntityInput> producers = new ArrayList<>();

        try {
            /* parse the contents of the JSON file */
            FileReader fileReader = new FileReader(inputPath);

            JSONObject jsonObject = (JSONObject) jsonParser
                    .parse(fileReader);
            numberOfTurns = ((Long)
                    jsonObject.get(Constants.NO_OF_TURNS)).intValue();
            JSONObject initialData = (JSONObject)
                    jsonObject.get(Constants.INITIAL_DATA);
            JSONArray jsonConsumers = (JSONArray)
                    initialData.get(Constants.CONSUMERS);
            JSONArray jsonDistributors = (JSONArray)
                    initialData.get(Constants.DISTRIBUTORS);
            JSONArray jsonProducers = (JSONArray)
                    initialData.get(Constants.PRODUCERS);

            /* create consumers input */
            if (jsonConsumers != null) {
                for (Object jsonConsumer : jsonConsumers) {
                    consumers.add(new EntityInput(
                            Constants.CONSUMER,
                            ((Long) ((JSONObject) jsonConsumer).
                                    get(Constants.ID)).intValue(),
                            ((Long) ((JSONObject) jsonConsumer).
                                    get(Constants.INITIAL_BUDGET)).intValue(),
                            ((Long) ((JSONObject) jsonConsumer).
                                    get(Constants.MONTHLY_INCOME)).intValue()
                    ));
                }
            } else {
                consumers = null;
                System.out.println("NO VALID CONSUMERS");
            }

            /* create distributors input */
            if (jsonDistributors != null) {
                for (Object jsonDistributor : jsonDistributors) {
                    distributors.add(new EntityInput(
                            Constants.DISTRIBUTOR,
                            ((Long) ((JSONObject) jsonDistributor).
                                    get(Constants.ID)).intValue(),
                            ((Long) ((JSONObject) jsonDistributor).
                                    get(Constants.CONTRACT_LENGTH)).intValue(),
                            ((Long) ((JSONObject) jsonDistributor).
                                    get(Constants.INITIAL_BUDGET)).intValue(),
                            ((Long) ((JSONObject) jsonDistributor).
                                    get(Constants.INITIAL_INF_COST)).intValue(),
                            ((Long) ((JSONObject) jsonDistributor).
                                    get(Constants.ENERGY_NEEDED)).intValue(),
                            (String) ((JSONObject) jsonDistributor).
                                    get(Constants.PRODUCER_STRATEGY)
                    ));
                }
            } else {
                distributors = null;
                System.out.println("NO VALID DISTRIBUTORS");
            }

            /* create producers input */
            if (jsonProducers != null) {
                for (Object jsonProducer : jsonProducers) {
                    producers.add(new EntityInput(
                            Constants.PRODUCER,
                            ((Long) ((JSONObject) jsonProducer).
                                    get(Constants.ID)).intValue(),
                            (String) ((JSONObject) jsonProducer).
                                    get(Constants.ENERGY_TYPE),
                            ((Long) ((JSONObject) jsonProducer).
                                    get(Constants.MAX_DISTRIBUTORS)).intValue(),
                            (Double) ((JSONObject) jsonProducer).
                                    get(Constants.PRICE_KW),
                            ((Long) ((JSONObject) jsonProducer).
                                    get(Constants.ENERGY_PER_DISTRIBUTOR)).intValue()
                    ));
                }
            } else {
                producers = null;
                System.out.println("NO VALID PRODUCERS");
            }

            monthlyUpdates = readMonthlyUpdates(jsonObject);

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }

        /* initialize singleton input database */
        SingletonInput.init(numberOfTurns, consumers, distributors,
                            producers, monthlyUpdates);
    }

    /**
     * Reads the monthly updates from input file
     * @param jsonObject jsonParser
     * @return a list of monthly updates
     */
    public ArrayList<Update> readMonthlyUpdates(final JSONObject jsonObject) {
        ArrayList<Update> monthlyUpdates = new ArrayList<>();

        JSONArray jsonUpdates = (JSONArray)
                jsonObject.get(Constants.MONTHLY_UPDATES);

        if (jsonUpdates != null) {
            for (Object jsonIterator : jsonUpdates) {
                ArrayList<EntityInput> newConsumers = new ArrayList<>();
                ArrayList<DistributorChange> distributorChanges = new ArrayList<>();
                ArrayList<ProducerChange> producerChanges = new ArrayList<>();

                /* create new consumers input */
                JSONArray jsonNewConsumers = (JSONArray) ((JSONObject)
                        jsonIterator).get(Constants.NEW_CONSUMERS);

                if (jsonNewConsumers != null) {
                    for (Object jsonConsumer : jsonNewConsumers) {
                        newConsumers.add(new EntityInput(
                                Constants.CONSUMER,
                                ((Long) ((JSONObject) jsonConsumer).
                                        get(Constants.ID)).intValue(),
                                ((Long) ((JSONObject) jsonConsumer).
                                        get(Constants.INITIAL_BUDGET)).intValue(),
                                ((Long) ((JSONObject) jsonConsumer).
                                        get(Constants.MONTHLY_INCOME)).intValue()
                        ));
                    }
                } else {
                    newConsumers = null;
                    System.out.println("NO NEW CONSUMERS");
                }

                /* create distributor changes input */
                JSONArray jsonDistributorChanges = (JSONArray) ((JSONObject)
                        jsonIterator).get(Constants.DISTRIBUTOR_CHANGES);

                if (jsonDistributorChanges != null) {
                    for (Object jsonChange : jsonDistributorChanges) {
                        distributorChanges.add(new DistributorChange(
                                ((Long) ((JSONObject) jsonChange).
                                        get(Constants.ID)).intValue(),
                                ((Long) ((JSONObject) jsonChange).
                                        get(Constants.INF_COST)).intValue()
                        ));
                    }
                } else {
                    distributorChanges = null;
                    System.out.println("NO DISTRIBUTOR CHANGES");
                }

                /* create producer changes input */
                JSONArray jsonProducerChanges = (JSONArray) ((JSONObject)
                        jsonIterator).get(Constants.PRODUCER_CHANGES);

                if (jsonProducerChanges != null) {
                    for (Object jsonChange : jsonProducerChanges) {
                        producerChanges.add(new ProducerChange(
                                ((Long) ((JSONObject) jsonChange).
                                        get(Constants.ID)).intValue(),
                                ((Long) ((JSONObject) jsonChange).
                                        get(Constants.ENERGY_PER_DISTRIBUTOR)).intValue()
                        ));
                    }
                } else {
                    producerChanges = null;
                    System.out.println("NO PRODUCER CHANGES");
                }

                monthlyUpdates.add(new Update(newConsumers,
                        distributorChanges, producerChanges));
            }
        }
        return monthlyUpdates;
    }
}
