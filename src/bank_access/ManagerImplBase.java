package bank_access;

import java.util.Arrays;

import mware_lib.IImplBase;

public abstract class ManagerImplBase implements IImplBase{
	
	public abstract String createAccount(String owner, String branch);

	public static ManagerImplBase narrowCast(Object rawObjectRef) {
//		System.out.println("ManagerImplBase - narrowCast aufgerufen mit Objektreferenz " + rawObjectRef);
		String reference = (String) rawObjectRef;
//		System.out.println("ManagerImplBase - narrowCast aufgerufen mit reference " + reference);
        String[] split = reference.split(";");
//        System.out.println("ManagerImplBase - reference aufgeteilt in " + Arrays.toString(split));
        String ip = split[0];
        int port = Integer.parseInt(split[1]);
//        System.out.println("ManagerImplBase - Stub wird erzeugt mit IP " + ip + " und Port " + port);
        return new ManagerStub(ip, port, reference);
	}

	@Override
	public ManagerSkeleton getSkeleton() {
		return new ManagerSkeleton(this);
	}
}
