package business;


/**
 * {@code LoginException} is an {@link Exception} for when the Login information provided is not
 * correct.
 * <p>When thrown, a {@link LoginExceptionCause} must be given. This way, the object that catches it will
 * be able to know what was the cause of the exception
 *
 * @see Exception
 * @version 1.0
 */
public class LoginException extends Exception{

    /**
     * Defines the possible causes of a LoginException
     */
    public enum LoginExceptionCause{
        /**
         * The cause of the LoginException is that the name does not exist in the database
         */
        NAME_DOES_NOT_EXIST,
        /**
         * The cause of the LoginException is that the email doesn't exist in the databse
         */
        EMAIL_DOES_NOT_EXIST,
        /**
         * The cause of the LoginException is that the password provided is not correct
         */
        INCORRECT_PASSWORD
    }
    private LoginExceptionCause exceptionCause;


    /**
     * Default LoginException Constructor.
     * @param cause The cause of the LoginException
     */
    public LoginException(LoginExceptionCause cause){
        this.exceptionCause = cause;
    }


    /**
     * Returns a {@link LoginExceptionCause} informing about why this exception was thrown.
     * @return A {@link LoginExceptionCause} informing about why this exception was thrown.
     */
    public LoginExceptionCause getExceptionCause(){
        return exceptionCause;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void printStackTrace() {
        super.printStackTrace();
        System.err.println("Registration Exception Cause --> " + exceptionCause.toString());
    }
}
