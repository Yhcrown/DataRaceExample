import java.util.ArrayList;
import java.util.List;

public class DataRace {
    static String[] rep;

    public static void request() {
        List<String> arr3 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            arr3.add("Request 3");
        }
        rep = new String[5];
        arr3.toArray(rep);
    }
}
