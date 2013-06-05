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
}
