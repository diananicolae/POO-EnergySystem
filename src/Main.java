import database.Database;
import io.Parser;
import io.Writer;
import system.Process;

/**
 * Entry point for the game simulation
 */
public final class Main {
    private Main() {
    }

    /**
     * Process each input test file and write the result file
     *
     * @param args input and output files
     */
    public static void main(final String[] args) throws Exception {
        /* parse input data */
        Parser parser = new Parser(args[0]);
        parser.readData();

        /* initialize singleton database */
        Database.init();
        Database database = Database.getInstance();

        /* start a new process */
        Process process = new Process(database);
        /* process payments for the first month */
        process.processFirstMonthPayments();

        /* loop through the number of rounds */
        for (int monthNumber = 1; monthNumber <= database.getNumberOfTurns(); monthNumber++) {
            /* process payments and determine the game status */
            if (process.processPayments(monthNumber)) {
                break;
            }
        }

        /* print result to output */
        Writer writer = new Writer(args[1]);
        writer.writeFile(database);
    }
}
