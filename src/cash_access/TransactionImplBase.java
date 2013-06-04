package cash_access;

import bank_access.AccountSkeleton;
import mware_lib.IImplBase;

public abstract class TransactionImplBase implements IImplBase{

	public abstract void deposit(String accountID, double amount);

	public abstract void withdraw(String accountID, double amount) throws OverdraftException;

	public abstract double getBalance(String accountID);

	public static TransactionImplBase narrowCast(Object rawObjectRef) {
		// TODO
		return null;
	}

	@Override
	public TransactionSkeleton getSkeleton() {
		return new TransactionSkeleton(this);
	}
}
