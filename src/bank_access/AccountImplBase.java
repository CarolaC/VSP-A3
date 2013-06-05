package bank_access;

import mware_lib.IImplBase;

public abstract class AccountImplBase implements IImplBase {
	
	public abstract void transfer(double amount) throws OverdraftException;

	public abstract double getBalance();

	public static AccountImplBase narrowCast(Object rawObjectRef) {
		String reference = (String) rawObjectRef;
        String[] split = reference.split(";");
        String ip = split[0];
        int port = Integer.parseInt(split[1]);
        return new AccountStub(ip, port, reference);
	}
	
	@Override
	public AccountSkeleton getSkeleton() {
		return new AccountSkeleton(this);
	}

}
