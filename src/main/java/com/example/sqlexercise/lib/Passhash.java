package com.example.sqlexercise.lib;

import org.mindrot.jbcrypt.BCrypt;

public class Passhash {

    public static boolean comparePassword(String password, String passhash){
        return BCrypt.checkpw(password, passhash);
    }

    public static String getPasshash(String password){
        String salt = BCrypt.gensalt(10);
        return BCrypt.hashpw(password, salt);
    }

}
