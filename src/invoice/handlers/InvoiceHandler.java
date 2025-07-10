package invoice.handlers;

import invoice.interfaces.ComparatorCallBack;
import invoice.models.Address;
import invoice.models.Customer;
import invoice.models.Invoice;
import invoice.models.Item;
import invoice.utils.*;

import java.time.LocalDate;
import java.util.*;

public class InvoiceHandler
{

    public enum Status {
        DRAFT,
        SENT,
        PARTIALLY_PAID,
        CLOSED
    }

    private final InvoiceUtil invoiceUtil;

    public InvoiceHandler() {
        this.invoiceUtil = new InvoiceUtil();
    }

    // Used in Main

    public Invoice create(List<Item> items, List<Customer> customers) {
        System.out.println("\nYou can add Invoice now...");

        boolean customerCreated = false;

        int cusNo = 0;

        if (customers.isEmpty()) {
            Customer customer = new CustomerHandler().create(customers);

            customers.add(customer);

            System.out.println("\nCustomer has been created and now can create Invoice\n");

            cusNo = customers.size() - 1;

            customerCreated = true;
        }

        if (!customerCreated) {
            cusNo = InputUtil.getCustomerNumber(customers);
        }

        Customer cus = customers.get(cusNo);

        int paymentTerm = invoiceUtil.getPaymentTermInput();

        Map<Item, Integer> itemTable = new TreeMap<>((item1, item2) -> Integer.compare(item1.getItemNo(), item2.getItemNo()));
        double subTotal = itemTableOperations(cus, null, items, itemTable, false);

        if (items.isEmpty()) {
            return null;
        }

        Invoice invoice = new Invoice(cus, paymentTerm, itemTable, subTotal);

        double discount = invoiceUtil.getDiscountInput(true);

        invoice.setDiscount(discount);

        double invTotal = subTotal - ((subTotal / 100) * discount);

        double shippingCharges = invoiceUtil.getShippingCharges(true);

        invoice.setShippingCharges(shippingCharges);

        invTotal += shippingCharges;

        double paymentReceived = 0;

        char conformationOption = ValidationUtil.collectToggleChoice( 'y', "Payment Receival", "Have you received payment (y -> yes, any other key -> no)");

        if (conformationOption == 'y') {
            paymentReceived = invoiceUtil.getPaymentInput(invTotal);
        }

        Status status;

        if (paymentReceived == invTotal) {
            status = Status.CLOSED;
        } else if (paymentReceived > 0) {
            status = Status.PARTIALLY_PAID;
        } else {
            char invoiceSentConfirmation = ValidationUtil.collectToggleChoice(  'y', "Invoice Sent", "Have you sent the invoice to customer? (y -> yes, any other key -> no)");

            if (invoiceSentConfirmation == 'Y' || invoiceSentConfirmation == 'y') {
                status = Status.SENT;
            } else {
                status = Status.DRAFT;
            }

        }

        invoice.setDueAmount(invTotal - paymentReceived);

        invoice.setStatus(status);

        return invoice;
    }

    public void update(Invoice invoice, List<Item> items, List<Customer> customers) {

        if (showWarning(invoice, "update")) {
            return;
        }

        int option = -1;

        invoiceUpdationMenu:
        {
            while (true) {
                System.out.println("\nOption 1 -> (Item Table, Payment Term, Customer) Modifications");
                System.out.println("Option 2 -> (Discount, Shipping Charges) Modifications");
                System.out.println("Option 3 -> Exit the Update Module");

                System.out.println("\nEnter Option: ");
                option = InputUtil.handleIntegerInputMisMatches(option, -1);

                if (showWarning(invoice, "update")) {
                    return;
                }

                switch (option) {
                    case 1: {

                        modifyInvoicePrimaryDetails(invoice, items, customers);
                        break;
                    }

                    case 2: {
                        modifyInvoiceOtherCharges(invoice);
                        break;
                    }

                    case 3: {
                        System.out.println("\nExiting the Invoice Updation Module...");
                        break invoiceUpdationMenu;
                    }

                    default: {
                        System.out.println("Enter a Valid input (1 - 3)");
                        break;
                    }
                }
            }
        }

        System.out.println("\nUpdated Invoice Number: " + invoice.getInvNo());
    }

    public List<Invoice> searchByCustomer(String customerName, List<Invoice> invoices, double limit)
    {
        List<Invoice> invoicesForCustomer = new ArrayList<>();
        for (Invoice invoice : invoices) {
            if (invoice.getCustomer().getName().equalsIgnoreCase(customerName) && invoice.getDueAmount() > limit) {
                invoicesForCustomer.add(invoice);
            }
        }

        return invoicesForCustomer;
    }

