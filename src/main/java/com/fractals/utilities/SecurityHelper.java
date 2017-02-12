package com.fractals.utilities;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 *
 * @author lynn
 */
public class SecurityHelper implements Serializable {
    private SecureRandom random = new SecureRandom();
    
    //Creates a randomly generated String
    public String getSalt(){       
        return new BigInteger(140, random).toString(32);
    }
    
    //Takes a password and a salt a performs a one way hashing on them, returning an array of bytes.
    public byte[] hash(String password, String salt){
        try{
            SecretKeyFactory skf = SecretKeyFactory.getInstance( "PBKDF2WithHmacSHA512" );

            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), 
                    salt.getBytes(), 1024, 254 );
            SecretKey key = skf.generateSecret( spec );
            byte[] hash = key.getEncoded();
            return hash;
        }
	catch( NoSuchAlgorithmException | InvalidKeySpecException e ) {
            return null;
        }
    }
    
    public boolean isHashValid(byte[] valid, byte[] given) {
        for(int i = 0; i < valid.length; i++)
            if(valid[i] != given[i])
                return false;
        return true;
    }
}
