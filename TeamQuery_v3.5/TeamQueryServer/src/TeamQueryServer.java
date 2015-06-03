import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import Main.FM;
import Main.license;
import PerMinute.PMPerMinute;

import com.eva.me.cm.ConfigUtil;

public class TeamQueryServer implements MessageInterface,EventInterface {


	static FM fm;
	static license li;
	static PMPerMinute pm;

	static Students stus;
	static ServerSocket serverSocket = null;

	private static boolean init() {
		ConfigUtil cm;
		cm = new ConfigUtil();
		cm.loadConfigFile();
		// 读取配置的用户名和密码进行校验
		String strUser = cm.getProperty("User");
		String strPass = cm.getProperty("Password");
		int nLicense = Integer.parseInt(cm.getProperty("license"));
		String strFmPath = cm.getProperty("FMFilePath");
		String strPmDir = cm.getProperty("PMDirPath");
		String strTeamData = cm.getProperty("TeamData");
		
		String strPMTimeInterval = cm.getProperty("PMTimeInterval");
		long PMTimeInterval;
		if(strPMTimeInterval.equals("default")) {
			PMTimeInterval = 60000;//1 minute
		} else {
			PMTimeInterval = Long.parseLong(cm.getProperty("PMTimeInterval"));
		}
		
		if (strFmPath.equals("default")) {
			strFmPath = "fm.log";
		}
		if (strPmDir.equals("default")) {
			strPmDir = "pm";
		}

		if (strUser.equals("abc") && strPass.equals("123456")) {

			// fm设置文件
			try {
				fm = new FM();
				fm.setLogFile(strFmPath);
			} catch (Exception e) {
				logError("fm Init Error");
				return false;
			}
			// pm设置文件
			try {
				pm = new PMPerMinute(strPmDir);
				pm.setIntervalTime(PMTimeInterval);
				pm.start();

			} catch (Exception e) {
				logError("pm Init Error");
				return false;
			}

			li = new license();
			// license设置容量
			li.ChangeInitialNum(nLicense);


		} else {
			logError("User no Access");
			return false;
		}


		if (strTeamData.equals("default")) {
			strTeamData = "team.txt";
		}

		try {
			stus = new Students(strTeamData);
		} catch (IOException e) {
			logError("TeamData not found");
			return false;
		}
		fm.warn(100, "Init Success");
		return true;
	}
	private static boolean reload() {

		
		ConfigUtil cm;
		cm = new ConfigUtil();
		cm.loadConfigFile();

		
		// 读取配置的用户名和密码进行校验

		int nLicense = Integer.parseInt(cm.getProperty("license"));
		String strFmPath = cm.getProperty("FMFilePath");
		String strPmDir = cm.getProperty("PMDirPath");
		String strPMTimeInterval = cm.getProperty("PMTimeInterval");
		String strTeamData = cm.getProperty("TeamData");
		long PMTimeInterval;
		if(strPMTimeInterval.equals("default")) {
			PMTimeInterval = 60000;//1 minute
		} else {
			PMTimeInterval = Long.parseLong(cm.getProperty("PMTimeInterval"));
		}
		
		
		
		if (strFmPath.equals("default")) {
			strFmPath = "fm.log";
		}
		if (strPmDir.equals("default")) {
			strPmDir = "pm";
		}

		// fm设置文件
		try {
			fm.setLogFile(strFmPath);
		} catch (Exception e) {
			logError("fm reload Error");
			return false;
		}
		// pm设置文件
		try {
			//pm.stop();
			pm.setPath(strPmDir);
			pm.setIntervalTime(PMTimeInterval);		
			pm.start();

		} catch (Exception e) {
			logError("pm reload Error");
			return false;
		}

		li.ChangeInitialNum(nLicense);
		li.ChangeCalculateNum(0);

		// 读取名单

		if (strTeamData.equals("default")) {
			strTeamData = "team.txt";
		}

		try {
			stus = new Students(strTeamData);
		} catch (IOException e) {
			logError("TeamData not found");
			return false;
		}
		fm.warn(101, "Reload Success");

		return true;
	
	}
	public boolean isAccess() {
		

		// 判断并加1
		if (li.JudgeServiceRequest()) { // 提供服务
			fm.warn(123, "InService");
			pm.addIndex("InService", 1);
			
			
			return true;
		} else { // 拒绝服务

			pm.addIndex("OutService", 1);
			fm.warn(124, "OutService");
			
			return false;
		}	
	}

