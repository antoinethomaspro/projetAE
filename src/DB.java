import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DB {
	Connection con = null;
	Statement statement = null;

	ResultSet res = null;
	String driver = "com.mysql.cj.jdbc.Driver";
	public static final String URL = "jdbc:mysql://localhost:3306/appent_projet";
	public static final String USER = "root";
	public static final String PASSWD = "root";
	
	public DB() throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		con = DriverManager.getConnection(URL, USER, PASSWD);
		statement = con.createStatement();
	}
}
