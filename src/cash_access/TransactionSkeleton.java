package cash_access;

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
			switch (part[2]) {
			case "deposit":
//				System.out.println("TransactionSkeleton - deposit auf "+part[3]+" mit "+Double.parseDouble(part[4]));
				try {
					object.deposit(part[3], Double.parseDouble(part[4]));
				} catch(Exception e) {
					return "exception:RuntimeException:Fehler bei deposit";
				}
				return "return:void";
			case "withdraw":
				try {
					object.withdraw(part[3], Double.parseDouble(part[4]));
				} catch (NumberFormatException e) {
					return "exception:RuntimeException:Konnte String nicht in ein Double parsen.";
				} catch (Exception e) {
					return "exception:OverdraftException:" + e.getMessage();
				}
				return "return:void";
			case "getBalance":
				Double balance = object.getBalance(part[3]);
				return "return:" + balance;
			default:
				return "exception:RuntimeException:Methode " + part[2]
						+ " wurde nicht gefunden.";
			}
		} else {
			// Wenn es keine Methode ist, sondern ein return oder exception
			return "exception:RuntimeException:TransactionSkeleton hat eine Methode erwartet und eine "
					+ part[0] + " bekommen";
		}
	}

}
