package persistence;

import business.RegistrationException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    public boolean checkUserRegister(String usernameTextField, String mailTextField) {
        String aux = "'" + usernameTextField + "'";
        String query = "SELECT * FROM user WHERE user.name=" + aux;

        try {
            ResultSet resultSet = JDBCConnector.getInstance().queryDatabase(query);
            aux = "'" + mailTextField + "'";
            System.out.println(query);

            if (!resultSet.isBeforeFirst() ) {
                System.out.println(query);
                query = "SELECT * FROM user WHERE user.mail=" + aux;
                resultSet = JDBCConnector.getInstance().queryDatabase(query);
                System.out.println(mailTextField);
                if (!resultSet.isBeforeFirst() ) {
                    return true;
                }else{
                    throw new RegistrationException(RegistrationException.RegistrationExceptionCause.EMAIL_ALREADY_IN_USE);
                }
            }else{
                throw new RegistrationException(RegistrationException.RegistrationExceptionCause.NAME_ALREADY_IN_USE);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public void createNewUser(String usernameTextField, String mailTextField, String passwordTextField) {
    }

    public String checkUserLogin(String name, String hash){
        return null;
    }

    public void deleteCurrentUser(String hash){
    }
}
