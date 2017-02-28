
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
    
    public int generateRandomId(){
        randomId = (int) (Math.random() * 10000);
        return randomId;
    }

    public int getRandomId() {
        return randomId;
    }
       
}
