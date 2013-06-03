package bank_access;

import java.io.IOException;
import java.net.UnknownHostException;

import mware_lib.Kommunikationsmodul.*;

public class ManagerStub extends ManagerImplBase{

	private String ip;
	private int port;
	
	public ManagerStub(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}
	
	@Override
	public String createAccount(String owner, String branch) {
		//Erzeuge einen neuen Sender
		try {
			//Senden des Methodenaufrufs
			Sender sender = new Sender(this.ip, this.port);
			sender.send("method:createAccount:"+owner+":"+branch+"\n");
			
			String receive[] = sender.receive().split(":");
			switch(receive[0]) {
				case "return":
					//TODO: Was machen wir bei einem void aufruf?
					if(!receive[1].equals("void")) {
						//Erwartetes Ergebnis
						sender.close();
						return receive[2];
					}else{
						//TODO
						throw new RuntimeException("Unkown Error");
					}
				case "exception":
					//TODO
					throw new RuntimeException("Unkown Error");
			}
		} catch (UnknownHostException e) {
			throw new RuntimeException("Exception: Konnte keine Verbindung zu "+ip+":"+port+" herstellen.");	
		} catch (IOException e) {
			throw new RuntimeException("Exception: Konnte keine Verbindung zu "+ip+":"+port+" herstellen.");
		}
		
		//TODO
		throw new RuntimeException("Unkown Error");
	}

}
