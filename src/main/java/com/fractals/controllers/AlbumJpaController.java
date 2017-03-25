package com.fractals.controllers;

import com.fractals.beans.Album;
import com.fractals.beans.Artist;
import com.fractals.beans.Genre;
import com.fractals.beans.OrderItem;
import com.fractals.beans.Track;
import com.fractals.controllers.exceptions.IllegalOrphanException;
import com.fractals.controllers.exceptions.NonexistentEntityException;
import com.fractals.controllers.exceptions.RollbackFailureException;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.transaction.UserTransaction;

/**
 *
 * @author MOUFFOK Sarah, Danieil Skrinikov
 */

@Named
@RequestScoped
public class AlbumJpaController {

    @Resource
    private UserTransaction utx;

    @PersistenceContext
    private EntityManager em;

    public EntityManager getEntityManager(){return em;}
    
    public void create(Album album) throws RollbackFailureException, Exception {
        if (album.getTracks() == null) {
            album.setTracks(new ArrayList<Track>());
        }
        if (album.getOrderItems() == null) {
            album.setOrderItems(new ArrayList<OrderItem>());
        }
        try {
            utx.begin();
            Artist artist = album.getArtist();
            if (artist != null) {
                artist = em.getReference(artist.getClass(), artist.getId());
                album.setArtist(artist);
            }
            List<Track> attachedTracksCollection = new ArrayList<>();
            for (Track tracksCollectionTrackToAttach : album.getTracks()) {
                tracksCollectionTrackToAttach = em.getReference(tracksCollectionTrackToAttach.getClass(), 
tracksCollectionTrackToAttach.getId());
                attachedTracksCollection.add(tracksCollectionTrackToAttach);
            }
            album.setTracks(attachedTracksCollection);
            List<OrderItem> attachedOrderItemsCollection = new ArrayList<OrderItem>();
            for (OrderItem orderItemsCollectionOrderItemToAttach : album.getOrderItems()) {
                orderItemsCollectionOrderItemToAttach = em.getReference(orderItemsCollectionOrderItemToAttach.getClass(), 
orderItemsCollectionOrderItemToAttach.getId());
                attachedOrderItemsCollection.add(orderItemsCollectionOrderItemToAttach);
            }
            album.setOrderItems(attachedOrderItemsCollection);
            em.persist(album);
            if (artist != null) {
                artist.getAlbums().add(album);
                artist = em.merge(artist);
            }
            for (Track tracksCollectionTrack : album.getTracks()) {
                Album oldAlbumIdOfTracksCollectionTrack = tracksCollectionTrack.getAlbum();
                tracksCollectionTrack.setAlbum(album);
                tracksCollectionTrack = em.merge(tracksCollectionTrack);
                if (oldAlbumIdOfTracksCollectionTrack != null) {
                    oldAlbumIdOfTracksCollectionTrack.getTracks().remove(tracksCollectionTrack);
                    oldAlbumIdOfTracksCollectionTrack = em.merge(oldAlbumIdOfTracksCollectionTrack);
                }
            }
            for (OrderItem orderItemsCollectionOrderItem : album.getOrderItems()) {
                Album oldAlbumIdOfOrderItemsCollectionOrderItem = orderItemsCollectionOrderItem.getAlbum();
                orderItemsCollectionOrderItem.setAlbum(album);
                orderItemsCollectionOrderItem = em.merge(orderItemsCollectionOrderItem);
                if (oldAlbumIdOfOrderItemsCollectionOrderItem != null) {
                    oldAlbumIdOfOrderItemsCollectionOrderItem.getOrderItems().remove(orderItemsCollectionOrderItem);
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
            List<Track> tracksCollectionOld = persistentAlbum.getTracks();
            List<Track> tracksCollectionNew = album.getTracks();
            List<OrderItem> orderItemsCollectionOld = persistentAlbum.getOrderItems();
            List<OrderItem> orderItemsCollectionNew = album.getOrderItems();
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
            List<Track> attachedTracksCollectionNew = new ArrayList<Track>();
            for (Track tracksCollectionNewTrackToAttach : tracksCollectionNew) {
                tracksCollectionNewTrackToAttach = em.getReference(tracksCollectionNewTrackToAttach.getClass(), tracksCollectionNewTrackToAttach.getId());
                attachedTracksCollectionNew.add(tracksCollectionNewTrackToAttach);
            }
            tracksCollectionNew = attachedTracksCollectionNew;
            album.setTracks(tracksCollectionNew);
            List<OrderItem> attachedOrderItemsCollectionNew = new ArrayList<OrderItem>();
            for (OrderItem orderItemsCollectionNewOrderItemToAttach : orderItemsCollectionNew) {
                orderItemsCollectionNewOrderItemToAttach = em.getReference(orderItemsCollectionNewOrderItemToAttach.getClass(), orderItemsCollectionNewOrderItemToAttach.getId());
                attachedOrderItemsCollectionNew.add(orderItemsCollectionNewOrderItemToAttach);
            }
            orderItemsCollectionNew = attachedOrderItemsCollectionNew;
            album.setOrderItems(orderItemsCollectionNew);
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
                    Album oldAlbumIdOfTracksCollectionNewTrack = tracksCollectionNewTrack.getAlbum();
                    tracksCollectionNewTrack.setAlbum(album);
                    tracksCollectionNewTrack = em.merge(tracksCollectionNewTrack);
                    if (oldAlbumIdOfTracksCollectionNewTrack != null && !oldAlbumIdOfTracksCollectionNewTrack.equals(album)) {
                        oldAlbumIdOfTracksCollectionNewTrack.getTracks().remove(tracksCollectionNewTrack);
                        oldAlbumIdOfTracksCollectionNewTrack = em.merge(oldAlbumIdOfTracksCollectionNewTrack);
                    }
                }
            }
            for (OrderItem orderItemsCollectionOldOrderItem : orderItemsCollectionOld) {
                if (!orderItemsCollectionNew.contains(orderItemsCollectionOldOrderItem)) {
                    orderItemsCollectionOldOrderItem.setAlbum(null);
                    orderItemsCollectionOldOrderItem = em.merge(orderItemsCollectionOldOrderItem);
                }
            }
            for (OrderItem orderItemsCollectionNewOrderItem : orderItemsCollectionNew) {
                if (!orderItemsCollectionOld.contains(orderItemsCollectionNewOrderItem)) {
                    Album oldAlbumIdOfOrderItemsCollectionNewOrderItem = orderItemsCollectionNewOrderItem.getAlbum();
                    orderItemsCollectionNewOrderItem.setAlbum(album);
                    orderItemsCollectionNewOrderItem = em.merge(orderItemsCollectionNewOrderItem);
                    if (oldAlbumIdOfOrderItemsCollectionNewOrderItem != null && !oldAlbumIdOfOrderItemsCollectionNewOrderItem.equals(album)) {
                        oldAlbumIdOfOrderItemsCollectionNewOrderItem.getOrderItems().remove(orderItemsCollectionNewOrderItem);
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
            List<Track> tracksCollectionOrphanCheck = album.getTracks();
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
            List<OrderItem> orderItemsCollection = album.getOrderItems();
            for (OrderItem orderItemsCollectionOrderItem : orderItemsCollection) {
                orderItemsCollectionOrderItem.setAlbum(null);
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
    
    public List<Album> findAlbumsByGenre(Genre genre, int count, boolean random){
        if (genre == null || count <= 0)
            throw new IllegalArgumentException("Cannot retrive albums");
        
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Album> query = cb.createQuery(Album.class);
        Root<Album> root = query.from(Album.class);
        Join genreJoin = root.join("tracks").join("genre");
        query.where(cb.equal(genreJoin.get("id"), genre.getId()))
                .distinct(true);
        
        
        
        List<Album> albums = new ArrayList<>();
        
        if (random == true){
            albums = em.createQuery(query).getResultList();
            //Will shuffle the list and trim the list with the count 
            albums = getRandomAlbums(albums, count);
        }
        else{
            //Since random is not needed, can just set the max results
            albums = em.createQuery(query).setMaxResults(count).getResultList();
        }
        
        return albums;
        
    }
    
    
    public boolean isEmpty(){
        return getAlbumCount() == 0;
    }

    /**
     * Gets n amount of albums which have the same genre as the original album, where n is the amount of albums to retrieve.
     * 
     * @author Danieil Skrinikov
     * @param origin Original Album, will be used to get its genre.
     * @param count amount of albums to return.
     * @return list of albums with the same genre as the original.
     */
    public List<Album> getSimilarAlbums(Album origin, int count){
        if(origin == null || count <= 0)
            return null;
        
        // Album with no songs. Cannot retrive tracks
        if(origin.getTracks().isEmpty())
            return null;
        
        CriteriaBuilder cb = em.getCriteriaBuilder();
        
        CriteriaQuery<Album> query = cb.createQuery(Album.class);
        Root<Album> root = query.from(Album.class);
        query.select(root);
        Join genre = root.join("tracks").join("genre");
        Join artist = root.join("artist");
        query.where(cb.and(
                cb.equal(
                        genre.get("id"),
                        origin.getTracks().get(0).getGenre().getId()))
                ,
                cb.notEqual(artist, origin.getArtist()
                )
        ).distinct(true);
 
        return getRandomAlbums( em.createQuery(query).getResultList(), 3);
    }
    
    /**
     * Module that returns the total sales of an album
     * @param album Album
     * @return total sales of an album
     */
    public Number getTotalSales(Album album){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Number> query = cb.createQuery(Number.class);
        
        //Select sum (cost) from from OrderItem where album_id = ?
        
        Root<OrderItem> root = query.from(OrderItem.class);
        query.select(cb.sum(root.get("cost")));
        query.where(cb.equal(root.get("album"), album));
        
        return em.createQuery(query).getSingleResult();
    }
    
    /**
     * Takes the list of Album, shuffles it 
     * and returns the first Tracks based on the limit
     * @param albums
     * @param limit
     * @return Randomized List of n limit of Tracks
     */
    private List<Album> getRandomAlbums(List<Album> albums, int limit){
        Collections.shuffle(albums);
        
        if (limit > albums.size())
            limit = albums.size();
        
        List<Album> newAlbums = new ArrayList<>();
        
        for (int i = 0; i < limit; i++){
            newAlbums.add(albums.get(i));
        }
        
        return newAlbums;
    }
}
