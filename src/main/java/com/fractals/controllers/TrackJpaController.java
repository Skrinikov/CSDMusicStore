package com.fractals.controllers;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.fractals.beans.Album;
import com.fractals.beans.Genre;
import com.fractals.beans.Artist;
import com.fractals.beans.OrderItem;
import com.fractals.beans.OrderItem_;
import com.fractals.beans.Track;
import com.fractals.beans.Track_;
import com.fractals.controllers.exceptions.NonexistentEntityException;
import com.fractals.controllers.exceptions.RollbackFailureException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.UserTransaction;

/**
 *
 * @author Danieil Skrinikov
 * @author Thai Vu Nguyen
 */
@Named
@RequestScoped
public class TrackJpaController implements Serializable {
   @Resource
    private UserTransaction utx;
   
   @PersistenceContext
    private EntityManager em;

    public void create(Track track) throws RollbackFailureException, Exception {
        if (track.getArtists() == null) {
            track.setArtists(new ArrayList<Artist>());
        }
   
        try {
            utx.begin();
   
            Album albumId = track.getAlbum();
            if (albumId != null) {
                albumId = em.getReference(albumId.getClass(), albumId.getId());
                track.setAlbum(albumId);
            }
            Genre genreId = track.getGenre();
            if (genreId != null) {
                genreId = em.getReference(genreId.getClass(), genreId.getId());
                track.setGenre(genreId);
            }
            List<Artist> attachedArtists = new ArrayList<Artist>();
            for (Artist artistsArtistToAttach : track.getArtists()) {
                artistsArtistToAttach = em.getReference(artistsArtistToAttach.getClass(), artistsArtistToAttach.getId());
                attachedArtists.add(artistsArtistToAttach);
            }
            track.setArtists(attachedArtists);
            em.persist(track);
            if (albumId != null) {
                albumId.getTracks().add(track);
                albumId = em.merge(albumId);
            }
            if (genreId != null) {
                genreId.getTracks().add(track);
                genreId = em.merge(genreId);
            }
            for (Artist artistsArtist : track.getArtists()) {
                artistsArtist.getTracks().add(track);
                artistsArtist = em.merge(artistsArtist);
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

    public void edit(Track track) throws NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
            Track persistentTrack = em.find(Track.class, track.getId());
            Album albumIdOld = persistentTrack.getAlbum();
            Album albumIdNew = track.getAlbum();
            Genre genreIdOld = persistentTrack.getGenre();
            Genre genreIdNew = track.getGenre();
            List<Artist> artistsOld = persistentTrack.getArtists();
            List<Artist> artistsNew = track.getArtists();
            if (albumIdNew != null) {
                albumIdNew = em.getReference(albumIdNew.getClass(), albumIdNew.getId());
                track.setAlbum(albumIdNew);
            }
            if (genreIdNew != null) {
                genreIdNew = em.getReference(genreIdNew.getClass(), genreIdNew.getId());
                track.setGenre(genreIdNew);
            }
            List<Artist> attachedArtistsNew = new ArrayList<Artist>();
            for (Artist artistsNewArtistToAttach : artistsNew) {
                artistsNewArtistToAttach = em.getReference(artistsNewArtistToAttach.getClass(), artistsNewArtistToAttach.getId());
                attachedArtistsNew.add(artistsNewArtistToAttach);
            }
            artistsNew = attachedArtistsNew;
            track.setArtists(artistsNew);
            track = em.merge(track);
            if (albumIdOld != null && !albumIdOld.equals(albumIdNew)) {
                albumIdOld.getTracks().remove(track);
                albumIdOld = em.merge(albumIdOld);
            }
            if (albumIdNew != null && !albumIdNew.equals(albumIdOld)) {
                albumIdNew.getTracks().add(track);
                albumIdNew = em.merge(albumIdNew);
            }
            if (genreIdOld != null && !genreIdOld.equals(genreIdNew)) {
                genreIdOld.getTracks().remove(track);
                genreIdOld = em.merge(genreIdOld);
            }
            if (genreIdNew != null && !genreIdNew.equals(genreIdOld)) {
                genreIdNew.getTracks().add(track);
                genreIdNew = em.merge(genreIdNew);
            }
            for (Artist artistsOldArtist : artistsOld) {
                if (!artistsNew.contains(artistsOldArtist)) {
                    artistsOldArtist.getTracks().remove(track);
                    artistsOldArtist = em.merge(artistsOldArtist);
                }
            }
            for (Artist artistsNewArtist : artistsNew) {
                if (!artistsOld.contains(artistsNewArtist)) {
                    artistsNewArtist.getTracks().add(track);
                    artistsNewArtist = em.merge(artistsNewArtist);
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
                Integer id = track.getId();
                if (findTrack(id) == null) {
                    throw new NonexistentEntityException("The track with id " + id + " no longer exists.");
                }
            }
            throw ex;
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
            Track track;
            try {
                track = em.getReference(Track.class, id);
                track.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The track with id " + id + " no longer exists.", enfe);
            }
            Album albumId = track.getAlbum();
            if (albumId != null) {
                albumId.getTracks().remove(track);
                albumId = em.merge(albumId);
            }
            Genre genreId = track.getGenre();
            if (genreId != null) {
                genreId.getTracks().remove(track);
                genreId = em.merge(genreId);
            }
            List<Artist> artists = track.getArtists();
            for (Artist artistsArtist : artists) {
                artistsArtist.getTracks().remove(track);
                artistsArtist = em.merge(artistsArtist);
            }
            em.remove(track);
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

    public List<Track> findTrackEntities() {
        return findTrackEntities(true, -1, -1);
    }

    public List<Track> findTrackEntities(int maxResults, int firstResult) {
        return findTrackEntities(false, maxResults, firstResult);
    }

    private List<Track> findTrackEntities(boolean all, int maxResults, int firstResult) {     
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Track.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
    }

    public Track findTrack(Integer id) {       
            return em.find(Track.class, id);  
    }

    public int getTrackCount() {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Track> rt = cq.from(Track.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
    }
    
    public boolean isEmpty(){
        return getTrackCount() == 0;
    }
    
    /**
     * Selects tracks based on genre
     * 
     * @param genre Genre
     * @param count int amount of tracks to return
     * @param random true to return shuffled list
     * @return list of tracks
     * @author Thai-Vu Nguyen
     */
    public List<Track> findTracksByGenre(Genre genre, int count, boolean random){
        if (genre == null || count <= 0)
            throw new IllegalArgumentException ("Cannot retrieve tracks");
        
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Track> query = cb.createQuery(Track.class);
        Root<Track> root = query.from(Track.class);
        query.select(root);
        query.where(cb.equal(root.get("genre"), genre));
        
        List<Track> tracks = new ArrayList<>();
        
        if (random == true){
            tracks = em.createQuery(query).getResultList();
            tracks = getRandomTracks(tracks, count);
            
        }else{
            tracks = em.createQuery(query).setMaxResults(count).getResultList();
        }
        
        return tracks;
        
    }
    
    /**
     * Selects tracks based on their addition date to the database. 
     * 
     * @author Danieil Skrinikov
     * @param count amount of tracks to return. 
     * @return list of tracks.
     */
    public List<Track> getMostRecentTracks(int count){
        if(count < 1)
            return null;
        
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Track> query = cb.createQuery(Track.class);
        Root<Track> root = query.from(Track.class);
        query.select(root);
        query.orderBy(cb.desc(root.get("createdAt")));
        return em.createQuery(query).setMaxResults(count).getResultList();
    }
    
    /**
     * Gets a list of similar Tracks to a track
     * 
     * @param track Track
     * @return List of Tracks similar to the passed track
     * @author Thai-Vu Nguyen
     */
    public List<Track> getSimilarTracks(Track track){
        //By Default, putting putting a limit of 3
        return getSimilarTracks(track, 3);
    }

    /**
     * Selects tracks based on a genre of a Track
     * 
     * @param track
     * @param count amount of tracks to return
     * @return list of similar tracks
     */
    public List<Track> getSimilarTracks (Track track, int count){
         if (count < 1){
             return new ArrayList<Track>();
         }
         
         
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Track> query = cb.createQuery(Track.class);
        Root<Track> root = query.from(Track.class);
        query.select(root);
        query.where(cb
                .and(
                    cb.equal(root.get("genre"), track.getGenre()),
                    cb.notEqual(root.get("id"), track.getId())
                )
            );
        
        List<Track> tracks = em.createQuery(query).getResultList();
                 
        return getRandomTracks(tracks, count);
    }
        
     /**
      * Randomly selects tracks which are on special and returns them as a List
      * 
      * @param count amount of tracks to return. 
      * @return List of tracks.
      */
     public List<Track> getSpecials(int count){
         if(count < 1)
             return null;
         
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Track> query = cb.createQuery(Track.class);
        Root<Track> root = query.from(Track.class);
        query.select(root);
        query.where(cb.greaterThan(root.<Double>get(Track_.salePrice), 0.0));
        return getRandomTracks(em.createQuery(query).getResultList(), count);
     }
     
     /**
      * Module to return the total sales of a track
      * @param track
      * @return Total Sales
      * @author Thai-Vu Nguyen
      */
     public Number getTotalSales(Track track){
         if (track == null)
             return 0;
         
         CriteriaBuilder cb = em.getCriteriaBuilder();
         CriteriaQuery<Number> query = cb.createQuery(Number.class);
         
         //Select sum(cost) from OrderItem where album_id = ?
         
         Root<OrderItem> root = query.from(OrderItem.class);
         query.select(cb.sum(root.get("cost")));
         query.where(cb.equal(root.get("track"), track));
         
         return em.createQuery(query).getSingleResult();
     }
    
    /**
     * Takes the list of Tracks, shuffles it 
     * and returns the first Tracks based on the limit
     * @param tracks
     * @param limit
     * @return Randomized List of n limit of Tracks
     * @author Thai-Vu Nguyen
     */
    private List<Track> getRandomTracks(List<Track> tracks, int limit){
        Collections.shuffle(tracks);
        
        if (limit > tracks.size())
            limit = tracks.size();
        
        List<Track> newTracks = new ArrayList<>();
        
        for (int i = 0; i < limit; i++){
            newTracks.add(tracks.get(i));
        }
        
        return newTracks;
    }
        
}
