/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fractals.controllers;

import com.fractals.beans.Genre;
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
@Named("genreController")
@SessionScoped
public class GenreJpaController implements Serializable {

    @Resource
    private UserTransaction utx;

    @PersistenceContext
    private EntityManager em;

    public void create(Genre genre) throws RollbackFailureException, Exception {
        if (genre.getTracks() == null) {
            genre.setTracks(new ArrayList<Track>());
        }
        try {
            utx.begin();
            List<Track> attachedTracksCollection = new ArrayList<Track>();
            for (Track tracksCollectionTrackToAttach : genre.getTracks()) {
                tracksCollectionTrackToAttach = em.getReference(tracksCollectionTrackToAttach.getClass(), tracksCollectionTrackToAttach.getId());
                attachedTracksCollection.add(tracksCollectionTrackToAttach);
            }
            genre.setTracks(attachedTracksCollection);
            em.persist(genre);
            for (Track tracksCollectionTrack : genre.getTracks()) {
                Genre oldGenreIdOfTracksCollectionTrack = tracksCollectionTrack.getGenre();
                tracksCollectionTrack.setGenre(genre);
                tracksCollectionTrack = em.merge(tracksCollectionTrack);
                if (oldGenreIdOfTracksCollectionTrack != null) {
                    oldGenreIdOfTracksCollectionTrack.getTracks().remove(tracksCollectionTrack);
                    oldGenreIdOfTracksCollectionTrack = em.merge(oldGenreIdOfTracksCollectionTrack);
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

    public void edit(Genre genre) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
            Genre persistentGenre = em.find(Genre.class, genre.getId());
            List<Track> tracksCollectionOld = persistentGenre.getTracks();
            List<Track> tracksCollectionNew = genre.getTracks();
            List<String> illegalOrphanMessages = null;
            for (Track tracksCollectionOldTrack : tracksCollectionOld) {
                if (!tracksCollectionNew.contains(tracksCollectionOldTrack)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Track " + tracksCollectionOldTrack + " since its genreId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Track> attachedTracksCollectionNew = new ArrayList<Track>();
            for (Track tracksCollectionNewTrackToAttach : tracksCollectionNew) {
                tracksCollectionNewTrackToAttach = em.getReference(tracksCollectionNewTrackToAttach.getClass(), tracksCollectionNewTrackToAttach.getId());
                attachedTracksCollectionNew.add(tracksCollectionNewTrackToAttach);
            }
            tracksCollectionNew = attachedTracksCollectionNew;
            genre.setTracks(tracksCollectionNew);
            genre = em.merge(genre);
            for (Track tracksCollectionNewTrack : tracksCollectionNew) {
                if (!tracksCollectionOld.contains(tracksCollectionNewTrack)) {
                    Genre oldGenreIdOfTracksCollectionNewTrack = tracksCollectionNewTrack.getGenre();
                    tracksCollectionNewTrack.setGenre(genre);
                    tracksCollectionNewTrack = em.merge(tracksCollectionNewTrack);
                    if (oldGenreIdOfTracksCollectionNewTrack != null && !oldGenreIdOfTracksCollectionNewTrack.equals(genre)) {
                        oldGenreIdOfTracksCollectionNewTrack.getTracks().remove(tracksCollectionNewTrack);
                        oldGenreIdOfTracksCollectionNewTrack = em.merge(oldGenreIdOfTracksCollectionNewTrack);
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
                Integer id = genre.getId();
                if (findGenre(id) == null) {
                    throw new NonexistentEntityException("The genre with id " + id + " no longer exists.");
                }
            }
            throw ex;
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
            Genre genre;
            try {
                genre = em.getReference(Genre.class, id);
                genre.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The genre with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Track> tracksCollectionOrphanCheck = genre.getTracks();
            for (Track tracksCollectionOrphanCheckTrack : tracksCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Genre (" + genre + ") cannot be destroyed since the Track " + tracksCollectionOrphanCheckTrack + " in its tracksCollection field has a non-nullable genreId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(genre);
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

    public List<Genre> findGenreEntities() {
        return findGenreEntities(true, -1, -1);
    }

    public List<Genre> findGenreEntities(int maxResults, int firstResult) {
        return findGenreEntities(false, maxResults, firstResult);
    }

    private List<Genre> findGenreEntities(boolean all, int maxResults, int firstResult) {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Genre.class));
        Query q = em.createQuery(cq);
        if (!all) {
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        return q.getResultList();
    }

    public Genre findGenre(Integer id) {
        return em.find(Genre.class, id);

    }

    public int getGenreCount() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<Genre> rt = cq.from(Genre.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
    public boolean isEmpty(){
        return getGenreCount() == 0;
    }
}