    public void deleteInvoice (List<Invoice> invoices)
    {
        int deleteInvoiceNo = InputUtil.getInvoiceNumber(invoices);

        Invoice selectedInvoice = invoices.get(deleteInvoiceNo);

        if (showWarning(invoices.get(deleteInvoiceNo), "delete")) {
            return;
        }

        System.out.println("Invoice of the " + selectedInvoice.getCustomer().getName() + " with Inv No " + selectedInvoice.getInvNo() + " is deleted");

        invoices.remove(deleteInvoiceNo);
    }

    public void paymentModule(List<Invoice> invoices, List<Customer> customers) {
        int optionForPayment = -1;


        invoicePaymentMenu:
        {
            while (true) {
                System.out.println("\nPayment Options");

                System.out.println("\nOption 1 -> Pay for Particular Invoice");
                System.out.println("\nOption 2 -> Pay for Due Amount remaining for the particular Customer");
                System.out.println("\nOption 3 -> Pay Due Amount for Particular range of Date: ");
                System.out.println("\nOption 4 -> Exiting the Payment Module");

                System.out.println("\nHow you want to pay: ");
                optionForPayment = InputUtil.handleIntegerInputMisMatches(optionForPayment, -1);

                switch (optionForPayment) {
                    case 1: {
                        int invNo = InputUtil.getInvoiceNumber(invoices);

                        Invoice paymentInvoice = invoices.get(invNo);

                        payInvoice(paymentInvoice);

                        break;
                    }

                    case 2: {
                        int cusNo = InputUtil.getCustomerNumber(customers);

                        List<Invoice> customerInvoices = searchByCustomer(customers.get(cusNo).getName(), invoices, 0);

                        payOutstandingInvoices(customerInvoices);

                        break;
                    }
                    case 3: {
                        int cusNo = InputUtil.getCustomerNumber(customers);

                        List<Invoice> customerInvoices = searchByCustomer(customers.get(cusNo).getName(), invoices, 0);

                        List<Invoice> inRangeInvoices = new ArrayList<>();

                        LocalDate startDate = parseDateFromInput("Start Date");
                        LocalDate endDate = parseDateFromInput("End Date");

                        while (ComparisonUtil.compareDates(startDate, endDate) == -1)
                        {
                            System.out.println("\nStart date should be before the end date, you entered wrongly");
                            startDate = parseDateFromInput("Start Date");
                            endDate = parseDateFromInput("End Date");
                        }

                        for (Invoice invoice : customerInvoices) {
                            LocalDate invoiceDate = invoice.getCreatedAt();

                            if (ComparisonUtil.compareDates(startDate, invoiceDate) == 1
                                    && ComparisonUtil.compareDates(invoiceDate, endDate) == 1) {
                                inRangeInvoices.add(invoice);
                            }
                        }

                        if (inRangeInvoices.isEmpty())
                        {
                            System.out.println("\nThere is no invoices in this range for the Customer");
                        } else {
                            payOutstandingInvoices(inRangeInvoices);
                        }

                        break;
                    }
                    case 4: {
                        System.out.println("\nExiting the Payment Module");
                        break invoicePaymentMenu;
                    }
                    default: {
                        System.out.println("\nEnter a valid input (1 - 3): ");
                        break;
                    }
                }
            }
        }
    }

