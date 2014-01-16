package org.jboss.aerogear.judconcricket.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import org.jboss.aerogear.judconcricket.model.Match;
import org.jboss.aerogear.judconcricket.rest.dto.MatchDTO;
import org.jboss.aerogear.judconcricket.service.CricScoreService;

/**
 * 
 */
@Stateless
@Path("/matchs")
public class MatchEndpoint
{
   @PersistenceContext(unitName = "forge-default")
   private EntityManager em;

   @POST
   @Consumes("application/json")
   public Response create(MatchDTO dto)
   {
      Match entity = dto.fromDTO(null, em);
      em.persist(entity);
      return Response.created(UriBuilder.fromResource(MatchEndpoint.class).path(String.valueOf(entity.getId())).build()).build();
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") Long id)
   {
      Match entity = em.find(Match.class, id);
      if (entity == null)
      {
         return Response.status(Status.NOT_FOUND).build();
      }
      em.remove(entity);
      return Response.noContent().build();
   }

   @GET
   @Path("/{id:[0-9][0-9]*}")
   @Produces("application/json")
   public Response findById(@PathParam("id") Long id)
   {
      TypedQuery<Match> findByIdQuery = em.createQuery("SELECT DISTINCT m FROM Match m LEFT JOIN FETCH m.comments WHERE m.id = :entityId ORDER BY m.id", Match.class);
      findByIdQuery.setParameter("entityId", id);
      Match entity = findByIdQuery.getSingleResult();
      if (entity == null)
      {
         return Response.status(Status.NOT_FOUND).build();
      }
      MatchDTO dto = new MatchDTO(entity);
      return Response.ok(dto).build();
   }

   @GET
   @Produces("application/json")
   public List<MatchDTO> listAll()
   {
	   CricScoreService cricScoreService = new CricScoreService();
      final List<Match> searchResults = em.createQuery("SELECT DISTINCT m FROM Match m LEFT JOIN FETCH m.comments ORDER BY m.id", Match.class).getResultList();
      final List<MatchDTO> results = new ArrayList<MatchDTO>();
      for (Match searchResult : searchResults)
      {
         MatchDTO dto = new MatchDTO(searchResult);
         results.add(dto);
      }
      //let's retrieve the remote matches
      results.addAll(cricScoreService.getMatches());
      return results;
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Consumes("application/json")
   public Response update(@PathParam("id") Long id, MatchDTO dto)
   {
      TypedQuery<Match> findByIdQuery = em.createQuery("SELECT DISTINCT m FROM Match m LEFT JOIN FETCH m.comments WHERE m.id = :entityId ORDER BY m.id", Match.class);
      findByIdQuery.setParameter("entityId", id);
      Match entity = findByIdQuery.getSingleResult();
      entity = dto.fromDTO(entity, em);
      entity = em.merge(entity);
      return Response.noContent().build();
   }
}