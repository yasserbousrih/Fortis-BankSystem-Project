package data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import bus.Transaction;
import bus.EnumTransactionType;

@SuppressWarnings("unused")
public class TRANSACTIONDB {
    private static Connection connection;
    static {
        try {
			connection = DBConnection.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    public static void insert(Transaction transaction) throws SQLException {
        String sql = "INSERT INTO TRANSACTION VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, transaction.getTransactionNumber());
            statement.setString(2, transaction.getDescription());
            statement.setDate(3, transaction.getTransactionDate());
            statement.setString(4, transaction.getTransactionType().name());
            statement.setDouble(5, transaction.getAmount());
            statement.executeUpdate();
        }
    }

    public static void update(Transaction transaction) throws SQLException {
        String sql = "UPDATE TRANSACTION SET DESCRIPTION=?, TRANSACTION_DATE=?, TRANSACTION_TYPE=?, AMOUNT=?, ACCOUNT_NUMBER=? WHERE TRANSACTION_NUMBER=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
        	statement.setLong(1, transaction.getTransactionNumber());
            statement.setString(2, transaction.getDescription());
            statement.setDate(3, transaction.getTransactionDate());
            statement.setString(4, transaction.getTransactionType().name());
            statement.setDouble(5, transaction.getAmount());
            statement.executeUpdate();
        }
        }

    public static void delete(String transactionNumber) throws SQLException {
        String sql = "DELETE FROM TRANSACTION WHERE TRANSACTION_NUMBER=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, transactionNumber);

            statement.executeUpdate();
        }
    }

    public static Transaction getTransactionByNumber(String transactionNumber) throws SQLException {
        String sql = "SELECT * FROM TRANSACTION WHERE TRANSACTION_NUMBER=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, transactionNumber);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Transaction(
                    	    resultSet.getLong("TRANSACTION_NUMBER"),
                    	    resultSet.getString("DESCRIPTION"),
                    	    resultSet.getDouble("AMOUNT"),
                    	    resultSet.getDate("TRANSACTION_DATE"), 
                    	    EnumTransactionType.valueOf(resultSet.getString("TRANSACTION_TYPE"))
                    	);
                }
            }
        }

        return null; // Transaction not found
    }



    public static ArrayList<Transaction> getAllTransactions() throws SQLException {
        ArrayList<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM TRANSACTION";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Transaction transaction = new Transaction(
                	    resultSet.getLong("TRANSACTION_NUMBER"),
                	    resultSet.getString("DESCRIPTION"),
                	    resultSet.getDouble("AMOUNT"),
                	    resultSet.getDate("TRANSACTION_DATE"), 
                	    EnumTransactionType.valueOf(resultSet.getString("TRANSACTION_TYPE"))
                	);

                transactions.add(transaction);
            }
        }

        return transactions;
    }
}
