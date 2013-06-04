package nameservice;

import bank_access.OverdraftException;
import mware_lib.Skeleton;

public class NameserviceSkeleton implements Skeleton {

	private Nameservice ns;
	
	public NameserviceSkeleton(Nameservice ns) {
		this.ns = ns;
	}
	
	@Override
	public String remoteInvoke(String message) {
		String part[] = message.split(":");
		
		if(part[0].equals("method")) {
			//Wenn es eine Methode ist schaue nach welche
			switch(part[2]) {
				default:
					return "exception:RuntimeException:Methode " + part[1] + " wurde nicht gefunden.";
				case "rebind":
					ns.rebind(part[3], part[4]);
					return "return:void";
				case "resolve":
					String resolve = (String)ns.resolve(part[3]);
					return "return:"+resolve;
			}
		}else{
			//Wenn es keine Methode ist sondern ein return oder exception
			return "exception:RuntimeException:AccountSkeleton hat eine Methode erwartet und eine " + part[0] + " bekommen";
		}
	}

}
