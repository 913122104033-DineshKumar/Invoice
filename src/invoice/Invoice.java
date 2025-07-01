package invoice;

import java.time.LocalDate;
import java.util.*;

public class Invoice {
    private Customer customer;

    private static int totalInvoices = 0;
    private final int invNo;
    private LocalDate date;
    private int paymentTerm;
    private Map<Item, Integer> itemTable;
    private double subTotal;
    private int discount;
    private int shippingCharges;
    private double total;
    private static String customerNotes;
    private int status;

    public Invoice(Customer customer, LocalDate date, int paymentTerm, Map<Item, Integer> itemTable, double subTotal, int discount, int shippingCharges, double total, int status) {
        this.customer = customer;
        this.date = date;
        this.paymentTerm = paymentTerm;
        this.itemTable = itemTable;
        this.subTotal = subTotal;
        this.discount = discount;
        this.shippingCharges = shippingCharges;
        this.total = total;
        this.status = status;
        totalInvoices++;
        this.invNo = totalInvoices;
    }

    public static Invoice create (List<Item> items, List<Customer> customers) {
        System.out.println("You can add Invoice now");
        Scanner scanner = new Scanner(System.in);
        Utils.showCustomers(customers);
        System.out.println("Enter the Customer No, you want to create the invoice: ");
        int cusNo = scanner.nextInt();
        scanner.nextLine();
        Customer cus = customers.get(cusNo - 1);
        LocalDate date = LocalDate.now();
        int paymentTerm = 0; // NET 15
        Map<Item, Integer> itemTable = new HashMap<>();
        System.out.println("Options for the payment terms...");
        System.out.println("NET 15 -> 1");
        System.out.println("NET 30 -> 2");
        System.out.println("NET 45 -> 3");
        int paymentTermOption = scanner.nextInt();
        scanner.nextLine();
        switch (paymentTermOption) {
            case 1:
                paymentTerm = 15;
                break;
            case 2:
                paymentTerm = 30;
                break;
            case 3:
                paymentTerm = 45;
                break;
            default:
                System.out.println("You have entered the wrong input, so we set the payment term as NET 0");
                break;
        }
        boolean isPurchasing = true;
        while (isPurchasing) {

            System.out.println("Option 1 -> Add Items to the list");
            System.out.println("Option 2 -> Remove Items from the list");
            System.out.println("Option 3 -> Stop purchasing");
            int purchaseOption = scanner.nextInt();
            scanner.nextLine();

            switch (purchaseOption) {
                case 1:
                    Utils.showItems(items);

                    System.out.println("Enter the Item No: ");
                    int itemNo = scanner.nextInt();

                    System.out.println("Enter the Quantity: ");
                    int quantity = scanner.nextInt();
                    scanner.nextLine();

                    itemTable.put(items.get(itemNo - 1), quantity);
                    break;
                case 2:
                    System.out.println("Item No " + "Item Name " + "Quantity");
                    for (Item cartItem : itemTable.keySet()) {
                        System.out.println(cartItem.getItemNo() + " " + cartItem.getItemName() + " " + itemTable.get(cartItem));
                    }
                    System.out.println("Option 1 -> Remove the Entire the items from the table");
                    System.out.println("Option 2 -> Remove partial items");
                    int removeOption = scanner.nextInt();
                    if (removeOption == 1) {
                        System.out.println("Enter the Item no, you want to remove: ");
                        int removalItemNo = scanner.nextInt();
                        Item item = items.get(removalItemNo - 1);
                        itemTable.remove(item);
                    } else {
                        System.out.println("Enter the Item no, you want to partially remove: ");
                        int removalItemNo = scanner.nextInt();
                        Item item = items.get(removalItemNo - 1);
                        int removalQuantity = itemTable.get(item) + 1;
                        while (removalQuantity > itemTable.get(item
                        )) {
                            System.out.println("Enter the quantity you want remove");
                            removalQuantity = scanner.nextInt();
                        }
                        itemTable.put(item, itemTable.get(item) - removalQuantity);
                    }
                    scanner.nextLine();
                    break;
                case 3:
                    isPurchasing = false;
                    System.out.println("Exiting the purchasing...");
                    break;
                default:
                    System.out.println("Enter a valid option (1 - 3)");
                    break;
            }
        }
        double subTotal = 0;
        System.out.println("Is this transaction within the state, if yes -> 0, No -> 1");
        int stateOption = scanner.nextInt();
        boolean withinState = stateOption == 0;
        for (Item cartItem : itemTable.keySet()) {
            double cost = cartItem.getRate(itemTable.get(cartItem), withinState);
            subTotal += cost;
        }
        int discount = 0;
        System.out.println("Is discount applied, if yes -> 0, No -> 1");
        int discountOption = scanner.nextInt();
        if (discountOption == 0) {
            System.out.println("Enter the discount percentage: ");
            discount = scanner.nextInt();
        }
        double invTotal = subTotal - ((subTotal / 100) * discount);
        int shippingCharges = 0;
        System.out.println("Is shipping charges applied, if yes -> 0, No -> 1");
        int shippingChargeOption = scanner.nextInt();
        if (shippingChargeOption == 0) {
            System.out.println("Enter the shipping charges: ");
            shippingCharges = scanner.nextInt();
        }
        invTotal += shippingCharges;
        return new Invoice(cus, date, paymentTerm, itemTable, subTotal, discount, shippingCharges, invTotal, 0);
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public static int getTotalInvoices() {
        return totalInvoices;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getPaymentTerm() {
        return paymentTerm;
    }

    public void setPaymentTerm(int paymentTerm) {
        this.paymentTerm = paymentTerm;
    }

    public Map<Item, Integer> getItemTable() {
        return itemTable;
    }

    public void setItemTable(Map<Item, Integer> itemTable) {
        this.itemTable = itemTable;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getShippingCharges() {
        return shippingCharges;
    }

    public void setShippingCharges(int shippingCharges) {
        this.shippingCharges = shippingCharges;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getCustomerNotes() {
        return customerNotes;
    }

    public void setCustomerNotes(String customerNotes) {
        customerNotes = customerNotes;
    }

    public String getStatus() {
        return Utils.invoiceStatus.get(status);
    }

    public void setStatus (int status) {
        this.status = status;
    }

    public int getInvNo() {
        return this.invNo;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Invoice Details: \n");
        sb.append("================\n");

        sb.append("Invoice Number: ").append(invNo).append("\n");
        sb.append("Customer Name: ").append(customer.getName()).append("\n");
        sb.append("Date: ").append(date).append("\n");

        sb.append("Payment Term: NET ").append(paymentTerm).append("\n");

        for (Item cartItem : itemTable.keySet()) {
            sb.append("Item Name: ").append(cartItem.getItemName()).append(" Item Quantity: ").append(itemTable.get(cartItem)).append(" Item Price: ");
        }

        sb.append("Sub Total: ").append(subTotal).append("\n");
        sb.append("Discount: ").append(discount).append("\n");
        sb.append("Shipping Charges: ").append(shippingCharges).append("\n");
        sb.append("Total: ").append(total).append("\n");

        sb.append("Status: ").append(status);

        return sb.toString();
    }
}
