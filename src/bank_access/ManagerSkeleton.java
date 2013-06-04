package bank_access;

import mware_lib.Skeleton;

public class ManagerSkeleton implements Skeleton {

	ManagerImplBase object;

	public ManagerSkeleton(ManagerImplBase object) {
		this.object = object; // Das originale Objekt
	}

	@Override
	public String remoteInvoke(String message) {
		String part[] = message.split(":");

		if (part[0].equals("method")) {
			// Wenn es eine Methode ist, schaue nach welche

			switch (part[2]) {
			case "createAccount":
				String account = object.createAccount(part[3], part[4]);
				return "return:" + account;
			default:
				return "exception:RuntimeException:Methode " + part[2]
						+ " wurde nicht gefunden.";
			}
		} else {
			// Wenn es keine Methode ist, sondern ein return oder exception
			return "exception:RuntimeException:ManagerSkeleton hat eine Methode erwartet und eine "
					+ part[0] + " bekommen";
		}
	}
}
