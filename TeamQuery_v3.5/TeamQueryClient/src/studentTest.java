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
	        return Arrays.asList(new Object[][] { { 1252992, "���ݷ�" }, { 1252996, "���컶" }, { 1252984, "����˹" },  
	                { 1252937, "�����B" }, { 1252885, "��Цӯ" }, { 1252977, "����" }, { 1252878, "��" } });  
	    } 
	
	
	public studentTest(int id, String name)
	{
		stuId = id;
		stuName = name;
	}

	@Before
	public void setUp() throws Exception {
		System.out.println("���Կ�ʼ��");
		stu = new Students("Team.txt");
		System.out.println("stu���󱻳�ʼ����");
	}

	@After
	public void tearDown() throws Exception {
        System.out.println("stu���󽫱�����");
        stu = null;
        System.out.println("���Խ�����");
	}

	@Test
	public void testGetStudentByName() {
		Student std = stu.getStudentByName(stuName);
		assertEquals("failure of the getStudentByName when use "+stuName+" as the parameter",stuId,std.getStudentId());
		System.out.println("getStudentByName���������ԣ�");
	}

	@Test
	public void testGetStudentById() {
		Student std1 = stu.getStudentById(stuId);
		assertEquals("failure of the getStudentById when use "+stuId+" as the parameter",stuName,std1.getStudentName());
		System.out.println("getStudentById���������ԣ�");
	}

}