    public void sortingModule(List<Invoice> invoices) {
        SortingUtil sortingUtil = new SortingUtil();

        System.out.println("\nEnter the choice based in which you want to sort the Invoices: ");

        int sortBy = -1;

        sortModule:
        {
            while (true) {
                System.out.println("\nOption 1 -> Sort by Invoice Number");
                System.out.println("Option 2 -> Sort by Date");
                System.out.println("Option 3 -> Sort by Due Amount");
                System.out.println("Option 4 -> Exit the Sorting Module");

                System.out.println("\nEnter the Sort by Option: ");

                sortBy = InputUtil.handleIntegerInputMisMatches(sortBy, -1);

                int sortingOrder = -1;

                if (sortBy >= 1 && sortBy <= 3) {
                    orderInput:
                    {
                        while (true) {
                            System.out.println("\nOption 1 -> Ascending Order");
                            System.out.println("Option 2 -> Descending Order");

                            System.out.println("\nEnter the Sorting Order Option: ");

                            sortingOrder = InputUtil.handleIntegerInputMisMatches(sortingOrder, -1);

                            switch (sortingOrder) {
                                case 1, 2: {
                                    break orderInput;
                                }
                                default: {
                                    System.out.println("\nEnter a valid input (1 - 2)");
                                    break;
                                }
                            }
                        }

                    }
                }

                int finalSortingOrder = sortingOrder;

                switch (sortBy) {
                    case 1: {

                        sortingUtil.mergeSort(0, invoices.size() - 1, invoices, new ComparatorCallBack() {
                            @Override
                            public <T> int comparator(T obj1, T obj2) {
                                Invoice inv1 = (Invoice) obj1;
                                Invoice inv2 = (Invoice) obj2;
                                return finalSortingOrder == 1 ? ComparisonUtil.compareIntegers(inv1.getInvNo(), inv2.getInvNo()) : ComparisonUtil.compareIntegers(inv2.getInvNo(), inv1.getInvNo());
                            }
                        });

                        break;
                    }
                    case 2: {

                        sortingUtil.mergeSort(0, invoices.size() - 1, invoices, new ComparatorCallBack() {
                            @Override
                            public <T> int comparator(T obj1, T obj2) {
                                Invoice inv1 = (Invoice) obj1;
                                Invoice inv2 = (Invoice) obj2;
                                return finalSortingOrder == 1 ? ComparisonUtil.compareDates(inv1.getCreatedAt(), inv2.getCreatedAt()) : ComparisonUtil.compareDates(inv2.getCreatedAt(), inv1.getCreatedAt());
                            }
                        });

                        break;
                    }
                    case 3: {

                        sortingUtil.mergeSort(0, invoices.size() - 1, invoices, new ComparatorCallBack() {
                            @Override
                            public <T> int comparator(T obj1, T obj2) {
                                Invoice inv1 = (Invoice) obj1;
                                Invoice inv2 = (Invoice) obj2;
                                return finalSortingOrder == 1 ? ComparisonUtil.compareDoubles(inv1.getDueAmount(), inv2.getDueAmount()) : ComparisonUtil.compareDoubles(inv2.getDueAmount(), inv1.getDueAmount());
                            }
                        });

                        break;
                    }
                    case 4: {
                        System.out.println("\nExiting sorting module...");
                        break sortModule;
                    }
                    default: {
                        System.out.println("\nEnter a valid option (1 - 4)");
                        break;
                    }
                }

                DisplayUtil.showInvoices(invoices);

            }
        }

    }

    public void payInvoice(Invoice invoice) {

        System.out.println("\n");

        invoice.showInvoice();

        System.out.println("\nOutstanding Amount: Rs." + invoice.getDueAmount());
        System.out.println("Note: To cancel payment, Enter 0");

        double paymentAmount = invoiceUtil.getPaymentInput(invoice.getDueAmount());

        if (paymentAmount > 0) {
            System.out.println("\nPayment Recorded: Rs." + paymentAmount);

            invoice.setDueAmount(invoice.getDueAmount() - paymentAmount);

            updateInvoiceStatusAfterPayment(invoice);

            System.out.println("\n--- Updated Invoice---");

            invoice.showInvoice();
        } else {
            System.out.println("\nNo payment recorded - Transaction cancelled and status remains in " + invoice.getStatus());
        }
    }

    public void updateStatus (List<Invoice> invoices)
    {
        int invNo = InputUtil.getInvoiceNumber(invoices);

        Invoice invoice = invoices.get(invNo);

        System.out.println("\nThe invoice you chosen\n");

        invoice.showInvoice();

        int previousStatusOrdinal = invoice.getStatus().ordinal();

        int statusOrdinal;


        // To get a proper Status Code
        status:
        while (true) {
            statusOrdinal = ValidationUtil.getValidRange(1, 4, "Invoice Status", "Enter the Status No:\n" + showStatuses()) - 1;

            if (previousStatusOrdinal == statusOrdinal) {
                System.out.println("\nThis invoice is already " + switch (statusOrdinal) {
                    case 0 -> InvoiceHandler.Status.DRAFT;
                    case 1 -> InvoiceHandler.Status.SENT;
                    case 2 -> InvoiceHandler.Status.PARTIALLY_PAID;
                    default -> InvoiceHandler.Status.CLOSED;
                });
                continue status;
            }
            break;
        }

        // Demotion of the statuses
        if (previousStatusOrdinal > statusOrdinal) {

            if (previousStatusOrdinal > InvoiceHandler.Status.SENT.ordinal())
            {
                char confirmationOption = ValidationUtil.collectToggleChoice(   'y',"Status Demotion", "You are trying to demote the Status" + "\nDo you want to continue this process, then if you had paid the amount for this invoice will be refunded and the due will increase by " + (invoice.getTotal() - invoice.getDueAmount()) + " (y -> yes, any other key -> no)");

                if (confirmationOption == 'y')
                {
                    System.out.println("\nPaid amount " + (invoice.getTotal() - invoice.getDueAmount()) + " is refunded");

                    invoice.setDueAmount(invoice.getTotal());

                    invoice.setStatus(InvoiceHandler.Status.SENT);

                    System.out.println("\nStatus is changed from " + InvoiceHandler.Status.values()[previousStatusOrdinal] + " to " + InvoiceHandler.Status.SENT);
                } else
                {
                    System.out.println("\nStatus is not updated");
                }
            } else
            {
                char confirmationOption = ValidationUtil.collectToggleChoice(  'y',"Status Demotion", "Do you want the Status from SENT to DRAFT (y -> yes, any other key -> no)");

                if (confirmationOption == 'y')
                {
                    System.out.println("\nStatus changed from SENT to DRAFT\n");

                    invoice.setStatus(InvoiceHandler.Status.DRAFT);
                }
            }
            return;
        }

        // Actual status Changing
        if (statusOrdinal == InvoiceHandler.Status.SENT.ordinal())
        {
            invoice.setStatus(InvoiceHandler.Status.SENT);

            System.out.println("\nINVOICE #" + invoice.getInvNo() + " Status updated from " + InvoiceHandler.Status.values()[previousStatusOrdinal] + " to " + invoice.getStatus());
            return;
        }

        payInvoice(invoice);
    }

