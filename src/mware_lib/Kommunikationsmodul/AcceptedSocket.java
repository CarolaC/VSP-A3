package mware_lib.Kommunikationsmodul;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class AcceptedSocket {
	
    private BufferedReader in;
    private OutputStream out;

    public AcceptedSocket(Socket socket) throws IOException {
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = socket.getOutputStream();
    }

    public String receive() throws IOException {
    	System.out.println("AcceptedSocket - wird gelesen");
        String str = in.readLine();
        System.out.println("AcceptedSocket - habe folgendes gelesen: " + str);
        return str;
    }

    public void send(String message) throws IOException {
    	System.out.println("AcceptedSocket - wird gesendet");
        out.write((message).getBytes());
    }

    public void close() throws IOException {
        in.close();
        out.close();
    }
	
}
