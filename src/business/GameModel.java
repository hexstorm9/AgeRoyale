package business;

import business.entities.Player;
import persistence.JDBCConnector;
import persistence.UserDAO;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Main {@code Model} class of the application.
 *
 * <p>Lets you perform actions such as login in or registering a new user, and holds all the information of the current player.
 *
 * @version 1.0
 */
public class GameModel {

    private Player player;

    private UserDAO userDAO;


    public GameModel() {
        player = new Player();
        userDAO = new UserDAO();
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


    /**
     * Checks whether the Username/Mail and Password provided are correct in the database.
     * <p>If they're correct, the class attribute of type {@link Player} is initialized and loaded.
     * <p>If they're not correct or a connection to the database can't be established, an exception is thrown.
     *
     * @param usernameOrMail Username or Mail of the player
     * @param password Password of the player
     * @throws LoginException When the login can't be done correctly (see {@link LoginException#getExceptionCause()})
     * @throws SQLException When the connection to the database can't be established
     *
     * @see LoginException
     */
    public void checkLoginAndLoadUser(String usernameOrMail, char[] password) throws LoginException, SQLException{
        final String hash = SecurityUtility.getHashAndDeletePassword(password);
        final boolean isMailProvided = SecurityUtility.checkIfEmailIsCorrect(usernameOrMail);

        userDAO.checkUserLogin(usernameOrMail, hash, isMailProvided);
        player.initialize(usernameOrMail);
    }


    /**
     * Creates a new user registering it into the database.
     *
     * <p>Before creating it, this method checks whether the username, mail and password provided are correct/fulfill the
     * requirements. If not, an {@link RegistrationException} with its {@link business.RegistrationException.RegistrationExceptionCause}
     * will be thrown.
     *
     * <p>Another {@link RegistrationException} can be also thrown if username or mail are already present in the database.
     *
     * @param username The username to register
     * @param mail The mail of the user that wants to register
     * @param password The password of the user that wants to register
     * @throws RegistrationException When username, mail or password do not fulfill the requirements, or when the user/mail already exist in the database.
     * @throws SQLException If a connection to the database can't be established or queries are wrong
     */
    public void createNewUser(String username, String mail, char[] password) throws RegistrationException, SQLException{
        //If username does not fulfill the format expected, throw a RegistrationException
        if(SecurityUtility.checkIfUsernameIsCorrect(username) == false) throw new RegistrationException(RegistrationException.RegistrationExceptionCause.NAME_NOT_WELL_FORMATTED);

        //If email is not valid, throw a RegistrationException
        if(SecurityUtility.checkIfEmailIsCorrect(mail) == false) throw new RegistrationException(RegistrationException.RegistrationExceptionCause.EMAIL_NOT_VALID);

        //If password is not secure enough, throw a RegistrationException
        if(SecurityUtility.checkIfPasswordIsSecure(password) == false) throw new RegistrationException(RegistrationException.RegistrationExceptionCause.PASSWORD_NOT_SECURE);


        //If all fields are correct, let's query the database (the createNewUser() method can also throw a RegistrationException)
        String hash = SecurityUtility.getHashAndDeletePassword(password);
        userDAO.createNewUser(username, mail, hash);

        //Now that the user has been created, let's load all its values from the database into our player
        player.initialize(username);
    }


    /**
     * Checks whether an email exists in the database or not.
     * <p>If it does, returns true. Otherwise, returns false.
     *
     * @param email The email to check if it exists
     * @return Whether the email provided exists or not
     * @throws SQLException If a connection to the database can't be established or queries are wrong
     */
    public boolean checkIfMailExists(String email) throws SQLException{
        if(SecurityUtility.checkIfEmailIsCorrect(email) == false) return false; //If email is not correct, return false
        return userDAO.getIfEmailExists(email);
    }


    public void changeUserPassword(String email, char[] password) throws RegistrationException, SQLException{
        if(SecurityUtility.checkIfEmailIsCorrect(email) == false) throw new RegistrationException(RegistrationException.RegistrationExceptionCause.EMAIL_NOT_VALID);
        if(SecurityUtility.checkIfPasswordIsSecure(password) == false) throw new RegistrationException(RegistrationException.RegistrationExceptionCause.PASSWORD_NOT_SECURE);

        String hash = SecurityUtility.getHashAndDeletePassword(password);

        userDAO.modifyHash(email, hash);
    }


    /**
     * Deletes the user of the whole system (in the Database and from RAM too).
     * <p>When this method deletes successfully the user, a log out needs to be performed too (as the {@link Player} of this class is deleted too).
     *
     * @param password The password of the current user that wants its account to be deleted
     *
     * @return Boolean telling whether the user has been deleted or not. If it has not been deleted, the cause will always
     * be that the password introduced is not correct
     * @throws SQLException If a connection to the database can't be established or queries are wrong
     */
    public boolean deleteUser(char[] password) throws SQLException{
        //If the password provided does not match the security criteria, it means it will be incorrect for sure
        if(SecurityUtility.checkIfPasswordIsSecure(password) == false) return false;

        String hash = SecurityUtility.getHashAndDeletePassword(password);
        boolean accountDeleted = userDAO.deleteUser(player.getName(), hash);
        if(accountDeleted) forgetPlayer();

        return accountDeleted;
    }

   public Player getPlayer(){
        return player;
   }

   public void forgetPlayer(){
        //Delete the reference to the current player (let the GC do its job) and create a new empty one
        player = null;
        player = new Player();
   }

}
