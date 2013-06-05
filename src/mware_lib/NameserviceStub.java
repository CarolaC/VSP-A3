package mware_lib;

import java.io.IOException;
import java.net.UnknownHostException;
import mware_lib.Kommunikationsmodul.*;

public class NameserviceStub extends Nameservice {

	private String nameservice_host; 	 // IP des entfernten Namensdienstes
	private int nameservice_port; 		 // Port des entfernten Namensdienstes
	private Referenzmodul referenzmodul; // Referenzmodul (Objektreferenz -> Skeleton)
	private int receiverPort;			 // Port des Empfaengers

	public NameserviceStub(String host, int port, Referenzmodul referenzmodul, int receiverPort) {
		System.out.println("NameserviceStub - wird erzeugt");
		this.nameservice_host = host;
		this.nameservice_port = port;
		this.referenzmodul = referenzmodul;
		this.receiverPort = receiverPort;
	}

	@Override
	public void rebind(Object servant, String name) {
		try {
			// Objektreferenz als Stringrepraesentation
			String objRefString = "";
			objRefString += java.net.InetAddress.getLocalHost().getHostAddress();
			objRefString += "|" + this.receiverPort;
			objRefString += "|" + servant.getClass();
			
			// erzeuge Methoden-Aufruf String
			String request = "methode:rebind:" + objRefString + ":" + name + "\n";
			// neuen Sender erzeugen
			Client client = new Client(this.nameservice_host,
					this.nameservice_port);
			// String absenden
			client.send(request);
						
			String answer = client.receive();
			String[] blocks = answer.split(":");
			
			switch (blocks[0]) {
			case "return":
				// Beende die Verbindung
				client.close();

				if (blocks[1].equals("void")) {

					// Skeleton zur Objektreferenz im Referenzmodul eintragen
					this.referenzmodul.putSkeleton(servant, ((IImplBase)servant).getSkeleton());	
					
					return;
				} else {
					throw new RuntimeException("Exception: Falscher Datentyp");
				}
			case "exception":
				// Beende die Verbindung
				client.close();

				if (blocks[1].equals("RuntimeException")) {
					throw new RuntimeException(blocks[2]);
				} else {
					throw new RuntimeException("Unbekannter Fehler");
				}
			default:
				client.close();
				throw new RuntimeException("Exception: Falscher Datentyp"); 
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

	@Override
	public Object resolve(String name) {
		try {
			// erzeuge Methoden-Aufruf String
			String request = "methode:resolve:" + name + "\n";
			// neuen Sender erzeugen
			Client client = new Client(this.nameservice_host,
					this.nameservice_port);
			// String absenden
			client.send(request);
			// Objektreferenz vom Namensdienst zurueckbekommen
			String string = client.receive();

			String[] blocks = string.split(":");
			
			switch (blocks[0]) {
			case "return":
				// Beende die Verbindung
				client.close();

				if (!blocks[1].equals("void")) {
					return (Object)blocks[1];
				} else {
					throw new RuntimeException("Exception: Falscher Datentyp");
				}
			case "exception":
				// Beende die Verbindung
				client.close();

				if (blocks[1].equals("RuntimeException")) {
					throw new RuntimeException(blocks[2]);
				} else {
					throw new RuntimeException("Unbekannter Fehler");
				}
			default:
				client.close();
				throw new RuntimeException("Exception: Falscher Datentyp");
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
