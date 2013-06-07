package mware_lib.Kommunikationsmodul;

import java.io.IOException;

import mware_lib.ObjectBroker;
import mware_lib.Referenzmodul;
import mware_lib.Skeleton;

public class ReceiverThread extends Thread {

	private AcceptedSocket socket;
	private Skeleton skeleton;
	private Referenzmodul referenzmodul;

	public ReceiverThread(AcceptedSocket socket, Skeleton skeleton) {
		// System.out.println("ReceiverThread - wird erzeugt mit Socket " +
		// socket);
		this.socket = socket;
		this.skeleton = skeleton;
	}

	public ReceiverThread(AcceptedSocket socket, ObjectBroker broker) {
		this.socket = socket;
		this.referenzmodul = broker.getReferenzmodul();
	}

	protected void shutDownSocket() throws IOException {
		try {
			socket.close();
		} catch (Exception e) {
		}
	}

	@Override
	public void run() {

		try {
			String message = null;
			message = socket.receive();
			if (message != null) {
				// System.out.println("ReceiverThread - message angekommen: " +
				// message);
				String[] blocks = message.split(":");
				Skeleton skeleton;

				if (blocks[0].equals("method")) {
					if (this.skeleton == null) {
						// this.referenzmodul.getAllSkeletons();
						// System.out.println("ReceiverThread - suche nach Skeleton von "
						// + blocks[1]);
						skeleton = this.referenzmodul.getSkeleton(blocks[1]);
						// System.out.println("ReceiverThread - Skeleton " +
						// skeleton + " aus Referenzmodul wird verwendet");
						if (skeleton == null) {
							socket.send("exception:exception;returntyp:RuntimeException;returnvalue: Objektreferenz nicht im Referenzmodul enthalten");
						}
					} else {
						skeleton = this.skeleton;
						// System.out.println("ReceiverThread - NameserviceSkeleton wird verwendet");
					}
					// System.out.println("ReceiverThread - remoteInvoke wird aufgerufen mit "
					// + message);
					String answer = skeleton.remoteInvoke(message);
					// System.out.println("ReceiverThread - remoteInvoke hat zurückgegeben "
					// + answer);
					socket.send(answer);
				} else {
					socket.send("exception:exception;returntyp:RuntimeException;returnvalue: Methode erwartet aber nicht erhalten");
				}

			}
			socket.close();

		} catch (Exception e) {
			//System.out.println("ReceiverThread - Socket has been closed");
		}

		//System.out.println("ReceiverThread has finished.");
	}
}
