package invoice.utils;

public class ValidationUtil
{
    // Validation Methods
    public static char collectToggleChoice(char key, String fieldName, String prompt)
    {
        char inputOption;
        do
        {
            System.out.println("\n" + prompt.trim() + "\n" + "Note: Function will consider any other than " + key + " as secondary Option");

            inputOption = InputUtil.handleCharacterInput(fieldName);


        } while (inputOption == '\0');

        return inputOption;

    }

    // Overloading the Valid function for String Types
    public static String getValidStringInput (String regex, String example, String fieldName, String prompt, boolean isMandatory)
    {
        String value = "";

        System.out.println("\n" + prompt.trim());

        value = InputUtil.handleStringInputMisMatches(value, "");
        while (!value.matches(regex))
        {
            if (!isMandatory && value.isEmpty())
            {
                break;
            }

            System.out.println("\n" + ValidationUtil.regexMatchFailedError(fieldName, example));
            value = InputUtil.handleStringInputMisMatches(value, "");
        }
        return value;
    }

    // Overloading the Valid function for Integer Types
    public static int getValidRange(int lowerLimit, int upperLimit, String fieldName, String prompt)
    {
        int inputOption = 0;

        System.out.println("\n" + prompt.trim());

        inputOption = InputUtil.handleIntegerInputMisMatches(inputOption, lowerLimit - 1);

        while (inputOption < lowerLimit || inputOption > upperLimit)
        {
            System.out.println("\n");

            if (inputOption < 0)
            {
                System.out.println("You have entered a negative number" + ".\n\n" + "The range for " + fieldName + " is " + "(" + lowerLimit + " - " + upperLimit + ")" + ".\n\n" + "Enter the number from the above mentioned range for " + fieldName + ":");
            } else
            {
                System.out.println("You have entered an number that's not exist in the input range for "
                        + fieldName + ".\n\n" + "The range for " + fieldName + " is " + "(" + lowerLimit + " - " + upperLimit + ")" + ".\n\n" + "Enter the number from the above mentioned range for " + fieldName + ":");
            }

            inputOption = InputUtil.handleIntegerInputMisMatches(inputOption, lowerLimit - 1);
        }

        return inputOption;
    }

    // Overloading the Valid function for Double Types
    public static double getValidDoubleInput(double lowerLimit, String fieldName, String prompt)
    {
        double inputValue = 0;

        System.out.println("\n" + prompt.trim());

        inputValue = InputUtil.handleDoubleInput(inputValue, lowerLimit -1);

        while (inputValue < lowerLimit)
        {
            System.out.println("\n" + "You have entered a negative number" + ".\n\n" + "Enter the number a non negative number for " + fieldName + ":");

            inputValue = InputUtil.handleDoubleInput(inputValue, lowerLimit -1);
        }

        if (inputValue == -0)
        {
            inputValue = 0;
        }

        return inputValue;
    }

    public static double getValidDoubleRange (double lowerLimit,  double upperLimit, String fieldName, String prompt)
    {
        double inputValue = 0;

        System.out.println("\n" + prompt.trim());

        inputValue = InputUtil.handleDoubleInput(inputValue, lowerLimit - 1);

        while (inputValue < lowerLimit || inputValue > upperLimit)
        {
            System.out.println("\n");

            if (inputValue < 0)
            {
                System.out.println("You have entered a negative number" + ".\n\n" + "The Amount range for " + fieldName + " is " + "(" + lowerLimit + " - " + upperLimit + ")" + ".\n\n" + "Pay the amount from the above mentioned range for " + fieldName + ":");
            } else {
                System.out.println("You have entered an number exceeds the Amount to be paid for "
                        + fieldName + ".\n\n" + "The Amount range for " + fieldName + " is " + "(" + lowerLimit + " - " + upperLimit + ")" + ".\n\n" + "Pay the amount from the above mentioned range for " + fieldName + ":");
            }

            inputValue = InputUtil.handleDoubleInput(inputValue, lowerLimit - 1);
        }

        return inputValue;
    }

    // Error Message for Improper regex
    public static String regexMatchFailedError (String fieldName, String example)
    {
        return "Your " + fieldName + " didn't match with the validation regex.\n\n" + "Example: " + example + "\n\nEnter the input again, refer the above one:";
    }
}
