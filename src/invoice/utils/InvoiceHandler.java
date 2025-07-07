package invoice.utils;

import invoice.GlobalConstants;
import invoice.src.Customer;
import invoice.src.Invoice;
import invoice.src.Item;

import java.time.LocalDate;
import java.util.*;

public class InvoiceHandler
{

    private final Scanner scanner;
    private final InvoiceUtil invoiceUtils;

    public InvoiceHandler (Scanner scanner) {
        this.scanner = scanner;
        this.invoiceUtils = new InvoiceUtil(scanner);
    }

    public Invoice create (List<Item> items, List<Item> sortedItems, List<Customer> customers, List<Customer> sortedCustomers, Set<String> customerEmails) {
        System.out.println("\nYou can add Invoice now...");

        boolean isAvailableCustomer = customers.isEmpty();

        int cusNo = 0;

        if (customers.isEmpty())
        {
            if (redirectVerification("Customers"))
            {
                Customer customer = Customer.create(customerEmails, scanner);

                customers.add(customer);
                sortedCustomers.add(customer);

                CustomerUtil.reArrangeCustomerList(customers);

                cusNo = customers.size() - 1;
            } else
            {
                return null;
            }
        }

        if (isAvailableCustomer)
        {
            System.out.println("\nCustomer has been created and now can create Invoice\n");
        } else
        {
            if (Utils.isOnlyOneData(customers))
            {
                Utils.showMessageOnlyOneDataIsThere("Customer");
            }
            else
            {
                Utils.showCustomers(customers);

                cusNo = invoiceUtils.getSerialNumberInput(1, customers.size(), "Customer") - 1;
            }
        }

        Customer cus = customers.get(cusNo);

        LocalDate date = LocalDate.now();

        int paymentTerm = invoiceUtils.getPaymentTermInput();

        Map<Item, Integer> itemTable = new TreeMap<>((item1, item2) -> Integer.compare(item1.getItemNo(), item2.getItemNo()));
        double subTotal = itemTableOperations(cus, null, items, sortedItems, itemTable, false);

        double discount = invoiceUtils.getDiscountInput(true);

        double invTotal = subTotal - ((subTotal / 100) * discount);

        double shippingCharges = invoiceUtils.getShippingCharges(true);

        invTotal += shippingCharges;

        double paymentReceived = invoiceUtils.haveReceivedPayment(invTotal);

        int status = -1;

        if (paymentReceived == invTotal)
        {
            status = 3; // CLOSED
        } else if (paymentReceived > 0)
        {
            status = 2; // PARTIALLY PAID
        } else
        {
            char yesOrNo = Utils.getValidOption(GlobalConstants.YES_NO_OPTIONS, scanner, "Invoice Sent", "Have you sent the invoice to customer?\nY -> Yes\nN -> No");

            if (yesOrNo == 'Y' || yesOrNo == 'y')
            {
                status = 1;
            } else
            {
                status = 0;
            }

        }

        return new Invoice(cus, date, paymentTerm, itemTable, subTotal, discount, shippingCharges, invTotal, invTotal - paymentReceived, status);
    }

    public void update(Invoice invoice, List<Item> items, List<Item> sortedItems, List<Customer> customers) {

        if (invoiceUtils.neglectWarning(invoice, "update"))
        {
            return;
        }

        int option = -1;
        do
        {
            System.out.println("\nOption 1 -> (Item Table, Payment Term, Customer) Modifications");
            System.out.println("Option 2 -> (Discount, Shipping Charges, status) Modifications");
            System.out.println("Option 3 -> Exit the Update Module");

            System.out.println("\nEnter Option: ");
            option = Utils.handleIntegerInputMisMatches(option, -1, scanner);

            switch (option)
            {
                case 1:
                {
                    if (invoiceUtils.neglectWarning(invoice, "update"))
                    {
                        return;
                    }
                    modifyInvoicePrimaryDetails(invoice, items, sortedItems, customers);
                    break;
                }

                case 2:
                {
                    if (invoiceUtils.neglectWarning(invoice, "update"))
                    {
                        return;
                    }
                    modifyInvoiceOtherCharges(invoice);
                    break;
                }

                case 3:
                {
                    System.out.println("\nExiting the Invoice Updation Module...");
                    break;
                }

                default:
                {
                    System.out.println("Enter a Valid input (1 - 3)");
                    break;
                }
            }
        } while (option != 3);

        System.out.println("\nUpdated Invoice Number: " + invoice.getInvNo());

        invoice.showInvoice();
    }

