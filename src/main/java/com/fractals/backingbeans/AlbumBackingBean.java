/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fractals.backingbeans;

import com.fractals.beans.Album;
import com.fractals.beans.Artist;
import com.fractals.beans.Order;
import com.fractals.beans.OrderItem;
import com.fractals.controllers.AlbumJpaController;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

/**
 *
 * @author 1710030
 */
@Named("theAlbums")
@SessionScoped
public class AlbumBackingBean implements Serializable {

    private boolean editable = false;
    public boolean getEditable() {return editable;}
    public void setEditable(boolean b) {editable = b;}
    public void makeEditable(){setEditable(true);};
    public void makeUneditable(){setEditable(false);}

    private Album selectedAlbum;
    public Album getSelectedAlbum() {return selectedAlbum;}
    public void setSelectedAlbum(Album a) {selectedAlbum = a; makeUneditable();}

    @Inject
    private AlbumJpaController albumJpaController;
    public List<Album> getAlbums() {return albumJpaController.findAlbumEntities();}
    public boolean isEmpty() {return albumJpaController.isEmpty();}

    public void edit() throws Exception {
        makeUneditable();
        albumJpaController.edit(selectedAlbum);
    }

    public void delete() throws Exception {
        selectedAlbum.setRemovedAt(LocalDateTime.now());
        edit();
    }
    
    private Album createdAlbum;

    public Album getCreatedAlbum() {
        if (createdAlbum == null)
            createdAlbum = new Album();
        return createdAlbum;
    }

    public void setCreatedAlbum(Album a) {createdAlbum = a;}

    public void create() throws Exception {
        createdAlbum.setCreatedAt(LocalDateTime.now());
        albumJpaController.create(createdAlbum);
        selectedAlbum = createdAlbum;
        createdAlbum = null;
    }
    
    
        
    public Number getTotalSales(){
        CriteriaBuilder cb = albumJpaController.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Number> query = cb.createQuery(Number.class);
        Root<OrderItem> root = query.from(OrderItem.class);
        Join<OrderItem,Order> order = root.join("order");
        query.select(cb.sum(order.get("netCost")));
        query.where(cb.equal(root.get("album"), selectedAlbum));
        Number result = albumJpaController.getEntityManager().createQuery(query).getSingleResult();      
        return result == null ? 0 : result;
    }

}