    // Internal use

    private void payOutstandingInvoices(List<Invoice> customerInvoices) {
        double totalDueAmount = 0;

        for (Invoice invoice : customerInvoices) {
            totalDueAmount += invoice.getDueAmount();
        }

        System.out.println("\n--- CUSTOMER'S OUTSTANDING INVOICES ---");

        DisplayUtil.showInvoices(customerInvoices);

        System.out.println("\n TOTAL AMOUNT DUE: Rs." + String.format("%.2f", totalDueAmount));
        System.out.println("\nYou can pay the full amount or partial amount.Then you can distribute the amount for outstanding invoices");

        double totalAmountPaid = invoiceUtil.getPaymentInput(totalDueAmount);

        if (totalAmountPaid == 0) {
            System.out.println("\nPayment cancelled. No invoices updated.");
            return;
        }

        payInvoiceBalances:
        {
            while (totalAmountPaid > 0)
            {
                for (Invoice invoice : customerInvoices)
                {
                    if (invoice.getDueAmount() == 0)
                    {
                        continue;
                    }

                    System.out.println("\nINVOICE #" + invoice.getInvNo());
                    System.out.println("Due Amount: Rs." + String.format("%.2f", invoice.getDueAmount()));
                    System.out.println("Remaining Payment: Rs." + String.format("%.2f", totalAmountPaid));

                    double amountPaidForThisInvoice = 0;

                    if (invoice.getDueAmount() == 0)
                    {
                        System.out.println("\nINVOICE #" + invoice.getInvNo() + " is already paid");
                        continue;
                    }

                    char fullPaymentConfirmation = isFullyPaid();

                    boolean canPayFully = (fullPaymentConfirmation == 'Y' || fullPaymentConfirmation == 'y') && totalAmountPaid >= invoice.getDueAmount();

                    if (canPayFully) {
                        amountPaidForThisInvoice = invoice.getDueAmount();

                        invoice.setStatus(Status.CLOSED); // Closed Status

                        invoice.setDueAmount(0);

                        System.out.println("\n Invoice #" + invoice.getInvNo() + " marked as FULLY PAID");
                    } else
                    {
                        if (fullPaymentConfirmation == 'Y' || fullPaymentConfirmation == 'y')
                        {
                            System.out.println("\nInsufficient funds! Available: Rs." + String.format("%.2f", totalAmountPaid));
                        }

                        amountPaidForThisInvoice = invoiceUtil.getPaymentInput(Math.min(invoice.getDueAmount(), totalAmountPaid));

                        if (amountPaidForThisInvoice > 0)
                        {
                            System.out.println("\nApplying Rs." + String.format("%.2f", amountPaidForThisInvoice) +
                                    " to Invoice #" + invoice.getInvNo());

                            invoice.setDueAmount(invoice.getDueAmount() - amountPaidForThisInvoice);

                            updateInvoiceStatusAfterPayment(invoice);
                        } else {
                            System.out.println("️\n No payment applied to Invoice #" + invoice.getInvNo());
                        }
                    }

                    if (amountPaidForThisInvoice > 0) {
                        System.out.println("\nUpdated Invoice Details");
                        invoice.showInvoice();
                    }

                    totalAmountPaid -= amountPaidForThisInvoice;
                    totalDueAmount -= amountPaidForThisInvoice;

                    System.out.println("\n Payment Summary:");
                    System.out.println("Amount used for this invoice: Rs." + String.format("%.2f", amountPaidForThisInvoice));
                    System.out.println("Remaining payment balance: Rs." + String.format("%.2f", totalAmountPaid));

                    if (totalAmountPaid == 0) {
                        System.out.println("\nAll payment has been distributed!");
                        break payInvoiceBalances;
                    }

                }

                if (totalAmountPaid > 0) {
                    System.out.println("\nStarting next round of payment distribution, because you have remaining paid amount to be paid...");
                    System.out.println("Remaining amount to distribute: Rs." + String.format("%.2f", totalAmountPaid));
                }

            }
        }

    }

