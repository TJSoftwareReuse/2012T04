import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;  
import java.awt.HeadlessException;  
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;  
import javax.swing.JFrame;  
import javax.swing.JLabel;  
import javax.swing.JPanel;  
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;  


  
public class QueryDialog extends JFrame implements ActionListener{  
  
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private EventInterface interfaceEvent = null;  
      
    JPanel row1 = new JPanel();
    JPanel row2 = new JPanel();  
    JPanel row3 = new JPanel();  
    
    JLabel labelName = new JLabel("Name" , JLabel.LEFT); 
    JLabel labelId = new JLabel("Id" , JLabel.LEFT);
    JLabel labelTeamId = new JLabel("TeamId" , JLabel.LEFT); 
    JLabel labelSex = new JLabel("Sex" , JLabel.LEFT);   
    JLabel labelAccount = new JLabel("Account" , JLabel.LEFT);
    

   
      
      
    JTextField textName = new JTextField(); 
    JTextField textId = new JTextField(); 
    JTextField textTeamId = new JTextField(); 

    
    
    JButton btnByName = new JButton("ByName");  
    JButton btnById = new JButton("ById");  
    JButton btnByTeamId = new JButton("ByTeam");
    
    //JPanel row4 = new JPanel();     
    JTextArea textMember = new JTextArea(); 
    JScrollPane jsp = new JScrollPane(textMember);
    public QueryDialog() throws HeadlessException, IOException {  
        super("TeamQuery");
        this.setResizable(false);
        
        setSize(400 , 400);  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //整体布局 1行3列
        GridLayout gridLayout = new GridLayout(2, 3, 12, 1);
        row1.setLayout(gridLayout);
        row1.add(textName);  
        row1.add(textId); 
        row1.add(textTeamId);  
        row1.add(btnByName);  
        row1.add(btnById);
        row1.add(btnByTeamId);
        
        //第二列布局，5个Text
        GridLayout gridLayout1 = new GridLayout(1, 5, 10, 1);
        row2.setLayout(gridLayout1);        
        row2.add(labelName);  
        row2.add(labelId); 
        row2.add(labelTeamId);  
        row2.add(labelSex);  
        row2.add(labelAccount); 
        
        //第三列布局，5个Button,只有两个可用
 /*       GridLayout gridLayout3 = new GridLayout(5, 1, 10, 1);
        row3.setLayout(gridLayout3);
        
        row3.add(btnByName);
        row3.add(btnById);
        row3.add(btnByTeamId);*/
        
        btnByName.addActionListener(this);
        btnById.addActionListener(this);
        btnByTeamId.addActionListener(this);
        
/*        GridLayout gridLayout4 = new GridLayout(1, 1, 10, 1);
        row4.setLayout(gridLayout4);        
        row4.add(textMember);
        add(row1);
        add(row2);        
        add(row3);
        add(row4);*/  
        jsp.setPreferredSize(new Dimension(400,280));
        
        this.setLayout(new FlowLayout());
        
        this.add(row1);
        this.add(row2);
        this.add(jsp);
        setVisible(true);  
    }


	@Override
	public void actionPerformed(ActionEvent e) {
	       if(e.getSource() == btnByName) {
	    	   String strStudentName = textName.getText();
	    	   textId.setText("");
	    	   textTeamId.setText("");
	    	   interfaceEvent.queryByName(strStudentName);
	       } else if(e.getSource() == btnById) {
		   		int nStudentId = 0;
		   		
				try {
					nStudentId = Integer.parseInt(textId.getText());
			    	textName.setText("");
			    	textTeamId.setText("");
				}
				catch (NumberFormatException e2) {
					textId.setText("error");
				}
				interfaceEvent.queryById(nStudentId);
	       } else if(e.getSource() == btnByTeamId) {
		   		int nTeamId = 0;
		   		textMember.setText("");
				try {
					nTeamId = Integer.parseInt(textTeamId.getText());
			    	textId.setText("");
			    	textName.setText("");
				}
				catch (NumberFormatException e2) {
					textTeamId.setText("error");
				}
				interfaceEvent.queryByTeamId(nTeamId);
	       }
	}


	public void setUI(String strName, int nId, int nTeamId, String strSex, String strAccount) {
		String outStr = String.format("%23s %10d %10d %15s %20s", strName,nId,nTeamId,strSex,strAccount);
		this.textMember.setText(outStr);
	}

	public void setList(ArrayList<Student> arrStus) {
		for(Student stu:arrStus) {
			String strTemp = textMember.getText();
			String strName = stu.getStudentName();
			int nId = stu.getStudentId();
			int nTeamId = stu.getTeamId();
			String strSex = stu.getSex();
			String strAccount = stu.getGithubAccount();
			String strLine = String.format("%23s %10d %10d %15s %20s", strName,nId,nTeamId,strSex,strAccount);
			strTemp += strLine;
			strTemp += "\r\n";
			textMember.setText(strTemp);			
		}
	}

	public void setInterfaceEvent(EventInterface interfaceEvent) {
		this.interfaceEvent = interfaceEvent;
	}  
      



}