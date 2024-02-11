package bus;
import java.util.Comparator;
import java.util.function.Predicate;

public class CustomerPredicate {
    public static Predicate<Customer> hasFirstName(String firstName) {
        return customer -> customer.getFirstName().equalsIgnoreCase(firstName);
    }

    public static Predicate<Customer> hasLastName(String lastName) {
        return customer -> customer.getLastName().equalsIgnoreCase(lastName);
    }

    public static Comparator<Customer> sortByLastName() {
        return Comparator.comparing(Customer::getLastName);
    }
}
