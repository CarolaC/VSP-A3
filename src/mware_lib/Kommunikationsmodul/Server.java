package mware_lib.Kommunikationsmodul;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	private ServerSocket serverSocket;

	public Server(int listenPort) throws IOException {
		serverSocket = new ServerSocket(listenPort);
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