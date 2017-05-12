package chatapplication;


import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class usersWindow extends Frame implements ActionListener, WindowListener {

        Container c;
        String loginUser;
        JLabel title;
        String userPassword;
       // String user;
        public static void main(String[] args) {
        	usersWindow users = new usersWindow(null,null);
        	users.setSize(550,350);
                users.setVisible(true);
        }

        public usersWindow(String loginUser, String userPassword) {
                		 
		 title=new JLabel("<html><font color=#003300 size=+1><strong>Users Available</strong></font>");
		 
		this.loginUser=loginUser;
                this.userPassword=userPassword;
		JLabel imageLabel1 = new JLabel();
		ImageIcon v1 = new ImageIcon(this.getClass().getResource("users.png"));
		imageLabel1.setIcon(v1);			 
		// c=getContentPane();
		 setLayout(null);		 
		 add(imageLabel1);
                  setLayout(new FlowLayout());
		// add(title); 
                 addWindowListener(this);
		 setBackground(Color.LIGHT_GRAY);
                 this.setLocation(350,100);	 
		title.setBounds(110,30,700,50);	
	        addusers(loginUser);
               
	   	imageLabel1.setBounds(300,75,263,192);	 
		 setSize(350,350);
		
		 setTitle("Available Users");
             
        }

        private void addusers(String loginuser) {
        	try {
                   
        		sqlConnection sqlCon=new sqlConnection();
        		Connection con=sqlCon.connectSqlDb();
                Statement stmt = con.createStatement();
                String query = "select * from users ;";
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                	if(!rs.getObject(1).toString().trim().equals(loginuser))
                	{
                        JButton btn = new JButton(rs.getObject(1).toString());
                        btn.addActionListener(this);
                        add(btn);
                	}
                }
            } catch (SQLException e) {
                e.printStackTrace();
                for(Throwable ex : e) {
                    System.err.println("Error occurred " + ex);
                }
                System.out.println("Error in fetching data");
            }
			
		}

		public void actionPerformed(ActionEvent e) {
            try {
                chatWindow c;
                c = new chatWindow(loginUser,e.getActionCommand(),userPassword); // TODO Auto-generated catch block
                c.setVisible(true);
            } catch (SQLException ex) {
                Logger.getLogger(usersWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        	
        	
        		   
        }

        public void windowClosing(WindowEvent e) {
                dispose();
                System.exit(0);
        }

        public void windowOpened(WindowEvent e) {}
        public void windowActivated(WindowEvent e) {}
        public void windowIconified(WindowEvent e) {}
        public void windowDeiconified(WindowEvent e) {}
        public void windowDeactivated(WindowEvent e) {}
        public void windowClosed(WindowEvent e) {}
}