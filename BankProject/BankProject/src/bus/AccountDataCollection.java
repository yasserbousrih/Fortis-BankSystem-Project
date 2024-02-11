package bus;

import java.util.ArrayList;
import java.util.List;

public class AccountDataCollection {
    private static List<Account> accounts = new ArrayList<>();

    public static void addAccount(Account account) {
        accounts.add(account);
    }

    public static void removeAccount(Account account) {
        accounts.remove(account);
    }

    public static Account getAccountById(long accountId) {
        for (Account account : accounts) {
            if (account.getAccountNumber() == accountId) {
                return account;
            }
        }
        return null;
    }
}