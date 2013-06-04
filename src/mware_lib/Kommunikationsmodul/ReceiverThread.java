package mware_lib.Kommunikationsmodul;

import java.io.IOException;

import mware_lib.Referenzmodul;
import mware_lib.Skeleton;

public class ReceiverThread extends Thread {

	private AcceptedSocket socket;
	private Referenzmodul referenzmodul;

	public ReceiverThread(AcceptedSocket socket, Referenzmodul referenzmodul) {
		this.socket = socket;
		this.referenzmodul = referenzmodul;
	}

	protected void shutDownSocket() throws IOException {
		socket.close();
	}

	@Override
	public void run() {

		try {
			String message = socket.receive();
			String[] blocks = message.split(":");

			if (blocks[0].equals("method")) {
				Skeleton skeleton = this.referenzmodul.getSkeleton(blocks[1]);

				socket.send(skeleton.remoteInvoke(message));
			}

			socket.close();

		} catch (IOException ex) {
		}
	}
}
