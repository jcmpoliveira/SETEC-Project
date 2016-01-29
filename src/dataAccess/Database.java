package dataAccess;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class Database 
{

	static String url = "jdbc:mysql://localhost:3306/setec";
    static String user = "root";
    static String password = "root";
    static String driver = "com.mysql.jdbc.Driver";
    
    public static String getUrl() 
    {
        return url;
    }

     
    public Connection getConnection() {
    	java.sql.Connection con = null;
    		try {	
    			Class.forName(driver);
				con = DriverManager.getConnection(url, user, password);
			} catch (SQLException e ) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	
    		return (Connection) con;    	    	
    }
    
    public static String getUser() 
    {
        return user;
    }
  
    public static String getPassword() 
    {
        return password;
    }
   
}
