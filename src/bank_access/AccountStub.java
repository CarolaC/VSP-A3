package bank_access;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Arrays;

import mware_lib.Kommunikationsmodul.*;

public class AccountStub extends AccountImplBase {

	private String ip; 		// IP-Adresse der Serverseite
	private int port; 		// Port der Serverseite
	private Object objRef;	// Referenz zum konkreten Objekt

	public AccountStub(String ip, int port, Object objRef) {
		this.ip = ip;
		this.port = port;
		this.objRef = objRef;
	}

	@Override
	public void transfer(double amount) throws OverdraftException {
		// Erzeuge einen neuen Sender
		try {
			// Senden des Methodenaufrufs
			Client client = new Client(this.ip, this.port);
//			System.out.println("AccountStub - Client erzeugt mit " + this.ip + " " + this.port);
			String request = "method:" + this.objRef + ":transfer:" + amount + "\n";
//			System.out.println("AccountStub - Folgendes wird gesendet: " + request);
			client.send(request);

			String receive[] = client.receive().split(":");
			
//			System.out.println("AccountStub - Antwort bekommen: " + Arrays.toString(receive));
			
			switch (receive[0]) {
			case "return":
				// Beende die Verbindung
				client.close();

				if (receive[1].equals("void")) {
//					System.out.println("AccountStub - es gibt keinen Rückgabewert");
					// Erwartetes Ergebnis
					return;
				} else {
					// Nicht erwarteter Dateityp
					throw new RuntimeException("Exception: Falscher Datentyp");
				}
			case "exception":
				// Beende die Verbindung
				client.close();

				if (receive[1].equals("RuntimeException")) {
					throw new RuntimeException(receive[2]);
				} else if (receive[1].equals("OverdraftException")) {
					throw new OverdraftException(receive[2]);
				} else {
					throw new RuntimeException("Unbekannter Fehler");
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

	@Override
	public double getBalance() {
		try {
			// Senden des Methodenaufrufs
			Client sender = new Client(this.ip, this.port);
			sender.send("method:" + this.objRef + ":getBalance" + "\n");

			String receive[] = sender.receive().split(":");

			switch (receive[0]) {
			case "return":
				if (!receive[1].equals("void")) {
					return Double.parseDouble(receive[1]);
				} else {
					throw new RuntimeException("Exception: Falscher Datentyp");
				}
			case "exception":
				// Beende die Verbindung
				sender.close();

				if (receive[1].equals("RuntimeException")) {
					throw new RuntimeException(receive[2]);
				} else {
					throw new RuntimeException(receive[2]);
				}
			default:
				sender.close();
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
