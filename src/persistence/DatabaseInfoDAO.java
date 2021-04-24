package persistence;

import business.DatabaseInfo;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * DatabaseInfoDAO is the class in charge of retrieving the database information from the file
 * config.json located in the project.
 * <p>A single method {@link DatabaseInfoDAO#read()} will be provided in order to access the read information.
 *
 * @version 1.0
 */
public class DatabaseInfoDAO {

    private static final String FILE_PATH = "./resources/config.json";

    /**
     * This method will return the read information of the database in a String array. The array will use
     * the following format:
     * <ul><li>1. IP Address</li><li>2. Port</li><li>3. Database User</li><li>4. Database User Password</li>
     * <li>5. Database Name</li></ul>
     * @return String array of Database information
     * @throws IOException Whenever the information file can't be read
     */
    public String[] read() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(FILE_PATH));
        DatabaseInfoFile databaseInfoFile = new Gson().fromJson(br, DatabaseInfoFile.class);
        return new String[]{
                databaseInfoFile.ip, databaseInfoFile.port, databaseInfoFile.user, databaseInfoFile.password, databaseInfoFile.databaseName
        };
    }

    /**
     * Inner class matching the json file structure so as to read easier with Gson
     */
    private class DatabaseInfoFile{
        private String ip;
        private String port;
        private String user;
        private String password;
        private String databaseName;
    }
}

