package mware_lib.Kommunikationsmodul;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
	
	private ServerSocket serverSocket;

	public Server(int listenPort) throws IOException {
		System.out.println("Server - Server wird gestartet");
		try {
		serverSocket = new ServerSocket(listenPort);
		}catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("Server - Lauscht auf Port: "+listenPort);
	}

	public AcceptedSocket accept() throws IOException {
		return new AcceptedSocket(serverSocket.accept());
	}

	public void shutdown() throws IOException {
		serverSocket.close();
	}

	public int getListeningPort() {
		return this.serverSocket.getLocalPort();
	}	
}