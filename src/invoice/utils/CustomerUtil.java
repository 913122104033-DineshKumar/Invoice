package invoice.utils;

import invoice.src.Customer;
import java.util.*;

public class CustomerUtil
{

    public String getNameInput()
    {
        final String NAME_REGEX = "[a-zA-Z\\s'-]+";

        return InputUtils.getValidStringInput(NAME_REGEX, "Dinesh Kumar K K", "Customer Name Input", "Enter the Name (Eg. Dinesh Kumar K K):", true);
    }

    public char getCustomerTypeInput()
    {
        return InputUtils.getToggleInput( 'i', "Customer Type Input", "Enter the Customer Type (i -> Individual, any other key will be considered as -> Business):");
    }

    public String getCompanyNameInput()
    {
        final String COMPANY_REGEX = "[a-zA-Z0-9\\s'-]+";

        return InputUtils.getValidStringInput(COMPANY_REGEX, "Zoho Corp", "Company Name", "Enter the Company Name (Eg. Zoho Corp):", true);
    }

    public String getEmailInput(List<Customer> customers)
    {
        final String EMAIL_REGEX = "[a-zA-Z][a-zA-Z0-9._%+-]+@[a-z][a-zA-Z0-9-]+.[a-zA-Z]{2,}";

        String email = InputUtils.getValidStringInput(EMAIL_REGEX, "abc@gmail.com", "Customer Email", "Enter the Email (Eg. abc@gmail.com):", true);

        while (emailAlreadyExists(email, customers) || !email.matches(EMAIL_REGEX))
        {
            if (emailAlreadyExists(email, customers)) {
                System.out.println("Email already exists, try with any email");
            } else {
                System.out.println(InputUtils.regexMatchFailedError("Email" , "abc@gmal.com"));
            }
            email = InputUtils.handleStringInputMisMatches(email, "");
        }

        return email;
    }

    public String getPhoneInput()
    {
        final String PHONE_REGEX = "\\d{10}";

        return InputUtils.getValidStringInput(PHONE_REGEX, "1234567890", "Customer Phone Number", "Enter the Phone Number (Eg. 1234567890):", true);
    }

    private boolean emailAlreadyExists (String email, List<Customer> customers)
    {
        for (Customer customer : customers)
        {
            if (customer.getEmail().equals(email))
            {
                return true;
            }
        }
        return false;
    }

}
