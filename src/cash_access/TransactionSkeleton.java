package cash_access;

import bank_access.ManagerImplBase;
import mware_lib.Skeleton;

public class TransactionSkeleton implements Skeleton {

	TransactionImplBase object;
	
	public TransactionSkeleton(TransactionImplBase object) {
		this.object = object;	//Das Originale Objekt
	}
	
	@Override
	public String remoteInvoke(String message) {
		String part[] = message.split(":");
		
		if(part[0].equals("method")) {
			//Wenn es eine Methode ist schaue nach welche
			switch(part[1]) {
				default:
					return "exception:RuntimeException:Methode "+part[1]+" wurde nicht gefunden.";
				case "createAccount":
					String account = object.createAccount(part[2], part[3]);
					return "return:"+account;
			}
		}else{
			//Wenn es keine Methode ist sondern ein return oder exception
			return "exception:RuntimeException:AccountSkeleton hat eine Methode erwartet und eine "+part[0]+" bekommen";
		}
	}

}
