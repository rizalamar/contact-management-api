package rizalamar.contact_management_api.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {
    //    hasihng plain password to hash
    public static String hashPassword(String plainPassword){
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

//    validate user password
    public static boolean checkPassword(String plainPassword, String hashPassword){
        return BCrypt.checkpw(plainPassword, hashPassword);
    }
}
