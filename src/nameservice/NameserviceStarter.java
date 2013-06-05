package nameservice;

import java.io.IOException;

import mware_lib.Kommunikationsmodul.AcceptedSocket;
import mware_lib.Kommunikationsmodul.ReceiverThread;
import mware_lib.Kommunikationsmodul.Server;

public class NameserviceStarter {
	
	private static final int NAMESERVICE_PORT = 10000;
	
	public static void main(String[] args) {
//		System.out.println("Nameservice wird gestartet");
		NameService ns = new NameService();

        try {
//        	System.out.println("Starte Server");
            Server server = new Server(NAMESERVICE_PORT);
//        	System.out.println("Server gestartet");
            
        	while (true) {
                AcceptedSocket socket = server.accept();
                ReceiverThread rt = new ReceiverThread(socket, new NameserviceSkeleton(ns));
                rt.start();
            }
        } catch (IOException ex) {
        }
	}

}
