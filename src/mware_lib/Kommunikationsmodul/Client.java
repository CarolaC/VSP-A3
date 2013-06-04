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
		socket = new Socket(ip, port);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new BufferedOutputStream(socket.getOutputStream());
	}
	
	public void send(String str) throws IOException {
		out.write(str.getBytes());
	}
	
	public String receive() throws IOException {
		return in.readLine();
	}
	
	public void close() throws IOException {
		in.close();
		out.close();
		socket.close();
	}
	
}
