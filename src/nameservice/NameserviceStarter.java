package nameservice;

import java.io.IOException;

import mware_lib.Kommunikationsmodul.AcceptedSocket;
import mware_lib.Kommunikationsmodul.ReceiverThread;
import mware_lib.Kommunikationsmodul.Server;

public class NameserviceStarter {
	public static void main(String[] args) {
		
		int nameservice_port;
		
		//Finde den Port in den Argumenten
		if(args.length > 0) {
			try {
				nameservice_port = Integer.parseInt(args[0]);
			} catch(NumberFormatException e) {
				System.out.println("Fehler beim parsen des Nameservice Port, nun auf 10000 gesetzt.");
				nameservice_port = 10000;
			}
		}else{
			nameservice_port = 10000;
		}
		
		System.out.println("Nameservice wird gestartet auf Port "+nameservice_port);
		NameService ns = new NameService();

        try {
//        	System.out.println("Starte Server");
            Server server = new Server(nameservice_port);
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
