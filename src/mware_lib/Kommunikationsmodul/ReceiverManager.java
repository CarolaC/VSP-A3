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
				// System.out.println("ReceiverManager - erzeuge neuen Receiverthread auf Socket "
				// + socket);
				thread.start();
				this.addReceiverThread(thread);
				this.removeInactiveThreads();
			} catch (IOException ex) {
				if (!ex.getMessage().equals("socket closed")) {
					System.out
							.println("ListenerThread : getConnection Fehler. Message: "
									+ ex.getMessage());
				}
				Thread.currentThread().interrupt();
			}
		}
		closeAllThreads();
	}

	private synchronized void removeInactiveThreads() {
		ReceiverThread threadElem;
		for (int i = 0; i < receiverThreads.size(); i++) {
			threadElem = receiverThreads.get(i);
			if (!threadElem.isAlive()) {
				receiverThreads.remove(threadElem);
			}
		}
	}

	private synchronized void closeAllThreads() {
		for (ReceiverThread rt : this.receiverThreads) {
			try {
				rt.interrupt();
				rt.shutDownSocket();
				rt.join();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public void shutDownServer() throws IOException {
		server.shutdown();
	}
}
