package bus;

import java.util.Comparator;
import java.util.Date;
import java.util.function.Predicate;

public class AccountPredicate {
    public static Predicate<Account> hasCustomerNumber(Long customerNumber) {
        return account -> account.getCustomerNumber() != null && account.getCustomerNumber().equals(customerNumber);
    }

    public static Predicate<Account> hasBalanceAbove(double threshold) {
        return account -> account.getBalance() > threshold;
    }

    public static Comparator<Account> sortByBalance() {
        return Comparator.comparing(Account::getBalance);
    }
}