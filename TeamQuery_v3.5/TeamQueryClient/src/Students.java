
import java.net.Socket;
import java.util.ArrayList;


public class Students {


	private Socket socket;

	
	
	public Students(String IP, int Port) {
		try {
			socket = new Socket(IP,Port);
		} catch (Exception e) {
			
		}
	}
	
	
	public Student getStudentByName(String strStudentName) {
		
		Msg msg = new Msg(1,strStudentName);
		msg.setSocket(socket);
		msg.Send();
		
		Msg msgRecv = new Msg();
		msgRecv.setSocket(socket);
		msgRecv.Recv();
		
		if(msgRecv.getType() == 100) {
			String strField[] = msgRecv.getValue().split(",");
			Student stu = new Student(
					Integer.parseInt(strField[0]),//ID
					Integer.parseInt(strField[1]),//TeamId
					strField[2],//Name
					strField[3],//Sex
					strField[4]);//Account
			
			return stu;
		} else if (msgRecv.getType() == 404) {
			Student stu = new Student(
					-1,//ID
					-1,//TeamId
					"not found",//Name
					"not found",//Sex
					"not found");//Account		
			
			return stu;
		} else if(msgRecv.getType() == 403) {
			Student stu = new Student(
					-1,//ID
					-1,//TeamId
					"out serivce",//Name
					"out serivce",//Sex
					"out serivce");//Account		
			
			return stu;		
		}
		return null;
	}
	
	public Student getStudentById(int nStudentId) {
		Msg msg = new Msg(2, Integer.toString(nStudentId));
		msg.setSocket(socket);
		msg.Send();
		
		Msg msgRecv = new Msg();
		msgRecv.setSocket(socket);
		msgRecv.Recv();
		
		if(msgRecv.getType() == 100) {
			String strField[] = msgRecv.getValue().split(",");
			Student stu = new Student(
					Integer.parseInt(strField[0]),//ID
					Integer.parseInt(strField[1]),//TeamId
					strField[2],//Name
					strField[3],//Sex
					strField[4]);//Account
			return stu;
		} else if (msgRecv.getType() == 404) {
			Student stu = new Student(
					-1,//ID
					-1,//TeamId
					"not found",//Name
					"not found",//Sex
					"not found");//Account		
			
			return stu;
		} else if(msgRecv.getType() == 403) {
			Student stu = new Student(
					-1,//ID
					-1,//TeamId
					"out serivce",//Name
					"out serivce",//Sex
					"out serivce");//Account		
			
			return stu;		
		}
		return null;
	}
	
	public ArrayList<Student> getStudentsByTeamId(int nTeamId) {
		
		ArrayList<Student> arrStudent = new ArrayList<Student>();
		Msg msg = new Msg(3, Integer.toString(nTeamId));
		msg.setSocket(socket);
		msg.Send();
		
		Msg msgRecv = new Msg();
		msgRecv.setSocket(socket);
		msgRecv.Recv();
		
		if(msgRecv.getType() == 300) {
			int Count = Integer.parseInt(msgRecv.getValue());
			for(int i = 0; i < Count; i++) {
				msgRecv.Recv();
				if(msgRecv.getType() == 100) {
					String strField[] = msgRecv.getValue().split(",");
					Student stu = new Student(
							Integer.parseInt(strField[0]),//ID
							Integer.parseInt(strField[1]),//TeamId
							strField[2],//Name
							strField[3],//Sex
							strField[4]);//Account
					arrStudent.add(stu);
				}
			}
			return arrStudent;
		} else if (msgRecv.getType() == 404) {
			Student stu = new Student(
					-1,//ID
					-1,//TeamId
					"not found",//Name
					"not found",//Sex
					"not found");//Account		
			arrStudent.add(stu);
			return arrStudent;

		} else if(msgRecv.getType() == 403) {
			Student stu = new Student(
					-1,//ID
					-1,//TeamId
					"out serivce",//Name
					"out serivce",//Sex
					"out serivce");//Account		
			arrStudent.add(stu);
			return arrStudent;
		}
		return null;
	}
}
