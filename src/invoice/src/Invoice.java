package invoice.src;

import invoice.GlobalConstants;
import invoice.utils.Utils;

import java.time.LocalDate;
import java.util.*;

public class Invoice {
    private Customer customer;

    private static int totalInvoices = 0;
    private int invNo;
    private LocalDate date;
    private int paymentTerm;
    private Map<Item, Integer> itemTable;
    private double subTotal;
    private double discount;
    private double shippingCharges;
    private double total;
    private double dueAmount;
    private int status;

    public Invoice(Customer customer, LocalDate date, int paymentTerm, Map<Item, Integer> itemTable, double subTotal, double discount, double shippingCharges, double total, double dueAmount, int status) {
        this.customer = customer;
        this.date = date;
        this.paymentTerm = paymentTerm;
        totalInvoices++;
        this.invNo = totalInvoices;
        this.itemTable = itemTable;
        this.subTotal = subTotal;
        this.discount = discount;
        this.shippingCharges = shippingCharges;
        this.total = total;
        this.dueAmount = dueAmount;
        this.status = status;
    }

    public Customer getCustomer()
    {
        return customer;
    }

    public void setCustomer (Customer customer) {
        this.customer = customer;
    }

    public static int getTotalInvoices() {
        return totalInvoices;
    }

    public LocalDate getDate()
    {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getPaymentTerm()
    {
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

    public void setTotal(double total)
    {
        this.total = total;
    }

    public String getStatus() {
        return GlobalConstants.INVOICE_STATUS.get(status);
    }

    public void setStatus (int status) {
        this.status = status;
    }

    public int getInvNo() {
        return this.invNo;
    }

    public void setInvNo (int invNo) {
        this.invNo = invNo;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public double getDueAmount() {
        return dueAmount;
    }

    public void setDueAmount(double dueAmount) {
        this.dueAmount = dueAmount;
    }

    public void showInvoice() {
        Utils.printLines(145);

        // Print header
        System.out.printf("| %15s | %15s | %15s | %15s | %15s | %15s | %15s | %15s | %15s |\n",
                "Invoice Number",
                "Customer Name",
                "Date",
                "Payment Term",
                "Sub Total",
                "Discount",
                "Shipping Charges",
                "Total",
                "Status");

        Utils.printLines(145);

        // Print invoice data
        System.out.printf("| %15s | %15s | %15s | %15s | %15s | %15s | %15s | %15s | %15s |\n",
                invNo,
                customer.getName(),
                date,
                paymentTerm,
                String.format("Rs.%.2f", subTotal),
                String.format("%.2f%%", discount),
                String.format("Rs.%.2f", shippingCharges),
                String.format("Rs.%.2f", total),
                getStatus());
    }

}
