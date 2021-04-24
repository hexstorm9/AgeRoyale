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
    HAVE_AN_ACCOUNT("haveAccount");


    private String stringValue;
    Sentences(String stringValue){
        this.stringValue = stringValue;
    }

    public String toString(){
        return stringValue;
    }
}
