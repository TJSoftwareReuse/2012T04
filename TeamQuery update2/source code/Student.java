public class Student {
	
	private int nTeamId;
	private int nStudentId;
	private String strStudentName;
	private String strSex;
	private String strGithubAccount;
	
	public Student(int nTeamId, int nStudentId, String strStudentName, String strSex, String strGithubAccount) {
		this.nTeamId = nTeamId;	
		this.nStudentId = nStudentId;		
		this.strStudentName = strStudentName;
		this.strSex = strSex;
		this.strGithubAccount = strGithubAccount;
	}
	
	public int getTeamId() {
		return nTeamId;
	}
	public void setTeamId(int nTeamId) {
		this.nTeamId = nTeamId;
	}
	public int getStudentId() {
		return nStudentId;
	}
	public void setStudentId(int nStudentId) {
		this.nStudentId = nStudentId;
	}
	public String getStudentName() {
		return strStudentName;
	}
	public void setStudentName(String strStudentName) {
		this.strStudentName = strStudentName;
	}
	public String getSex() {
		return strSex;
	}
	public void setSex(String strSex) {
		this.strSex = strSex;
	}
	public String getGithubAccount() {
		return strGithubAccount;
	}
	public void setGithubAccount(String strGithubAccount) {
		this.strGithubAccount = strGithubAccount;
	}	
}
