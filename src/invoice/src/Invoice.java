package invoice.src;

import invoice.utils.Utils;

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
    private double discount;
    private double shippingCharges;
    private double total;
    private static String customerNotes;
    private int status;

    public Invoice(Customer customer, LocalDate date, int paymentTerm) {
        this.customer = customer;
        this.date = date;
        this.paymentTerm = paymentTerm;
        totalInvoices++;
        this.invNo = totalInvoices;
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

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getShippingCharges() {
        return shippingCharges;
    }

    public void setShippingCharges(double shippingCharges) {
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
