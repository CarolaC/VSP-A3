package cash_access;

import java.io.IOException;
import java.net.UnknownHostException;

import mware_lib.Sender;

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
			Sender sender = new Sender(this.ip, this.port);
			sender.send("method:deposit:"+accountID+":"+amount+"\n");
			
			String receive[] = sender.receive().split(":");
			switch(receive[0]) {
				case "return":
					//TODO: Was machen wir bei einem void aufruf?
					if(receive[1].equals("void")) {
						//Erwartetes Ergebnis
						sender.close();
						return;
					}else{
						//TODO: Nicht erwartetet Ergebnis! Exception werfen
					}
					break;
				case "exception":
					//TODO: Herausfinden welche Exception geworfen wurde
					break;
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void withdraw(String accountID, double amount) throws OverdraftException {
		//Erzeuge einen neuen Sender
		try {
			//Senden des Methodenaufrufs
			Sender sender = new Sender(this.ip, this.port);
			sender.send("method:withdraw:"+accountID+":"+amount+"\n");
			
			String receive[] = sender.receive().split(":");
			switch(receive[0]) {
				case "return":
					//TODO: Was machen wir bei einem void aufruf?
					if(receive[1].equals("void")) {
						//Erwartetes Ergebnis
						sender.close();
						return;
					}else{
						//TODO: Nicht erwartetet Ergebnis! Exception werfen
					}
					break;
				case "exception":
					//TODO: Herausfinden welche Exception geworfen wurde
					break;
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public double getBalance(String accountID) {
		try {
			Sender sender = new Sender(this.ip, this.port);
			sender.send("method:getBalance");
			
			String receive[] = sender.receive().split(":");
			
			switch(receive[0]) {
				case "exception":
					//TODO: Herausfinden was für eine exception
					break;
				case "return":
					if(!receive[1].equals("void")) {
						return Double.parseDouble(receive[1]);
					}else{
						//TODO: Falscher Dateityp EXCEPTION TIME!
					}
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//TODO
		throw new RuntimeException("Unkown Error");
	}

}