    public List<Invoice> searchByCustomer(String customerName, List<Invoice> invoices, double limit)
    {
        List<Invoice> invoicesOfCustomer = new ArrayList<>();
        for (Invoice invoice : invoices)
        {
            if (invoice.getCustomer().getName().equalsIgnoreCase(customerName) && invoice.getDueAmount() > limit)
            {
                invoicesOfCustomer.add(invoice);
            }
        }
        return invoicesOfCustomer;
    }

    public void paymentModule (List<Invoice> invoices, List<Customer> customers)
    {
        int optionForPayment = -1;


        label:
        while (true)
        {
            System.out.println("\nEntered into payment module");

            System.out.println("\nOption 1 -> Pay for Particular Invoice");
            System.out.println("\nOption 2 -> Pay for Due Amount remaining for the particular Customer");
            System.out.println("\nOption 3 -> Pay Due Amount for Particular range of Date: ");
            System.out.println("\nOption 4 -> Exiting the Payment Module");

            System.out.println("\nHow you want to pay: ");
            optionForPayment = Utils.handleIntegerInputMisMatches(optionForPayment, -1, scanner);

            switch (optionForPayment) {
                case 1:
                {
                    Utils.showInvoices(invoices);

                    int invNo = invoiceUtils.getSerialNumberInput(1, invoices.size(), "Invoice") - 1;

                    Invoice paymentInvoice = invoices.get(invNo);

                    payForParticularInvoice(paymentInvoice);
                }

                    break;
                case 2:
                {
                    int cusNo = 0;

                    if (Utils.isOnlyOneData(invoices))
                    {
                        Utils.showMessageOnlyOneDataIsThere("Item");
                    } else
                    {
                        Utils.showCustomers(customers);

                        cusNo = invoiceUtils.getSerialNumberInput(1, customers.size(), "Customer")- 1;
                    }

                    List<Invoice> customerInvoices = searchByCustomer(customers.get(cusNo).getName(), invoices, 0);

                    payDueRemainingForMultipleInvoices(customerInvoices);

                    break;
                }
                case 3:
                {
                    int cusNo = 0;

                    if (Utils.isOnlyOneData(invoices)) // Have to simply this with one method in Util
                    {
                        Utils.showMessageOnlyOneDataIsThere("Item");
                    } else
                    {
                        Utils.showCustomers(customers);

                        cusNo = invoiceUtils.getSerialNumberInput(1, customers.size(), "Customer") - 1;
                    }

                    List<Invoice> customerInvoices = searchByCustomer(customers.get(cusNo).getName(), invoices, 0);

                    payForParticularRange(customerInvoices);

                    break label;
                }
                case 4:
                {
                    System.out.println("\nExiting the Payment Module");
                    break;
                }
                default:
                {
                    System.out.println("\nEnter a valid input (1 - 3): ");
                    break;
                }
            }
        }
    }

