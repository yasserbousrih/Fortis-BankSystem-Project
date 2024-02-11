	package bus;
	
	import java.io.Serializable;
	import java.time.LocalDate;
	import java.util.ArrayList;
	import java.util.Date;
	import java.util.List;
	
	public abstract class Account implements IOperation, Serializable {
	    private static final long serialVersionUID = 1L;
	    private long accountNumber;
	    private Long customerNumber;
	    private Double balance;
	    private Date openedDate;
	    private EnumAccountType accountType;
	    private List<Transaction> transactions;
	
	    // Constructors
	    public Account(long accountNumber, Long customerNumber, Double balance, Date openedDate, EnumAccountType accountType) {
	        this.accountNumber = accountNumber;
	        this.customerNumber = customerNumber;
	        this.balance = balance;
	        this.openedDate = openedDate;
	        this.accountType = accountType;
	        this.transactions = new ArrayList<>();
	    }
	
	    // Getters
	    public long getAccountNumber() {
	        return accountNumber;
	    }
	
	    public Long getCustomerNumber() {
	        return customerNumber;
	    }
	
	    public Double getBalance() {
	        return balance;
	    }
	
	    public Date getOpenedDate() {
	        return openedDate;
	    }
	
	    public EnumAccountType getAccountType() {
	        return accountType;
	    }
	
	    public List<Transaction> getTransactions() {
	        return new ArrayList<>(transactions);
	    }
	
	    // Setters with validation
	    public void setAccountNumber(long accountNumber) {
	        this.accountNumber = accountNumber;
	    }
	
	    public void setCustomerNumber(Long customerNumber) throws NotNullException {
	        if (!Validator.isNotNull(customerNumber)) {
	            throw new NotNullException("Customer number cannot be null.");
	        }
	        this.customerNumber = customerNumber;
	    }
	
	    public void setBalance(Double balance) throws NotInRangeException {
	        if (!Validator.isValidAmount(balance)) {
	            throw new NotInRangeException("Balance must be a positive value.");
	        }
	        this.balance = balance;
	    }
	
	    public void setOpenedDate(Date openedDate) throws NotNullException {
	        if (!Validator.isNotNull(openedDate)) {
	            throw new NotNullException("Opened date cannot be null.");
	        }
	        this.openedDate = openedDate;
	    }
	
	    public void setAccountType(EnumAccountType accountType) throws NotNullException {
	        if (!Validator.isNotNull(accountType)) {
	            throw new NotNullException("Account type cannot be null.");
	        }
	        this.accountType = accountType;
	    }
	    
	    public void withdraw(double amount) throws InsufficientFundException, NotInRangeException {
	        if (!Validator.isValidAmount(amount)) {
	            throw new NotInRangeException("Withdrawal amount must be positive.");
	        }
	
	        if (balance - amount < 0) {
	            throw new InsufficientFundException("Insufficient funds for withdrawal.");
	        }
	
	        balance -= amount;
	        LocalDate transactionDate = LocalDate.now();
	        Transaction withdrawalTransaction = new Transaction(generateTransactionNumber(), "Withdrawal", amount, java.sql.Date.valueOf(transactionDate), EnumTransactionType.WITHDRAW);
	        transactions.add(withdrawalTransaction);
	    }
	
	    @Override
	    public void deposit(double amount) throws NotInRangeException {
	        if (!Validator.isValidAmount(amount)) {
	            throw new NotInRangeException("Deposit amount must be positive.");
	        }
	
	        balance += amount;
	        LocalDate transactionDate = LocalDate.now();
	        Transaction depositTransaction = new Transaction(generateTransactionNumber(), "Deposit", amount, java.sql.Date.valueOf(transactionDate), EnumTransactionType.DEPOSIT);
	        transactions.add(depositTransaction);
	    }
	
	
	    @Override
	    public double checkBalance() {
	        return balance;
	    }
	
	    @Override
	    public void transfer(Account targetAccount, double amount) throws InsufficientFundException, NotInRangeException, NotNullException {
	        if (targetAccount == null) {
	            throw new NotNullException("Target account cannot be null.");
	        }
	
	        withdraw(amount); 
	        targetAccount.deposit(amount); 
	    }
	
	    private long generateTransactionNumber() {
	        return System.currentTimeMillis();
	    }
	}