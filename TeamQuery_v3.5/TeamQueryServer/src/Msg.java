import java.io.DataInputStream;
import java.io.DataOutputStream;

import java.net.Socket;


public class Msg {
	private int Type;
	private String Value;
	

	
	
	public Socket getSocket() {
		return socket;
	}
	
	public void setSocket(Socket socket) {
		this.socket = socket;
	
		
	}
	
	private Socket socket;
	public Msg(int Type, String Value) {
		this.Type = Type;
		this.Value = Value;
	}
	public Msg() {

	}
	public String getValue() {
		return Value;
	}
	public void setValue(String value) {
		Value = value;
	}

	public int getType() {
		return Type;
	}
	public void setType(int type) {
		Type = type;
	}
	
	public boolean Send() {
		
		try {
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			dos.writeInt(Type);
			dos.writeUTF(Value);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean Recv() {
		
		try {
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			int Type = dis.readInt();

			String Value = dis.readUTF();
			
			this.Value = Value;
			this.Type = Type;
			return true;

		} catch (Exception e) {
			return false;
		}
		
	}
}
