package io;

import database.Database;
import entities.Consumer;
import entities.Producer;
import payment.Contract;
import entities.Distributor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileWriter;
import java.io.IOException;

/**
 * The class prints data to output file using Object Mapper
 */
public final class Writer {
    private final FileWriter file;

    public Writer(final String path) throws IOException {
        this.file = new FileWriter(path);
    }

    /**
     * Transforms content and writes to output file
     *
     * @param database final processed database
     * @throws IOException in case of exceptions to reading / writing
     */
    public void writeFile(final Database database) throws IOException {

        JSONArray jsonConsumers = new JSONArray();
        JSONArray jsonDistributors = new JSONArray();
        JSONArray jsonProducers = new JSONArray();
        JSONObject finalObject = new JSONObject();

        /* create consumer object */
        for (Consumer consumer : database.getConsumers()) {
            JSONObject object = new JSONObject();
            object.put(Constants.ID, consumer.getId());
            object.put(Constants.IS_BANKRUPT, consumer.isBankrupt());
            object.put(Constants.BUDGET, consumer.getBudget());
            jsonConsumers.add(object);
        }

        /* create distributor object */
        for (Distributor distributor : database.getDistributors()) {
            JSONObject object = new JSONObject();
            object.put(Constants.ID, distributor.getId());
            object.put(Constants.ENERGY_NEEDED, distributor.getEnergyNeededKW());
            object.put(Constants.CONTRACT_COST, distributor.getContractCost());
            object.put(Constants.BUDGET, distributor.getBudget());
            object.put(Constants.IS_BANKRUPT, distributor.isBankrupt());

            /* create contracts array */
            JSONArray jsonContracts = new JSONArray();
            for (Contract contract : distributor.getContracts()) {
                JSONObject contractObject = new JSONObject();
                contractObject.put(Constants.CONSUMER_ID, contract.getConsumerId());
                contractObject.put(Constants.PRICE, contract.getPrice());
                contractObject.put(Constants.REM_CONTRACT_MONTHS,
                        contract.getRemainedContractMonths());
                jsonContracts.add(contractObject);
            }
            object.put(Constants.CONTRACTS, jsonContracts);
            jsonDistributors.add(object);
        }

        /* create producer object */
        for (Producer producer : database.getProducers()) {
            JSONObject object = new JSONObject();
            object.put(Constants.ID, producer.getId());
            object.put(Constants.MAX_DISTRIBUTORS, producer.getMaxDistributors());
            object.put(Constants.PRICE_KW, producer.getPriceKW());
            object.put(Constants.ENERGY_TYPE, producer.getEnergyType());
            object.put(Constants.ENERGY_PER_DISTRIBUTOR, producer.getEnergyPerDistributor());

            /* create monthly stats array */
            JSONArray jsonStats = new JSONArray();
            jsonProducers.add(object);
        }

        /* create final object */
        finalObject.put(Constants.CONSUMERS, jsonConsumers);
        finalObject.put(Constants.DISTRIBUTORS, jsonDistributors);
        finalObject.put(Constants.ENERGY_PRODUCERS, jsonProducers);

        ObjectMapper mapper = new ObjectMapper();

        /* write the final object to output file
        * using Object Mapper */
        try {
            file.write(mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(finalObject));
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
