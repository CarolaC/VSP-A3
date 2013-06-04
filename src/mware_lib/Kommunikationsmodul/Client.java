package mware_lib.Kommunikationsmodul;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

	private Socket socket;
	private BufferedReader in;
	private BufferedOutputStream out;
	
	public Client(String ip, int port) throws UnknownHostException, IOException {
		//Erstellt einen neuen Socket	
		socket = new Socket(ip, port);
			
		//Initialisiere den reader
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		//Initialisiere den output
		out = new BufferedOutputStream(socket.getOutputStream());
	}
	
	/**
	 * Sendet eine Nachricht
	 * 
	 * @param str die zu sendene Nachricht
	 * @throws IOException bei Fehler
	 */
	public void send(String str) throws IOException {
		out.write(str.getBytes());
	}
	
	/**
	 * empfängt eine Nachricht und lässt den Thread einfrieren bis eine
	 * Nachricht ankommt.
	 * 
	 * @return Die Empfangene Nachricht als String
	 * @throws IOException falls ein Fehler auftritt
	 */
	public String receive() throws IOException {
		return in.readLine();
	}
	
	/**
	 * Schließt den Socket sauber
	 * 
	 * @throws IOException falls ein Fehler auftritt
	 */
	public void close() throws IOException {
		in.close();
		out.close();
		socket.close();
	}
	
}
