package invoice;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GlobalConstants {

    public static String NAME_REGEX;
    public static Set<Character> YES_NO_OPTIONS;
    public static List<String> INVOICE_STATUS;

    public static void initialize () {
        NAME_REGEX = "[a-zA-Z\\s'-]+";
        YES_NO_OPTIONS = new HashSet<>(Arrays.asList('Y', 'N', 'y', 'n'));
        INVOICE_STATUS = Arrays.asList("DRAFT", "SENT", "PARTIALLY_PAID", "CLOSED");
    }

}
