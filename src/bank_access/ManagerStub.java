package bank_access;

import java.io.IOException;
import java.net.UnknownHostException;

import mware_lib.Kommunikationsmodul.*;

public class ManagerStub extends ManagerImplBase {

	private String ip; 		// IP-Adresse der Serverseite
	private int port; 		// Port der Serverseite
	private Object objRef;	// Referenz zum konkreten Objekt

	public ManagerStub(String ip, int port, Object objRef) {
		this.ip = ip;
		this.port = port;
		this.objRef = objRef;
	}

	@Override
	public String createAccount(String owner, String branch) {
		// Erzeuge einen neuen Sender
		try {
			// Senden des Methodenaufrufs
			Client client = new Client(this.ip, this.port);
			client.send("method:" + this.objRef + ":createAccount:" + owner
					+ ":" + branch + "\n");

			String receive[] = client.receive().split(":");

			switch (receive[0]) {
			case "return":
				client.close();

				if (!receive[1].equals("void")) {
					// Erwartetes Ergebnis
					return receive[2];
				} else {
					throw new RuntimeException("Exception: Falscher Datentyp");
				}
			case "exception":
				// Beende die Verbindung
				client.close();

				if (receive[1].equals("RuntimeException")) {
					throw new RuntimeException(receive[2]);
				} else {
					throw new RuntimeException("Unbekannter Feheler");
				}
			default:
				client.close();
				throw new RuntimeException("Exception: Falscher Datentyp"); // Unbekannter
																			// Fehler
			}
		} catch (UnknownHostException e) {
			throw new RuntimeException("Exception: Konnte keine Verbindung zu "
					+ ip + ":" + port + " herstellen.");
		} catch (IOException e) {
			throw new RuntimeException("Exception: Konnte keine Verbindung zu "
					+ ip + ":" + port + " herstellen.");
		}
	}

}
