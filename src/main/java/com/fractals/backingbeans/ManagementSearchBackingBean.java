/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fractals.backingbeans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;

import javax.inject.Named;

/**
 *
 * @author 1710030
 */
@Named("managementSearch")
@RequestScoped
public class ManagementSearchBackingBean {
    ArrayList<String> objects = new ArrayList<String>(
           Arrays.asList("Albums", "Tracks"));
    
     ArrayList<String> album = new ArrayList<String>(
           Arrays.asList("id", "title"));
     
    public ArrayList<String> getObjects(){return objects;}
    
}
