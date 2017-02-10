/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fractals.controllers;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.fractals.beans.Album;
import com.fractals.beans.Genre;
import com.fractals.beans.Artist;
import com.fractals.beans.Track;
import com.fractals.controllers.exceptions.NonexistentEntityException;
import com.fractals.controllers.exceptions.RollbackFailureException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.Resource;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

/**
 *
 * @author 1710030
 */
@Named("trackController")
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
   
            Album albumId = track.getAlbumId();
            if (albumId != null) {
                albumId = em.getReference(albumId.getClass(), albumId.getId());
                track.setAlbumId(albumId);
            }
            Genre genreId = track.getGenreId();
            if (genreId != null) {
                genreId = em.getReference(genreId.getClass(), genreId.getId());
                track.setGenreId(genreId);
            }
            Collection<Artist> attachedArtists = new ArrayList<Artist>();
            for (Artist artistsArtistToAttach : track.getArtists()) {
                artistsArtistToAttach = em.getReference(artistsArtistToAttach.getClass(), artistsArtistToAttach.getId());
                attachedArtists.add(artistsArtistToAttach);
            }
            track.setArtists(attachedArtists);
            em.persist(track);
            if (albumId != null) {
                albumId.getTracksCollection().add(track);
                albumId = em.merge(albumId);
            }
            if (genreId != null) {
                genreId.getTracksCollection().add(track);
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
            Album albumIdOld = persistentTrack.getAlbumId();
            Album albumIdNew = track.getAlbumId();
            Genre genreIdOld = persistentTrack.getGenreId();
            Genre genreIdNew = track.getGenreId();
            Collection<Artist> artistsOld = persistentTrack.getArtists();
            Collection<Artist> artistsNew = track.getArtists();
            if (albumIdNew != null) {
                albumIdNew = em.getReference(albumIdNew.getClass(), albumIdNew.getId());
                track.setAlbumId(albumIdNew);
            }
            if (genreIdNew != null) {
                genreIdNew = em.getReference(genreIdNew.getClass(), genreIdNew.getId());
                track.setGenreId(genreIdNew);
            }
            Collection<Artist> attachedArtistsNew = new ArrayList<Artist>();
            for (Artist artistsNewArtistToAttach : artistsNew) {
                artistsNewArtistToAttach = em.getReference(artistsNewArtistToAttach.getClass(), artistsNewArtistToAttach.getId());
                attachedArtistsNew.add(artistsNewArtistToAttach);
            }
            artistsNew = attachedArtistsNew;
            track.setArtists(artistsNew);
            track = em.merge(track);
            if (albumIdOld != null && !albumIdOld.equals(albumIdNew)) {
                albumIdOld.getTracksCollection().remove(track);
                albumIdOld = em.merge(albumIdOld);
            }
            if (albumIdNew != null && !albumIdNew.equals(albumIdOld)) {
                albumIdNew.getTracksCollection().add(track);
                albumIdNew = em.merge(albumIdNew);
            }
            if (genreIdOld != null && !genreIdOld.equals(genreIdNew)) {
                genreIdOld.getTracksCollection().remove(track);
                genreIdOld = em.merge(genreIdOld);
            }
            if (genreIdNew != null && !genreIdNew.equals(genreIdOld)) {
                genreIdNew.getTracksCollection().add(track);
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
            Album albumId = track.getAlbumId();
            if (albumId != null) {
                albumId.getTracksCollection().remove(track);
                albumId = em.merge(albumId);
            }
            Genre genreId = track.getGenreId();
            if (genreId != null) {
                genreId.getTracksCollection().remove(track);
                genreId = em.merge(genreId);
            }
            Collection<Artist> artists = track.getArtists();
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
    
}
