package cash_access;

import mware_lib.IImplBase;

public abstract class TransactionImplBase implements IImplBase{

	public abstract void deposit(String accountID, double amount);

	public abstract void withdraw(String accountID, double amount) throws OverdraftException;

	public abstract double getBalance(String accountID);

	public static TransactionImplBase narrowCast(Object rawObjectRef) {
		String reference = (String) rawObjectRef;
        String[] split = reference.split(":");
        String ip = split[0];
        int port = Integer.parseInt(split[1]);
        return new TransactionStub(ip, port, reference);
	}

	@Override
	public TransactionSkeleton getSkeleton() {
		return new TransactionSkeleton(this);
	}
}
