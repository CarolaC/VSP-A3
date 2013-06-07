package nameservice;

import mware_lib.Skeleton;

public class NameserviceSkeleton implements Skeleton {

	private NameService ns;

	public NameserviceSkeleton(NameService ns) {
		this.ns = ns;
	}

	@Override
	public String remoteInvoke(String message) {
		String part[] = message.split(":");

		if (part[0].equals("method")) {
			
			// welche Methode soll ausgefuehrt werden
			switch (part[1]) {
			case "rebind":
				System.out.println("NameserviceSkeleton - rebind auf Nameservice aufrufen mit Params: " + part[2] + " und " + part[3]);
				ns.rebind(part[2], part[3]);
				System.out.println("NameserviceSkeleton - rebind erfolgt");
				return "return:void";
			case "resolve":
				System.out.println("NameserviceSkeleton - resolve auf Nameservice aufrufen mit Params: " + part[2]);
				String resolve = (String) ns.resolve(part[2]);
				System.out.println("NameserviceSkeleton - resolve erfolgt: " + resolve);
				return "return:" + resolve;
			default:
				System.out.println("NameserviceSkeleton - Exception");
				return "exception:RuntimeException:Methode " + part[1]
						+ " wurde nicht gefunden.";
			}
			
		} else {
			// Exception, wenn das Schluesselwort method nicht �bergeben wurde
			return "exception:RuntimeException:NameserviceSkeleton hat eine Methode erwartet und eine "
					+ part[0] + " bekommen";
		}
	}

}
