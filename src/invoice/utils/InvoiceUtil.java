package invoice.utils;

import invoice.handlers.InvoiceHandler;
import invoice.src.Invoice;

public class InvoiceUtil
{

    public InvoiceUtil()
    {
    }

    public int getSerialNumberInput (int lowerLimit, int upperLimit, String fieldName)
    {

        return InputUtils.getValidRange( lowerLimit, upperLimit, fieldName+ " Number", "Enter the " + fieldName.trim() + " Number from the table: ");
    }

    public int getPaymentTermInput ()
    {
        return InputUtils.getValidRange( 0, (int) 1e9, "Invoice's Payment Term", "Enter the Payment Term (Eg. 15, 30, 45, 90): ");
    }

    public double getDiscountInput (boolean isCreation)
    {
        double discount = 0;
        if (isCreation)
        {
            char conformationOption = InputUtils.getToggleInput(  'y', "Discount Option", "Want to Enter discount (y -> yes, any other key -> no)");

            if (conformationOption == 'y')
            {
                discount = getDoubleInput( "Discount");
            }
        } else
        {
            discount = getDoubleInput( "Discount(%)");
        }
        return discount;
    }

    public double getShippingCharges (boolean isCreation)
    {
        double shippingCharges = 0;

        if (isCreation)
        {
            char conformationOption = InputUtils.getToggleInput(  'y', "Shipping Charges Option", "Want to Enter the Shipping Charges (y -> yes, any other key -> no)");

            if (conformationOption == 'y')
            {
                shippingCharges = getDoubleInput("Shipping Charges");
            }
        } else
        {
            shippingCharges = getDoubleInput("Shipping Charges");
        }

        return shippingCharges;
    }

    public double getPaymentInput (double dueAmount)
    {

        return InputUtils.getValidDoubleRange(0, dueAmount,  "Invoice Due Amount", "\nEnter the amount (Due Amount (or) Remaining Amount: " + dueAmount + "):");
    }

    public boolean showWarning(Invoice invoice, String module)
    {
        if (invoice.getStatus().equals(InvoiceHandler.Status.CLOSED) || invoice.getStatus().equals(InvoiceHandler.Status.PARTIALLY_PAID))
        {
            System.out.println("\nSince, invoice is closed or partially paid, it's not recommended to " + module + "...");

            char conformationOption = InputUtils.getToggleInput(   'y', "Invoice updation option",  "Still you want to " + module + " (y -> yes, any other key -> no)");

            if (conformationOption != 'y')
            {
                System.out.println("\nNo " + module + " has been done");
                return true;
            }

        }
        return false;
    }

    private double getDoubleInput (String fieldName)
    {
        return InputUtils.getValidDoubleInput( 0,  fieldName, "Enter the " + fieldName + ":");
    }

}
