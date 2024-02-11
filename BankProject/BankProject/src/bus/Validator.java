package bus;

public class Validator {
	public static boolean isNullOrEmpty(String value) {
	        return value == null || value.trim().isEmpty();
	    }

	public static boolean isNotNull(Object value) {
	        return value != null;
	    }

    public static boolean isValidCustomerNumber(String customerNumber) {
        return customerNumber != null && !customerNumber.isEmpty();
    }

    public static boolean isValidPIN(String pin) {       
        return pin != null && pin.matches("\\d{4}"); 
    }

    public static boolean isValidEmail(String email) {       
        return email != null && email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}$");
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        
        return phoneNumber != null && phoneNumber.matches("\\d{10}"); 
    }

    public static boolean isValidAccountNumber(String accountNumber) {
        
        return accountNumber != null && !accountNumber.isEmpty();
    }

    public static boolean isValidAccountType(String accountType) {
        return accountType != null && (accountType.equalsIgnoreCase("CHECKING") || accountType.equalsIgnoreCase("SAVING"));
    }

    public static boolean isValidDate(String date) {
        return date != null && date.matches("\\d{4}-\\d{2}-\\d{2}");
    }

    public static boolean isValidAmount(double amount) {        
        return amount >= 0;
    }
    public static void validateTransactionAmount(double amount) throws NotNullException, InsufficientFundException, NotInRangeException  {
        if (!Validator.isNotNull(amount)) {
            throw new NotNullException("Transaction amount cannot be null.");
        }

        if (amount < 0) {
            throw new InsufficientFundException("Transaction amount must be a positive value.");
        }

        if (amount > 50000) {
            throw new NotInRangeException("Transaction amount cannot exceed 50,000.");
        }       
    }
}
