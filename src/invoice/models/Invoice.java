package invoice.models;

import invoice.handlers.InvoiceHandler;
import invoice.utils.InputUtils;

import java.time.LocalDate;
import java.util.*;

public class Invoice
{
    private Customer customer;

    private static int totalInvoices = 1;
    private final int invNo;
    private final LocalDate createdAt;
    private int paymentTerm;
    private Map<Item, Integer> itemTable;
    private double subTotal;
    private double discount;
    private double shippingCharges;
    private double total;
    private double dueAmount;
    private InvoiceHandler.Status status;

    public Invoice (Customer customer, int paymentTerm, Map<Item, Integer> itemTable, double subTotal)
    {
        this.customer = customer;
        this.createdAt = LocalDate.now();
        this.paymentTerm = paymentTerm;
        this.itemTable = itemTable;
        this.subTotal = subTotal;
        this.invNo = totalInvoices;
        totalInvoices++;
    }

    public Invoice(Customer customer, LocalDate createdAt, int paymentTerm, Map<Item, Integer> itemTable, double subTotal, double discount, double shippingCharges, double total, double dueAmount, InvoiceHandler.Status status)
    {
        this.customer = customer;
        this.createdAt = createdAt;
        this.paymentTerm = paymentTerm;
        this.invNo = totalInvoices;
        totalInvoices++;
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

    public LocalDate getCreatedAt()
    {
        return createdAt;
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

    public InvoiceHandler.Status getStatus()
    {
        return status;
    }

    public void setStatus (InvoiceHandler.Status status)
    {
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

    public double getDueAmount() {
        return dueAmount;
    }

    public void setDueAmount(double dueAmount) {
        this.dueAmount = dueAmount;
    }

    public String generateInvId ()
    {
        return "INV" + totalInvoices;
    }

    public void showInvoice()
    {
        InputUtils.printHyphens(145);

        // Print header
        System.out.printf("| %15s | %15s | %15s | %15s | %15s | %15s | %15s | %15s| %15s | %15s |\n",
                "Invoice Number",
                "Customer Name",
                "Date",
                "Payment Term",
                "Sub Total",
                "Discount",
                "Shipping Charges",
                "Total",
                "Due Amount",
                "Status");

        InputUtils.printHyphens(145);

        // Print invoice data
        System.out.printf("| %15s | %15s | %15s | %15s | %15s | %15s | %15s | %15s| %15s | %15s |\n",
                this.invNo,
                this.customer.getName(),
                this.createdAt,
                this.paymentTerm,
                String.format("Rs.%.2f", subTotal),
                String.format("%.2f%%", discount),
                String.format("Rs.%.2f", shippingCharges),
                String.format("Rs.%.2f", total),
                String.format("Rs.%.2f", dueAmount),
                this.status);
    }

}
