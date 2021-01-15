import database.Database;
import io.Parser;
import io.Writer;
import system.Process;

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
//        Parser parser = new Parser("checker/resources/in/basic_12.json");
        parser.readData();

        /* initialize singleton database */
        Database.init();
        Database database = Database.getInstance();
        /* start a new process */
        Process process = new Process(database);
        /* process payments for the first month */
        process.processFirstMonthPayments();
        /* loop through monthly updates */
        for (int monthNumber = 1; monthNumber < database.getNumberOfTurns(); monthNumber++) {
            /* process payments */
            boolean gameStatus = process.processPayments(monthNumber);

            /* determine if the game has ended */
            if (gameStatus) {
                break;
            }
        }

        /* print result to output */
        Writer writer = new Writer(args[1]);
//        Writer writer = new Writer("result.out");
        writer.writeFile(database);
        Writer writer1 = new Writer("result/out_" + args[0].substring(66));
        writer1.writeFile(database);
    }
}