    private char isFullyPaid() {
        return ValidationUtil.collectToggleChoice( 'y', "Amount", "\nWant to pay the full amount (y -> yes, any other key -> no)");

    }

    private void updateInvoiceStatusAfterPayment(Invoice invoice) {

        if (invoice.getDueAmount() > 0) {
            invoice.setStatus(Status.PARTIALLY_PAID);
            System.out.println("\n✓ Invoice marked as PARTIALLY PAID");
        } else {
            invoice.setStatus(Status.CLOSED);
            System.out.println("\n✓ Invoice marked as CLOSED (Fully Paid)");
        }
    }

    private void modifyInvoicePrimaryDetails(Invoice invoice, List<Item> items, List<Customer> customers) {
        int option = -1;

        invoicePrimaryDetailsMenu:
        {
            while (true) {
                System.out.println("\nOption 1 -> Update Item Table");
                System.out.println("Option 2 -> Update Payment Term");
                System.out.println("Option 3 -> Update Customer");
                System.out.println("Option 4 -> Exit the Invoice Primary Details Modification Module");

                System.out.println("\nEnter Option: ");
                option = InputUtil.handleIntegerInputMisMatches(option, -1);

                if (showWarning(invoice, "update")) {
                    return;
                }

                switch (option) {
                    case 1: {
                        Invoice previousInvoice = cloneInvoice(invoice);

                        Map<Item, Integer> previousItemTable = previousInvoice.getItemTable();

                        Map<Item, Integer> currentItemTable = invoice.getItemTable();

                        itemTableOperations(invoice.getCustomer(), invoice, items, invoice.getItemTable(), true);

                        System.out.println("\nPrevious Item Table\n");
                        showItemDetails(previousItemTable);
                        System.out.println("\nPrevious Invoice");
                        previousInvoice.showInvoice();


                        System.out.println("\nUpdated Item Table\n");
                        showItemDetails(currentItemTable);
                        System.out.println("\nUpdated Invoice");
                        invoice.showInvoice();

                        break;
                    }

                    case 2: {
                        int previousPaymentTerm = invoice.getPaymentTerm();

                        int paymentTerm = invoiceUtil.getPaymentTermInput();

                        if (previousPaymentTerm == paymentTerm)
                        {
                            System.out.println("\nYou entered the same payment term again, so no updation is done");
                            break;
                        }

                        invoice.setPaymentTerm(paymentTerm);

                        System.out.println("\nInvoice's Payment Term updated from " + previousPaymentTerm + " to " + paymentTerm);
                        invoice.showInvoice();

                        break;
                    }

                    case 3: {
                        String previousCustomerName = invoice.getCustomer().getName();

                        int cusNo = 0;

                        boolean customerCreated = false;

                        if (customers.isEmpty()) {
                            Customer customer = new CustomerHandler().create(customers);

                            customers.add(customer);

                            System.out.println("\nCustomer has been created and now can create Invoice\n");

                            cusNo = customers.size() - 1;

                            customerCreated = true;
                        }

                        if (!customerCreated) {
                            DisplayUtil.showCustomers(customers);

                            cusNo = invoiceUtil.getSerialNumberInput(1, customers.size(), "Customer") - 1;
                        }

                        if (previousCustomerName.equalsIgnoreCase(customers.get(cusNo).getName()))
                        {
                            System.out.println("\nYou selected the same Customer again, so no updation is done");
                            break;
                        }

                        invoice.setCustomer(customers.get(cusNo));

                        if (previousCustomerName.equalsIgnoreCase(invoice.getCustomer().getName()))
                        {
                            System.out.println("\nYou assigned the invoice to the same customer " + previousCustomerName + " again");
                        } else
                        {
                            System.out.println("\nInvoice's Customer updated from " + previousCustomerName + " to " + customers.get(cusNo).getName());
                        }
                        invoice.showInvoice();

                        break;
                    }

                    case 4: {
                        System.out.println("\nExiting the Invoice Primary Details Updation Module...");
                        break invoicePrimaryDetailsMenu;
                    }

                    default: {
                        System.out.println("\nEnter a Valid input (1 - 4)");
                        break;
                    }
                }
            }
        }

    }

