/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapplication;
import java.awt.*;
import java.awt.event.*;
import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.swing.*;

import java.sql.*;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger; 
import org.mindrot.BCrypt;
import sun.misc.BASE64Encoder;
public class otpVerificationWindow extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	private JButton Ok;
        private JTextField otpTextField;
        private Container cp;
        private JLabel title;
        String username;
        String password;
        String emailId;
        String otp;
        Container c;    
    public otpVerificationWindow(String username, String password, String emailId) {         		 
		title=new JLabel("<html><font color=#003300 size=+1><strong>Please Enter the Otp</strong></font>");
		JLabel imageLabel1 = new JLabel();			    		
		 Ok=new JButton("Ok");
                 this.username=username;
                 this.password=password;
                 this.emailId=emailId;
		 Ok.addActionListener(this);	
		 otpTextField=new JTextField(10);	 
		 c=getContentPane();
		 c.setLayout(null);		 
		 c.add(imageLabel1);		 
		 c.add(title);		 
	         c.add(Ok);
		 c.add(otpTextField);		 
		c.setBackground(Color.LIGHT_GRAY);
                 this.setLocation(350,100);	
		 title.setBounds(110,30,700,50);	   		
		 Ok.setBounds(110,155,75,25);              
		 otpTextField.setBounds(110,100,150,25);		
		 setSize(550,350);
		 setVisible(true);
		 setTitle("Login");
                 Random r=new Random();
                otp=Integer.toString(r.nextInt(1000));
                email em=new email();
                em.sendmail(emailId,otp);
    }     
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Ok"))
        { 
                	sqlConnection connection=new sqlConnection();
            	        Connection con=connection.connectSqlDb();
            try {
                    if(otpTextField.getText().toString().trim().equals(otp)) 
                          {
                                 
                                 java.sql.Statement stmt=con.createStatement();
                                 String query = "INSERT INTO users(user_name,user_password,email_ID,public_key,private_key) VALUES (?,?,?,?,?)";
				 PreparedStatement preparedStmt = con.prepareStatement(query);
  				 preparedStmt.setString(1,username);   
				 preparedStmt.setString(2,BCrypt.hashpw(password, BCrypt.gensalt()));
                                 preparedStmt.setString(3,emailId);
                                 //public and private key genration
                                 
                                  KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            KeyFactory fact = KeyFactory.getInstance("RSA");
            kpg.initialize(2048);
            KeyPair kp = kpg.genKeyPair();
            PublicKey publicKey = kp.getPublic();
            PrivateKey privateKey = kp.getPrivate();
            //converting public key  and private key to byte array
            byte privkey[]=privateKey .getEncoded();
            byte pubkey[]=publicKey.getEncoded();
            BASE64Encoder encoder = new BASE64Encoder();
            //converting byte array of public key to string
            String privatekey= encoder.encode(privkey);
            String key = "randomSalt"+ username + password;
           String encryptedPrivateKey= aesEncryption.encrypt(privatekey, key);
            
            String publickey= encoder.encode(pubkey);
                                 
                                 preparedStmt.setString(4,publickey);
                                 preparedStmt.setString(5,encryptedPrivateKey);
				 preparedStmt.executeUpdate();
				 String query1="CREATE TABLE"+" "+username+"  (ID int NOT NULL AUTO_INCREMENT,user VARCHAR(30000), message VARCHAR(30000),  PRIMARY KEY (ID));";
				 PreparedStatement s=con.prepareStatement(query1);
				 s.executeUpdate();
				 con.close();
                                 loginPage l=new loginPage();
                                 l.setVisible(true);
                                 dispose();
                           }   
                           else
                           {
                               JOptionPane.showMessageDialog(null, "OTP Entered is Wrong");
                               Registration reg=new Registration();
                               reg.setVisible(true);
                           }
            } catch (SQLException ex) {
                Logger.getLogger(otpVerificationWindow.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(otpVerificationWindow.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(otpVerificationWindow.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(otpVerificationWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
					
        }
    }
 
    public static void main(String[] args) {
        new otpVerificationWindow(null,null,null);
    }
 
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}
