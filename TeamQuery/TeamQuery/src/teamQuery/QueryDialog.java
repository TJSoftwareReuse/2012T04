package teamQuery;
import java.awt.GridLayout;  
import java.awt.HeadlessException;  
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;  
import javax.swing.JFrame;  
import javax.swing.JLabel;  
import javax.swing.JPanel;  
import javax.swing.JTextField;  


  
public class QueryDialog extends JFrame implements ActionListener{  
  
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private EventInterface interfaceEvent = null;  
      
    JPanel row1 = new JPanel();
    JLabel labelName = new JLabel("Name" , JLabel.RIGHT); 
    JLabel labelId = new JLabel("Id" , JLabel.RIGHT);
    JLabel labelTeamId = new JLabel("TeamId" , JLabel.RIGHT); 
    JLabel labelSex = new JLabel("Sex" , JLabel.RIGHT);   
    JLabel labelAccount = new JLabel("Account" , JLabel.RIGHT);
    
    
    JPanel row2 = new JPanel();     
    JTextField textName = new JTextField(); 
    JTextField textId = new JTextField(); 
    JTextField textTeamId = new JTextField(); 
    JTextField textSex = new JTextField(); 
    JTextField textAccount = new JTextField(); 

    
    JPanel row3 = new JPanel();  
    JButton btnByName = new JButton("ByName");  
    JButton btnById = new JButton("ById");  

  
    public QueryDialog() throws HeadlessException, IOException {  
        super("TeamQuery");
        this.setResizable(false);
        
        setSize(280 , 150);  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //整体布局 1行3列
        GridLayout gridLayout = new GridLayout(1, 3, 10, 10);
        setLayout(gridLayout);
        
        //第一列布局，5个Label
        GridLayout gridLayout1 = new GridLayout(5, 1, 10, 1);
        row1.setLayout(gridLayout1);
        row1.add(labelName);  
        row1.add(labelId); 
        row1.add(labelTeamId);  
        row1.add(labelSex);  
        row1.add(labelAccount);
        
        //第二列布局，5个Text
        GridLayout gridLayout2 = new GridLayout(5, 1, 10, 1);
        row2.setLayout(gridLayout2);
        
        row2.add(textName);  
        row2.add(textId); 
        row2.add(textTeamId);  
        row2.add(textSex);  
        row2.add(textAccount); 
        
        textTeamId.setEditable(false);
        textSex.setEditable(false);
        textAccount.setEditable(false);
        
        //第三列布局，5个Button,只有两个可用
        GridLayout gridLayout3 = new GridLayout(5, 1, 10, 1);
        row3.setLayout(gridLayout3);
        
        row3.add(btnByName);
        row3.add(btnById);

        btnByName.addActionListener(this);
        btnById.addActionListener(this);
        add(row1);
        add(row2);        
        add(row3);
        
        setVisible(true);  
    }


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	       if(e.getSource() == btnByName) {
	    	   String strStudentName = textName.getText();
	    	   interfaceEvent.queryByName(strStudentName);
	       }
	       else if(e.getSource() == btnById) {
		   		int nStudentId = 0;
		   		
				try {
					nStudentId = Integer.parseInt(textId.getText());
				}
				catch (NumberFormatException e2) {
					textId.setText("error");
				}
				interfaceEvent.queryById(nStudentId);
	       }
	}


	public void setUI(String strName, int nId, int nTeamId, String strSex, String strAccount) {
		textName.setText(strName);
		textId.setText(Integer.toString(nId));
		textTeamId.setText(Integer.toString(nTeamId));
		textSex.setText(strSex);
		textAccount.setText(strAccount);
	}
	

	public void setInterfaceEvent(EventInterface interfaceEvent) {
		this.interfaceEvent = interfaceEvent;
	}  
      



}