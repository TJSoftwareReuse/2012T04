import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class teamQueryTest {
	private Students stus = null;
	private int teamId;
	private ArrayList<String> stusOfTeam;
	
	@Parameters
	 public static Iterable<Object[]> data()
	 {
		 ArrayList<String> t1 = new ArrayList<String>();
		 t1.add("吴逸菲");
		 t1.add("黄徐欢");
		 t1.add("李亚斯");
		 t1.add("许铭淏");
		 ArrayList<String> t5 = new ArrayList<String>();
		 t5.add("关清晨");
		 t5.add("杨春雨");
		 t5.add("周泽宏");
		 t5.add("杨宇歆");
		 t5.add("张良");
		 ArrayList<String> t8 = new ArrayList<String>();
		 t8.add("丁宇笙");
		 t8.add("邱尚昭");
		 t8.add("高屹");
		 t8.add("杨丰");
		 t8.add("贺志鹏");
		 return Arrays.asList(new Object[][] { {1,t1},{5,t5},{8,t8} } );
	 }
	 public teamQueryTest(int _teamId, ArrayList<String> _stusOfTeam)
	 {
		 teamId = _teamId;
		 stusOfTeam = _stusOfTeam;
	 }
	 private boolean compare(ArrayList<String> pre , ArrayList<String> res)
	 {
		 for(String preName:pre)
		 {
			 boolean flag = false;
			 for(String resName:res)
			 {
				 if(preName.equals(resName))
				 {
					 flag = true;
					 break;
				 }
			 }
			 if(!flag)
			 {
				 return false;
			 }
		 }
		 return true;
	 }
	
	@Before
	public void setUp() throws Exception {
		System.out.println("测试开始！");
		stus = new Students("Team.txt");
		System.out.println("stus对象被初始化！");
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("stus对象将被清理！");
        stus = null;
        System.out.println("测试结束！");
	}

	@Test
	public void testGetStudentsByTeamId() {
		ArrayList<Student> stuRes = stus.getStudentsByTeamId(teamId);
		ArrayList<String> nameRes = new ArrayList<String>();
		for(Student s:stuRes)
		{
			nameRes.add(s.getStudentName());
		}
		assertTrue("failure of the getStudentById when use "+teamId+" as the parameter",compare(stusOfTeam,nameRes));
		System.out.println("getStudentsByTeamId方法被测试！");
	}

}
