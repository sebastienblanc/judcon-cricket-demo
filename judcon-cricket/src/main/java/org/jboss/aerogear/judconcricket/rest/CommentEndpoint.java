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
import org.jboss.aerogear.judconcricket.model.Comment;
import org.jboss.aerogear.judconcricket.rest.dto.CommentDTO;

/**
 * 
 */
@Stateless
@Path("/comments")
public class CommentEndpoint
{
   @PersistenceContext(unitName = "forge-default")
   private EntityManager em;

   @POST
   @Consumes("application/json")
   public Response create(CommentDTO dto)
   {
      Comment entity = dto.fromDTO(null, em);
      em.persist(entity);
      return Response.created(UriBuilder.fromResource(CommentEndpoint.class).path(String.valueOf(entity.getId())).build()).build();
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") Long id)
   {
      Comment entity = em.find(Comment.class, id);
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
      TypedQuery<Comment> findByIdQuery = em.createQuery("SELECT DISTINCT c FROM Comment c LEFT JOIN FETCH c.match WHERE c.id = :entityId ORDER BY c.id", Comment.class);
      findByIdQuery.setParameter("entityId", id);
      Comment entity = findByIdQuery.getSingleResult();
      if (entity == null)
      {
         return Response.status(Status.NOT_FOUND).build();
      }
      CommentDTO dto = new CommentDTO(entity);
      return Response.ok(dto).build();
   }

   @GET
   @Produces("application/json")
   public List<CommentDTO> listAll()
   {
      final List<Comment> searchResults = em.createQuery("SELECT DISTINCT c FROM Comment c LEFT JOIN FETCH c.match ORDER BY c.id", Comment.class).getResultList();
      final List<CommentDTO> results = new ArrayList<CommentDTO>();
      for (Comment searchResult : searchResults)
      {
         CommentDTO dto = new CommentDTO(searchResult);
         results.add(dto);
      }
      return results;
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Consumes("application/json")
   public Response update(@PathParam("id") Long id, CommentDTO dto)
   {
      TypedQuery<Comment> findByIdQuery = em.createQuery("SELECT DISTINCT c FROM Comment c LEFT JOIN FETCH c.match WHERE c.id = :entityId ORDER BY c.id", Comment.class);
      findByIdQuery.setParameter("entityId", id);
      Comment entity = findByIdQuery.getSingleResult();
      entity = dto.fromDTO(entity, em);
      entity = em.merge(entity);
      return Response.noContent().build();
   }
}