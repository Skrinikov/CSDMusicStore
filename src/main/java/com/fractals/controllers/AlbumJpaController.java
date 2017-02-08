/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fractals.controllers;

import com.fractals.beans.Album;
import com.fractals.beans.Artist;
import com.fractals.beans.OrderItem;
import com.fractals.beans.Track;
import com.fractals.beans.exceptions.IllegalOrphanException;
import com.fractals.beans.exceptions.NonexistentEntityException;
import com.fractals.beans.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

/**
 *
 * @author 1710030
 */

@Named("albumController")
@SessionScoped
public class AlbumJpaController implements Serializable {

    @Resource
    private UserTransaction utx;

    @PersistenceContext
    private EntityManager em;

    public void create(Album album) throws RollbackFailureException, Exception {
        if (album.getTracksCollection() == null) {
            album.setTracksCollection(new ArrayList<Track>());
        }
        if (album.getOrderItemsCollection() == null) {
            album.setOrderItemsCollection(new ArrayList<OrderItem>());
        }
        try {
            utx.begin();
            Artist artist = album.getArtist();
            if (artist != null) {
                artist = em.getReference(artist.getClass(), artist.getId());
                album.setArtist(artist);
            }
            Collection<Track> attachedTracksCollection = new ArrayList<Track>();
            for (Track tracksCollectionTrackToAttach : album.getTracksCollection()) {
                tracksCollectionTrackToAttach = em.getReference(tracksCollectionTrackToAttach.getClass(), tracksCollectionTrackToAttach.getId());
                attachedTracksCollection.add(tracksCollectionTrackToAttach);
            }
            album.setTracksCollection(attachedTracksCollection);
            Collection<OrderItem> attachedOrderItemsCollection = new ArrayList<OrderItem>();
            for (OrderItem orderItemsCollectionOrderItemToAttach : album.getOrderItemsCollection()) {
                orderItemsCollectionOrderItemToAttach = em.getReference(orderItemsCollectionOrderItemToAttach.getClass(), orderItemsCollectionOrderItemToAttach.getId());
                attachedOrderItemsCollection.add(orderItemsCollectionOrderItemToAttach);
            }
            album.setOrderItemsCollection(attachedOrderItemsCollection);
            em.persist(album);
            if (artist != null) {
                artist.getAlbums().add(album);
                artist = em.merge(artist);
            }
            for (Track tracksCollectionTrack : album.getTracksCollection()) {
                Album oldAlbumIdOfTracksCollectionTrack = tracksCollectionTrack.getAlbumId();
                tracksCollectionTrack.setAlbumId(album);
                tracksCollectionTrack = em.merge(tracksCollectionTrack);
                if (oldAlbumIdOfTracksCollectionTrack != null) {
                    oldAlbumIdOfTracksCollectionTrack.getTracksCollection().remove(tracksCollectionTrack);
                    oldAlbumIdOfTracksCollectionTrack = em.merge(oldAlbumIdOfTracksCollectionTrack);
                }
            }
            for (OrderItem orderItemsCollectionOrderItem : album.getOrderItemsCollection()) {
                Album oldAlbumIdOfOrderItemsCollectionOrderItem = orderItemsCollectionOrderItem.getAlbumId();
                orderItemsCollectionOrderItem.setAlbumId(album);
                orderItemsCollectionOrderItem = em.merge(orderItemsCollectionOrderItem);
                if (oldAlbumIdOfOrderItemsCollectionOrderItem != null) {
                    oldAlbumIdOfOrderItemsCollectionOrderItem.getOrderItemsCollection().remove(orderItemsCollectionOrderItem);
                    oldAlbumIdOfOrderItemsCollectionOrderItem = em.merge(oldAlbumIdOfOrderItemsCollectionOrderItem);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        }
    }

    public void edit(Album album) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
            Album persistentAlbum = em.find(Album.class, album.getId());
            Artist artistOld = persistentAlbum.getArtist();
            Artist artistNew = album.getArtist();
            Collection<Track> tracksCollectionOld = persistentAlbum.getTracksCollection();
            Collection<Track> tracksCollectionNew = album.getTracksCollection();
            Collection<OrderItem> orderItemsCollectionOld = persistentAlbum.getOrderItemsCollection();
            Collection<OrderItem> orderItemsCollectionNew = album.getOrderItemsCollection();
            List<String> illegalOrphanMessages = null;
            for (Track tracksCollectionOldTrack : tracksCollectionOld) {
                if (!tracksCollectionNew.contains(tracksCollectionOldTrack)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Track " + tracksCollectionOldTrack + " since its albumId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (artistNew != null) {
                artistNew = em.getReference(artistNew.getClass(), artistNew.getId());
                album.setArtist(artistNew);
            }
            Collection<Track> attachedTracksCollectionNew = new ArrayList<Track>();
            for (Track tracksCollectionNewTrackToAttach : tracksCollectionNew) {
                tracksCollectionNewTrackToAttach = em.getReference(tracksCollectionNewTrackToAttach.getClass(), tracksCollectionNewTrackToAttach.getId());
                attachedTracksCollectionNew.add(tracksCollectionNewTrackToAttach);
            }
            tracksCollectionNew = attachedTracksCollectionNew;
            album.setTracksCollection(tracksCollectionNew);
            Collection<OrderItem> attachedOrderItemsCollectionNew = new ArrayList<OrderItem>();
            for (OrderItem orderItemsCollectionNewOrderItemToAttach : orderItemsCollectionNew) {
                orderItemsCollectionNewOrderItemToAttach = em.getReference(orderItemsCollectionNewOrderItemToAttach.getClass(), orderItemsCollectionNewOrderItemToAttach.getId());
                attachedOrderItemsCollectionNew.add(orderItemsCollectionNewOrderItemToAttach);
            }
            orderItemsCollectionNew = attachedOrderItemsCollectionNew;
            album.setOrderItemsCollection(orderItemsCollectionNew);
            album = em.merge(album);
            if (artistOld != null && !artistOld.equals(artistNew)) {
                artistOld.getAlbums().remove(album);
                artistOld = em.merge(artistOld);
            }
            if (artistNew != null && !artistNew.equals(artistOld)) {
                artistNew.getAlbums().add(album);
                artistNew = em.merge(artistNew);
            }
            for (Track tracksCollectionNewTrack : tracksCollectionNew) {
                if (!tracksCollectionOld.contains(tracksCollectionNewTrack)) {
                    Album oldAlbumIdOfTracksCollectionNewTrack = tracksCollectionNewTrack.getAlbumId();
                    tracksCollectionNewTrack.setAlbumId(album);
                    tracksCollectionNewTrack = em.merge(tracksCollectionNewTrack);
                    if (oldAlbumIdOfTracksCollectionNewTrack != null && !oldAlbumIdOfTracksCollectionNewTrack.equals(album)) {
                        oldAlbumIdOfTracksCollectionNewTrack.getTracksCollection().remove(tracksCollectionNewTrack);
                        oldAlbumIdOfTracksCollectionNewTrack = em.merge(oldAlbumIdOfTracksCollectionNewTrack);
                    }
                }
            }
            for (OrderItem orderItemsCollectionOldOrderItem : orderItemsCollectionOld) {
                if (!orderItemsCollectionNew.contains(orderItemsCollectionOldOrderItem)) {
                    orderItemsCollectionOldOrderItem.setAlbumId(null);
                    orderItemsCollectionOldOrderItem = em.merge(orderItemsCollectionOldOrderItem);
                }
            }
            for (OrderItem orderItemsCollectionNewOrderItem : orderItemsCollectionNew) {
                if (!orderItemsCollectionOld.contains(orderItemsCollectionNewOrderItem)) {
                    Album oldAlbumIdOfOrderItemsCollectionNewOrderItem = orderItemsCollectionNewOrderItem.getAlbumId();
                    orderItemsCollectionNewOrderItem.setAlbumId(album);
                    orderItemsCollectionNewOrderItem = em.merge(orderItemsCollectionNewOrderItem);
                    if (oldAlbumIdOfOrderItemsCollectionNewOrderItem != null && !oldAlbumIdOfOrderItemsCollectionNewOrderItem.equals(album)) {
                        oldAlbumIdOfOrderItemsCollectionNewOrderItem.getOrderItemsCollection().remove(orderItemsCollectionNewOrderItem);
                        oldAlbumIdOfOrderItemsCollectionNewOrderItem = em.merge(oldAlbumIdOfOrderItemsCollectionNewOrderItem);
                    }
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = album.getId();
                if (findAlbum(id) == null) {
                    throw new NonexistentEntityException("The album with id " + id + " no longer exists.");
                }
            }
            throw ex;
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
            Album album;
            try {
                album = em.getReference(Album.class, id);
                album.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The album with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Track> tracksCollectionOrphanCheck = album.getTracksCollection();
            for (Track tracksCollectionOrphanCheckTrack : tracksCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Album (" + album + ") cannot be destroyed since the Track " + tracksCollectionOrphanCheckTrack + " in its tracksCollection field has a non-nullable albumId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Artist artist = album.getArtist();
            if (artist != null) {
                artist.getAlbums().remove(album);
                artist = em.merge(artist);
            }
            Collection<OrderItem> orderItemsCollection = album.getOrderItemsCollection();
            for (OrderItem orderItemsCollectionOrderItem : orderItemsCollection) {
                orderItemsCollectionOrderItem.setAlbumId(null);
                orderItemsCollectionOrderItem = em.merge(orderItemsCollectionOrderItem);
            }
            em.remove(album);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        }
    }

    public List<Album> findAlbumEntities() {
        return findAlbumEntities(true, -1, -1);
    }

    public List<Album> findAlbumEntities(int maxResults, int firstResult) {
        return findAlbumEntities(false, maxResults, firstResult);
    }

    private List<Album> findAlbumEntities(boolean all, int maxResults, int firstResult) {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Album.class));
        Query q = em.createQuery(cq);
        if (!all) {
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        return q.getResultList();
    }

    public Album findAlbum(Integer id) {
        return em.find(Album.class, id);
    }

    public int getAlbumCount() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<Album> rt = cq.from(Album.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
    
    
    
    public boolean isEmpty(){
        return getAlbumCount() == 0;
    }

    
}