    private void modifyInvoiceOtherCharges(Invoice invoice) {
        int option = -1;

        invoiceOtherChargesMenu:
        {
            while (true) {
                System.out.println("\nOption 1 -> Update Discount");
                System.out.println("Option 2 -> Update Shipping Charges");
                System.out.println("Option 3 -> Exit the Invoice Other Charges Module");

                System.out.println("\nEnter Option: ");
                option = InputUtil.handleIntegerInputMisMatches(option, -1);

                if (showWarning(invoice, "update")) {
                    return;
                }

                switch (option) {
                    case 1: {

                        double previousDiscount = invoice.getDiscount();

                        double nDiscount = invoiceUtil.getDiscountInput(false);

                        invoice.setDiscount(nDiscount);

                        double total = invoice.getTotal();

                        invoice.setTotal(invoice.getSubTotal() - ((invoice.getSubTotal() / 100) * nDiscount) + invoice.getShippingCharges());

                        adjustDueAmount(total, invoice);

                        System.out.println("\nInvoice's Discount updated from " + previousDiscount + " to " + nDiscount);
                        System.out.println("\n---Updated Invoice---\n");

                        invoice.showInvoice();

                        break;
                    }

                    case 2: {

                        double previousShippingCharges = invoice.getShippingCharges();

                        double shippingCharges = invoiceUtil.getShippingCharges(false);

                        double total = invoice.getTotal();

                        invoice.setShippingCharges(shippingCharges);

                        invoice.setTotal(invoice.getTotal() - previousShippingCharges + shippingCharges);

                        adjustDueAmount(total, invoice);

                        System.out.println("\nInvoice's Shipping Charges updated from " + previousShippingCharges + " to " + shippingCharges);
                        System.out.println("\n---Updated Invoice---\n");

                        invoice.showInvoice();

                        break;
                    }

                    case 3: {
                        System.out.println("\nExiting the Invoice Other Charges Updation Module...");
                        break invoiceOtherChargesMenu;
                    }

                    default: {
                        System.out.println("\nEnter a Valid input (1 - 3)");
                        break;
                    }
                }
            }
        }
    }

    private double itemTableOperations(Customer customer, Invoice invoice, List<Item> items, Map<Item, Integer> itemTable, boolean isUpdation) {
        int option = -1;

        ItemHandler itemHandler = new ItemHandler();

        itemTableUpdation:
        {
            while (true) {

                System.out.println("\nOption 1 -> Add Items to the list");
                if (!itemTable.isEmpty()) {
                    System.out.println("Option 2 -> Remove an Item from the list");
                    System.out.println("Option 3 -> Adjust the Items in the list");

                } else {
                    System.out.println("No Items are added yet");
                }
                System.out.println("Option 4 -> Quit");

                System.out.println("\nEnter the Option: ");
                option = InputUtil.handleIntegerInputMisMatches(option, -1);

                switch (option) {

                    case 1: {
                        int itemNo = 0;

                        boolean itemCreated = false;

                        if (items.isEmpty()) {
                            Item item = itemHandler.create( items);

                            items.add(item);

                            System.out.println("\nItem has been created.\nNow you can add quantity for the item");

                            itemNo = items.size() - 1;

                            itemCreated = true;
                        }

                        if (!itemCreated) {
                            itemNo = InputUtil.getItemNumber(items);
                        }

                        int quantity = ValidationUtil.getValidRange(1, 100, items.get(itemNo).getItemName() + "'s Quantity: ", "Enter the quantity:");

                        itemTable.put(items.get(itemNo), itemTable.getOrDefault(items.get(itemNo), 0) + quantity);

                        calculateCurrentTotal(customer, invoice, itemTable, isUpdation);

                        if (invoice != null) {
                            invoice.showInvoice();
                        }
                        System.out.println("\n");
                        showItemDetails(itemTable);

                        break;
                    }

                    case 2: {
                        if (itemTable.isEmpty()) {

                            System.out.println("\nNo items added yet");
                            break;
                        }

                        int itemIndex = getItemIndex(itemTable, items, itemHandler);
                        Item item = items.get(itemIndex);
                        itemTable.remove(item);

                        System.out.println("\nItem Name: " + item.getItemName() + " is removed");
                        System.out.println("\nUpdated Invoice and Item Table");

                        calculateCurrentTotal(customer, invoice, itemTable, isUpdation);

                        if (invoice != null) {
                            invoice.showInvoice();
                        }
                        System.out.println("\n");
                        showItemDetails(itemTable);

                        break;
                    }

                    case 3:
                    {
                        if (itemTable.isEmpty())
                        {
                            System.out.println("\nNo items added yet");
                            break;
                        }

                        int itemIndex = getItemIndex(itemTable, items, itemHandler);

                        Item item = items.get(itemIndex);

                        int isAddItem = ValidationUtil.getValidRange(1, 2, "Item Table Updation", "Do you want add (or) remove items from the existing list\nTo add Item -> 1\nTo remove Item -> 2");

                        System.out.println("\nItem Name: " + item.getItemName() + ", Item Quantity: " + itemTable.get(item));

                        if (isAddItem == 1) {
                            System.out.println("\nEnter the Quantity to add:");
                        } else {
                            System.out.println("\nNote: The removal Quantity should be less or equal to Existing Quantity\nEnter the Quantity to remove:");
                        }

                        int quantity = 0;
                        quantity = InputUtil.handleIntegerInputMisMatches(quantity, 0);


                        if (isAddItem == 1)
                        {
                            while (quantity < 0)
                            {
                                System.out.println("\nEnter the Quantity (Quantity shouldn't be negative):");
                                quantity = InputUtil.handleIntegerInputMisMatches(quantity, 0);
                            }

                            itemTable.put(item, itemTable.getOrDefault(item, 0) + quantity);
                        }
                        else {
                            if (quantity < 0) {
                                quantity *= -1;
                            }

                            while (quantity > itemTable.get(item))
                            {
                                System.out.println("\nItem Name: " + item.getItemName());

                                System.out.println("\nItem Quantity: " + itemTable.get(item));

                                System.out.println("\nEnter the appropriate quantity, that should be less than the actual quantity");

                                quantity = InputUtil.handleIntegerInputMisMatches(quantity, 0);

                                if (quantity < 0) {
                                    quantity *= -1;
                                }
                            }

                            itemTable.put(item, itemTable.get(item) - quantity);

                            if (itemTable.get(item) == 0)
                            {
                                itemTable.remove(item);
                            }
                        }

                        System.out.println("\nItem Table has been updated");
                        calculateCurrentTotal(customer, invoice, itemTable, isUpdation);

                        if (invoice != null) {
                            invoice.showInvoice();
                        }
                        System.out.println("\n");
                        showItemDetails(itemTable);
                        break;
                    }

                    case 4: {
                        System.out.println("\nExiting the Item Purchasing Module...");
                        break itemTableUpdation;
                    }

                    default: {
                        System.out.println("\nEnter a valid option (1 - 3)");
                        break;
                    }
                }
            }
        }

        return calculateCurrentTotal(customer, invoice, itemTable, isUpdation);

    }

