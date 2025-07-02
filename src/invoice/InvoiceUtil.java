package invoice;

import java.util.Scanner;

public class InvoiceUtil {
    private final Scanner scanner;

    public InvoiceUtil(Scanner scanner) {
        this.scanner = scanner;
    }

    public int getCustomerInput (int lowerLimit, int upperLimit) {
        int cusNo = -1;

        cusNo = Utils.getValidInput(cusNo, lowerLimit, upperLimit, scanner, "Enter a Valid Customer Number (" + lowerLimit + " - " + (upperLimit - 1) + ")") - 1;

        scanner.nextLine();

        return cusNo;
    }

    public int getPaymentTermInput () {
        int paymentTerm = -1;

        paymentTerm = Utils.getValidInput(paymentTerm, 0, 90, scanner, "Enter the NET Days _____ (Eg. 15, 30, 45, 90)");

        scanner.nextLine();

        return paymentTerm;
    }

    public double getDiscountInput () {
        char discountOption = 'A';
        discountOption = Utils.getValidOption(discountOption, 'Y', 'N', scanner, "Is discount applied, if Yes -> Y, No -> N");

        double discount = 0;
        if (discountOption == 'Y') {
            System.out.println("Enter the discount percentage(%): ");
            discount = scanner.nextDouble();
        }
        return discount;
    }

    public double getShippingCharges () {
        double shippingCharges = 0;

        char shippingChargeOption = 'A';
        System.out.println("Invoice Utils");
        shippingChargeOption = Utils.getValidOption(shippingChargeOption, 'Y', 'N', scanner, "Is shipping charges applied, if Yes -> Y, No -> N");

        if (shippingChargeOption == 'Y') {
            System.out.println("Enter the shipping charges: ");
            shippingCharges = scanner.nextDouble();
        }

        return shippingCharges;
    }

    public boolean neglectWarning (Invoice invoice, String type) {
        if (invoice.getStatus().equals("CLOSED") || invoice.getStatus().equals("PARTIALLY_PAID")) {
            System.out.println("Since, invoice is closed or partially paid, it's not recommended to " + type + "...");

            char updationOption = 'A';
            updationOption = Utils.getValidOption(updationOption, 'Y', 'N', scanner, "If you still want to " + type + ", Press Y\n If you don't want to update, Press N");

            if (updationOption == 'N') {
                System.out.println("No " + type + " has been done");
                return true;
            }

        }
        return false;
    }

}
