package bus;

public interface IOperation {
	void withdraw(double amount) throws InsufficientFundException, NotInRangeException;
	void deposit(double amount) throws NotInRangeException;
    double checkBalance();
    void transfer(Account targetAccount, double amount) throws InsufficientFundException, NotInRangeException, NotNullException;
}