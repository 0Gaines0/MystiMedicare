package application.DAL;

/**
 * Connection string
 * @author drivera
 */
public class ConnectionString {
	
	private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String HOST = "cs-dblab01.uwg.westga.edu";
    private static final String DATABASE = "cs3230f24b";
    private static final int PORT = 3306;

    public static final String CONNECTION_STRING = String.format("jdbc:mysql://%s:%d/%s?user=%s&password=%s", HOST, PORT, DATABASE, USERNAME, PASSWORD);

}
