package bank_access;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Arrays;

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
//		System.out.println("ManagerStub - createAccount aufgerufen mit " + owner + " " + branch);
		try {
			// Senden des Methodenaufrufs
			Client client = new Client(this.ip, this.port);
			String str = "method:" + this.objRef + ":createAccount:" + owner
					+ ":" + branch + "\n";
			client.send(str);
//			System.out.println("MangagerStub - String wurde gesendet: " + str);

			String receive[] = client.receive().split(":");
//			System.out.println("ManagerStub - Antwort erhalten mit " + Arrays.toString(receive));
			switch (receive[0]) {
			case "return":
				client.close();

				if (!receive[1].equals("void")) {
					// Erwartetes Ergebnis
					return receive[1];
				} else {
					throw new RuntimeException("Exception: Falscher Datentyp");
				}
			case "exception":
				// Beende die Verbindung
				client.close();

				if (receive[1].equals("RuntimeException")) {
					throw new RuntimeException(receive[2]);
				} else {
					throw new RuntimeException(receive[2]);
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