    private double calculateCurrentTotal (Customer customer, Invoice invoice, Map<Item, Integer> itemTable, boolean isUpdation)
    {
        double subTotal = 0;

        Address address = customer.getAddress();

        String state = " ";

        if (address != null && address.getState() != null)
        {
            state = address.getState();
        }

        boolean withinState = state.equalsIgnoreCase("Tamil Nadu");

        for (Item cartItem : itemTable.keySet()) {
            double cost = getRate(cartItem, itemTable.get(cartItem), withinState);
            subTotal += cost;
        }

        if (isUpdation && invoice != null) {
            double invTotal = subTotal - ((subTotal / 100) * invoice.getDiscount());

            invTotal += invoice.getShippingCharges();

            invoice.setSubTotal(subTotal);
            invoice.setItemTable(itemTable);
            adjustDueAmount(invoice.getTotal(), invoice);

            invoice.setTotal(invTotal);
        }

        return subTotal;
    }

    private double getRate (Item item, int quantity, boolean withinState) {
        double baseRate = item.getPrice();
        if (withinState) {
            baseRate += (item.getPrice() / 100) * item.getIntraTaxRate();
        } else {
            baseRate += (item.getPrice() / 100) * item.getInterTaxRate();
        }
        return baseRate * quantity;
    }

    private int getItemIndex(Map<Item, Integer> itemTable, List<Item> items, ItemHandler itemHandler) {
        int removalItemNo = 0;

        int itemIndex;

        removalItem:
        {
            while (true) {
                showItemDetails(itemTable);

                System.out.println("\nEnter a Valid Item Number from the Cart: ");

                removalItemNo = InputUtil.handleIntegerInputMisMatches(removalItemNo, -1);

                itemIndex = itemHandler.searchByItemNo(removalItemNo, items);

                if (itemIndex != -1 && itemTable.containsKey(items.get(itemIndex))) {
                    break removalItem;
                }
                System.out.println("\nYou entered an item number, that doesn't exist in your cart");
            }

        }

        return itemIndex;
    }

    private void showItemDetails(Map<Item, Integer> itemTable) {
        System.out.println("-".repeat(45));
        System.out.printf("| %-10s | %-25s | %-15s | %-15s |%n",
                "Item No", "Item Name", "Item Quantity", "Total");
        System.out.println("-".repeat(45));

        for (Item cartItem : itemTable.keySet()) {
            System.out.printf("| %-10d | %-25s | %-15s | %-15s |\n",
                    cartItem.getItemNo(),
                    cartItem.getItemName(),
                    itemTable.get(cartItem),
                    String.format("%.2f", cartItem.getPrice() * itemTable.get(cartItem))
            );
        }

        System.out.println("-".repeat(45));
    }

