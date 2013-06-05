package mware_lib;

import java.io.IOException;

import mware_lib.Kommunikationsmodul.ReceiverManager;
import mware_lib.Kommunikationsmodul.Server;

public class ObjectBroker {

	// - Front-End der Middleware -

	private String serviceHost; // IP-Adresse des Nameservice
	private int listenPort; // Port des Nameservice
	private NameserviceStub ns;
	private Referenzmodul referenzmodul; // Referenzmodul
	private int port = 20000; // Port des Empfaengers

	private ReceiverManager receiverManager;

	private ObjectBroker(String serviceHost, int listenPort) {
		this.serviceHost = serviceHost;
		this.listenPort = listenPort;
		this.referenzmodul = new Referenzmodul();
	}

	// Das hier zur�ckgelieferte Objekt soll der zentrale Einstiegspunkt
	// der Middleware aus Anwendersicht sein.
	// Parameter: Host und Port, bei dem die Dienste (Namensdienst)
	// kontaktiert werden sollen.
	public static ObjectBroker init(String serviceHost, int listenPort) {
		// Erzeuge ein neues Broker Objekt
		ObjectBroker objBroker = new ObjectBroker(serviceHost, listenPort);
		System.out.println("ObjectBroker - ObjectBroker erzeugt");
		// Starte den Receiver Manager
		objBroker.startManager();
		System.out.println("ObjectBroker - receiverManager gestartet");
		return objBroker;
	}

	// startet beliebig viele ReceiverThread
	public void startManager() {
		try {
			Server server = new Server(port);
			this.receiverManager = new ReceiverManager(server, this);
			this.receiverManager.start();
			System.out.println("ReceiverManager lauscht auf Port " + port);
		} catch (IOException e) {
			port++;
			startManager();
		}
	}

	// Liefert den Namensdienst (Stellvetreterobjekt).
	public NameService getNameService() {
		System.out.println("ObjectBroker - getNameservice aufgerufen");
		return (ns == null ? new NameserviceStub(serviceHost, listenPort, referenzmodul,port) : ns);
	}

	public Referenzmodul getReferenzmodul() {
		return this.referenzmodul;
	}

	// Beendet die Benutzung der Middleware in dieser Anwendung.
	public void shutDown() {

	}

}
