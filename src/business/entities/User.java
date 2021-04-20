package business.entities;

public class User {

    private static User singletonInstance;


    private User(){}

    public static User getInstance(){
        if(singletonInstance == null) singletonInstance = new User();
        return singletonInstance;
    }



}
