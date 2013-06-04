package bank_access;

import mware_lib.Skeleton;

public class AccountSkeleton implements Skeleton {
	
	AccountImplBase object;
	
	public AccountSkeleton(AccountImplBase object) {
		this.object = object;	//Das Originale Objekt
	}

	@Override
	public String remoteInvoke(String message) {
		String part[] = message.split(":");
		
		if(part[0].equals("method")) {
			//Wenn es eine Methode ist schaue nach welche
			switch(part[1]) {
				default:
					return "exception:RuntimeException:Methode " + part[1] + " wurde nicht gefunden.";
				case "transfer":
					Double amount = Double.parseDouble(part[2]);
					try {
						object.transfer(amount);
						return "return:void";
					}catch(OverdraftException e) {
						return "exception:OverdraftException:"+e.getMessage();
					}
				case "getBalance":
					Double balance = object.getBalance();
					return "return:"+balance;
			}
		}else{
			//Wenn es keine Methode ist sondern ein return oder exception
			return "exception:RuntimeException:AccountSkeleton hat eine Methode erwartet und eine " + part[0] + " bekommen";
		}
	}
}
