package bus;

import java.sql.Date;

public class SavingsAccount extends Account {
    private double annualInterestRate;
    private double annualGain;

    // Constructors
    public SavingsAccount(long accountNumber, long customerNumber, double balance, Date openedDate, EnumAccountType accountType,
            double annualInterestRate, double annualGain) {
				super(accountNumber, customerNumber, balance, openedDate, accountType);
				this.annualInterestRate = annualInterestRate;
				this.annualGain = annualGain;
    		}
    // Getters and Setters
    public double getAnnualInterestRate() {
        return annualInterestRate;
    }

    public double getAnnualGain() {
        return annualGain;
    }
}
