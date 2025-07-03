package invoice.utils;

import invoice.src.Invoice;

import java.util.Scanner;

public class InvoiceUtil {
    private final Scanner scanner;

    public InvoiceUtil(Scanner scanner) {
        this.scanner = scanner;
    }

    public int getCustomerInput (int lowerLimit, int upperLimit) {
        System.out.println("\nEnter the Customer Number from the Table: ");

        int cusNo = 0;
        cusNo = (int) Utils.handleIntegerInputMisMatches(cusNo, scanner);

        cusNo = Utils.getValidInput(cusNo, lowerLimit, upperLimit, scanner, "Customer Number");

        return cusNo;
    }

    public int getPaymentTermInput () {
        System.out.println("\nEnter the Payment Term: ");

        int paymentTerm = -1;
        paymentTerm = (int) Utils.handleIntegerInputMisMatches(paymentTerm, scanner);

        paymentTerm = (int) Utils.getValidInput(paymentTerm, 0.1, 90, scanner, "Invoice's Payment Term");

        return paymentTerm;
    }

    public double getDiscountInput (boolean isCreation) {
        double discount = -1;
        if (isCreation){
            char discountOption = 'A';

            System.out.println("\nWant to Enter discount\nY -> Yes\nN -> No");

            discountOption = Utils.handleOptionStringOutOfBoundError(scanner, discountOption, "Discount Option");

            discountOption = Character.toUpperCase(discountOption);

            String[][] availableOptions = { {"Y", "Yes"}, {"N", "No"} };

            discountOption = Utils.getValidOption(discountOption, 'Y', 'N', scanner, "Discount Option", availableOptions);
            if (discountOption == 'Y') {
                discount = getDoubleInput(100, "Discount");
            }
        } else {
            discount = getDoubleInput(100, "Discount(%)");
        }
        return discount;
    }

    public double getShippingCharges (boolean isCreation) {
        double shippingCharges = 0;

        char shippingChargeOption = 'A';

        if (isCreation) {

            System.out.println("\nWant to Enter the Shipping Charges\nY -> Yes\nN -> No");

            shippingChargeOption = Utils.handleOptionStringOutOfBoundError(scanner, shippingChargeOption, "Shipping Charges Option");

            shippingChargeOption = Character.toUpperCase(shippingChargeOption);

            String[][] availableOptions = { {"Y", "Yes"}, {"N", "No"} };

            shippingChargeOption = Utils.getValidOption(shippingChargeOption, 'Y', 'N', scanner, "Shipping Charges Option", availableOptions);

            if (shippingChargeOption == 'Y') {
                shippingCharges = getDoubleInput(Integer.MAX_VALUE, "Shipping Charges");
            }
        } else {
            shippingCharges = getDoubleInput(Integer.MAX_VALUE, "Shipping Charges");
        }

        return shippingCharges;
    }

    private double getDoubleInput (int upperLimit, String fieldName) {
        System.out.println("\nEnter the " + fieldName + ":");

        double fieldValue = -1;
        fieldValue = Utils.handleIntegerInputMisMatches(fieldValue, scanner);

        fieldValue = Utils.getValidInput(fieldValue, 0, upperLimit, scanner, fieldName);

        return fieldValue;
    }


    public boolean neglectWarning (Invoice invoice, String type) {
        if (invoice.getStatus().equals("CLOSED") || invoice.getStatus().equals("PARTIALLY_PAID")) {
            System.out.println("\nSince, invoice is closed or partially paid, it's not recommended to " + type + "...");

            System.out.println("\nStill you want to delete\nY -> Yes\nN -> No");

            char updationOption = 'A';

            updationOption = Utils.handleOptionStringOutOfBoundError(scanner, updationOption,"Invoice Updation Option");

            updationOption = Character.toUpperCase(updationOption);

            String[][] availableOptions = { {"Y", "Yes"}, {"N", "No"} };

            updationOption = Utils.getValidOption(updationOption, 'Y', 'N', scanner, "Invoice updation option", availableOptions);

            if (updationOption == 'N') {
                System.out.println("\nNo " + type + " has been done");
                return true;
            }

        }
        return false;
    }

}
