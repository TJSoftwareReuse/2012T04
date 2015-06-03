import java.net.Socket;


public interface MessageInterface {
	public boolean MessageHandler(Socket clientSocket, Msg msg);
}
