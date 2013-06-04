package cash_access;

import java.io.IOException;
import java.net.UnknownHostException;

import mware_lib.Kommunikationsmodul.*;

public class TransactionStub extends TransactionImplBase {

	private String ip;
	private int port;
	
	public TransactionStub(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}
	
	@Override
	public void deposit(String accountID, double amount) {
		//Erzeuge einen neuen Sender
		try {
			//Senden des Methodenaufrufs
			Client sender = new Client(this.ip, this.port);
			sender.send("method:deposit:"+accountID+":"+amount+"\n");
			
			String receive[] = sender.receive().split(":");
			switch(receive[0]) {
				case "return":
					//Beende den Sender sauber
					sender.close();
					
					if(receive[1].equals("void")) {
						//Erwartetes Ergebnis
						return;
					}else{
						//Nicht erwarteter Dateityp
						throw new RuntimeException("Exception: Falscher Datentyp");
					}
				case "exception":
					//Beende den Sender sauber
					sender.close();
					
					if(receive[1].equals("RuntimeException")) {
						throw new RuntimeException(receive[2]);
					}else {
						throw new RuntimeException("Unbekannter Feheler");
					}
				default:
					sender.close();
					throw new RuntimeException("Exception: Falscher Datentyp");	//Unbekannter Fehler
			}
		} catch (UnknownHostException e) {
			throw new RuntimeException("Exception: Konnte keine Verbindung zu " + ip + ":" + port + " herstellen.");	
		} catch (IOException e) {
			throw new RuntimeException("Exception: Konnte keine Verbindung zu " + ip + ":" + port + " herstellen.");
		}
	}

	@Override
	public void withdraw(String accountID, double amount) throws OverdraftException {
		//Erzeuge einen neuen Sender
		try {
			//Senden des Methodenaufrufs
			Client sender = new Client(this.ip, this.port);
			sender.send("method:withdraw:"+accountID+":"+amount+"\n");
			
			String receive[] = sender.receive().split(":");
			switch(receive[0]) {
				case "return":
					//Beende den Sender sauber
					sender.close();
					
					if(receive[1].equals("void")) {
						//Erwartetes Ergebnis
						return;
					}else{
						//Nicht erwarteter Dateityp
						throw new RuntimeException("Exception: Falscher Datentyp");
					}
				case "exception":
					//Beende den Sender sauber
					sender.close();
					
					if(receive[1].equals("RuntimeException")) {
						throw new RuntimeException(receive[2]);
					}else if(receive[1].equals("OverdraftException")) {
						throw new OverdraftException(receive[2]);
					}else {
						throw new RuntimeException("Unbekannter Feheler");
					}
				default:
					sender.close();
					throw new RuntimeException("Exception: Falscher Datentyp");	//Unbekannter Fehler
			}
		} catch (UnknownHostException e) {
			throw new RuntimeException("Exception: Konnte keine Verbindung zu " + ip + ":" + port + " herstellen.");	
		} catch (IOException e) {
			throw new RuntimeException("Exception: Konnte keine Verbindung zu " + ip + ":" + port + " herstellen.");
		}
	}

	@Override
	public double getBalance(String accountID) {
		try {
			Client sender = new Client(this.ip, this.port);
			sender.send("method:getBalance");
			
			String receive[] = sender.receive().split(":");
			
			switch(receive[0]) {
				case "return":
					if(!receive[1].equals("void")) {
						return Double.parseDouble(receive[1]);
					}else{
						throw new RuntimeException("Exception: Falscher Datentyp");					
					}
				case "exception":
					//Beende den Sender sauber
					sender.close();
					
					if(receive[1].equals("RuntimeException")) {
						throw new RuntimeException(receive[2]);
					}else {
						throw new RuntimeException("Unbekannter Feheler");
					}
				default:
					sender.close();
					throw new RuntimeException("Exception: Falscher Datentyp");	//Unbekannter Fehler
			}
		} catch (UnknownHostException e) {
			throw new RuntimeException("Exception: Konnte keine Verbindung zu "+ ip+ " :"+ port+ "  herstellen.");	
		} catch (IOException e) {
			throw new RuntimeException("Exception: Konnte keine Verbindung zu "+ ip+ " :"+ port+ "  herstellen.");
		}
	}

}
