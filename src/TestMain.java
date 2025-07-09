import invoice.models.Address;
import invoice.models.Customer;
import invoice.models.Invoice;
import invoice.models.Item;
import invoice.handlers.InvoiceHandler;

import java.time.LocalDate;
import java.util.*;

public class TestMain {

    private static List<Customer> customers;
    private static List<Item> items;
    private static List<Invoice> invoices = new ArrayList<>();

    public static void main (String[] args)
    {
        addCustomers();
        addItems();
        addInvoices();
        Main.initialize(customers, items, invoices);
        Main.main(args);
    }

    private static void addCustomers ()
    {
        Customer c1 = new Customer('B', LocalDate.of(2023, 1, 10), "John Doe", "AlphaCorp", "john@alphacorp.com", "9876543210");

        Customer c2 = new Customer('I', LocalDate.of(2023, 1, 11), "Alice Smith", null, "alice@gmail.com", "9876543211");
        Customer c3 = new Customer('B', LocalDate.of(2023, 1, 12), "Bob Johnson", "BetaTech", "bob@betatech.com", "9876543212");
        c3.setAddress(new Address("India", "TN", "Chennai", "Main Road", "600001"));
        Customer c4 = new Customer('I', LocalDate.of(2023, 1, 13), "Clara White", null, "clara@gmail.com", "9876543213");
        Customer c5 = new Customer('B', LocalDate.of(2023, 1, 14), "David Black", "Gamma Ltd", "david@gammaltd.com", "9876543214");
        c5.setAddress(new Address("India", "KA", "Bangalore", "MG Road", "560001"));
        c5.setShippingAddress(new Address("India", "KA", "Mysore", "Palace Rd", "570001"));

        Customer c6 = new Customer('I', LocalDate.of(2023, 1, 15), "Eva Green", null, "eva@gmail.com", "9876543215");
        Customer c7 = new Customer('B', LocalDate.of(2023, 1, 16), "Frank Blue", "Delta Inc", "frank@deltainc.com", "9876543216");
        Customer c8 = new Customer('I', LocalDate.of(2023, 1, 17), "Grace Gray", null, "grace@gmail.com", "9876543217");
        Customer c9 = new Customer('B', LocalDate.of(2023, 1, 18), "Henry Brown", "Epsilon LLC", "henry@epsilon.com", "9876543218");
        c9.setAddress(new Address("India", "MH", "Mumbai", "Marine Drive", "400001"));
        Customer c10 = new Customer('I', LocalDate.of(2023, 1, 19), "Isla Red", null, "isla@gmail.com", "9876543219");

        Customer c11 = new Customer('B', LocalDate.of(2023, 1, 20), "Jack White", "Zeta Systems", "jack@zeta.com", "9876543220");
        Customer c12 = new Customer('I', LocalDate.of(2023, 1, 21), "Karen Blue", null, "karen@gmail.com", "9876543221");
        Customer c13 = new Customer('B', LocalDate.of(2023, 1, 22), "Leo Stone", "Theta Pvt Ltd", "leo@theta.com", "9876543222");
        c13.setAddress(new Address("India", "DL", "Delhi", "Ring Road", "110001"));
        Customer c14 = new Customer('I', LocalDate.of(2023, 1, 23), "Mia Snow", null, "mia@gmail.com", "9876543223");
        Customer c15 = new Customer('B', LocalDate.of(2023, 1, 24), "Nate Sky", "Lambda Corp", "nate@lambda.com", "9876543224");
        c15.setAddress(new Address("India", "WB", "Kolkata", "Park Street", "700001"));
        c15.setShippingAddress(new Address("India", "WB", "Howrah", "GT Road", "711101"));
        Customer c16 = new Customer('I', LocalDate.of(2023, 1, 25), "Olivia Pink", null, "olivia@gmail.com", "9876543225");
        Customer c17 = new Customer('B', LocalDate.of(2023, 1, 26), "Paul Mint", "Omega Inc", "paul@omega.com", "9876543226");
        Customer c18 = new Customer('I', LocalDate.of(2023, 1, 27), "Quinn Rose", null, "quinn@gmail.com", "9876543227");
        Customer c19 = new Customer('B', LocalDate.of(2023, 1, 28), "Rachel Lime", "Sigma Ltd", "rachel@sigma.com", "9876543228");
        c19.setAddress(new Address("India", "GJ", "Ahmedabad", "CG Road", "380001"));
        Customer c20 = new Customer('I', LocalDate.of(2023, 1, 29), "Sam Violet", null, "sam@gmail.com", "9876543229");

        Customer c21 = new Customer('B', LocalDate.of(2023, 1, 30), "Tina Azure", "Upsilon Corp", "tina@upsilon.com", "9876543230");
        Customer c22 = new Customer('I', LocalDate.of(2023, 2, 1), "Uma Coral", null, "uma@gmail.com", "9876543231");
        Customer c23 = new Customer('B', LocalDate.of(2023, 2, 2), "Victor Gold", "Phi Solutions", "victor@phi.com", "9876543232");
        c23.setAddress(new Address("India", "RJ", "Jaipur", "Pink Street", "302001"));
        c23.setShippingAddress(new Address("India", "RJ", "Udaipur", "Lake Road", "313001"));
        Customer c24 = new Customer('I', LocalDate.of(2023, 2, 3), "Wendy Pearl", null, "wendy@gmail.com", "9876543233");
        Customer c25 = new Customer('B', LocalDate.of(2023, 2, 4), "Xavier Cyan", "ChiTech", "xavier@chi.com", "9876543234");

        customers = List.of(
                c1, c2, c3, c4, c5,
                c6, c7, c8, c9, c10,
                c11, c12, c13, c14, c15,
                c16, c17, c18, c19, c20,
                c21, c22, c23, c24, c25
        );
    }

