package business;

import persistence.DatabaseInfoDAO;

import java.io.IOException;

public class DatabaseInfo {

    private String ip;
    private int port;
    private String user;
    private String password;
    private String databaseName;

    private static DatabaseInfo databaseInfoInstance;

    private DatabaseInfo(){}

    public static DatabaseInfo getInstance() throws IOException {
        if(databaseInfoInstance == null) databaseInfoInstance = new DatabaseInfoDAO().read();
        return databaseInfoInstance;
    }

    public String getIp(){
        return ip;
    }

    public int getPort(){
        return port;
    }

    public String getUser(){
        return user;
    }

    public String getPassword(){
        return password;
    }

    public String getDatabaseName(){
        return databaseName;
    }

}