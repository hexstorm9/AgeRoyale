package business.entities;

public enum Language {
    ENGLISH("en", 0), SPANISH("es", 1), CATALAN("ca", 2);


    private String stringValue;
    private int intValue;

    Language(String stringValue, int intValue){
        this.stringValue = stringValue;
        this.intValue = intValue;
    }

    public String toString(){
        return stringValue;
    }
    public int toInt(){
        return intValue;
    }
}
