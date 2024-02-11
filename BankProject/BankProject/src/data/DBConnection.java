package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DBConnection {

	public static Connection getConnection(){
		Connection con=null;
		try
		{
			Class.forName("oracle.jdbc.OracleDriver");
			String url="jdbc:oracle:thin:@localhost:1521:XE";
			String uname="bankdb";
			String pass="bankdatabase";
			con=DriverManager.getConnection(url,uname,pass);
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return con;
	}
}
