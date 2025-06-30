package invoice;

import java.time.LocalDate;
import java.util.List;

public class Invoice {
    private Customer customer;
    private static int invNo = 1;
    private LocalDate date;
    private int paymentTerm;
    private List<Item> itemTable;
    private int discount;
    private int shippingCharges;
    private int tds;
    private double total;
    private String customerNotes;
    private Utils.Status status;

    public Invoice (Customer customer, LocalDate date, int paymentTerm, List<Item> itemTable, int discount, int shippingCharges, int tds, double total, String customerNotes, Utils.Status status) {
        this.customer = customer;
        this.date = date;
        this.paymentTerm = paymentTerm;
        this.itemTable = itemTable;
        this.discount = discount;
        this.shippingCharges = shippingCharges;
        this.tds = tds;
        this.total = total;
        this.customerNotes = customerNotes;
        this.status = status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public static int getInvNo() {
        return invNo;
    }

    public static void setInvNo(int invNo) {
        Invoice.invNo = invNo;
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

    public List<Item> getItemTable() {
        return itemTable;
    }

    public void setItemTable(List<Item> itemTable) {
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

    public int getTds() {
        return tds;
    }

    public void setTds(int tds) {
        this.tds = tds;
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
        this.customerNotes = customerNotes;
    }

    public Utils.Status getStatus() {
        return status;
    }

    public void setStatus(Utils.Status status) {
        this.status = status;
    }

}
