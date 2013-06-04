package cash_access;

import bank_access.ManagerImplBase;
import mware_lib.Skeleton;

public class TransactionSkeleton implements Skeleton {

	TransactionImplBase object;

	public TransactionSkeleton(TransactionImplBase object) {
		this.object = object; // Das Originale Objekt
	}

	@Override
	public String remoteInvoke(String message) {
		String part[] = message.split(":");

		if (part[0].equals("method")) {
			// Wenn es eine Methode ist schaue nach welche
			switch (part[1]) {
			default:
				return "exception:RuntimeException:Methode " + part[1]
						+ " wurde nicht gefunden.";
			case "deposit":
				object.deposit(part[2], Double.parseDouble(part[3]));
				return "return:void";
			case "withdraw":
				try {
					object.withdraw(part[2], Double.parseDouble(part[3]));
				} catch (NumberFormatException e) {
					return "exception:RuntimeException:Konnte String nicht in ein Double parsen.";
				} catch (OverdraftException e) {
					return "exception:OverdraftException:"+e.getMessage();
				}
				return "return:void";
			case "getBalance":
				Double balance = object.getBalance(part[2]);
				return "return:"+balance;
			}
		} else {
			// Wenn es keine Methode ist sondern ein return oder exception
			return "exception:RuntimeException:AccountSkeleton hat eine Methode erwartet und eine "
					+ part[0] + " bekommen";
		}
	}

}
