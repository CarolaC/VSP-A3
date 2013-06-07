package mware_lib.Kommunikationsmodul;

import java.io.IOException;
import java.util.LinkedList;

import mware_lib.ObjectBroker;

public class ReceiverManager extends Thread {
	private LinkedList<ReceiverThread> receiverThreads;
	private Server server;
	private ObjectBroker broker;
	private boolean exit = false;

	public ReceiverManager(Server server, ObjectBroker broker) {
		receiverThreads = new LinkedList<>();
		this.server = server;
		this.broker = broker;
	}

	public synchronized void addReceiverThread(ReceiverThread rt) {
		this.receiverThreads.add(rt);
	}

	public void setExit() {
		this.exit = true;
	}
	
	public void run() {
		AcceptedSocket socket;

		while (!exit) {
			try {
				socket = server.accept();
				ReceiverThread thread = new ReceiverThread(socket, broker);
				// System.out.println("ReceiverManager - erzeuge neuen Receiverthread auf Socket "
				// + socket);
				thread.start();
				this.addReceiverThread(thread);
				//System.out.println("ReceiverManager - added a new thread" + thread.toString());
				this.removeNotRunningThreads();
			} catch (Exception e) {
				//System.out.println("ReceiverManager - Socket has been closed");
			}
		}
		closeAllThreads();
		//System.out.println("ReceiverManager has finished.");
	}

	private synchronized void removeNotRunningThreads() {
		ReceiverThread rt;
		for (int i = 0; i < receiverThreads.size(); i++) {
			rt = receiverThreads.get(i);
			if (!rt.isAlive()) {
				receiverThreads.remove(rt);
			}
		}
	}

	private synchronized void closeAllThreads() {
//		System.out.println("ReceiverManager - CLOSING ALL THREADS");
//		System.out.println("Running Threads: " + receiverThreads.size());
		for (ReceiverThread rt : this.receiverThreads) {
			try {
				//System.out.println("ReceiverManager - Closing Thread: " + rt);
				rt.shutDownSocket();
				rt.join();
				//System.out
				//		.println("ReceiverManager - Thread " + rt + " successfully closed");
			} catch (IOException e) {
				//System.out.println("ReceiverManager - IOException");
			} catch (InterruptedException e) {
				//System.out.println("ReceiverManager - InterruptedException");
				e.printStackTrace();
			}
		}
	}

	public void shutDownServer() throws IOException {
		server.shutdown();
	}
}
