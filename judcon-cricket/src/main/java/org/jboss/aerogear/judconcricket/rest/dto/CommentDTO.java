package org.jboss.aerogear.judconcricket.rest.dto;

import java.io.Serializable;
import org.jboss.aerogear.judconcricket.model.Comment;
import javax.persistence.EntityManager;
import org.jboss.aerogear.judconcricket.rest.dto.NestedMatchDTO;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CommentDTO implements Serializable
{

   private String content;
   private Long id;
   private String author;
   private String title;
   private String description;
   private NestedMatchDTO match;
   private int version;

   public CommentDTO()
   {
   }

   public CommentDTO(final Comment entity)
   {
      if (entity != null)
      {
         this.content = entity.getContent();
         this.id = entity.getId();
         this.author = entity.getAuthor();
         this.title = entity.getTitle();
         this.description = entity.getDescription();
         this.match = new NestedMatchDTO(entity.getMatch());
         this.version = entity.getVersion();
      }
   }

   public Comment fromDTO(Comment entity, EntityManager em)
   {
      if (entity == null)
      {
         entity = new Comment();
      }
      entity.setContent(this.content);
      entity.setAuthor(this.author);
      entity.setTitle(this.title);
      entity.setDescription(this.description);
      if (this.match != null)
      {
         entity.setMatch(this.match.fromDTO(entity.getMatch(), em));
      }
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

   public NestedMatchDTO getMatch()
   {
      return this.match;
   }

   public void setMatch(final NestedMatchDTO match)
   {
      this.match = match;
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