package invoice.utils;

public class ErrorUtils {

    public static String optionError(String fieldName, String[][] availableOptions) {
        StringBuilder sb = new StringBuilder();

        sb.append("You have entered Wrong Input in ").append(fieldName).append(", try with the following").append("\n\n").append("The Options are").append("\n");

        for (String[] availableOption : availableOptions) {
            sb.append(availableOption[0]).append(", ").append(availableOption[0].toLowerCase()).append(" -> ").append(availableOption[1]).append("\n");
        }

        sb.append("\n").append("Enter the Option again, refer the above options:");

        return sb.toString().trim();
    }

    public static String regexMatchFailedError(String fieldName, String example) {
        return "Your " + fieldName + " didn't match with the validation regex.\n\n" + "Example: " + example + "\n\nEnter the input again, refer the above one:";
    }

    public static String rangeOutOfBoundError(String fieldName, double input, double lowerLimit, double upperLimit) {
        if (input < 0) {
            return "You have entered a negative number" + ".\n\n" + "The range for " + fieldName + " is " + "(" + lowerLimit + " - " + upperLimit + ")" + ".\n\n" + "Enter the number from the above mentioned range for " + fieldName + ":";
        }
        return "You have entered an number that's not exist in the input range for "
                + fieldName + ".\n\n" + "The range for " + fieldName + " is " + "(" + lowerLimit + " - " + upperLimit + ")" + ".\n\n" + "Enter the number from the above mentioned range for " + fieldName + ":";
    }

}
