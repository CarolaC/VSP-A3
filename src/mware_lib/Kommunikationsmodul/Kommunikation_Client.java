package mware_lib.Kommunikationsmodul;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

// hier hab ich noch keine Ahnung...

public class Kommunikation_Client {

	private static Socket clientSocket;

	private static DataOutputStream outToServer;
	private static BufferedReader inFromServer;

	// als Thread lösen?
	public static void send(String host, int port, String request) {

		try {
			clientSocket = new Socket(host, port);
			
			outToServer = new DataOutputStream(clientSocket.getOutputStream());

			outToServer.writeBytes(request);

			clientSocket.close();
			
		} catch (UnknownHostException e) {
		} catch (IOException e) {
		}
	}

	// als Thread lösen?
	public static String receive() {
		try {
			String request = inFromServer.readLine();
		
		String[] blocks = request.split(":");
		if (blocks[0].equals("return")) {
			return blocks[1];
		} else if (blocks[0].equals("exception")) {

		} else {
			// Exception
			}
		} catch (IOException e) {
		}
	}
}
