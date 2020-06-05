package util;

public class PasswordHashing {
    // Define the BCrypt workload to use when generating password hashes. 10-31 is a valid value.
    private static int workload = 12;

    public static String hashPassword(String password_plaintext) {
        String salt = BCrypt.gensalt(workload);
        String hashed_password = BCrypt.hashpw(password_plaintext, salt);

        return hashed_password;
    }

    public static boolean checkPassword(String password_plaintext, String stored_hash) {
        if(stored_hash == null || !stored_hash.startsWith("$2a$"))
            throw new java.lang.IllegalArgumentException("Invalid hash provided for comparison");

       return BCrypt.checkpw(password_plaintext, stored_hash);
    }

}
