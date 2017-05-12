package chatapplication;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import org.mindrot.BCrypt;
public class loginPage extends JFrame implements ActionListener{
 	private static final long serialVersionUID = 1L;
	 JLabel fname;
	 JLabel file;
	 JLabel packet;
	 JLabel title;
	 JLabel n1,n2;
	 JButton OkButton,RegisterButton;
	 JTextField userName;
	 JPasswordField password;
	 String msg="";
	 Container c;
         public loginPage() {
		 fname=new JLabel("<html><font color=#003300 size=3><strong>User Name</strong></font>");
		 file=new JLabel("<html><font color=#003300 size=3><strong>Password</strong></font>");
                 title=new JLabel("<html><font color=#003300 size=+1><strong>User Login</strong></font>");
		 JLabel imageLabel1 = new JLabel();
		ImageIcon v1 = new ImageIcon(this.getClass().getResource("user_login.png"));
		imageLabel1.setIcon(v1);
	        OkButton=new JButton("Login");
		OkButton.addActionListener(this);
                  RegisterButton=new JButton("Register");
		 RegisterButton.addActionListener(this);
	         userName=new JTextField(10);
		 password=new JPasswordField(10);
		 c=getContentPane();
		 c.setLayout(null);
		 c.add(fname);
		 c.add(file);
		 c.add(imageLabel1);
		 
		 c.add(title);
		 
	     c.add(OkButton);
             c.add(RegisterButton);
	
		 c.add(userName);
		 c.add(password);
		 
		 c.setBackground(Color.LIGHT_GRAY);
         this.setLocation(350,100);
          title.setBounds(110,30,700,50);
         imageLabel1.setBounds(300,75,263,192);
	  fname.setBounds(30,100,250,25);
	file.setBounds(30,150,250,25);
	OkButton.setBounds(120,215,75,25);
         RegisterButton.setBounds(240, 215, 100, 25);
	userName.setBounds(150,100,150,25);
		password.setBounds(150,150,150,25);
		setSize(550,350);
		 setVisible(true);
		 setTitle("Login");
            }     
    class MyButton extends JButton {
 	   private static final long serialVersionUID = 1L;
           MouseListener m = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                System.exit(0);
            }
        };
         public MyButton(String label){
            super(label);
                    addMouseListener(m);
            }
         }
   
    public void actionPerformed(ActionEvent e) {
       
        if(e.getActionCommand().equals("Register"))
        {   
            setVisible(false);
             Registration r=new Registration();
               r.setVisible(true);             
        }
        else if(e.getActionCommand().equals("Login"))
        {
                        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            sqlConnection s=new sqlConnection();
            Connection conn = s.connectSqlDb();
            Statement st = conn.createStatement();
            ResultSet rec = st.executeQuery("SELECT user_name, user_password FROM users");
 
        while (rec.next()) {
            String x = userName.getText();
            String y = password.getText();
            if (x.trim().equals(rec.getString("user_name").trim())) {
                if (BCrypt.checkpw(y.trim(),rec.getString("user_password").trim())) {
                	usersWindow u=new usersWindow(x.trim(),y.trim());
                	u.setVisible(true);
                	dispose();
                } else {
                	 JOptionPane.showMessageDialog(null, "Invalid Password");
                }
            }  
 
        }
 
        st.close();
        } catch(SQLException d) {
            System.out.println(e.toString());
        } catch(ClassNotFoundException f) {
            System.out.println(e.toString());
        }
        }
    }
    public static void main(String[] args) {
        new loginPage();
    }
 
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}