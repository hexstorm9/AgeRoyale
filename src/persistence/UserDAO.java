package persistence;

import business.LoginException;
import business.RegistrationException;

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAO {

    /**
     * Creates a new user in the database.
     * <p>Before creating it, the method will check whether the username and mail provided are unique in the database. If not,
     * a {@link RegistrationException} with its own {@link business.RegistrationException.RegistrationExceptionCause} will be thrown.
     *
     * @param username The name of the new user
     * @param mail The mail of the new user
     * @param hash The hash of the password provided by the new user
     * @throws RegistrationException When either the username or the mail is already in use. See {@link business.RegistrationException.RegistrationExceptionCause}
     * @throws SQLException When a connection can't be established with the database or the Queries provided are wrong
     */
    public void createNewUser(String username, String mail, String hash) throws RegistrationException, SQLException {
        final String checkEqualUserQuery = "SELECT name FROM user WHERE name = '" + username + "';";
        final String checkEqualMailQuery = "SELECT mail FROM user WHERE mail = '" + mail + "';";
        ArrayList<Object> resultsList;

        //Let's query the DB to see whether somebody already owns this name
        resultsList = JDBCConnector.getInstance().queryDatabase(checkEqualUserQuery);
        if(resultsList.size() != 0) throw new RegistrationException(RegistrationException.RegistrationExceptionCause.NAME_ALREADY_IN_USE);

        //Let's query the DB to see whether somebody already owns this mail
        resultsList = JDBCConnector.getInstance().queryDatabase(checkEqualMailQuery);
        if(resultsList.size() != 0) throw new RegistrationException(RegistrationException.RegistrationExceptionCause.EMAIL_ALREADY_IN_USE);


        //Now that we know nobody has this email or username, let's create the user
        //We'll put information into the user, player and user_cards tables
        final String createUserQuery = "INSERT INTO user (name, mail, hash) VALUES ('" + username + "', '" + mail + "', '" + hash + "');";
        final String createPlayerQuery = "INSERT INTO player (name) VALUES ('" + username + "');";
        final String createUserCardsQuery = "INSERT INTO user_cards (name) VALUES ('" + username + "');";

        String[] queriesToExecute = new String[]{ createUserQuery, createPlayerQuery, createUserCardsQuery };

        JDBCConnector.getInstance().updateDatabase(queriesToExecute);
    }


    /**
     * Checks whether the User name/mail and hash provided matches the ones in the database.
     * <p>If it does, nothing occurs. If it doesn't, a {@link LoginException} is thrown with a {@link business.LoginException.LoginExceptionCause}.
     *
     * @param nameOrMail Name or mail of the user
     * @param hash Hash of the user
     * @param isEmail Whether the first field 'name' is an email or not (if it's not, it will be the name of the user)
     * @throws LoginException When the user does not match any user in the database, or the hash is not correct for that user
     */
    public void checkUserLogin(String nameOrMail, String hash, boolean isEmail) throws LoginException, SQLException {
        String getHashQuery = null;
        if(isEmail) getHashQuery = "SELECT hash FROM user WHERE mail = '" + nameOrMail + "';";
        else getHashQuery = "SELECT hash FROM user WHERE name = '" + nameOrMail + "';";

        ArrayList<Object> informationList = JDBCConnector.getInstance().queryDatabase(getHashQuery);

        if(informationList.isEmpty() && isEmail) throw new LoginException(LoginException.LoginExceptionCause.EMAIL_DOES_NOT_EXIST); //If nothing has been returned and an email was introduced
        else if(informationList.isEmpty()) throw new LoginException(LoginException.LoginExceptionCause.NAME_DOES_NOT_EXIST); //If nothing has been returned and an username was introduced

        String userCorrectHash = (String) informationList.get(0); //We only queried for a single field, the hash of that user/mail
        if(!userCorrectHash.equals(hash)) throw new LoginException(LoginException.LoginExceptionCause.INCORRECT_PASSWORD); //If both hashes aren't equals, the password has been introduced incorrectly
    }


    /**
     * Deletes the data from the user, provided that name and hash are correct. If the user can't be deleted, the method will return {@code false}
     *
     * @param name Name of the user to delete
     * @param hash Hash of the user to delete
     * @throws SQLException When a connection can't be established with the database or the query is wrong
     *
     * @return Whether the user has been deleted or not. If user is correct, and no {@link SQLException} is thrown, the cause will always be that the hash is wrong.
     */
    public boolean deleteUser(String name, String hash) throws SQLException{
        final String getHashQuery = "SELECT hash from user where name = '" + name + "';";

        ArrayList<Object> informationList = JDBCConnector.getInstance().queryDatabase(getHashQuery);
        if(informationList.isEmpty()) return false; //If there is no user with the name provided, return false

        String hashRetrieved = (String) informationList.get(0);
        if(!hashRetrieved.equals(hash)) return false; //If both hashes are not equals


        final String deleteUserBattleHistoryQuery = "DELETE FROM battle_history WHERE player_name = '" + name + "';";
        final String deleteUserCardsQuery = "DELETE FROM user_cards WHERE name = '" + name + "';";
        final String deletePlayerQuery = "DELETE FROM player WHERE name = '" + name + "';";
        final String deleteUserQuery = "DELETE FROM user WHERE name = '" + name + "';";

        //Queries must follow this order. We can't delete the user first as user_cards and player use user.name as Primary Key
        String[] queries = new String[]{deleteUserBattleHistoryQuery, deleteUserCardsQuery, deletePlayerQuery, deleteUserQuery};

        JDBCConnector.getInstance().updateDatabase(queries);
        return true; //If everything is OK, return true (the user has been deleted successfully)
    }


    /**
     * Returns whether an email exists in the database or not.
     *
     * @param email Email to check if it exists.
     * @return Whether the email provided exists in the database or not.
     * @throws SQLException When a connection can't be established with the database or the query is wrong
     */
    public boolean getIfEmailExists(String email) throws SQLException{
        final String emailQuery = "SELECT mail FROM user WHERE mail = '" + email + "';";
        ArrayList<Object> informationList = JDBCConnector.getInstance().queryDatabase(emailQuery);

        //If the informationList is empty, it means that the mail does not exist. Otherwise, it does.
        return informationList.isEmpty() ? false: true;
    }


    /**
     * Modifies the hash of the user that has the email provided.
     *
     * @param email The email of the user that wants to change its hash
     * @param newHash The new hash of the user
     * @throws SQLException When a connection can't be established with the database or the query is wrong
     */
    public void modifyHash(String email, String newHash) throws SQLException{
        if(!getIfEmailExists(email)) throw new SQLException("Email provided does not exist! Can't change the hash.");

        final String modifyHashQuery = "UPDATE user SET hash = '" + newHash + "' WHERE mail = '" + email + "';";
        JDBCConnector.getInstance().updateDatabase(modifyHashQuery);
    }

}