    public void sortingModule (List<Invoice> invoices)
    {
        SortingUtil sortingUtil = new SortingUtil();

        System.out.println("\nEnter the choice based in which you want to sort the Invoices: ");

        int sortBy = -1;
        do
        {
            System.out.println("\nOption 1 -> Sort by Invoice Number");
            System.out.println("\nOption 2 -> Sort by Date");
            System.out.println("\nOption 3 -> Sort by Due Amount");

            sortBy = Utils.handleIntegerInputMisMatches(sortBy, -1, scanner);

        } while (sortBy < 1 || sortBy > 3);

        int sortingOrder = -1;

        do
        {
            System.out.println("\nOption 1 -> Ascending Order");
            System.out.println("\nOption 2 -> Descending Order");

            sortingOrder = Utils.handleIntegerInputMisMatches(sortingOrder, -1, scanner);

        } while (sortingOrder < 1 || sortingOrder > 2);

        if (sortBy == 1)
        {
            List<Integer> helper = new ArrayList<>();

            for (Invoice invoice : invoices)
            {
                helper.add(invoice.getInvNo());
            }

            sortingUtil.mergeSort(0, invoices.size() - 1, invoices, helper);
        }
        else if (sortBy == 2)
        {
            List<LocalDate> helper = new ArrayList<>();

            for (Invoice invoice : invoices)
            {
                helper.add(invoice.getCreatedAt());
            }

            sortingUtil.mergeSort(0, invoices.size() - 1, invoices, helper);
        } else
        {
            List<Double> helper = new ArrayList<>();

            for (Invoice invoice : invoices)
            {
                helper.add(invoice.getDueAmount());
            }

            sortingUtil.mergeSort(0, invoices.size() - 1, invoices, helper);
        }

        if (sortingOrder == 2)
        {
            Utils.reverse(invoices);
        }

        Utils.showInvoices(invoices);

    }

    public void payForParticularRange (List<Invoice> invoices)
    {
        List<Invoice> inRangeInvoices = new ArrayList<>();

        LocalDate startDate = getFormattedDate("Start Date");
        LocalDate endDate = getFormattedDate("End Date");

        for (Invoice invoice : invoices)
        {
            LocalDate invoiceDate = invoice.getCreatedAt();

            if (Utils.compareDates(startDate, invoiceDate) == 1
            && Utils.compareDates(invoiceDate, endDate) == 1)
            {
                inRangeInvoices.add(invoice);
            }
        }

        Utils.showInvoices(inRangeInvoices);

        payDueRemainingForMultipleInvoices(inRangeInvoices);

    }

    private void payDueRemainingForMultipleInvoices (List<Invoice> customerInvoices)
    {
        double totalAmountToBePaid = 0;

        for (Invoice invoice : customerInvoices)
        {
            totalAmountToBePaid += invoice.getDueAmount();
        }

        double paidAmount = invoiceUtils.getPaymentInput(totalAmountToBePaid);

        Utils.showInvoices(customerInvoices);

        for (Invoice invoice : customerInvoices)
        {
            if (paidAmount == 0)
            {
                break;
            }

            System.out.println("Remaining Due: " + totalAmountToBePaid);

            System.out.println("\nInvoice No: " + invoice.getInvNo() + " has due amount of " + invoice.getDueAmount());

            double amountPaidForThisInvoice = invoiceUtils.getPaymentInput(Math.min(invoice.getDueAmount(), paidAmount));

            invoice.setDueAmount(invoice.getDueAmount() - amountPaidForThisInvoice);

            paidAmount -= amountPaidForThisInvoice;
            totalAmountToBePaid -= amountPaidForThisInvoice;

        }

    }

    private void payForParticularInvoice (Invoice invoice)
    {
        System.out.println("\nDue Amount need to be paid: " + invoice.getDueAmount());

        double paidAmount = invoiceUtils.getPaymentInput(invoice.getDueAmount());

        System.out.println(paidAmount != 0 ? "\nAmount paid: " + paidAmount : "Amount is not paid (Or) Cancelled the Payment");

        invoice.setDueAmount(invoice.getDueAmount() - paidAmount);
    }

