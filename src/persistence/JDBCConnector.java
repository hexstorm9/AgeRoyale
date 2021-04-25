package persistence;

import business.DatabaseInfo;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

/**
 * JDBCConnector is the class that connects and performs operations to the Database
 * which access information is found in the {@code resources.json} file.
 *
 * <p> Before using it, the method {@link JDBCConnector#initializeAndTestConnection()} must be called.
 *
 * @see DatabaseInfo
 * @see DatabaseInfoDAO
 * @version 1.0
 */
public class JDBCConnector {

    private static JDBCConnector singletonInstance;
    private DatabaseInfo databaseInfo;

    private static final int CONNECTION_TIMEOUT = 3500; //Max. 3500ms

    private String databaseURL;
    private Connection databaseConnection;


    /**
     * Default JDBCConnector Constructor.
     * <p>Private as the class uses the singleton pattern.
     */
    private JDBCConnector(){}

    /**
     * Returns the singleton instance of the JDBCConnector class
     * @return Singleton instance of the class
     */
    public static JDBCConnector getInstance(){
        if(singletonInstance == null) singletonInstance = new JDBCConnector();
        return singletonInstance;
    }

    /**
     * Before start using the singleton, this method has to be called in order to
     * retrieve database information from a file and initialize the connection with it.
     *
     * @throws IOException If DatabaseInformation from file can't be read
     * @throws ClassNotFoundException If the JDBC Driver can't be loaded
     * @throws SQLException If the DatabaseConnection is not valid or a connection can't be established
     */
    public void initializeAndTestConnection() throws IOException, ClassNotFoundException, SQLException {
        databaseInfo = new DatabaseInfo();
        databaseURL = "jdbc:mysql://" + databaseInfo.getIp() + ":" + databaseInfo.getPort() +
                "/" + databaseInfo.getDatabaseName();

        Class.forName("com.mysql.cj.jdbc.Driver"); //Load the MySQL JDBC Driver through Reflection

        databaseConnection = DriverManager.getConnection(databaseURL, databaseInfo.getUser(), databaseInfo.getPassword());
        boolean validConnection = databaseConnection.isValid(CONNECTION_TIMEOUT);
        if(!validConnection) throw new SQLException("There is no valid connection to the Database");
        else System.out.println("Connection valid!");

        databaseConnection.close();
    }


    /**
     * Queries the Database with the query provided and returns the ResultSet object
     *
     * <p>This methods is only intended for SELECT queries (getting information from the database).
     * For other types of query, use {@link JDBCConnector#updateDatabase(String)}
     *
     * @param formattedQuery The SQL query to be executed
     * @return An {@code ArrayList<Object>} with the information retrieved from the database without formatting (Java Object format)
     * @throws SQLException If the connection can't be established or the query is incorrect
     */
    public ArrayList<Object> queryDatabase(String formattedQuery) throws SQLException{
        databaseConnection = DriverManager.getConnection(databaseURL, databaseInfo.getUser(), databaseInfo.getPassword());
        Statement statement = databaseConnection.createStatement();
        ResultSet resultSet = statement.executeQuery(formattedQuery); //Holds the information retrieved from the DB

        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int columns = resultSetMetaData.getColumnCount();

        ArrayList<Object> list = new ArrayList<>();
        while(resultSet.next()){
            for(int i = 0; i < columns; i++){
                list.add(resultSet.getObject(i+1)); //Result set index starts at 1
            }
        }

        databaseConnection.close();
        return list;
    }


    /**
     * Queries the Database with the multiple queries provided and returns an ArrayList of Objects.
     *
     * <p>This methods is only intended for SELECT queries (getting information from the database).
     * For other types of query, use {@link JDBCConnector#updateDatabase(String)}
     *
     * @param formattedQueries The SQL queries to be executed
     * @return An {@code ArrayList<Object>} with the information retrieved from the database without formatting (Java Object format)
     * @throws SQLException If the connection can't be established or the query is incorrect
     */
    public ArrayList<Object> queryDatabase(String[] formattedQueries) throws SQLException{
        databaseConnection = DriverManager.getConnection(databaseURL, databaseInfo.getUser(), databaseInfo.getPassword());
        Statement statement = databaseConnection.createStatement();

        ArrayList<Object> list = new ArrayList<>();

        for(String currentQuery: formattedQueries){
            ResultSet resultSet = statement.executeQuery(currentQuery); //Holds the information retrieved from the DB

            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columns = resultSetMetaData.getColumnCount();

            while(resultSet.next()){
                for(int i = 0; i < columns; i++){
                    list.add(resultSet.getObject(i+1)); //Result set index starts at 1
                }
            }
        }

        databaseConnection.close();
        return list;
    }


    /**
     * Creates, Updates or Deletes the database with the query provided.
     * <p>These queries do not return information, so the method does neither.
     * <p>If the query fails, a {@link SQLException} is thrown.
     *
     * @param formattedQuery The query to be executed in the database
     * @throws SQLException When the query is incorrect or a connection can't be established with the database
     */
    public void updateDatabase(String formattedQuery) throws SQLException{
        databaseConnection = DriverManager.getConnection(databaseURL, databaseInfo.getUser(), databaseInfo.getPassword());
        Statement statement = databaseConnection.createStatement();

        statement.executeUpdate(formattedQuery);
        databaseConnection.close();
    }


    /**
     * Creates, Updates or Deletes the database with the queries provided.
     * <p>These queries do not return information, so the method does neither.
     * <p>If the query fails, a {@link SQLException} is thrown.
     *
     * @param formattedQueries The multiple queries to be executed in the database
     * @throws SQLException When some query is incorrect or a connection can't be established with the database
     */
    public void updateDatabase(String[] formattedQueries) throws SQLException{
        databaseConnection = DriverManager.getConnection(databaseURL, databaseInfo.getUser(), databaseInfo.getPassword());
        Statement statement = databaseConnection.createStatement();

        for(String currentQuery: formattedQueries)
            statement.executeUpdate(currentQuery);


        databaseConnection.close();
    }

}
