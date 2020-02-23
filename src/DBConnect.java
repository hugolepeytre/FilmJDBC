import java.sql.*;
import java.util.Properties;

public abstract class DBConnect {
    protected Connection connect;
    public DBConnect() {
    	try {
            Class.forName("com.mysql.cj.jdbc.Driver");	    
            // Properties for user and password.
            Properties p = new Properties();
            p.put("user", "root");
            p.put("password", "69fbb653e11ee50ae27c60646cc94339");
            connect = DriverManager.getConnection("jdbc:mysql://127.0.0.1/ekt?autoReconnect=true&useSSL=false", p);
        } catch (Exception e) {
            throw new RuntimeException("Unable to connect", e);
    	}
    }
}
