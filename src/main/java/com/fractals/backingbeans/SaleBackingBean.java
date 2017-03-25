/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fractals.backingbeans;


import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author MOUFFOK Sarah
 */
@Named("sale")
@RequestScoped
public class SaleBackingBean {

    private String selection = "album";

    public String getSelection() {
        return selection;
    }

    public void setSelection(String s) {
        selection = s;
    }
    
    public boolean isAlbum(){
        return selection.equals("album");
    }
    
    public boolean isTrack(){
        return selection.equals("track");
    }
}
