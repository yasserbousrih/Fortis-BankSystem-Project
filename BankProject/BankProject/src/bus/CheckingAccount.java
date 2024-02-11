package bus;

import java.sql.Date;

public class CheckingAccount extends Account {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1026661060956524725L;
	private int freeTransactionLimit;
    private double transactionFee;

    // Constructors
    public CheckingAccount(long accountNumber, long customerNumber, double balance, Date openedDate, EnumAccountType accountType,
            int freeTransactionLimit, double transactionFee) {
				super(accountNumber, customerNumber, balance, openedDate, accountType);
				this.freeTransactionLimit = freeTransactionLimit;
				this.transactionFee = transactionFee;
    		}

    // Getters and Setters
    public int getFreeTransactionLimit() {
        return freeTransactionLimit;
    }

    public double getTransactionFee() {
        return transactionFee;
    }
}
