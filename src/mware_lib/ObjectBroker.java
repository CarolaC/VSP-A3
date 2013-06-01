package mware_lib;

public class ObjectBroker {

	// - Front-End der Middleware -

	// Das hier zurückgelieferte Objekt soll der zentrale Einstiegspunkt
	// der Middleware aus Anwendersicht sein.
	// Parameter: Host und Port, bei dem die Dienste (Namensdienst)
	// kontaktiert werden sollen.
	public static ObjectBroker init(String serviceHost, int listenPort) {
		// TODO
	}
	
	// Liefert den Namensdienst (Stellvetreterobjekt).
	public NameService getNameService() {
		// TODO
	}

	// Beendet die Benutzung der Middleware in dieser Anwendung.
	public void shutDown() {
		// TODO
	}
	
}
