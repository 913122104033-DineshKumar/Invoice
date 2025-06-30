import invoice.Customer;
import invoice.Invoice;
import invoice.Item;
import invoice.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        List<Customer> customers = new ArrayList<>();
        List<Item> items = new ArrayList<>();
        List<Invoice> invoices = new ArrayList<>();
        while (isRunning) {
            System.out.println("Enter Option 1 for Adding Customer: ");
            System.out.println("Enter Option 2 for Adding Item: ");
            System.out.println("Enter Option 3 for Creating Invoice: ");
            System.out.println("Enter Option 4 for Exiting the app...: ");
            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    System.out.println("Are you business man, if true enter Y: ");
                    char isBusiness = scanner.next().charAt(0);
                    if (isBusiness == 'Y') {

                    }
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    isRunning = false;
                    break;
                default:
                    break;
            }
        }
        scanner.close();
    }

}