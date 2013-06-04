package nameservice;

import java.io.IOException;

import mware_lib.Kommunikationsmodul.AcceptedSocket;
import mware_lib.Kommunikationsmodul.ReceiverThread;
import mware_lib.Kommunikationsmodul.Server;

public class NameserviceStarter {
	
	private static final int NAMESERVICE_PORT = 1000;
	
	public static void main() {
		Nameservice ns = new Nameservice();

        try {
            Server server = new Server(NAMESERVICE_PORT);

            while (true) {
                AcceptedSocket socket = server.accept();
                ReceiverThread rt = new ReceiverThread(socket, referenzmodul);
                rt.start();
            }
        } catch (IOException ex) {
        }
	}

}
