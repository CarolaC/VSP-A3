package bank_access;

import mware_lib.IImplBase;

public abstract class ManagerImplBase implements IImplBase{
	
	public abstract String createAccount(String owner, String branch);

	public static ManagerImplBase narrowCast(Object rawObjectRef) {
		return null;
	}

	@Override
	public ManagerSkeleton getSkeleton() {
		return new ManagerSkeleton(this);
	}
}
