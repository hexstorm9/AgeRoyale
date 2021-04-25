package business.entities;

public enum Sentences {
    LOGIN("login"),
    USERNAME("username"),
    PASSWORD("password"),
    PASSWORD_FORGOTTEN("passwordForgotten"),
    DONT_HAVE_ACCOUNT("dontHaveAccount"),
    ENTER("enter"),
    PLAY("play"),
    PAUSE("pause"),
    LOADING("loading"),
    EXIT("exit"),
    REGISTER("register"),
    CONFIRM_PASSWORD("confirmPassword"),
    MAIL("mail"),
    HAVE_AN_ACCOUNT("haveAccount"),
    NAME_DOES_NOT_EXIST("nameDoesNotExist"),
    EMAIL_DOES_NOT_EXIST("emailDoesNotExist"),
    INCORRECT_PASSWORD("incorrectPassword"),
    DATABASE_ERROR("databaseError"),
    UNKNOWN_ERROR("unknownError"),
    PASSWORDS_DONT_MATCH("passwordsDontMatch"),
    PASSWORD_NOT_SECURE("passwordNotSecure"),
    EMAIL_NOT_VALID("emailNotValid"),
    NAME_NOT_WELL_FORMATTED("nameNotWellFormatted"),
    NAME_ALREADY_IN_USE("nameAlreadyInUse"),
    EMAIL_ALREADY_IN_USE("emailAlreadyInUse"),
    SEND_EMAIL("sendEmail"),
    RECOVER_PASSWORD("recoverPassword"),
    VERIFICATION_CODE("verificationCode"),
    RETURN_LOGIN("returnLogin"),
    CHECK_CODE("checkCode"),
    ENTER_NEW_PASSWORD("enterNewPassword"),
    GENERATE_NEW_PASSWORD("generateNewPassword"),
    ENTER_PASSWORD("enterNewPassword");


    private String stringValue;
    Sentences(String stringValue){
        this.stringValue = stringValue;
    }

    public String toString(){
        return stringValue;
    }
}
