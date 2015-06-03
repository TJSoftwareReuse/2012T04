import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SessionThread extends Thread {

	private Socket clientSocket = null;
	private MessageInterface msgInterface = null;

	public SessionThread(Socket socket) {
		clientSocket = socket;
	}

	@Override
	public void run() {
		System.out.println("Thread Start" + Thread.currentThread().getId());

		try {
			InputStream in = clientSocket.getInputStream();
			OutputStream out = clientSocket.getOutputStream();

			DataInputStream dis = new DataInputStream(in);
			DataOutputStream dos = new DataOutputStream(out);

			while (true) {
				Msg msgRecv = new Msg();
				msgRecv.setSocket(clientSocket);
				if(!msgRecv.Recv())break;

				if(!msgInterface.MessageHandler(clientSocket, msgRecv)) break;
			}
			clientSocket.close();

		} catch (Exception e) {

		}
		System.out.println("Thread Exit" + Thread.currentThread().getId());
	}
	public void setMsgInterface(MessageInterface msgInterface) {
		this.msgInterface = msgInterface;
	}
}
