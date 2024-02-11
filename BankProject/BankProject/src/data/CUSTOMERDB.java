package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import bus.Customer;

public class CUSTOMERDB {
    private static Connection connection;

    static {
        try {
            connection = DBConnection.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insert(Customer customer) throws SQLException {
        String sql = "INSERT INTO CUSTOMER VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, customer.getCustomerNumber());
            statement.setString(2, customer.getFirstName());
            statement.setString(3, customer.getLastName());
            statement.setString(4, customer.getPin());
            statement.setString(5, customer.getEmail());
            statement.setString(6, customer.getPhoneNumber());

            statement.executeUpdate();
        }
    }

    public static void update(Customer customer) throws SQLException {
        String sql = "UPDATE CUSTOMER SET FIRST_NAME=?, LAST_NAME=?, PIN=?, EMAIL=?, PHONE_NUMBER=? WHERE IDENTIFICATION_NUMBER=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, customer.getCustomerNumber());
            statement.setString(2, customer.getFirstName());
            statement.setString(3, customer.getLastName());
            statement.setString(4, customer.getPin());
            statement.setString(5, customer.getEmail());
            statement.setString(6, customer.getPhoneNumber());
            
            statement.executeUpdate();
        }
    }

    public static void delete(String identificationNumber) throws SQLException {
        String sql = "DELETE FROM CUSTOMER WHERE IDENTIFICATION_NUMBER=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, identificationNumber);

            statement.executeUpdate();
        }
    }

    public static Customer getCustomerById(String identificationNumber) throws SQLException {
        String sql = "SELECT * FROM CUSTOMER WHERE IDENTIFICATION_NUMBER=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, identificationNumber);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Customer(
                            resultSet.getLong("CUSTOMER_NUMBER"),
                            resultSet.getString("FIRST_NAME"),
                            resultSet.getString("LAST_NAME"),
                            resultSet.getString("PIN"),
                            resultSet.getString("EMAIL"),
                            resultSet.getString("PHONE_NUMBER")
                    );
                }
            }
        }

        return null; // Customer not found
    }

    public static ArrayList<Customer> getAllCustomers() throws SQLException {
        ArrayList<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM CUSTOMER";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Customer customer = new Customer(
                        resultSet.getLong("CUSTOMER_NUMBER"),
                        resultSet.getString("FIRST_NAME"),
                        resultSet.getString("LAST_NAME"),
                        resultSet.getString("PIN"),
                        resultSet.getString("EMAIL"),
                        resultSet.getString("PHONE_NUMBER")
                );
                customers.add(customer);
            }
        }

        return customers;
    }
}
