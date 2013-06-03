package mware_lib;

import mware_lib.Kommunikationsmodul.*;

public class NameserviceStub extends Nameservice {

	private String nameservice_host;
	private int nameservice_port;
	
	public NameserviceStub(String host, int port) {
		this.nameservice_host = host;
		this.nameservice_port = port;
	}
	
	@Override
	public void rebind(Object servant, String name) {
		String request = "methode:rebind:" + servant + ":" + name;
		Kommunikation_Client.send(nameservice_host, nameservice_port, request);
	}

	@Override
	public Object resolve(String name) {
		String request = "methode:resolve:" + name;
		Kommunikation_Client.send(nameservice_host, nameservice_port, request);
		Object refObj = (Object)Kommunikation_Client.receive();
		return refObj;
	}

}
