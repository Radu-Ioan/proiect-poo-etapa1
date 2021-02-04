import factory.Result;
import utils.BusinessFlow;
import com.fasterxml.jackson.databind.ObjectMapper;
import input.Input;

import java.io.File;

public final class Main {

    private Main() { }

    /**
     * Punctul de intrare pentru simulare
     * @param args numele fisierelor de intrare si iesire
     * @throws Exception in caz de erori la input/output
     */
    public static void main(final String[] args) throws Exception {
        if (args.length != 2) {
            System.out.println("The expected number of arguments is 2,"
                    + " not " + args.length);
            return;
        }

        ObjectMapper collector = new ObjectMapper();
        Input center = collector.readValue(new File(args[0]), Input.class);
        BusinessFlow game = new BusinessFlow(center.getInitialData(),
                center.getMonthlyUpdates());

        try {
            game.play();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        Result result = new Result(game.getConsumers(), game.getDistributors());

        collector.writerWithDefaultPrettyPrinter().writeValue(new File(args[1]),
                result);
    }
}