    private static void addItems ()
    {
        Item i1 = new Item('G', 'P', LocalDate.of(2023, 1, 1), "Pen", 10.0, "Blue ink ball pen", true, 5.0, 12.0);
        Item i2 = new Item('S', 'M', LocalDate.of(2023, 1, 2), "Cleaning Service", 150.0, null, true, 9.0, 18.0);
        Item i3 = new Item('g', 'B', LocalDate.of(2023, 1, 3), "Notebook", 50.0, "A4 size ruled", true, 6.0, 12.0);
        Item i4 = new Item('s', 'N', LocalDate.of(2023, 1, 4), "Consulting", 200.0, null, true, 8.0, 16.0);
        Item i5 = new Item('G', 'p', LocalDate.of(2023, 1, 5), "Pencil", 5.0, "HB graphite", true, 5.0, 12.0);
        Item i6 = new Item('S', 'm', LocalDate.of(2023, 1, 6), "Software Support", 300.0, null, true, 9.0, 18.0);
        Item i7 = new Item('g', 'b', LocalDate.of(2023, 1, 7), "Eraser", 3.0, null, true, 5.0, 12.0);
        Item i8 = new Item('s', 'n', LocalDate.of(2023, 1, 8), "Web Hosting", 400.0, "Shared hosting", true, 9.0, 18.0);
        Item i9 = new Item('G', 'P', LocalDate.of(2023, 1, 9), "Mouse", 250.0, "Wireless optical mouse", true, 12.0, 18.0);
        Item i10 = new Item('S', 'M', LocalDate.of(2023, 1, 10), "Graphic Design", 800.0, null, true, 10.0, 18.0);
        Item i11 = new Item('g', 'B', LocalDate.of(2023, 1, 11), "Keyboard", 350.0, "Mechanical keyboard", true, 12.0, 18.0);
        Item i12 = new Item('s', 'N', LocalDate.of(2023, 1, 12), "Photography", 1200.0, "Event photography", true, 9.0, 18.0);
        Item i13 = new Item('G', 'p', LocalDate.of(2023, 1, 13), "Charger", 500.0, "Fast charging", true, 12.0, 18.0);
        Item i14 = new Item('S', 'm', LocalDate.of(2023, 1, 14), "Training", 1000.0, null, true, 9.0, 18.0);
        Item i15 = new Item('g', 'b', LocalDate.of(2023, 1, 15), "Speaker", 1500.0, "Bluetooth speaker", true, 12.0, 18.0);

// Non-taxable items
        Item i16 = new Item('s', 'n', LocalDate.of(2023, 2, 1), "Donation Service", 0.0, null, false, 0.0, 0.0);
        Item i17 = new Item('G', 'P', LocalDate.of(2023, 2, 2), "Old Books", 20.0, "Used textbooks", false, 0.0, 0.0);
        Item i18 = new Item('S', 'M', LocalDate.of(2023, 2, 3), "Free Seminar", 0.0, null, false, 0.0, 0.0);
        Item i19 = new Item('g', 'B', LocalDate.of(2023, 2, 4), "Used Laptop", 8000.0, "Refurbished", false, 0.0, 0.0);
        Item i20 = new Item('s', 'N', LocalDate.of(2023, 2, 5), "Volunteer Program", 0.0, null, false, 0.0, 0.0);
        Item i21 = new Item('G', 'p', LocalDate.of(2023, 2, 6), "Cloth Bag", 15.0, "Eco-friendly", false, 0.0, 0.0);
        Item i22 = new Item('S', 'm', LocalDate.of(2023, 2, 7), "Charity Transport", 0.0, null, false, 0.0, 0.0);
        Item i23 = new Item('g', 'b', LocalDate.of(2023, 2, 8), "Scrap Paper", 2.0, null, false, 0.0, 0.0);
        Item i24 = new Item('s', 'n', LocalDate.of(2023, 2, 9), "Free Support", 0.0, null, false, 0.0, 0.0);
        Item i25 = new Item('G', 'P', LocalDate.of(2023, 2, 10), "Used Furniture", 500.0, "Second-hand chair", false, 0.0, 0.0);
        Item i26 = new Item('S', 'M', LocalDate.of(2023, 2, 11), "Public Speaking", 0.0, null, false, 0.0, 0.0);
        Item i27 = new Item('g', 'B', LocalDate.of(2023, 2, 12), "Old Phone", 2000.0, null, false, 0.0, 0.0);
        Item i28 = new Item('s', 'N', LocalDate.of(2023, 2, 13), "Awareness Program", 0.0, null, false, 0.0, 0.0);
        Item i29 = new Item('G', 'p', LocalDate.of(2023, 2, 14), "Reused Bottle", 5.0, null, false, 0.0, 0.0);
        Item i30 = new Item('S', 'm', LocalDate.of(2023, 2, 15), "Free Tech Help", 0.0, null, false, 0.0, 0.0);

        items = List.of(
                i1, i2, i3, i4, i5,
                i6, i7, i8, i9, i10,
                i11, i12, i13, i14, i15,
                i16, i17, i18, i19, i20,
                i21, i22, i23, i24, i25,
                i26, i27, i28, i29, i30
        );

    }

