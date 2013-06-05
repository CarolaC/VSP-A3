package mware_lib.Kommunikationsmodul;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

	private Socket socket;
	private BufferedReader in;
	private BufferedWriter out;
	private String ip;
	private int port;
	
	public Client(String ip, int port) throws UnknownHostException, IOException {
		socket = new Socket(ip, port);
		this.ip = ip;
		this.port = port;
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
	}
	
	public void send(String str) throws IOException {
		System.out.println("Client - " + str + " wird gesendet an " + this.ip + ":" + this.port);
		str = str + "\n";
		try {
		out.write(str);
		out.newLine();
		out.flush();
		}catch(Exception e) {
			System.out.println("Client - Senden Fehlgeschlagen von: " + str);
			e.printStackTrace();
		}
	}
	
	public String receive() throws IOException {
		System.out.println("Client - wird gelesen");
		String str = in.readLine();
		System.out.println("Client - habe folgendes gelesen: " + str);
		return str;
	}
	
	public void close() throws IOException {
		in.close();
		out.close();
		socket.close();
	}
	
}
