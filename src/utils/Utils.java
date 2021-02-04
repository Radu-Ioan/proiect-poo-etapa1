package utils;
import factory.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import input.Input;

import java.io.File;

public final class Utils {
    private static final String RESULT_FILE = "my_results\\basic_";
    private static final String INPUT_FILE = "checker\\resources\\in\\basic_";
    private static final int NO_TESTS = 15;

    private Utils() { }
    /**
     * Metoda proprie care pastreaza propriile rezultate pentru a le compara
     * cu cele din ref
     * NU ARE TREABA CU REZOLVAREA TEMEI, ci e pusa doar pentru debug
     */
    public static void main(final String[] args) throws Exception {

        ObjectMapper collector = new ObjectMapper();
        Input center;
        File input, output;

        for (int i = 1; i <= NO_TESTS; i++) {
            input = new File(INPUT_FILE + i + ".json");
            output = new File(RESULT_FILE + i + ".json");
            center = collector.readValue(input, Input.class);

            BusinessFlow game = new BusinessFlow(center.getInitialData(),
                    center.getMonthlyUpdates());
            game.play();

            Result result = new Result(game.getConsumers(),
                    game.getDistributors());
            collector.writerWithDefaultPrettyPrinter().writeValue(output,
                    result);
        }
    }
}