    private static void addInvoices ()
    {
        // Group 1: Fully Non-Taxable, no extra charges (items 15-29)
        invoices.add(new Invoice(customers.get(0), LocalDate.of(2024, 1, 1), 15, createItemTable(new Object[][]{{items.get(15), 2}}), 40.0, 0.0, 0.0, 40.0, 40.0, InvoiceHandler.Status.DRAFT));
        invoices.add(new Invoice(customers.get(1), LocalDate.of(2024, 1, 2), 30, createItemTable(new Object[][]{{items.get(16), 1}, {items.get(17), 2}}), 65.0, 0.0, 0.0, 65.0, 65.0, InvoiceHandler.Status.DRAFT));
        invoices.add(new Invoice(customers.get(2), LocalDate.of(2024, 1, 3), 10, createItemTable(new Object[][]{{items.get(18), 1}}), 20.0, 0.0, 0.0, 20.0, 20.0, InvoiceHandler.Status.DRAFT));
        invoices.add(new Invoice(customers.get(3), LocalDate.of(2024, 1, 4), 5, createItemTable(new Object[][]{{items.get(19), 2}}), 30.0, 0.0, 0.0, 30.0, 30.0, InvoiceHandler.Status.DRAFT));
        invoices.add(new Invoice(customers.get(4), LocalDate.of(2024, 1, 5), 0, createItemTable(new Object[][]{{items.get(20), 1}, {items.get(21), 1}}), 50.0, 0.0, 0.0, 50.0, 50.0, InvoiceHandler.Status.DRAFT));
        invoices.add(new Invoice(customers.get(5), LocalDate.of(2024, 1, 6), 12, createItemTable(new Object[][]{{items.get(22), 1}}), 18.0, 0.0, 0.0, 18.0, 18.0, InvoiceHandler.Status.DRAFT));
        invoices.add(new Invoice(customers.get(6), LocalDate.of(2024, 1, 7), 7, createItemTable(new Object[][]{{items.get(23), 3}}), 45.0, 0.0, 0.0, 45.0, 45.0, InvoiceHandler.Status.DRAFT));
        invoices.add(new Invoice(customers.get(7), LocalDate.of(2024, 1, 8), 14, createItemTable(new Object[][]{{items.get(24), 1}, {items.get(25), 1}}), 55.0, 0.0, 0.0, 55.0, 55.0, InvoiceHandler.Status.DRAFT));
        invoices.add(new Invoice(customers.get(0), LocalDate.of(2024, 1, 9), 10, createItemTable(new Object[][]{{items.get(26), 2}}), 60.0, 0.0, 0.0, 60.0, 60.0, InvoiceHandler.Status.DRAFT));
        invoices.add(new Invoice(customers.get(1), LocalDate.of(2024, 1, 10), 20, createItemTable(new Object[][]{{items.get(27), 1}}), 22.0, 0.0, 0.0, 22.0, 22.0, InvoiceHandler.Status.DRAFT));

        // Group 2: Fully Taxable (items 0-14), no extra charges
        invoices.add(new Invoice(customers.get(2), LocalDate.of(2024, 1, 11), 10, createItemTable(new Object[][]{{items.get(0), 2}}), 100.0, 0.0, 0.0, 100.0, 100.0, InvoiceHandler.Status.SENT));
        invoices.add(new Invoice(customers.get(3), LocalDate.of(2024, 1, 12), 5, createItemTable(new Object[][]{{items.get(1), 1}, {items.get(2), 1}}), 90.0, 0.0, 0.0, 90.0, 90.0, InvoiceHandler.Status.SENT));
        invoices.add(new Invoice(customers.get(4), LocalDate.of(2024, 1, 13), 30, createItemTable(new Object[][]{{items.get(3), 3}}), 150.0, 0.0, 0.0, 150.0, 150.0, InvoiceHandler.Status.SENT));
        invoices.add(new Invoice(customers.get(5), LocalDate.of(2024, 1, 14), 25, createItemTable(new Object[][]{{items.get(4), 2}}), 60.0, 0.0, 0.0, 60.0, 60.0, InvoiceHandler.Status.SENT));
        invoices.add(new Invoice(customers.get(6), LocalDate.of(2024, 1, 15), 15, createItemTable(new Object[][]{{items.get(5), 1}}), 300.0, 0.0, 0.0, 300.0, 300.0, InvoiceHandler.Status.SENT));
        invoices.add(new Invoice(customers.get(7), LocalDate.of(2024, 1, 16), 5, createItemTable(new Object[][]{{items.get(6), 2}}), 80.0, 0.0, 0.0, 80.0, 80.0, InvoiceHandler.Status.SENT));
        invoices.add(new Invoice(customers.get(8), LocalDate.of(2024, 1, 17), 0, createItemTable(new Object[][]{{items.get(7), 1}, {items.get(8), 1}}), 200.0, 0.0, 0.0, 200.0, 200.0, InvoiceHandler.Status.SENT));
        invoices.add(new Invoice(customers.get(9), LocalDate.of(2024, 1, 18), 18, createItemTable(new Object[][]{{items.get(9), 2}}), 90.0, 0.0, 0.0, 90.0, 90.0, InvoiceHandler.Status.SENT));
        invoices.add(new Invoice(customers.get(10), LocalDate.of(2024, 1, 19), 8, createItemTable(new Object[][]{{items.get(10), 1}}), 50.0, 0.0, 0.0, 50.0, 50.0, InvoiceHandler.Status.SENT));
        invoices.add(new Invoice(customers.get(11), LocalDate.of(2024, 1, 20), 22, createItemTable(new Object[][]{{items.get(11), 2}, {items.get(12), 1}}), 110.0, 0.0, 0.0, 110.0, 110.0, InvoiceHandler.Status.SENT));

        // Group 3: Mixed Taxable + Non-Taxable
        invoices.add(new Invoice(customers.get(12), LocalDate.of(2024, 1, 21), 15, createItemTable(new Object[][]{{items.get(0), 1}, {items.get(15), 1}}), 60.0, 0.0, 0.0, 60.0, 60.0, InvoiceHandler.Status.SENT));
        invoices.add(new Invoice(customers.get(13), LocalDate.of(2024, 1, 22), 10, createItemTable(new Object[][]{{items.get(2), 1}, {items.get(20), 1}}), 55.0, 0.0, 0.0, 55.0, 55.0, InvoiceHandler.Status.SENT));
        invoices.add(new Invoice(customers.get(14), LocalDate.of(2024, 1, 23), 5, createItemTable(new Object[][]{{items.get(4), 1}, {items.get(17), 1}}), 70.0, 0.0, 0.0, 70.0, 70.0, InvoiceHandler.Status.SENT));
        invoices.add(new Invoice(customers.get(15), LocalDate.of(2024, 1, 24), 20, createItemTable(new Object[][]{{items.get(6), 2}, {items.get(18), 1}}), 105.0, 0.0, 0.0, 105.0, 105.0, InvoiceHandler.Status.SENT));
        invoices.add(new Invoice(customers.get(16), LocalDate.of(2024, 1, 25), 12, createItemTable(new Object[][]{{items.get(10), 1}, {items.get(25), 1}}), 45.0, 0.0, 0.0, 45.0, 45.0, InvoiceHandler.Status.SENT));
        invoices.add(new Invoice(customers.get(0), LocalDate.of(2024, 1, 26), 14, createItemTable(new Object[][]{{items.get(13), 1}, {items.get(28), 2}}), 95.0, 0.0, 0.0, 95.0, 95.0, InvoiceHandler.Status.SENT));
        invoices.add(new Invoice(customers.get(1), LocalDate.of(2024, 1, 27), 8, createItemTable(new Object[][]{{items.get(7), 1}, {items.get(27), 1}}), 85.0, 0.0, 0.0, 85.0, 85.0, InvoiceHandler.Status.SENT));
        invoices.add(new Invoice(customers.get(2), LocalDate.of(2024, 1, 28), 5, createItemTable(new Object[][]{{items.get(9), 2}, {items.get(26), 1}}), 100.0, 0.0, 0.0, 100.0, 100.0, InvoiceHandler.Status.SENT));
        invoices.add(new Invoice(customers.get(3), LocalDate.of(2024, 1, 29), 20, createItemTable(new Object[][]{{items.get(12), 1}, {items.get(22), 1}}), 80.0, 0.0, 0.0, 80.0, 80.0, InvoiceHandler.Status.SENT));
        invoices.add(new Invoice(customers.get(4), LocalDate.of(2024, 1, 30), 10, createItemTable(new Object[][]{{items.get(14), 1}, {items.get(19), 1}}), 92.0, 0.0, 0.0, 92.0, 92.0, InvoiceHandler.Status.SENT));

        // Group 4: Extra Charges
        invoices.add(new Invoice(customers.get(5), LocalDate.of(2024, 2, 1), 5, createItemTable(new Object[][]{{items.get(0), 2}}), 100.0, 10.0, 20.0, 110.0, 110.0, InvoiceHandler.Status.SENT));
        invoices.add(new Invoice(customers.get(6), LocalDate.of(2024, 2, 2), 15, createItemTable(new Object[][]{{items.get(1), 1}}), 50.0, 5.0, 0.0, 45.0, 45.0, InvoiceHandler.Status.SENT));
        invoices.add(new Invoice(customers.get(7), LocalDate.of(2024, 2, 3), 7, createItemTable(new Object[][]{{items.get(2), 1}}), 60.0, 0.0, 15.0, 75.0, 75.0, InvoiceHandler.Status.SENT));
        invoices.add(new Invoice(customers.get(8), LocalDate.of(2024, 2, 4), 12, createItemTable(new Object[][]{{items.get(3), 1}, {items.get(24), 1}}), 40.0, 8.0, 10.0, 42.0, 42.0, InvoiceHandler.Status.SENT));
        invoices.add(new Invoice(customers.get(9), LocalDate.of(2024, 2, 5), 9, createItemTable(new Object[][]{{items.get(4), 3}}), 90.0, 0.0, 30.0, 120.0, 120.0, InvoiceHandler.Status.SENT));
    }

    public static Map<Item, Integer> createItemTable(Object[][] itemQtyPairs) {
        Map<Item, Integer> itemTable = new TreeMap<>((i1, i2) -> Integer.compare(i1.getItemNo(), i2.getItemNo()));
        for (Object[] pair : itemQtyPairs) {
            itemTable.put((Item) pair[0], (Integer) pair[1]);
        }
        return itemTable;
    }


}
