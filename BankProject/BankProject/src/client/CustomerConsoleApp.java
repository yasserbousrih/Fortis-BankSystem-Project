package client;

import java.io.File;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import bus.Account;
import bus.CheckingAccount;
import bus.Customer;
import bus.EnumAccountType;
import bus.FileManager;
import bus.InsufficientFundException;
import bus.NotInRangeException;
import bus.NotNullException;
import bus.SavingsAccount;
import bus.Validator;
import data.*;

@SuppressWarnings("unused")
public class CustomerConsoleApp {
	private static ArrayList<Customer> customers = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
            runCustomerConsole();
        }

    private static void loadFromSerializedFile() throws IOException, ClassNotFoundException {
        File file = new File("bank.ser");

        if (file.exists()) {
            customers.addAll(FileManager.deSerializeCustomers());
        } else {
            System.out.println("Serialized file not found. Creating new empty ArrayList.");
        }
    }

    private static void saveToSerializedFile() throws IOException {
        FileManager.serializeCustomers(customers);
    }

    private static void runCustomerConsole() {
        int choice;
        do {
            displayMenu();
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    createCustomer(scanner);
                    break;
                case 2:
                    displayCustomers();
                    break;
                case 3:
                    openAccount(scanner);
                    break;
                case 4:
                    displayAccounts();
                    break;
                case 5:
                    performTransaction(scanner);
                    break;
                case 0:
                    try {
                        saveToSerializedFile();
                        System.out.println("Exiting the application. Data saved to 'bank.ser'. Goodbye!");
                    } catch (IOException e) {
                        System.out.println("Error saving data to serialized file: " + e.getMessage());
                    }
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\n===== Bank Management System =====");
        System.out.println("1. Create Customer");
        System.out.println("2. Display Customers");
        System.out.println("3. Open Account");
        System.out.println("4. Display Accounts");
        System.out.println("5. Perform Transaction");
        System.out.println("0. Exit");
    }

    private static void createCustomer(Scanner scanner) {
    	System.out.println("\n===== Create Customer =====");
        
        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter Customer Number(must be positive): ");
        long customerNumber = scanner.nextLong();
        scanner.nextLine();

        System.out.print("Enter PIN (4-digit): ");
        String pin = scanner.nextLine();

        System.out.print("Enter Email(valid email: example@example.example): ");
        String email = scanner.nextLine();

        System.out.print("Enter Phone Number(10-digit): ");
        String phoneNumber = scanner.nextLine();   
        
        if (!Validator.isValidCustomerNumber(String.valueOf(customerNumber)) ||
                !Validator.isValidPIN(pin) ||
                !Validator.isValidEmail(email) ||
                !Validator.isValidPhoneNumber(phoneNumber)) {
                System.out.println("Invalid input. Please check the customer details.");
                return;
            }

        Customer newCustomer = new Customer(customerNumber, firstName, lastName, pin, email, phoneNumber);

        customers.add(newCustomer);

        System.out.println("Customer created successfully!");
    }

    private static void displayCustomers() {
        System.out.println("\n===== Customers =====");
        for (Customer customer : customers) {
            System.out.println(customer);
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

    private static void openAccount(Scanner scanner) {
        System.out.println("\n===== Open Account =====");

        System.out.print("Enter Customer Number: ");
        long customerNumber = scanner.nextLong();
        scanner.nextLine();

        // Find the customer by customer number
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

        // Create a new account based on the chosen account type
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

        System.out.println("Account opened successfully!");
    }

    private static void displayAccounts() {
        System.out.println("\n===== Display Accounts =====");

        System.out.print("Enter Customer Number: ");
        long customerNumber = scanner.nextLong();
        scanner.nextLine(); 

        // Find the customer by customer number
        Customer customer = findCustomerByNumber(customerNumber);
        if (customer == null) {
            System.out.println("Customer not found. Please check the customer number.");
            return;
        }

        System.out.println("Accounts for " + customer.getFirstName() + " " + customer.getLastName() + ":");

        for (Account account : customer.getAccounts()) {
            System.out.println("Account Number: " + account.getAccountNumber());
            System.out.println("Account Type: " + account.getAccountType());
            System.out.println("Opened Date: " + account.getOpenedDate());
            System.out.println("Balance: " + account.getBalance());
            System.out.println("-----");
        }
    }

    private static void performTransaction(Scanner scanner) {
        System.out.println("\n===== Perform Transaction =====");

        System.out.print("Enter Customer Number: ");
        long customerNumber = scanner.nextLong();
        scanner.nextLine();

        // Find the customer by customer number
        Customer customer = findCustomerByNumber(customerNumber);
        if (customer == null) {
            System.out.println("Customer not found. Please check the customer number.");
            return;
        }

        System.out.print("Enter Account Number: ");
        long accountNumber = scanner.nextLong();
        scanner.nextLine();

        // Find the account by account number
        Account account = findAccountByNumber(customer, accountNumber);
        if (account == null) {
            System.out.println("Account not found. Please check the account number.");
            return;
        }

        System.out.println("Choose Transaction Type:");
        System.out.println("1. Withdraw");
        System.out.println("2. Deposit");
        System.out.println("3. Transfer");
        System.out.print("Enter your choice: ");
        int transactionTypeChoice = scanner.nextInt();
        scanner.nextLine();

        try {
            switch (transactionTypeChoice) {
                case 1:
                    // Withdraw
                    System.out.print("Enter Withdrawal Amount: ");
                    double withdrawAmount = scanner.nextDouble();
                    scanner.nextLine();
                    Validator.validateTransactionAmount(withdrawAmount);
                    account.withdraw(withdrawAmount);
                    System.out.println("Withdrawal successful. Updated balance: " + account.getBalance());
                    break;
                case 2:
                    // Deposit
                    System.out.print("Enter Deposit Amount: ");
                    double depositAmount = scanner.nextDouble();
                    scanner.nextLine();
                    Validator.validateTransactionAmount(depositAmount);
                    account.deposit(depositAmount);
                    System.out.println("Deposit successful. Updated balance: " + account.getBalance());
                    break;
                case 3:
                    // Transfer
                    System.out.print("Enter Target Account Number: ");
                    long targetAccountNumber = scanner.nextLong();
                    scanner.nextLine();

                    // Find the target account by account number
                    Account targetAccount = findAccountByNumber(customer, targetAccountNumber);
                    if (targetAccount == null) {
                        System.out.println("Target Account not found. Please check the account number.");
                        return;
                    }

                    System.out.print("Enter Transfer Amount: ");
                    double transferAmount = scanner.nextDouble();
                    scanner.nextLine();
                    Validator.validateTransactionAmount(transferAmount);

                    account.transfer(targetAccount, transferAmount);
                    System.out.println("Transfer successful. Updated balances - Source Account: "
                            + account.getBalance() + ", Target Account: " + targetAccount.getBalance());
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


}
