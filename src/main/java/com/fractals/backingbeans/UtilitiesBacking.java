
package com.fractals.backingbeans;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author Danieil
 */
@Named("utilBacking")
@RequestScoped
public class UtilitiesBacking implements Serializable{
    private int randomId;
    
    /**
     * Generates a random id.
     * 
     * @return random id
     */
    public String generateRandomId(){
        randomId = (int) (Math.random() * 999999);
        return "w3mnse"+randomId;
    }
    
    /**
     * Gets the random id.
     * 
     * @return 
     */
    public String getRandomId() {
        return "w3mnse"+randomId;
    }
       
}
