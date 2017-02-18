package com.fractals.utilities;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * SecurityHelper class is responsible for hashing, generating a random salt
 * and verifying if the hash is valid for login and registration.
 *
 * @author Alena Shulzhenko
 * @version 18/02/2017
 * @since 1.8
 */
public class SecurityHelper implements Serializable {
    private final SecureRandom random = new SecureRandom();
    private static final java.util.logging.Logger log = java.util.logging.Logger.getLogger("SecurityHelper.class");
    
    /**
     * Creates a randomly generated salt.
     * @return a randomly generated salt.
     */
    public String getSalt(){       
        return new BigInteger(140, random).toString(32);
    }
    
    /**
     * Takes a password and a salt and performs a one way hashing on them.
     * @param password The password as a string.
     * @param salt The salt to add to the password.
     * @return an array of bytes for the hashed password.
     */
    public byte[] hash(String password, String salt){
        try{
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");

            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), 
                    salt.getBytes(), 1024, 512);
            SecretKey key = skf.generateSecret(spec);
            byte[] hash = key.getEncoded();
            return hash;
        }
	catch(NoSuchAlgorithmException | InvalidKeySpecException e) {
            log.log(Level.WARNING, e.getMessage());
            return null;
        }
    }
    
    /**
     * Verifies if the submitted hash is valid against a correct combination.
     * @param valid The valid hash to compare against.
     * @param given The provided hash to validate.
     * @return true if given hash is valid; false otherwise.
     */
    public boolean isHashValid(byte[] valid, byte[] given) {
        if(valid.length != given.length)
            return false;
        for(int i = 0; i < valid.length; i++)
            if(valid[i] != given[i])
                return false;
        return true;
    }
}
