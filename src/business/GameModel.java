package business;

import business.entities.Player;

import java.io.IOException;

public class GameModel {

    private Player player;

    public GameModel() {

    }


    public void loadDatabase() throws IOException {
    }

    public void loadUserInfo() throws IOException{
    }

    public void checkLogin(String username, char[] password) {
        System.out.println(username + " " + password);
    }
}
