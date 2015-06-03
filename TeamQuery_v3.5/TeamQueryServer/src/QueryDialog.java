import java.awt.GridLayout;  
import java.awt.HeadlessException;  
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;  
import javax.swing.JFrame;  
import javax.swing.JLabel;  
import javax.swing.JList;
import javax.swing.JPanel;  
import javax.swing.JTextArea;
import javax.swing.JTextField;  


  
public class QueryDialog extends JFrame implements ActionListener{  
  
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private EventInterface interfaceEvent = null;  
      

    JButton btnReload = new JButton("Reload");
    

  
    public QueryDialog() throws HeadlessException, IOException {  
        super("TeamQuery");
        this.setResizable(false);
        
        setSize(400 , 150);  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        btnReload.addActionListener(this);
        add(btnReload);
        setVisible(true);  
    }


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	       if(e.getSource() == btnReload) {
	    	   interfaceEvent.btnReload_Click();
	       } 
	}

	public void setInterfaceEvent(EventInterface interfaceEvent) {
		this.interfaceEvent = interfaceEvent;
	}  
      



}