import java.awt.HeadlessException;
import java.io.IOException;

import java.util.ArrayList;

public class TeamQueryClient implements EventInterface {

	static QueryDialog dlg;
	static Students stus;

	@Override
	public boolean queryByName(String strStudentName) {

		if (!strStudentName.equals(null)) {
			Student stu = stus.getStudentByName(strStudentName);
			if (stu != null) {
				dlg.setUI(stu.getStudentName(), stu.getStudentId(),
						stu.getTeamId(), stu.getSex(), stu.getGithubAccount());
			} else {
				dlg.setUI("error", 0, 0, "error", "error");

			}
			return true;
		}
		return false;
	}

	@Override
	public boolean queryById(int nStudentId) {

		Student stu = stus.getStudentById(nStudentId);
		if (stu != null) {
			dlg.setUI(stu.getStudentName(), stu.getStudentId(),
					stu.getTeamId(), stu.getSex(), stu.getGithubAccount());
			return true;
		} else {
			dlg.setUI("error", 0, 0, "error", "error");
			return false;
		}
		
	}

	@Override
	public boolean queryByTeamId(int nTeamId) {

		ArrayList<Student> arrStus = stus.getStudentsByTeamId(nTeamId);
		if (arrStus != null) {

				dlg.setList(arrStus);
			
		} else {
			dlg.setUI("error", 0, 0, "error", "error");
		}
		return false;
	}

	public static void main(String[] args) throws Exception, IOException {
		// TODO Auto-generated method stub

		stus = new Students("127.0.0.1", 12345);
		// UI
		dlg = new QueryDialog();
		// …Ë÷√ªÿµ˜
		dlg.setInterfaceEvent(new TeamQueryClient());
	}

}