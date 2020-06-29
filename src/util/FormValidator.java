package util;

import util.exceptions.InvalidEmailException;
import util.exceptions.InvalidNameException;
import util.exceptions.InvalidPasswordException;

public class FormValidator {

    public static void validateName(String name) throws InvalidNameException {
        if(!checkName(name) || name.length() > 45) {
            throw new InvalidNameException("Invalid input for name: " + name + ". It should contains just letters and maximum 45 letters.");
        }
    }

    public static void validateEmail(String email) throws InvalidEmailException {
        for(int i = 0; i<email.length(); i++){
            if((email.charAt(i) >= 'a' && email.charAt(i) <= 'z') || (email.charAt(i) >= '0' && email.charAt(i) <= '9') ||
                    email.charAt(i) == '_' || email.charAt(i) == '.' || email.charAt(i) == '@') {
                //do nothing
            } else {
                throw new InvalidEmailException("Email should not contain special characters");
            }
        }

        if(!email.contains("@")) {
            throw new InvalidEmailException("Email should contain '@' character");
        }

        if(!email.contains(".")) {
            throw new InvalidEmailException("Email should contain at least one '.' character");
        }

    }

    public static void validatePassword(String password) throws InvalidPasswordException {
        if (password.length() < 6) {
            throw new InvalidPasswordException("Password should have at least 6 characters");
        }

        int countChar = 0;
        int countDigits = 0;
        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);
            if (ch >= '0' && ch <= '9') {
                countDigits++;
            } else if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')) {
                countChar++;
            }
        }

        if(!(countChar >= 1 && countDigits >= 2)) {
            throw new InvalidPasswordException("Password should contain at least two digits");
        }
    }

    public static void validatePasswords(String password, String confirmPassword) throws InvalidPasswordException {
        if(!password.equals(confirmPassword)) {
           throw new InvalidPasswordException("Invalid passwords. Characters are not the same");

        }
    }

    private static boolean checkName(String name){
        for(int i = 0; i< name.length(); i++){
            if((name.charAt(i) >= 'a' && name.charAt(i) <= 'z') || (name.charAt(i) >= 'A' && name.charAt(i) <= 'Z')) {
                // do nothing
            } else {
                return false;
            }
            // echivalent cu...
//            if(!((name.charAt(i) >= 'a' && name.charAt(i) <= 'z') || (name.charAt(i) >= 'A' && name.charAt(i) <= 'Z'))) {
//                return false;
//            }
        }
        return true;
    }

}
