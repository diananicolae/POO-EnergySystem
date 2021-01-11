import database.Database;
import io.Parser;
import io.Writer;
import strategies.Process;

/**
 * Entry point for the game.
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

        /* loop through (numberOfTurns + 1) updates */
        for (int updateIndex = 0; updateIndex < database.getNumberOfTurns(); updateIndex++) {
            /* start a new process for each month */
            Process process = new Process(database);

            /* updates start in the second round */
            if (updateIndex > 0) {
                process.processUpdate(updateIndex);
            }

            /* process payments */
            boolean gameStatus = process.processPayments();

            /* determine if the game has ended */
            if (gameStatus) {
                break;
            }
        }

        /* print result to output */
        Writer writer = new Writer(args[1]);
        writer.writeFile(database);
    }
}
