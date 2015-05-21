package teamQuery;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import Main.FM;
import Main.license;
import PerMinute.PMPerMinute;

import com.eva.me.cm.ConfigUtil;



public class TeamQuery implements EventInterface {

	static ConfigUtil  cm;
    static FM          fm;
	static license     li;
	static PMPerMinute pm;
	static QueryDialog dlg;
	static Students	   stus;


	private static boolean init() {


		cm = new ConfigUtil();
		cm.loadConfigFile();
		//��ȡ���õ��û������������У��
		String strUser = cm.getProperty("User");
		String strPass = cm.getProperty("Password");
		int    nLicense = Integer.parseInt(cm.getProperty("license"));
		String strFmPath = cm.getProperty("FMFilePath");
		String strPmDir = cm.getProperty("PMDirPath");


		if(strFmPath.equals("default")) {
			strFmPath = "fm.log";
		}
		if(strPmDir.equals("default")) {
			strPmDir = "pm";
		}


		if(strUser.equals("abc") && strPass.equals("123456")) {

			//fm�����ļ�
			try {
			fm = new FM();
			fm.setLogFile(strFmPath);
			} catch (Exception e) {
				logError("fm Init Error");
			}
			//pm�����ļ�
			try {
			pm = new PMPerMinute(strPmDir);
			pm.start();
			}catch (Exception e) {
				logError("pm Init Error");
			}

			li = new license();
			//license��������
			li.ChangeInitialNum(nLicense);

			//��ʼ�����
			return true;

		} else {
			logError("User no Access");
		}

		return false;
	}

	@Override
 	public boolean queryByName(String strStudentName) {
		pm.addIndex("GetInfo", 1);

		//�жϲ���1
		if(li.JudgeServiceRequest()) {
			//�ṩ����
			fm.warn(123, "InService");
			pm.addIndex("InService", 1);
			if(!strStudentName.equals(null)) {
				Student stu = stus.getStudentByName(strStudentName);
				if (stu != null) {
					dlg.setUI(stu.getStudentName(), stu.getStudentId(), stu.getTeamId(), stu.getSex(),stu.getGithubAccount());
				} else {
					dlg.setUI("not found", 0, 0, "not found","not found");

				}
				pm.addIndex("GetMsg", 1);
				int remainder = li.getRemainedNum();
				if(remainder <= 3)
				{
					JOptionPane.showMessageDialog(dlg, "ʣ���ѯ����Ϊ"+remainder+"��");
				}

			}
			return true;
		} else {
			//�ܾ�����
			dlg.setUI("out service", 0, 0, "out service","out service");
			pm.addIndex("OutService", 1);
			fm.warn(124, "OutService");
			pm.addIndex("GetMsg", 1);
			return false;
		}
	}

	@Override
	public boolean queryById(int nStudentId) {
		pm.addIndex("GetInfo", 1);
		//�жϲ���1
		if(li.JudgeServiceRequest()) {
			//�ṩ����
			fm.warn(123, "InService");
			pm.addIndex("InService", 1);
			Student stu = stus.getStudentById(nStudentId);
			if (stu != null) {
				dlg.setUI(stu.getStudentName(), stu.getStudentId(), stu.getTeamId(), stu.getSex(),stu.getGithubAccount());
			} else {
				dlg.setUI("not found", 0, 0, "not found","not found");

			}
			//������Ϣ
			pm.addIndex("GetMsg", 1);
			int remainder = li.getRemainedNum();
			if(remainder <= 3)
			{
				JOptionPane.showMessageDialog(dlg, "ʣ���ѯ����Ϊ"+remainder+"��");
			}
			return true;
		} else {
			//�ܾ�����
			dlg.setUI("out service", 0, 0, "out service","out service");
			fm.warn(124, "OutService");
			pm.addIndex("OutService", 1);
			//������Ϣ
			pm.addIndex("GetMsg", 1);
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
		if(init()) {
			//��ȡ����
			String strTeamData = cm.getProperty("TeamData");
			if(strTeamData.equals("default")) {
				strTeamData = "team.txt";
			}

			try {
				stus = new Students(strTeamData);
			}
			catch(IOException e) {
				logError("TeamData not found");
				return;
			}
			//UI
			dlg = new QueryDialog();
			//���ûص�
			dlg.setInterfaceEvent(new TeamQuery());
		}

	}


}
