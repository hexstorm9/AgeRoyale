package business;

import persistence.DatabaseInfoDAO;

import java.io.IOException;

/**
 * DatabaseInfo will hold the information to access a database.
 *
 * <p>The params it will contain are:
 * <ul><li>IP</li><li>Port</li><li>Database User</li><li>Database User Password</li><li>Database Name</li></ul>
 *
 * <p>Take into account that in order for this class to work, {@link DatabaseInfoDAO} is needed.
 *
 * @see DatabaseInfoDAO
 * @version 1.0
 */
public class DatabaseInfo {

    private DatabaseInfoDAO databaseInfoDAO;

    private String ip;
    private String port;
    private String user;
    private String password;
    private String databaseName;

    /**
     * Default DatabaseInfo constructor.
     * <p>Loads all the attributes with the help of a {@link DatabaseInfoDAO}
     *
     * @throws IOException Whenever the DatabaseInfo file can't be read or information is corrupted
     */
    public DatabaseInfo() throws IOException{
        databaseInfoDAO = new DatabaseInfoDAO();
        String[] databaseInfo = databaseInfoDAO.read();
        if(databaseInfo.length != 5) throw new IOException("Database Info is not correct");

        ip = databaseInfo[0];
        port = databaseInfo[1];
        user = databaseInfo[2];
        password = databaseInfo[3];
        databaseName = databaseInfo[4];
    }


    /**
     * Returns the IP of the database
     * @return IP of the database
     */
    public String getIp(){
        return ip;
    }

    /**
     * Returns the Port of the database
     * @return Port of the database
     */
    public String getPort(){
        return port;
    }

    /**
     * Returns the user of the database
     * @return User of the database
     */
    public String getUser(){
        return user;
    }

    /**
     * Returns the password of the user in the database
     * @return The password of the user in the database
     */
    public String getPassword(){
        return password;
    }

    /**
     * Returns the database name
     * @return The database name
     */
    public String getDatabaseName(){
        return databaseName;
    }

}