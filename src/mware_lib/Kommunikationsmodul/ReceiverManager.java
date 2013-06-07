package mware_lib.Kommunikationsmodul;

import java.io.IOException;
import java.util.LinkedList;

import mware_lib.ObjectBroker;

public class ReceiverManager extends Thread {
	private LinkedList<ReceiverThread> receiverThreads;
	private Server server;
	private ObjectBroker broker;
	
	public ReceiverManager(Server server, ObjectBroker broker) {
		receiverThreads = new LinkedList<>();
		this.server = server;
		this.broker = broker;
	}
	
	public void addReceiverThread(ReceiverThread rt) {
		this.receiverThreads.add(rt);
	}
	
	public void run() {
		AcceptedSocket socket;

        while (!isInterrupted()) {
            try {
                socket = server.accept();
                ReceiverThread thread = new ReceiverThread(socket, broker);
//                System.out.println("ReceiverManager - erzeuge neuen Receiverthread auf Socket " + socket);
                thread.start();
                this.addReceiverThread(thread);
            } catch (IOException ex) {
                if (!ex.getMessage().equals("socket closed")) {
                    System.out.println("ListenerThread : getConnection Fehler. Message: " + ex.getMessage());
                }
                Thread.currentThread().interrupt();
            }
        }
	}
	
	public synchronized void shutDown() {
		for (ReceiverThread rt : this.receiverThreads) {
			try {
				try {
					rt.shutDownSocket();
					rt.interrupt();
					rt.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (IOException e) {
				System.out.println("ReceiverManager - Konnte Thread nicht beenden.");
			}
		}
	}

	public void shutDownServer() throws IOException {
		server.shutdown();
	}
}