    private void modifyInvoicePrimaryDetails(Invoice invoice, List<Item> items, List<Item> sortedItems, List<Customer> customers) {
        int option = -1;

        do
        {
            System.out.println("\nOption 1 -> Update Item Table");
            System.out.println("Option 2 -> Update Payment Term");
            System.out.println("Option 3 -> Update Customer");
            System.out.println("Option 4 -> Exit the Invoice Primary Details Modification Module");

            System.out.println("\nEnter Option: ");
            option = Utils.handleIntegerInputMisMatches(option, -1, scanner);

            switch (option)
            {
                case 1:
                {
                    if (invoiceUtils.neglectWarning(invoice, "update"))
                    {
                        return;
                    }
                    Map<Item, Integer> previousItemTable = invoice.getItemTable();

                    Map<Item, Integer> currentItemTable = invoice.getItemTable();

                    double subTotal = itemTableOperations(invoice.getCustomer(), invoice, items, sortedItems, currentItemTable, true);

                    System.out.println("\nPrevious Item Table\n");
                    showItemDetails(previousItemTable);

                    System.out.println("\nUpdate Item Table\n");
                    showItemDetails(currentItemTable);

                    invoice.setItemTable(currentItemTable);
                    invoice.setSubTotal(subTotal);

                    break;
                }

                case 2:
                {
                    if (invoiceUtils.neglectWarning(invoice, "update"))
                    {
                        return;
                    }
                    int previousPaymentTerm = invoice.getPaymentTerm();

                    int paymentTerm = invoiceUtils.getPaymentTermInput();

                    invoice.setPaymentTerm(paymentTerm);

                    System.out.println("\nInvoice's Payment Term updated from " + previousPaymentTerm + " to " + paymentTerm);
                    break;
                }

                case 3:
                {
                    if (invoiceUtils.neglectWarning(invoice, "update"))
                    {
                        return;
                    }
                    String previousCustomerName = invoice.getCustomer().getName();

                    Utils.showCustomers(customers);

                    int cusNo = invoiceUtils.getSerialNumberInput(1, customers.size(), "Customer") - 1;

                    invoice.setCustomer(customers.get(cusNo));

                    System.out.println("\nInvoice's Customer updated from " + previousCustomerName + " to " + customers.get(cusNo).getName());

                    break;
                }

                case 4:
                {
                    System.out.println("\nExiting the Invoice Primary Details Updation Module...");
                    break;
                }

                default:
                {
                    System.out.println("\nEnter a Valid input (1 - 4)");
                    break;
                }
            }
        } while (option != 4);

    }

    private void modifyInvoiceOtherCharges(Invoice invoice)
    {
        int option = -1;

        do
        {
            System.out.println("\nOption 1 -> Update Discount");
            System.out.println("Option 2 -> Update Shipping Charges");
            System.out.println("Option 3 -> Update Status");
            System.out.println("Option 4 -> Exit the Invoice Other Charges Module");

            System.out.println("\nEnter Option: ");
            option = Utils.handleIntegerInputMisMatches(option, -1, scanner);


            switch (option)
            {
                case 1:
                {
                    if (invoiceUtils.neglectWarning(invoice, "update"))
                    {
                        return;
                    }

                    double previousDiscount = invoice.getDiscount();

                    double nDiscount = invoiceUtils.getDiscountInput(false);

                    invoice.setDiscount(nDiscount);

                    invoice.setTotal(invoice.getSubTotal() - ((invoice.getSubTotal() / 100) * nDiscount) + invoice.getShippingCharges());

                    System.out.println("\nInvoice's Discount updated from " + previousDiscount + " to " + nDiscount);

                    break;
                }

                case 2:
                {
                    if (invoiceUtils.neglectWarning(invoice, "update"))
                    {
                        return;
                    }

                    double previousShippingCharges = invoice.getShippingCharges();

                    double shippingCharges = invoiceUtils.getShippingCharges(false);

                    invoice.setShippingCharges(shippingCharges);

                    invoice.setTotal(invoice.getTotal() - previousShippingCharges + shippingCharges);

                    System.out.println("\nInvoice's Shipping Charges updated from " + previousShippingCharges + " to " + shippingCharges);

                    break;
                }

                case 3:
                {
                    if (invoiceUtils.neglectWarning(invoice, "update"))
                    {
                        return;
                    }
                    String previousStatus = invoice.getStatus();

                    int status = Utils.getValidRange( 1, GlobalConstants.INVOICE_STATUS.size(), scanner, "Invoice Status", "Enter the Status No:\n" + Utils.showStatuses()) - 1;

                    invoice.setStatus(status);

                    System.out.println("\nInvoice's Status updated from " + previousStatus + " to " + invoice.getStatus());

                    break;
                }

                case 4:
                {
                    System.out.println("\nExiting the Invoice Other Charges Updation Module...");
                    break;
                }

                default:
                {
                    System.out.println("\nEnter a Valid input (1 - 4)");
                    break;
                }
            }
        } while (option != 4);
    }