    private int getMonthNumber(String month) {
        String[] months = {"january", "february", "march", "april", "may", "june", "july", "august", "september", "october", "november", "december"};

        for (int i = 0; i < months.length; i++) {
            if (months[i].toLowerCase().startsWith(month.toLowerCase())) {
                return i + 1;
            }
        }

        return -1;
    }

    private LocalDate parseDateFromInput(String fieldName) {
        final String DATE_REGEX = "\\d{1,2}[-.\\/\\s]+(\\d{1,2}|[a-zA-Z]{3,})[-.\\/\\s]+\\d{4}";

        String date = ValidationUtil.getValidStringInput(DATE_REGEX, "01-01-2025 (Or) 01/Jan/2025 (Or) 01.January.2025 (Or) 01,January,2025 (Or) 01 January 2025\nNote: If you are mentioning month in String, mention like this Jan", fieldName, "Enter the " + fieldName + " (Eg. 01-01-2025 (Or) 01/Jan/2025 (Or) 01 January 2025):", true);

        String[] dateParts = date.split("[\\s./-]+");

        int dayOfMonth = Integer.parseInt(dateParts[0]);

        String monthInString = null;
        int month = -1;

        if (dateParts[1].length() > 2) {
            monthInString = dateParts[1].trim();
        } else {
            month = Integer.parseInt(dateParts[1]);
        }

        if (monthInString != null) {
            month = getMonthNumber(monthInString);
            if (month == -1) {
                System.out.println("\nYou have entered an incorrect Month name");
                return parseDateFromInput(fieldName);
            }
        }

        int year = Integer.parseInt(dateParts[2]);

        return LocalDate.of(year, month, dayOfMonth);
    }

    private void adjustDueAmount(double total, Invoice invoice) {
        if (total < invoice.getTotal())
        {
            invoice.setDueAmount(invoice.getDueAmount() + (invoice.getTotal() - total));
        }
        else if (total > invoice.getTotal()) {

            double amountRemaining = invoice.getDueAmount() - (total - invoice.getTotal());
            if (amountRemaining < 0)
            {
                System.out.println("\nThe Extra paid" + (invoice.getTotal() - invoice.getDueAmount()) + " is refunded");
                invoice.setDueAmount(0);
            } else if (amountRemaining > 0)
            {
                invoice.setDueAmount(invoice.getDueAmount() - (total - invoice.getTotal()));
            }
        }
    }

    private boolean showWarning(Invoice invoice, String module)
    {
        if (invoice.getStatus().equals(InvoiceHandler.Status.CLOSED) || invoice.getStatus().equals(InvoiceHandler.Status.PARTIALLY_PAID))
        {
            System.out.println("\nSince, invoice is closed or partially paid, it's not recommended to " + module + "...");

            char conformationOption = ValidationUtil.collectToggleChoice(   'y', "Invoice updation option",  "Still you want to " + module + " (y -> yes, any other key -> no)");

            if (conformationOption != 'y')
            {
                System.out.println("\nNo " + module + " has been done");
                return true;
            }

        }
        return false;
    }

    private String showStatuses ()
    {
        StringBuilder sb = new StringBuilder("Status\n");
        for (InvoiceHandler.Status s : InvoiceHandler.Status.values())
        {
            sb.append(s.ordinal() + 1).append(" -> ").append(s.name()).append("\n");
        }
        return sb.toString().trim();
    }

    private int getIndexOfInvoiceID (String invoiceId, List<Invoice> invoices)
    {
        for (int i = 0; i < invoices.size(); i++)
        {
            Invoice invoice = invoices.get(i);
            if (invoice.getInvoiceId().equalsIgnoreCase(invoiceId))
            {
                return i;
            }
        }
        return -1;
    }

    private Invoice cloneInvoice (Invoice invoice)
    {
        Invoice cloneInvoice = new Invoice(invoice.getCustomer(), LocalDate.now(), invoice.getPaymentTerm(), invoice.getItemTable(), invoice.getSubTotal(), invoice.getDiscount(), invoice.getShippingCharges(), invoice.getTotal(), invoice.getDueAmount(), invoice.getStatus());

        cloneInvoice.setInvoiceId(invoice.getInvoiceId());
        cloneInvoice.setInvNo(invoice.getInvNo());

        Map<Item, Integer> previousItemTable = new TreeMap<>((item1, item2) -> Integer.compare(item1.getItemNo(), item2.getItemNo()));

        previousItemTable.putAll(invoice.getItemTable());

        cloneInvoice.setItemTable(previousItemTable);

        return cloneInvoice;
    }
    
}
