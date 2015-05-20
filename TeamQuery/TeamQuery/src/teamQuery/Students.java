package teamQuery;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Students {
	ArrayList<Student> arrStudents = new ArrayList<Student>();
	
	
	public Students(String strPath) throws IOException {
		File file = new File(strPath);
		BufferedReader br = new BufferedReader(new FileReader(file));
		String strLine;
		while((strLine = br.readLine()) != null ) {
			
			int nTeamId=0;
			int nStudentId=0;
			String strStudentName=null;
			String strSex=null;
			String strGithubAccount=null;
		
			String strArray[] = strLine.split(",");
			if(strArray.length >= 4) {	
				nTeamId = Integer.parseInt((strArray[0].trim()));
				nStudentId = Integer.parseInt(strArray[1].trim());
				strStudentName = strArray[2].trim();
				strSex = strArray[3].trim();
				
				if(strArray.length >= 5)//¥Ê‘⁄’Àªß
					strGithubAccount = strArray[4];
	
				Student stu = new Student(nTeamId,nStudentId,strStudentName,strSex,strGithubAccount);
				arrStudents.add(stu);
			}

		}
		br.close();
	}
	
	
	public Student getStudentByName(String strStudentName) {
		for(Student stu:arrStudents) {
			if(strStudentName.equals(stu.getStudentName())) {
				return stu;
			}
		}
		return null;
	}
	
	public Student getStudentById(int nStudentId) {
		for(Student stu:arrStudents) {
			if(nStudentId == stu.getStudentId()) {
				return stu;
			}
		}
		return null;
	}	
}
