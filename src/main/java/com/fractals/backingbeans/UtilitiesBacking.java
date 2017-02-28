
package com.fractals.backingbeans;

import com.fractals.beans.Album;
import com.fractals.beans.Order;
import com.fractals.beans.OrderItem;
import com.fractals.beans.Track;
import com.fractals.beans.displaybeans.CustomOrderItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
