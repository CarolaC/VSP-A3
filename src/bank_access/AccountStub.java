package bank_access;

import java.io.IOException;
import java.net.UnknownHostException;

import mware_lib.Kommunikationsmodul.*;

public class AccountStub extends AccountImplBase {

	private String ip;		//IP Adresse an die gesendet werden soll
	private int port;		//Port an den gesendet werden soll
	private Object objRef;
	
	public AccountStub(String ip, int port, Object objRef) {
		this.ip = ip;
		this.port = port;
		this.objRef = objRef;
	}
	
	@Override
	public void transfer(double amount) throws OverdraftException {
		//Erzeuge einen neuen Sender
		try {
			//Senden des Methodenaufrufs
			Client sender = new Client(this.ip, this.port);
			sender.send("method:transfer:"+amount+"\n");
			
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
			throw new RuntimeException("Exception: Konnte keine Verbindung zu " + ip + ":" + port +" herstellen.");
		}
	}

	@Override
	public double getBalance() {
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
			throw new RuntimeException("Exception: Konnte keine Verbindung zu " + ip + ":" + port + " herstellen.");	
		} catch (IOException e) {
			throw new RuntimeException("Exception: Konnte keine Verbindung zu " + ip + ":" + port + " herstellen.");
		}
	}

}
