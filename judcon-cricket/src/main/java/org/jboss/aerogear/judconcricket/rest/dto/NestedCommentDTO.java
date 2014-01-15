package org.jboss.aerogear.judconcricket.rest.dto;

import java.io.Serializable;
import org.jboss.aerogear.judconcricket.model.Comment;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class NestedCommentDTO implements Serializable
{

   private String content;
   private Long id;
   private String author;
   private String title;
   private String description;
   private int version;

   public NestedCommentDTO()
   {
   }

   public NestedCommentDTO(final Comment entity)
   {
      if (entity != null)
      {
         this.content = entity.getContent();
         this.id = entity.getId();
         this.author = entity.getAuthor();
         this.title = entity.getTitle();
         this.description = entity.getDescription();
         this.version = entity.getVersion();
      }
   }

   public Comment fromDTO(Comment entity, EntityManager em)
   {
      if (entity == null)
      {
         entity = new Comment();
      }
      if (this.id != null)
      {
         TypedQuery<Comment> findByIdQuery = em.createQuery(
               "SELECT DISTINCT c FROM Comment c WHERE c.id = :entityId",
               Comment.class);
         findByIdQuery.setParameter("entityId", this.id);
         entity = findByIdQuery.getSingleResult();
         return entity;
      }
      entity.setContent(this.content);
      entity.setAuthor(this.author);
      entity.setTitle(this.title);
      entity.setDescription(this.description);
      entity.setVersion(this.version);
      entity = em.merge(entity);
      return entity;
   }

   public String getContent()
   {
      return this.content;
   }

   public void setContent(final String content)
   {
      this.content = content;
   }

   public Long getId()
   {
      return this.id;
   }

   public void setId(final Long id)
   {
      this.id = id;
   }

   public String getAuthor()
   {
      return this.author;
   }

   public void setAuthor(final String author)
   {
      this.author = author;
   }

   public String getTitle()
   {
      return this.title;
   }

   public void setTitle(final String title)
   {
      this.title = title;
   }

   public String getDescription()
   {
      return this.description;
   }

   public void setDescription(final String description)
   {
      this.description = description;
   }

   public int getVersion()
   {
      return this.version;
   }

   public void setVersion(final int version)
   {
      this.version = version;
   }
}