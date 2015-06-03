import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import teamQuery.Student;
import teamQuery.Students;

@RunWith(Parameterized.class)
public class studentTest {
	private Students stu = null;
	private int stuId;
	private String stuName;
	
	@Parameters
	 public static Iterable<Object[]> data() {  
	        return Arrays.asList(new Object[][] { { 1252992, "吴逸菲" }, { 1252996, "黄徐欢" }, { 1252984, "李亚斯" },  
	                { 1252937, "许铭淏" }, { 1252885, "王笑盈" }, { 1252977, "孙琳" }, { 1252878, "许舰" } });  
	    } 
	
	
	public studentTest(int id, String name)
	{
		stuId = id;
		stuName = name;
	}

	@Before
	public void setUp() throws Exception {
		System.out.println("测试开始！");
		stu = new Students("Team.txt");
		System.out.println("stu对象被初始化！");
	}

	@After
	public void tearDown() throws Exception {
        System.out.println("stu对象将被清理！");
        stu = null;
        System.out.println("测试结束！");
	}

	@Test
	public void testGetStudentByName() {
		Student std = stu.getStudentByName(stuName);
		assertEquals("failure of the getStudentByName when use "+stuName+" as the parameter",stuId,std.getStudentId());
		System.out.println("getStudentByName方法被测试！");
	}

	@Test
	public void testGetStudentById() {
		Student std1 = stu.getStudentById(stuId);
		assertEquals("failure of the getStudentById when use "+stuId+" as the parameter",stuName,std1.getStudentName());
		System.out.println("getStudentById方法被测试！");
	}

}
