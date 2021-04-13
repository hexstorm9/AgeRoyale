package persistence;

import business.DatabaseInfo;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DatabaseInfoDAO {

    private static final String FILE_PATH = "./resources/config.json";

    public DatabaseInfo read() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(FILE_PATH));
        DatabaseInfo databaseInfo = new Gson().fromJson(br, DatabaseInfo.class);
        return databaseInfo;
    }
}
