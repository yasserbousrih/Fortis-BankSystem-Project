package data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import bus.Account;
import bus.CheckingAccount;
import bus.EnumAccountType;
import bus.SavingsAccount;

public class ACCOUNTDB {
    private static Connection connection;

    static {
        try {
            connection = DBConnection.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insert(Account account) throws SQLException {
        String sql = "INSERT INTO ACCOUNT VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, account.getAccountNumber());
            statement.setLong(2, account.getCustomerNumber());
            statement.setString(3, account.getAccountType().name());
            statement.setDate(4, (Date) account.getOpenedDate());
            statement.setDouble(5, account.getBalance());

            statement.executeUpdate();
        }
    }

    public static void update(Account account) throws SQLException {
        String sql = "UPDATE ACCOUNT SET ACCOUNT_TYPE=?, OPENED_DATE=?, BALANCE=? WHERE ACCOUNT_NUMBER=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, account.getAccountType().name());
            statement.setDate(2, (Date) account.getOpenedDate());
            statement.setDouble(3, account.getBalance());
            statement.setLong(4, account.getAccountNumber());

            statement.executeUpdate();
        }
    }

    public static void delete(String accountNumber) throws SQLException {
        String sql = "DELETE FROM ACCOUNT WHERE ACCOUNT_NUMBER=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, accountNumber);

            statement.executeUpdate();
        }
    }

    public static Account getAccountByNumber(String accountNumber) throws SQLException {
        String sql = "SELECT * FROM ACCOUNT WHERE ACCOUNT_NUMBER=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, accountNumber);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String accountType = resultSet.getString("ACCOUNT_TYPE");
                    if ("CHECKING".equals(accountType)) {
                        return new CheckingAccount(
                                resultSet.getLong("ACCOUNT_NUMBER"),
                                resultSet.getLong("CUSTOMER_NUMBER"),
                                resultSet.getDouble("BALANCE"),
                                resultSet.getDate("OPENED_DATE"),
                                EnumAccountType.valueOf(accountType),
                                resultSet.getInt("FREE_TRANSACTION_LIMIT"),
                                resultSet.getDouble("TRANSACTION_FEE")
                        );
                    } else if ("SAVING".equals(accountType)) {
                        return new SavingsAccount(
                                resultSet.getLong("ACCOUNT_NUMBER"),
                                resultSet.getLong("CUSTOMER_NUMBER"),
                                resultSet.getDouble("BALANCE"),
                                resultSet.getDate("OPENED_DATE"),
                                EnumAccountType.valueOf(accountType),
                                resultSet.getDouble("ANNUAL_INTEREST_RATE"),
                                resultSet.getDouble("ANNUAL_GAIN")
                        );
                    }
                }
            }
        }
        return null; // Account not found
    }

    public static ArrayList<Account> getAllAccounts() throws SQLException {
        ArrayList<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM ACCOUNT";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                String accountType = resultSet.getString("ACCOUNT_TYPE");
                if ("CHECKING".equals(accountType)) {
                    accounts.add(new CheckingAccount(
                            resultSet.getLong("ACCOUNT_NUMBER"),
                            resultSet.getLong("CUSTOMER_NUMBER"),
                            resultSet.getDouble("BALANCE"),
                            resultSet.getDate("OPENED_DATE"),
                            EnumAccountType.valueOf(accountType),
                            resultSet.getInt("FREE_TRANSACTION_LIMIT"),
                            resultSet.getDouble("TRANSACTION_FEE")
                    ));
                } else if ("SAVING".equals(accountType)) {
                    accounts.add(new SavingsAccount(
                            resultSet.getLong("ACCOUNT_NUMBER"),
                            resultSet.getLong("CUSTOMER_NUMBER"),
                            resultSet.getDouble("BALANCE"),
                            resultSet.getDate("OPENED_DATE"),
                            EnumAccountType.valueOf(accountType),
                            resultSet.getDouble("ANNUAL_INTEREST_RATE"),
                            resultSet.getDouble("ANNUAL_GAIN")
                    ));
                }
            }
        }

        return accounts;
    }
}
