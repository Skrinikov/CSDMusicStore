package com.fractals.controllers;

import com.fractals.beans.Album;
import com.fractals.beans.Artist;
import com.fractals.beans.Track;
import com.fractals.controllers.exceptions.IllegalOrphanException;
import com.fractals.controllers.exceptions.NonexistentEntityException;
import com.fractals.controllers.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;

/**
 *
 * @author MOUFFOK Sarah
 */

@Named
@RequestScoped
public class ArtistJpaController implements Serializable {
    
    @Resource
    private UserTransaction utx;

    @PersistenceContext
    private EntityManager em;
    
    public void create(Artist artist) throws RollbackFailureException, Exception {
        if (artist.getTracks() == null) {
            artist.setTracks(new ArrayList<Track>());
        }
        if (artist.getAlbums() == null) {
            artist.setAlbums(new ArrayList<Album>());
        }
        
        try {
            utx.begin();
            List<Track> attachedTracks = new ArrayList<Track>();
            for (Track tracksTrackToAttach : artist.getTracks()) {
                tracksTrackToAttach = em.getReference(tracksTrackToAttach.getClass(), tracksTrackToAttach.getId());
                attachedTracks.add(tracksTrackToAttach);
            }
            artist.setTracks(attachedTracks);
            List<Album> attachedAlbums = new ArrayList<Album>();
            for (Album albumsAlbumToAttach : artist.getAlbums()) {
                albumsAlbumToAttach = em.getReference(albumsAlbumToAttach.getClass(), albumsAlbumToAttach.getId());
                attachedAlbums.add(albumsAlbumToAttach);
            }
            artist.setAlbums(attachedAlbums);
            em.persist(artist);
            for (Track tracksTrack : artist.getTracks()) {
                tracksTrack.getArtists().add(artist);
                tracksTrack = em.merge(tracksTrack);
            }
            for (Album albumsAlbum : artist.getAlbums()) {
                Artist oldArtistOfAlbumsAlbum = albumsAlbum.getArtist();
                albumsAlbum.setArtist(artist);
                albumsAlbum = em.merge(albumsAlbum);
                if (oldArtistOfAlbumsAlbum != null) {
                    oldArtistOfAlbumsAlbum.getAlbums().remove(albumsAlbum);
                    oldArtistOfAlbumsAlbum = em.merge(oldArtistOfAlbumsAlbum);
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

    public void edit(Artist artist) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
            utx.begin();
            try{
            Artist persistentArtist = em.find(Artist.class, artist.getId());
            List<Track> tracksOld = persistentArtist.getTracks();
            List<Track> tracksNew = artist.getTracks();
            List<Album> albumsOld = persistentArtist.getAlbums();
            List<Album> albumsNew = artist.getAlbums();
            List<String> illegalOrphanMessages = null;
            for (Album albumsOldAlbum : albumsOld) {
                if (!albumsNew.contains(albumsOldAlbum)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Album " + albumsOldAlbum + " since its artist field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Track> attachedTracksNew = new ArrayList<Track>();
            for (Track tracksNewTrackToAttach : tracksNew) {
                tracksNewTrackToAttach = em.getReference(tracksNewTrackToAttach.getClass(), tracksNewTrackToAttach.getId());
                attachedTracksNew.add(tracksNewTrackToAttach);
            }
            tracksNew = attachedTracksNew;
            artist.setTracks(tracksNew);
            List<Album> attachedAlbumsNew = new ArrayList<Album>();
            for (Album albumsNewAlbumToAttach : albumsNew) {
                albumsNewAlbumToAttach = em.getReference(albumsNewAlbumToAttach.getClass(), albumsNewAlbumToAttach.getId());
                attachedAlbumsNew.add(albumsNewAlbumToAttach);
            }
            albumsNew = attachedAlbumsNew;
            artist.setAlbums(albumsNew);
            artist = em.merge(artist);
            for (Track tracksOldTrack : tracksOld) {
                if (!tracksNew.contains(tracksOldTrack)) {
                    tracksOldTrack.getArtists().remove(artist);
                    tracksOldTrack = em.merge(tracksOldTrack);
                }
            }
            for (Track tracksNewTrack : tracksNew) {
                if (!tracksOld.contains(tracksNewTrack)) {
                    tracksNewTrack.getArtists().add(artist);
                    tracksNewTrack = em.merge(tracksNewTrack);
                }
            }
            for (Album albumsNewAlbum : albumsNew) {
                if (!albumsOld.contains(albumsNewAlbum)) {
                    Artist oldArtistOfAlbumsNewAlbum = albumsNewAlbum.getArtist();
                    albumsNewAlbum.setArtist(artist);
                    albumsNewAlbum = em.merge(albumsNewAlbum);
                    if (oldArtistOfAlbumsNewAlbum != null && !oldArtistOfAlbumsNewAlbum.equals(artist)) {
                        oldArtistOfAlbumsNewAlbum.getAlbums().remove(albumsNewAlbum);
                        oldArtistOfAlbumsNewAlbum = em.merge(oldArtistOfAlbumsNewAlbum);
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
                Integer id = artist.getId();
                if (findArtist(id) == null) {
                    throw new NonexistentEntityException("The artist with id " + id + " no longer exists.");
                }
            }
            throw ex;
        }    
    }
            
    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        try {    
            utx.begin();
            Artist artist;
            try {
                artist = em.getReference(Artist.class, id);
                artist.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The artist with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Album> albumsOrphanCheck = artist.getAlbums();
            for (Album albumsOrphanCheckAlbum : albumsOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Artist (" + artist + ") cannot be destroyed since the Album " + albumsOrphanCheckAlbum + " in its albums field has a non-nullable artist field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Track> tracks = artist.getTracks();
            for (Track tracksTrack : tracks) {
                tracksTrack.getArtists().remove(artist);
                tracksTrack = em.merge(tracksTrack);
            }
            em.remove(artist);
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

    public List<Artist> findArtistEntities() {
        return findArtistEntities(true, -1, -1);
    }

    public List<Artist> findArtistEntities(int maxResults, int firstResult) {
        return findArtistEntities(false, maxResults, firstResult);
    }

    private List<Artist> findArtistEntities(boolean all, int maxResults, int firstResult) {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Artist.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();

    }

    public Artist findArtist(Integer id) {
        return em.find(Artist.class, id);          
    }

    public int getArtistCount() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<Artist> rt = cq.from(Artist.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
    public boolean isEmpty(){
        return getArtistCount() == 0;
    }
}
