package invoice.utils;

import invoice.GlobalConstants;
import invoice.src.Invoice;

import java.util.List;
import java.util.Scanner;

public class InvoiceUtil
{
    private final Scanner scanner;

    public InvoiceUtil(Scanner scanner)
    {
        this.scanner = scanner;
    }

    public int getSerialNumberInput (int lowerLimit, int upperLimit, String fieldName) {

        return Utils.getValidRange( lowerLimit, upperLimit, scanner, fieldName+ " Number", "Enter the " + fieldName + " Number from the table: ");
    }

    public int getPaymentTermInput ()
    {
        return Utils.getValidRange( 0, 90, scanner, "Invoice's Payment Term", "Enter the Payment Term (Eg. 15, 30, 45, 90): ");
    }

    public double getDiscountInput (boolean isCreation) {
        double discount = 0;
        if (isCreation){
            char yesOrNo = Utils.getValidOption(GlobalConstants.YES_NO_OPTIONS, scanner, "Discount Option", "Want to Enter discount\nY -> Yes\nN -> No");
            if (yesOrNo == 'Y' || yesOrNo == 'y') {
                discount = getDoubleInput(100, "Discount");
            }
        } else {
            discount = getDoubleInput(100, "Discount(%)");
        }
        return discount;
    }

    public double getShippingCharges (boolean isCreation) {
        double shippingCharges = 0;

        if (isCreation) {
            char yesOrNo = Utils.getValidOption(GlobalConstants.YES_NO_OPTIONS, scanner, "Shipping Charges Option", "Want to Enter the Shipping Charges\nY -> Yes\nN -> No");

            if (yesOrNo == 'Y'|| yesOrNo == 'y') {
                shippingCharges = getDoubleInput(Integer.MAX_VALUE, "Shipping Charges");
            }
        } else {
            shippingCharges = getDoubleInput(Integer.MAX_VALUE, "Shipping Charges");
        }

        return shippingCharges;
    }

   public double haveReceivedPayment (double dueAmount)
   {
        char yesOrNo = Utils.getValidOption(GlobalConstants.YES_NO_OPTIONS, scanner, "Shipping Charges Option", "Have you received payment\nY -> yes\nN -> No");

        double paidAmount = 0;

        if (yesOrNo == 'Y' || yesOrNo == 'y') {

            paidAmount = getPaymentInput(dueAmount);

        }

        return paidAmount;
    }

    public double getPaymentInput (double dueAmount) {
        System.out.println("Enter the amount (Max Limit: " + dueAmount + " ):");

        return Utils.getValidDoubleInput( 0, scanner, "Amount", "\nEnter the amount:");

    }

    public void reArrangeInvoiceList (List<Invoice> invoices) {

        for (int i = 0; i < invoices.size(); i++) {
            Invoice invoice = invoices.get(i);
            if (i + 1 != invoice.getInvNo()) {
                invoice.setInvNo(i + 1);
            }
        }
    }

    private double getDoubleInput (int upperLimit, String fieldName)
    {
        return Utils.getValidDoubleInput( 0, scanner, fieldName, "Enter the " + fieldName + ":");
    }


    public boolean neglectWarning (Invoice invoice, String module)
    {
        if (invoice.getStatus().equals("CLOSED") || invoice.getStatus().equals("PARTIALLY_PAID")) {
            System.out.println("\nSince, invoice is closed or partially paid, it's not recommended to " + module + "...");

            char yesOrNo = Utils.getValidOption( GlobalConstants.YES_NO_OPTIONS, scanner, "Invoice updation option",  "Still you want to \" + module + \"\\nY -> Yes\\nN -> No");

            if (yesOrNo == 'N' || yesOrNo == 'n') {
                System.out.println("\nNo " + module + " has been done");
                return true;
            }

        }
        return false;
    }

}
