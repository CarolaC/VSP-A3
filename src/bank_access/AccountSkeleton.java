package bank_access;

import mware_lib.Skeleton;

public class AccountSkeleton implements Skeleton {

	AccountImplBase object;

	public AccountSkeleton(AccountImplBase object) {
		this.object = object; // Das originale Objekt
	}

	@Override
	public String remoteInvoke(String message) {
		String part[] = message.split(":");

		if (part[0].equals("method")) {

			// Wenn es eine Methode ist, schaue nach welche
			switch (part[2]) {
			case "transfer":
				Double amount = Double.parseDouble(part[3]);
				System.out.println("AccountSkeleton - Amount: " + amount);
				try {
					object.transfer(amount);
					return "return:void";
				} catch (OverdraftException e) {
					return "exception:OverdraftException:" + e.getMessage();
				} catch(RuntimeException e) {
					return "exception:RuntimeException:Konnte Transfer nicht ausführen";
				}
			case "getBalance":
				Double balance = object.getBalance();
				return "return:" + balance;
			default:
				return "exception:RuntimeException:Methode " + part[2]
						+ " wurde nicht gefunden.";
			}
		} else {
			// Wenn es keine Methode ist, sondern ein return oder exception
			return "exception:RuntimeException:AccountSkeleton hat eine Methode erwartet und eine "
					+ part[0] + " bekommen";
		}
	}
}
