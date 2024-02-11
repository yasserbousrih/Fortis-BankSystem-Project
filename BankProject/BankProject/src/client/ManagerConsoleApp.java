package client;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import bus.Account;
import bus.AccountPredicate;
import bus.Customer;
import bus.CustomerPredicate;
import bus.EnumAccountType;
import bus.FileManager;
import bus.SavingsAccount;
import bus.CheckingAccount;

public class ManagerConsoleApp {

    private static final ArrayList<Customer> customers = new ArrayList<>();
    private static final ArrayList<Account> accounts = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

            runManagerConsole();

        }

    private static void loadFromSerializedFile() throws IOException, ClassNotFoundException {
        File file = new File("bank.ser");
        
        if (file.exists()) {
            customers.addAll(FileManager.deSerializeCustomers());
            accounts.addAll(FileManager.deSerializeAccounts());
        } else {
            System.out.println("Serialized file not found. Creating new empty ArrayLists.");
        }
    }

    private static void saveToSerializedFile() throws IOException {
        FileManager.serializeCustomers(customers);
        FileManager.serializeAccounts(accounts);
    }

    private static void runManagerConsole() {
        int choice;
        do {
            displayMenu();
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            executeChoice(choice);
        } while (choice != 0);

        try {
            saveToSerializedFile();
            System.out.println("Exiting Manager Console. Data saved to 'bank.ser'.");
        } catch (IOException e) {
            System.out.println("Error saving data to serialized file: " + e.getMessage());
        }
    }

    private static void displayMenu() {
        System.out.println("===== Manager Console Menu =====");
        System.out.println("1. Open an Account");
        System.out.println("2. Delete an Account");
        System.out.println("3. Sort Customers by Last Name");
        System.out.println("4. Sort Accounts by Balance");
        System.out.println("0. Exit");
    }

    private static void executeChoice(int choice) {
        switch (choice) {
            case 1:
                openAccount();
                break;
            case 2:
                deleteAccount();
                break;
            case 3:
                sortCustomersByLastName();
                break;
            case 4:
                sortAccountsByBalance();
                break;
            case 0:
                System.out.println("Exiting Manager Console. Goodbye!");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void openAccount() {
        System.out.println("===== Open Account =====");

        System.out.print("Enter Customer Number: ");
        long customerNumber = scanner.nextLong();
        scanner.nextLine();

        Customer customer = findCustomerByNumber(customerNumber);
        if (customer == null) {
            System.out.println("Customer not found. Please check the customer number.");
            return;
        }

        System.out.print("Enter Account Number: ");
        long accountNumber = scanner.nextLong();
        scanner.nextLine();

        System.out.println("Choose Account Type:");
        System.out.println("1. Checking Account");
        System.out.println("2. Saving Account");
        System.out.print("Enter your choice: ");
        int accountTypeChoice = scanner.nextInt();
        scanner.nextLine();

        EnumAccountType accountType = (accountTypeChoice == 1) ? EnumAccountType.CHECKING : EnumAccountType.SAVINGS;

        Account newAccount;
        if (accountType == EnumAccountType.CHECKING) {
            System.out.print("Enter Initial Balance: ");
            double initialBalance = scanner.nextDouble();
            scanner.nextLine();
            LocalDate openedDate = LocalDate.now();
            newAccount = new CheckingAccount(accountNumber, customerNumber, initialBalance, java.sql.Date.valueOf(openedDate), accountType, 3, 1.5);
        } else {
            LocalDate openedDate = LocalDate.now();
            newAccount = new SavingsAccount(accountNumber, customerNumber, 1000.0, java.sql.Date.valueOf(openedDate), accountType, 0.02, 0.0);
        }

        customer.getAccounts().add(newAccount);
        accounts.add(newAccount);

        System.out.println("Account opened successfully!");
    }

    private static void deleteAccount() {
        System.out.println("===== Delete Account =====");

        System.out.print("Enter Customer Number: ");
        long customerNumber = scanner.nextLong();
        scanner.nextLine();

        Customer customer = findCustomerByNumber(customerNumber);
        if (customer == null) {
            System.out.println("Customer not found. Please check the customer number.");
            return;
        }

        System.out.print("Enter Account Number: ");
        long accountNumber = scanner.nextLong();
        scanner.nextLine();

        Account account = findAccountByNumber(customer, accountNumber);
        if (account == null) {
            System.out.println("Account not found. Please check the account number.");
            return;
        }

        customer.getAccounts().remove(account);
        accounts.remove(account);

        System.out.println("Account deleted successfully!");
    }

    private static void sortCustomersByLastName() {
        System.out.println("===== Sort Customers by Last Name =====");

        Collections.sort(customers, CustomerPredicate.sortByLastName());

        System.out.println("Customers sorted by last name:");
        for (Customer customer : customers) {
            System.out.println(customer.getLastName() + ", " + customer.getFirstName());
        }
    }

    private static void sortAccountsByBalance() {
        System.out.println("===== Sort Accounts by Balance =====");

        Collections.sort(accounts, AccountPredicate.sortByBalance());

        System.out.println("Accounts sorted by balance:");
        for (Account account : accounts) {
            System.out.println("Account Number: " + account.getAccountNumber() + ", Balance: " + account.getBalance());
        }
    }

    private static Customer findCustomerByNumber(long customerNumber) {
        for (Customer customer : customers) {
            if (customer.getCustomerNumber() == customerNumber) {
                return customer;
            }
        }
        return null;
    }

    private static Account findAccountByNumber(Customer customer, long accountNumber) {
        for (Account account : customer.getAccounts()) {
            if (account.getAccountNumber() == accountNumber) {
                return account;
            }
        }
        return null;
    }
}
