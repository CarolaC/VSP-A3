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
        return in.readLine();
    }

    public void send(String message) throws IOException {
        out.write((message + "\n").getBytes());
    }

    public void close() throws IOException {
        in.close();
        out.close();
    }
	
}
