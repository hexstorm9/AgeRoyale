package business;

public class LoginException extends RuntimeException{

    public enum LoginExceptionCause{
        NAME_DOES_NOT_EXIST,
        EMAIL_DOES_NOT_EXIST,
        INCORRECT_PASSWORD
    }
    private LoginExceptionCause exceptionCause;


    public LoginException(LoginExceptionCause cause){
        this.exceptionCause = cause;
    }


    public LoginExceptionCause getExceptionCause(){
        return exceptionCause;
    }


    @Override
    public void printStackTrace() {
        super.printStackTrace();
        System.err.println("Registration Exception Cause --> " + exceptionCause.toString());
    }
}
