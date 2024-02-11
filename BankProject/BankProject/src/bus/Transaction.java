package bus;


import java.sql.Date;

public class Transaction {
    private long transactionNumber;
    private String description;
    private double amount;
    private Date transactionDate;
    private EnumTransactionType transactionType;

    public Transaction(long transactionNumber, String description, double amount, Date transactionDate, EnumTransactionType transactionType) {
        this.transactionNumber = transactionNumber;
        this.description = description;
        this.transactionDate = transactionDate;
        this.transactionType = transactionType;
        this.amount = amount;
    }
    public long getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(long transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public EnumTransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(EnumTransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