    private double itemTableOperations (Customer customer, Invoice invoice, List<Item> items, List<Item> sortedItems, Map<Item, Integer> itemTable, boolean isUpdation)
    {
        int option = -1;

        do
        {

            System.out.println("\nOption 1 -> Add Items to the list");
            if (!itemTable.isEmpty())
            {
                System.out.println("Option 2 -> Remove Items from the list");
            }
            else
            {
                System.out.println("Option 2 is disabled, No items added yet...");
            }
            System.out.println("Option 3 -> Quit");

            System.out.println("\nEnter the Option: ");
            option = Utils.handleIntegerInputMisMatches(option, -1, scanner);

            switch (option)
            {

                case 1:
                {
                    int itemNo = 0;

                    boolean isItemListEmpty = items.isEmpty();

                    if (items.isEmpty())
                    {
                        if (redirectVerification("Items")) {
                            Item item = Item.create(scanner);

                            items.add(item);
                            sortedItems.add(item);


                            ItemUtil.reArrangeItemList(items);

                            System.out.println();

                            itemNo = items.size() - 1;
                        } else
                        {
                            break;
                        }
                    }

                    if (isItemListEmpty)
                    {
                        System.out.println("\nItem has been created.\nNow you can add quantity for the item");
                    } else
                    {
                        if (Utils.isOnlyOneData(items))
                        {
                            Utils.showMessageOnlyOneDataIsThere("Item");
                        }
                        else {
                            Utils.showItems(items);

                            itemNo = Utils.getValidRange(1, items.size(), scanner, "Item Number:",
                                    "Enter the Item No from the table:") - 1;
                        }
                    }

                    int quantity = Utils.getValidRange( 1, 100, scanner, items.get(itemNo).getItemName() + "'s Quantity: ","Enter the quantity:");

                    itemTable.put(items.get(itemNo), itemTable.getOrDefault(items.get(itemNo), 0) + quantity);
                    break;
                }

                case 2:
                {
                    if (!itemTable.isEmpty())
                    {

                        int removalItemNo = 0;

                        if (Utils.isOnlyOneData(items))
                        {
                            Utils.showMessageOnlyOneDataIsThere("Item");
                        } else
                        {
                            showItemDetails(itemTable);

                            removalItemNo = Utils.getValidRange( 1, items.size(), scanner, "Item Number:", "Enter the Item No from the Table:") - 1;
                        }

                        Item item = items.get(removalItemNo);

                        System.out.println("\nOption 1 -> Remove the Entire Item");
                        System.out.println("Option 2 -> Remove Partial Items");
                        System.out.println("Option 3 -> Quit");

                        int removeOption = Utils.getValidRange( 1, 3, scanner, "Item Remove Option", "Enter the Item Number for removing:");

                        if (removeOption == 1)
                        {

                            itemTable.remove(item);

                        } else if (removeOption == 2)
                        {
                            System.out.println("\nItem Quantity: " + itemTable.get(item));

                            System.out.println("\nEnter the removal Quantity less the above mentioned Quantity: ");

                            int removalQuantity = 0;
                            removalQuantity = Utils.handleIntegerInputMisMatches(removalQuantity, 0, scanner);

                            if (removalQuantity < 0)
                            {
                                removalQuantity *= -1;
                            }

                            while (removalQuantity > itemTable.get(item
                            ))
                            {
                                System.out.println("\nItem Quantity: " + itemTable.get(item));

                                System.out.println("\nEnter the appropriate quantity,  that should less than the actual quantity");

                                removalQuantity = Utils.handleIntegerInputMisMatches(removalQuantity, 0, scanner);

                                if (removalQuantity < 0)
                                {
                                    removalQuantity *= -1;
                                }
                            }

                            itemTable.put(item, itemTable.get(item) - removalQuantity);

                        } else
                        {
                            System.out.println("\nItem Table Updation");
                            break;
                        }
                    } else
                    {
                        System.out.println("\nNo items added yet");
                    }
                    break;
                }

                case 3:
                {
                    System.out.println("\nExiting the Item Purchasing Module...");
                    break;
                }

                default:
                {
                    System.out.println("\nEnter a valid option (1 - 3)");
                    break;
                }
            }
        } while (option != 3);

        double subTotal = 0;

        boolean withinState = customer.getAddress().getState().equalsIgnoreCase("Tamil Nadu");

        for (Item cartItem : itemTable.keySet())
        {
            double cost = cartItem.getRate(itemTable.get(cartItem), withinState);
            subTotal += cost;
        }

        if (isUpdation && invoice != null)
        {
            double invTotal = subTotal - ((subTotal / 100) * invoice.getDiscount());

            invTotal += invoice.getShippingCharges();

            invoice.setTotal(invTotal);
        }

        return subTotal;

    }

