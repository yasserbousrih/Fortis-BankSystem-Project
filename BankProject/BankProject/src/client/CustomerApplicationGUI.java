package client;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import data.*;

public class CustomerApplicationGUI extends JFrame {

	private JTextField customerNumberField;
	private JTextField accountNumberField;
	private JTextArea outputArea;

	private CustomerConsoleApp consoleApp;

	private JFrame frame;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				CustomerApplicationGUI window = new CustomerApplicationGUI();
				window.frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public CustomerApplicationGUI() {
		consoleApp = new CustomerConsoleApp();
		initializeUI();
	}

	private void initializeUI() {
		setTitle("Bank Management System GUI");
		setSize(600, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		customerNumberField = new JTextField(20);
		accountNumberField = new JTextField(20);
		outputArea = new JTextArea(10, 50);
		outputArea.setEditable(false);

		JButton createCustomerButton = new JButton("Create Customer");
		JButton displayCustomersButton = new JButton("Display Customers");
		JButton openAccountButton = new JButton("Open Account");
		JButton displayAccountsButton = new JButton("Display Accounts");
		JButton performTransactionButton = new JButton("Perform Transaction");

		createCustomerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createCustomer();
			}
		});

		displayCustomersButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				displayCustomers();
			}
		});

		openAccountButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openAccount();
			}
		});

		displayAccountsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				displayAccounts();
			}
		});

		performTransactionButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				performTransaction();
			}
		});

		JPanel panel = new JPanel();
		panel.add(new JLabel("Customer Number:"));
		panel.add(customerNumberField);
		panel.add(createCustomerButton);
		panel.add(displayCustomersButton);

		panel.add(new JLabel("Account Number:"));
		panel.add(accountNumberField);
		panel.add(openAccountButton);
		panel.add(displayAccountsButton);

		panel.add(performTransactionButton);

		add(panel);
		add(new JScrollPane(outputArea));

		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		setResizable(false);
		setVisible(true);
	}
}
