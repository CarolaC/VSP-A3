package mware_lib;

import java.io.IOException;
import java.net.UnknownHostException;

import mware_lib.Kommunikationsmodul.*;

public class NameserviceStub extends Nameservice {

	private String nameservice_host; // IP des entfernten Namensdienstes
	private int nameservice_port; // Port des entfernten Namensdienstes
	private Referenzmodul referenzmodul; // Referenzmodul (Objektreferenz -> Skeleton)

	public NameserviceStub(String host, int port, Referenzmodul referenzmodul) {
		this.nameservice_host = host;
		this.nameservice_port = port;
		this.referenzmodul = referenzmodul;
	}

	@Override
	public void rebind(Object servant, String name) {
		try {
			// erzeuge Objektreferenz
			String objRef = "";
			// erzeuge Methoden-Aufruf String
			String request = "methode:rebind:" + objRef + ":" + name;
			// neuen Sender erzeugen
			Client sender = new Client(this.nameservice_host,
					this.nameservice_port);
			// String absenden
			sender.send(request);
			
			this.referenzmodul.putSkeleton(objRef, ((IImplBase)servant).getSkeleton());
		} catch (UnknownHostException e) {
			throw new RuntimeException("Exception: Konnte keine Verbindung zu "
					+ this.nameservice_host + ":" + this.nameservice_port
					+ " herstellen.");
		} catch (IOException e) {
			throw new RuntimeException("Exception: Konnte keine Verbindung zu "
					+ this.nameservice_host + ":" + this.nameservice_port
					+ " herstellen.");
		}
	}

	@Override
	public Object resolve(String name) {
		try {
			// erzeuge Methoden-Aufruf String
			String request = "methode:resolve:" + name;
			// neuen Sender erzeugen
			Client sender = new Client(this.nameservice_host,
					this.nameservice_port);
			// String absenden
			sender.send(request);
			// Objektreferenz vom Namensdienst zurückbekommen
			String string = sender.receive();

			String[] blocks = string.split(":");
			if (blocks[0].equals("return")) {
				return (Object) blocks[1];
			} else {
				return null;
			}
		} catch (UnknownHostException e) {
			throw new RuntimeException("Exception: Konnte keine Verbindung zu "
					+ this.nameservice_host + ":" + this.nameservice_port
					+ " herstellen.");
		} catch (IOException e) {
			throw new RuntimeException("Exception: Konnte keine Verbindung zu "
					+ this.nameservice_host + ":" + this.nameservice_port
					+ " herstellen.");
		}
	}
}
