package client;

import javax.swing.*;
import bus.Customer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import data.CUSTOMERDB;
import data.DBConnection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class BankManager extends JFrame {

	// Updated createCustomer method
	private static boolean createCustomer(String firstName, String lastName, String pin, String email,
			String phoneNumber) {
		try {
			// Generate a unique customer number (for simplicity, using a random value)
			long customerNumber = generateUniqueCustomerNumber();

			// Create a new Customer object
			Customer newCustomer = new Customer(customerNumber, firstName, lastName, pin, email, phoneNumber);

			// Insert the new customer into the database
			CUSTOMERDB.insert(newCustomer);

			return true; // Customer created successfully
		} catch (SQLException e) {
			e.printStackTrace();
			return false; // Failed to create customer
		}
	}

	// Example method to generate a unique customer number
	private static long generateUniqueCustomerNumber() {
		return new Random().nextLong();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new BankManager().setVisible(true);
		});
	}

	public BankManager() {
		setTitle("Bank Manager");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create UI components
		JLabel nameLabel = new JLabel("Customer Name:");
		JTextField nameTextField = new JTextField();

		JLabel pinLabel = new JLabel("PIN:");
		JPasswordField pinPasswordField = new JPasswordField();

		JButton createCustomerButton = new JButton("Create Customer");

		// Set actions for the button
		createCustomerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Retrieve data from UI components
				String customerName = nameTextField.getText();
				String pin = new String(pinPasswordField.getPassword());

				// Call appropriate methods to handle data
				boolean customerCreated = createCustomer(customerName, "", pin, "", "");

				// Display a message or update UI as needed
				if (customerCreated) {
					JOptionPane.showMessageDialog(BankManager.this, "Customer created successfully!");

				} else {
					JOptionPane.showMessageDialog(BankManager.this, "Failed to create customer. Please try again.");

				}
			}
		});

		// Create layout
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(4, 2));
		panel.add(nameLabel);
		panel.add(nameTextField);
		panel.add(pinLabel);
		panel.add(pinPasswordField);
		panel.add(new JLabel()); // Empty label for spacing
		panel.add(createCustomerButton);

		// Add the panel to the frame
		add(panel);

		setSize(300, 200);
		setLocationRelativeTo(null); // Center the frame
	}
}