    private boolean redirectVerification (String module)
    {
        System.out.println("\nSince, there is no " + module + ", you have to create" + module + " .");

        char yesOrNo = Utils.getValidOption(GlobalConstants.YES_NO_OPTIONS, scanner, "Redirection to Customer Creation", "Would you like to create the " + module + "\nY -> Yes\nN -> No");

        return yesOrNo == 'Y' || yesOrNo == 'y';
    }

    private void showItemDetails(Map<Item, Integer> itemTable)
    {
        System.out.println("-".repeat(45));
        System.out.printf("| %-10s | %-25s | %-15s |%n",
                "Item No", "Item Name", "Item Quantity");
        System.out.println("-".repeat(45));

        for (Item cartItem : itemTable.keySet())
        {
            System.out.printf("| %-10d | %-25s | %-15s |\n",
                    cartItem.getItemNo(),
                    cartItem.getItemName(),
                    itemTable.get(cartItem));
        }

        System.out.println("-".repeat(45));
    }

    private int convertStringMonthToInteger (String month)
    {
        String[] months = { "january", "february", "march", "april", "may", "june", "july", "august", "september", "october", "november", "december" };

            for (int i = 0; i < months.length; i++)
            {
                if (months[i].toLowerCase().startsWith(month.toLowerCase()))
                {
                    return i + 1;
                }
            }

        return -1;
    }

    private LocalDate getFormattedDate (String fieldName)
    {
        final String DATE_REGEX = "\\d{1,2}[-.\\/\\s](\\d{1,2}|[a-zA-Z]{3,})[-.\\/\\s]\\d{4}$";

        String date = Utils.getValidStringInput(DATE_REGEX, scanner, "01-01-2025 (Or) 01/Jan/2025 (Or) 01.January.2025 (Or) 01,January,2025 (Or) 01 January 2025\nNote: If you are mentioning month in String, mention like this Jan", fieldName, "Enter the " + fieldName + ":", true);

        String[] dateParts = date.split("[\\s./-]+");

        int dayOfMonth = Integer.parseInt(dateParts[0]);

        String monthInString = null;
        int month = -1;

        if (dateParts[1].length() > 2)
        {
            monthInString = dateParts[1].trim();
        } else
        {
            month = Integer.parseInt(dateParts[1]);
        }

        if (monthInString != null)
        {
            month = convertStringMonthToInteger(monthInString);
            if (month == -1)
            {
                System.out.println("\nYou have entered an incorrect Month name");
                return getFormattedDate(fieldName);
            }
        }

        int year = Integer.parseInt(dateParts[2]);

        return LocalDate.of(year, month, dayOfMonth);
    }

}
