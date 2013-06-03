package mware_lib;

import java.io.IOException;
import java.net.UnknownHostException;

import mware_lib.Kommunikationsmodul.*;

public class NameserviceStub extends Nameservice {

	private String nameservice_host; // IP des entfernten Namensdienstes
	private int nameservice_port; // Port des entfernten Namensdienstes

	public NameserviceStub(String host, int port) {
		this.nameservice_host = host;
		this.nameservice_port = port;
	}

	@Override
	public void rebind(Object servant, String name) {
		try {
			// erzeuge Methoden-Aufruf String
			String request = "methode:rebind:" + servant + ":" + name;
			// neuen Sender erzeugen
			Sender sender = new Sender(this.nameservice_host,
					this.nameservice_port);
			// String absenden
			sender.send(request);
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
			String request = "methode:resolve:" + name;
			// neuen Sender erzeugen
			Sender sender = new Sender(this.nameservice_host,
					this.nameservice_port);
			// String absenden
			sender.send(request);
			// Objektreferenz vom Namensdienst zurückbekommen
			String string = sender.receive();

			String[] blocks = string.split(":");
			if (blocks[0].equals("return")) {
				return (Object)blocks[1];
			} else {
				return null;
;
			} catch (UnknownHostException e) {
				throw new RuntimeException("Exception: Konnte keine Verbindung zu " + ip + ":" + port + " herstellen.");	
			} catch (IOException e) {
				throw new RuntimeException("Exception: Konnte keine Verbindung zu " + ip + ":" + port +" herstellen.");
			}
		}}
