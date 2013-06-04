package cash_access;

public abstract class TransactionImplBase {

	public abstract void deposit(String accountID, double amount);

	public abstract void withdraw(String accountID, double amount) throws OverdraftException;

	public abstract double getBalance(String accountID);

	public static TransactionImplBase narrowCast(Object rawObjectRef) {
		// TODO
	}

}
