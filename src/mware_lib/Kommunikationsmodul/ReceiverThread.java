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
		this.socket = socket;
		this.skeleton = skeleton;
	}

	public ReceiverThread(AcceptedSocket socket, ObjectBroker broker) {
		this.socket = socket;
		this.referenzmodul = broker.getReferenzmodul();
	}

	protected void shutDownSocket() throws IOException {
		socket.close();
	}

	@Override
	public void run() {

		try {
			String message = socket.receive();
			String[] blocks = message.split(":");
			Skeleton skeleton;
			
			if (blocks[0].equals("method")) {
				if (this.skeleton == null) {
					skeleton = this.referenzmodul
							.getSkeleton(blocks[1]);
					if (skeleton == null) {
						socket.send("exception:exception;returntyp:RuntimeException;returnvalue: Objektreferenz nicht im Referenzmodul enthalten");
					}
				} else {
					skeleton = this.skeleton;
				}
				String answer = skeleton.remoteInvoke(message);
				socket.send(answer);
			}
			else {
				socket.send("exception:exception;returntyp:RuntimeException;returnvalue: Methode erwartet aber nicht erhalten");
			}

			socket.close();

		} catch (IOException ex) {
		}
	}
}
