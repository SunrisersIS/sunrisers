package chatapplication;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JPasswordField;
public class Registration extends JFrame implements ActionListener{
     private JTextField tf;
    private JPasswordField pf;
    private JTextField emailId;
 
    private Container cp;
    JLabel title;
    private JLabel chooseUserLabel;
    private JLabel label1 ;
     private JLabel emailIdLable;
    private  SignUpButton Ca = new SignUpButton("Signup");
     
    public Registration() {
        super("Registration");
        setLocation(500,280);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel imageLabel1 = new JLabel();
		ImageIcon v1 = new ImageIcon(this.getClass().getResource("user_login.png"));
		imageLabel1.setIcon(v1);
        title=new JLabel("<html><font color=#003300 size=+1><strong>User Registration</strong></font>");
        chooseUserLabel=new JLabel("<html><font color=#003300 size=3><strong>User Name</strong></font>");
        label1=new JLabel("<html><font color=#003300 size=3><strong>Password</strong></font>");
        emailIdLable=new JLabel("<html><font color=#003300 size=3><strong>Email</strong></font>");
        tf = new JTextField(10);
        pf = new JPasswordField(10);
        emailId=new JTextField(35);
        FlowLayout flow = new FlowLayout();
        
        cp = getContentPane();
        cp.setLayout(null);
        cp.setBackground(Color.LIGHT_GRAY);
        cp.add(title);
        title.setBounds(180,30,200,25);
        cp.add(chooseUserLabel);
        cp.add(tf);
       tf.setBounds(180,100,150,25);
       cp.add(imageLabel1);
       imageLabel1.setBounds(300,75,263,192);
        cp.add(label1);
        cp.add(pf);
       pf.setBounds(180,150,150,25);
        cp.add(emailIdLable);
        cp.add(emailId);
        emailId.setBounds(180,200,150,25);
        cp.add(Ca);
        Ca.setBounds(180,250,150,25);
        chooseUserLabel.setBounds(30,100,250,25);
	label1.setBounds(30,150,250,25);
        emailIdLable.setBounds(30,200,250,25);	
        tf.setHorizontalAlignment(SwingConstants.CENTER);
        pf.setHorizontalAlignment(SwingConstants.CENTER);
        setSize(550,350);     
        setVisible(true);
    } 
    
    class SignUpButton extends JButton {
        MouseListener m = new MouseAdapter() {
         
            public void mousePressed(MouseEvent e) {
                        
        		otpVerificationWindow v=new otpVerificationWindow(tf.getText(),pf.getText(),emailId.getText());
                        setVisible(false);
                        v.setVisible(true);                        
	                dispose();
				  	
            }
        };
               
        public SignUpButton(String label){
            super(label);
                    addMouseListener(m);
            }
     
    }
    public static void main(String args[])
    {
    	new Registration();
    }
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}