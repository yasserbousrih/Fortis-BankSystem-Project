package bus;

import java.util.ArrayList;
import java.util.List;

public class CustomerDataCollection {
    private static List<Customer> customers = new ArrayList<>();

    private CustomerDataCollection() {}

    public static void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public static void removeCustomer(Customer customer) {
        customers.remove(customer);
    }

    public static Customer getCustomerById(long customerNumber) {
        for (Customer customer : customers) {
            if (customer.getCustomerNumber() == customerNumber) {
                return customer;
            }
        }
        return null; // Customer not found
    }

    public static List<Customer> getAllCustomers() {
        return new ArrayList<>(customers);
    }
}
