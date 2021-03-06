package cash_access;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Arrays;

import mware_lib.Kommunikationsmodul.*;

public class TransactionStub extends TransactionImplBase {

	private String ip; 		// IP-Adresse der Serverseite
	private int port; 		// Port der Serverseite
	private Object objRef;	// Referenz zum konkreten Objekt

	public TransactionStub(String ip, int port, Object objRef) {
		this.ip = ip;
		this.port = port;
		this.objRef = objRef;
	}

	@Override
	public void deposit(String accountID, double amount) {
		// Erzeuge einen neuen Sender
//		System.out.println("TransactionStub - deposit wurde aufgerufen mit " + accountID + " und " + amount);
		try {
			// Senden des Methodenaufrufs
			Client client = new Client(this.ip, this.port);
			client.send("method:" + this.objRef + ":deposit:" + accountID + ":"
					+ amount + "\n");
			String answer = client.receive();
//			System.out.println("TransactionStub - Antwort auf deposit: " + answer);
			String[] receive = answer.split(":");
//			System.out.println("TransactionStub - Antwort auf deposit: " + Arrays.toString(receive));
			switch (receive[0]) {
			case "return":
				// Beende die Verbindung
				client.close();

				if (receive[1].equals("void")) {
					// Erwartetes Ergebnis
					return;
				} else {
					// Nicht erwarteter Dateityp
					throw new RuntimeException("Exception: Falscher Datentyp");
				}
			case "exception":
				// Beende die Verbindung
				client.close();

				if (receive[2].equals("RuntimeException")) {
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

	@Override
	public void withdraw(String accountID, double amount)
			throws OverdraftException {
		// Erzeuge einen neuen Sender
//		System.out.println("TransactionStub - withdraw wurde aufgerufen mit " + accountID + " und " + amount);
		try {
			// Senden des Methodenaufrufs
			Client sender = new Client(this.ip, this.port);
			sender.send("method:" + this.objRef + ":withdraw:" + accountID
					+ ":" + amount + "\n");

			String receive[] = sender.receive().split(":");
			
			switch (receive[0]) {
			case "return":
				// Beende die Verbindung
				sender.close();

				if (receive[1].equals("void")) {
					// Erwartetes Ergebnis
					return;
				} else {
					// Nicht erwarteter Dateityp
					throw new RuntimeException("Exception: Falscher Datentyp");
				}
			case "exception":
				// Beende die Verbindung
				sender.close();

				if (receive[1].equals("RuntimeException")) {
					throw new RuntimeException(receive[2]);
				} else if (receive[1].equals("OverdraftException")) {
					throw new OverdraftException(receive[2]);
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

	@Override
	public double getBalance(String accountID) {
		try {
			// Senden des Methodenaufrufs
//			System.out.println("TransactionStub - getBalance wurde aufgerufen mit " + accountID);
			Client sender = new Client(this.ip, this.port);
			sender.send("method:" + this.objRef + ":getBalance:" + accountID + "\n");

			String receive[] = sender.receive().split(":");
//			System.out.println("TransactionStub - Antwort auf getBalance: " + Arrays.toString(receive));
			
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
					+ ip + " :" + port + "  herstellen.");
		} catch (IOException e) {
			throw new RuntimeException("Exception: Konnte keine Verbindung zu "
					+ ip + " :" + port + "  herstellen.");
		}
	}

}
