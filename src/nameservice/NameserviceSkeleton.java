package nameservice;

import mware_lib.Skeleton;

public class NameserviceSkeleton implements Skeleton {

	private Nameservice ns;

	public NameserviceSkeleton(Nameservice ns) {
		this.ns = ns;
	}

	@Override
	public String remoteInvoke(String message) {
		String part[] = message.split(":");

		if (part[0].equals("method")) {
			
			// welche Methode soll ausgefuehrt werden
			switch (part[1]) {
			case "rebind":
				ns.rebind(part[2], part[3]);
				return "return:void";
			case "resolve":
				String resolve = (String) ns.resolve(part[2]);
				return "return:" + resolve;
			default:
				return "exception:RuntimeException:Methode " + part[1]
						+ " wurde nicht gefunden.";
			}
			
		} else {
			// Exception, wenn das Schluesselwort method nicht übergeben wurde
			return "exception:RuntimeException:NameserviceSkeleton hat eine Methode erwartet und eine "
					+ part[0] + " bekommen";
		}
	}

}
