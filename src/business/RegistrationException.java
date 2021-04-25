package business;

public class RegistrationException extends RuntimeException{

    public enum RegistrationExceptionCause{
        NAME_ALREADY_IN_USE,
        EMAIL_ALREADY_IN_USE,
        PASSWORD_NOT_SECURE,
        NAME_NOT_WELL_FORMATTED,
        EMAIL_NOT_VALID
    }
    private RegistrationExceptionCause exceptionCause;


    public RegistrationException(RegistrationExceptionCause cause){
        this.exceptionCause = cause;
    }


    public RegistrationExceptionCause getExceptionCause(){
        return exceptionCause;
    }


    @Override
    public void printStackTrace() {
        super.printStackTrace();
        System.err.println("Registration Exception Cause --> " + exceptionCause.toString());
    }
}
