package mware_lib;

public class ObjectBroker {
	
	// - Front-End der Middleware -
	
	private String serviceHost;
	private int listenPort;
	
	private ObjectBroker(String serviceHost, int listenPort) {
		this.serviceHost = serviceHost;
		this.listenPort = listenPort;
	}

	// Das hier zurückgelieferte Objekt soll der zentrale Einstiegspunkt
	// der Middleware aus Anwendersicht sein.
	// Parameter: Host und Port, bei dem die Dienste (Namensdienst)
	// kontaktiert werden sollen.
	public static ObjectBroker init(String serviceHost, int listenPort) {
		return new ObjectBroker(serviceHost, listenPort);
	}
	
	// Liefert den Namensdienst (Stellvetreterobjekt).
	public Nameservice getNameService() {
		return new NameserviceStub(serviceHost, listenPort);
	}

	// Beendet die Benutzung der Middleware in dieser Anwendung.
	public void shutDown() {
		// TODO
	}
	
}
