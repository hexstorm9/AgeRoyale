package business;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SecurityUtility, as the name says, provides utilities for managing security in user input.
 *
 * <p>The class provides methods to check if mails are correct, passwords are secure, and other similar services.
 *
 * @version 1.0
 */
public class SecurityUtility {

    private static String latestVerificationCodeGenerated;


    /**
     * Provided a password in {@code char[]} format, this method is going to return a hash
     * in {@code String} format representing the password introduced, and the {@code char[]} will be
     * deleted.
     * <p>The hash will be created using SHA-256
     *
     * <p>In some systems the hashing algorithm could fail. If this occurs, the {@code String}
     * returned will be {@code null}.
     *
     * @param password Password to convert to hash
     * @return Hash representing the password provided. If an error occurs, this will be {@code null}
     *
     * @see MessageDigest
     */
    public static String getHashAndDeletePassword(char[] password) {
        try{
            //We'll be using MessageDigest to get a SHA-256 hash
            final MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            final byte[] hash = messageDigest.digest(String.valueOf(password).getBytes("UTF-8"));
            Arrays.fill(password, '0'); //Delete the content of the char[] now that we have the hash

            //Let's transform the hash (now in byte[]) to a String
            StringBuilder hexString = new StringBuilder();
            for(int i = 0; i < hash.length; i++){
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hexString.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString(); //Return the hash in String format

        }catch(NoSuchAlgorithmException | UnsupportedEncodingException e){
            System.err.println("Error generating the Hash");
            e.printStackTrace();
            return null;
        }

    }


    /**
     * Checks whether a password is secure or not.
     *
     * <p>In order for this method to return that the password is secure, it needs to have the following requisites:
     * <ul>
     *     <li>At least 8 characters</li>
     *     <li>A mixture of both uppercase and lowercase letters</li>
     *     <li>At least one symbol</li>
     *     <li>At least one number</li>
     * </ul>
     *
     * @param password The password to be checked
     * @return Whether the password provided is secure or not
     */
    public static boolean checkIfPasswordIsSecure(char[] password){
        if(password.length < 8) return false;
        boolean hasUppercaseLetter, hasLowercaseLetter, hasSymbol, hasNumber;
        hasUppercaseLetter = false;
        hasLowercaseLetter = false;
        hasSymbol = false;
        hasNumber = false;

        for(char c: password){
            if(Character.isDigit(c)) hasNumber = true;
            if(Character.isLowerCase(c)) hasLowercaseLetter = true;
            if(Character.isUpperCase(c)) hasUppercaseLetter = true;
            if(String.valueOf(c).matches("[^a-zA-Z0-9]")) hasSymbol = true;
        }

        //If the four flags are true, return true (the password is secure). Otherwise, return false
        return hasUppercaseLetter && hasLowercaseLetter && hasSymbol && hasNumber ? true: false;
    }


    /**
     * Checks whether an email is well-formatted or not.
     *
     * @param email Email String to be checked
     * @return Whether the email is well-formatted or not.
     */
    public static boolean checkIfEmailIsCorrect(String email){
        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher matcher = pattern.matcher(email);

        //If the email matches the pattern is valid. If not, it's not valid.
        return matcher.matches() ? true: false;
    }


    /**
     * Checks whether the username is correct or not.
     * <p>For it to be correct, it must follow:
     * <ul>
     *     <li>No special symbols</li>
     *     <li>At least 4 characters</li>
     * </ul>
     * @param username The username to check
     * @return Whether the username is valid (correct) or not
     */
    public static boolean checkIfUsernameIsCorrect(String username) {
        if(username.length() < 4) return false;

        Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
        Matcher matcher = pattern.matcher(username);

        //If the username matches the pattern it means it has special symbols, so it's not valid. Else, it's valid.
        return matcher.matches() ? false: true;
    }


    /**
     * Returns a six digit random verification code and saves it into a class attribute so as to be retrieved
     * later on by {@link SecurityUtility#latestVerificationCodeGenerated)
     *
     * @return  A randomly generated six digit random verification code
     */
    public static String getSixDigitRandomVerificationCode(){
        Random r = new Random();
        int intCode = r.nextInt(999999);

        //String.format will always provide us with a 6 number string (even when the int has not a length of 6)
        return latestVerificationCodeGenerated = String.format("%06d", intCode);
    }


    /**
     * Returns the latest 6-digit verification code generated.
     * @return The latest 6-digit verification code generated.
     */
    public static String getLatestVerificationCodeGenerated(){
        return latestVerificationCodeGenerated;
    }
}
