package business;


/**
 * {@code RegistrationException} is an {@link Exception} for when the Registration information provided is not
 * correct.
 * <p>When thrown, a {@link RegistrationExceptionCause} must be given. This way, the object that catches it will
 * be able to know what was the cause of the exception
 *
 * @see Exception
 * @version 1.0
 */
public class RegistrationException extends RuntimeException{

    /**
     * Defines the possible causes of a RegistrationException
     */
    public enum RegistrationExceptionCause{
        /**
         * The cause of the RegistrationException is that the name is already in use
         */
        NAME_ALREADY_IN_USE,
        /**
         * The cause of the RegistrationException is that the email is already in use
         */
        EMAIL_ALREADY_IN_USE,
        /**
         * The cause of the RegistrationException is that the password provided is not secure enough
         */
        PASSWORD_NOT_SECURE,
        /**
         * The cause of the RegistrationException is that the name provided does not fulfill the format required
         */
        NAME_NOT_WELL_FORMATTED,
        /**
         * The cause of the RegistrationException is that the email introduced hasn't a valid format
         */
        EMAIL_NOT_VALID
    }
    private RegistrationExceptionCause exceptionCause;


    /**
     * Default RegistrationException constructor
     * @param cause The cause of the RegistrationException
     */
    public RegistrationException(RegistrationExceptionCause cause){
        this.exceptionCause = cause;
    }


    /**
     * Returns a {@link RegistrationExceptionCause} informing about why this exception was thrown
     * @return A {@link RegistrationExceptionCause} informing about why this exception was thrown
     */
    public RegistrationExceptionCause getExceptionCause(){
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
