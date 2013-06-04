package bank_access;

import mware_lib.IImplBase;

public abstract class AccountImplBase implements IImplBase {
	
	public abstract void transfer(double amount) throws OverdraftException;

	public abstract double getBalance();

	public static AccountImplBase narrowCast(Object rawObjectRef) {
		return null;//return new AccountStub(rawObjRef);
	}
	
	@Override
	public AccountSkeleton getSkeleton() {
		return new AccountSkeleton(this);
	}

}
