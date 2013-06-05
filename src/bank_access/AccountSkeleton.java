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
//				System.out.println("AccountSkeleton - Amount: " + amount);
				try {
//					System.out.println("object.transfer("+amount+");");
					object.transfer(amount);
					return "return:void";
				} catch (Exception e) {
					return "exception:OverdraftException:" + e.getMessage();
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
