package chatapplication;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class sqlConnection {
	
	public Connection connectSqlDb()
	{

		 String Db = "jdbc:mysql://ec2-35-167-85-89.us-west-2.compute.amazonaws.com:3306/test1";
			String username = "remoteu";
			String password = "root1";
		    
		    try {
		        Class.forName("com.mysql.jdbc.Driver");
		    } catch (ClassNotFoundException e) {
		        System.out.println("Where is your MySQL JDBC Driver?");
		        e.printStackTrace();
		    }


		    java.sql.Connection connection = null;
		    try {
		    	
		    	 connection = DriverManager.getConnection(Db, username, password);

		    } catch (SQLException e) {
		        System.out.println("Connection Failed!:\n" + e.getMessage());
		    }

		   
		        return connection;
	}

}
