package business;

import business.entities.Player;
import persistence.JDBCConnector;

import java.io.IOException;
import java.sql.SQLException;

public class GameModel {

    private Player player;

    public GameModel() {

    }

    /**
     * Loads and tests the connection to the database. If something fails, an exception is thrown.
     * If no exception is thrown, it means that everything is OK.
     *
     * @throws IOException If DatabaseInformation from file can't be read
     * @throws ClassNotFoundException If the JDBC Driver can't be loaded
     * @throws SQLException If the DatabaseConnection is not valid or a connection can't be established
     */
    public void loadDatabaseAndTestConnection() throws IOException, ClassNotFoundException, SQLException {
        JDBCConnector.getInstance().initializeAndTestConnection();
    }

    public void loadUserInfo() throws IOException{
    }

    public void checkLogin(String username, char[] password) {
        System.out.println(username + " " + password);
    }
}
