package bank_access;

import mware_lib.IImplBase;

public abstract class ManagerImplBase implements IImplBase{
	
	public abstract String createAccount(String owner, String branch);

	public static ManagerImplBase narrowCast(Object rawObjectRef) {
		String reference = (String) rawObjectRef;
        String[] split = reference.split(":");
        String ip = split[0];
        int port = Integer.parseInt(split[1]);
        return new ManagerStub(ip, port, reference);
	}

	@Override
	public ManagerSkeleton getSkeleton() {
		return new ManagerSkeleton(this);
	}
}