	public static void logError(String strMessage) {
		Logger log = null;
		log = Logger.getLogger("TeamQuery");
		PropertyConfigurator.configure("log4j.properties");
		log.error(strMessage);
	}

	public static void logWarn(String strMessage) {
		Logger log = null;
		log = Logger.getLogger("TeamQuery");
		PropertyConfigurator.configure("log4j.properties");
		log.warn(strMessage);
	}

	public static void logInfo(String strMessage) {
		Logger log = null;
		log = Logger.getLogger("TeamQuery");
		PropertyConfigurator.configure("log4j.properties");
		log.info(strMessage);
	}

	public static void main(String[] args) throws Exception, IOException {
		// TODO Auto-generated method stub
		if (init()) {


			QueryDialog dlg = new QueryDialog();
			dlg.setInterfaceEvent(new TeamQueryServer());
			ServerStartup(12345);
		}

	}

	public static void ServerStartup(int Port) {

		try {
			serverSocket = new ServerSocket(Port);
			System.out.println("Server Start up...");
			while (true) {
				Socket clientSocket = serverSocket.accept();

				SocketAddress clientAddress = clientSocket
						.getRemoteSocketAddress();

				System.out.println("Handling client at " + clientAddress);

				fm.warn(200, "Handling client at " + clientAddress);
				SessionThread sessionThread = new SessionThread(clientSocket);
				sessionThread.setMsgInterface(new TeamQueryServer());
				sessionThread.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean MessageHandler(Socket clientSocket, Msg msg) {

		pm.addIndex("GetMsg", 1);

		if (isAccess()) {
			if (msg.getType() == 1) {// QueryName
				String studentName = msg.getValue();
				Student stu = stus.getStudentByName(studentName);
				if (stu != null) {
					Msg msgSend = new Msg(100, stu.toString());
					msgSend.setSocket(clientSocket);
					msgSend.Send();
				} else {
					Msg msgSend = new Msg(404, "Not Found");
					msgSend.setSocket(clientSocket);
					msgSend.Send();
				}
			} else if (msg.getType() == 2) {// QueryId
				int studentId = Integer.parseInt(msg.getValue());
				Student stu = stus.getStudentById(studentId);
				if (stu != null) {
					Msg msgSend = new Msg(100, stu.toString());
					msgSend.setSocket(clientSocket);
					msgSend.Send();
				} else {
					Msg msgSend = new Msg(404, "Not Found");
					msgSend.setSocket(clientSocket);
					msgSend.Send();
				}
			} else if (msg.getType() == 3) {// QueryTeamId

				int TeamId = Integer.parseInt(msg.getValue());
				ArrayList<Student> arrStu = stus.getStudentsByTeamId(TeamId);
				if (arrStu.size() > 0) {
					Msg msgSend = new Msg(300, Integer.toString(arrStu.size()));
					msgSend.setSocket(clientSocket);
					msgSend.Send();

					for (Student stu : arrStu) {
						msgSend = new Msg(100, stu.toString());
						msgSend.setSocket(clientSocket);
						msgSend.Send();
					}
				} else {
					Msg msgSend = new Msg(404, "Not Found");
					msgSend.setSocket(clientSocket);
					msgSend.Send();
				}
			}
		} else {
			
			Msg msgSend = new Msg(403, "Forbidden");
			msgSend.setSocket(clientSocket);
			msgSend.Send();		
		}
		pm.addIndex("RetMsg", 1);
		System.out.println(msg.getType() + " " + msg.getValue());
		fm.warn(300, "Request Handler :" + msg.getType() + " " + msg.getValue());
		
		return true;
	}

	@Override
	public void btnReload_Click() {
		reload();
	}
}