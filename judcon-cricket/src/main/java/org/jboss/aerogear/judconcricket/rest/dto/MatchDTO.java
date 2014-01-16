package org.jboss.aerogear.judconcricket.rest.dto;

import java.io.Serializable;
import org.jboss.aerogear.judconcricket.model.Match;
import javax.persistence.EntityManager;
import java.util.Set;
import java.util.HashSet;
import org.jboss.aerogear.judconcricket.rest.dto.NestedCommentDTO;
import org.jboss.aerogear.judconcricket.model.Comment;
import java.util.Iterator;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MatchDTO implements Serializable
{

   private String teamOne;
   private String id;
   private String title;
   private String teamTwo;
   private String description;
   private String score;
   private Set<NestedCommentDTO> comments = new HashSet<NestedCommentDTO>();
   private int version;

   public MatchDTO()
   {
   }

   public MatchDTO(final Match entity)
   {
      if (entity != null)
      {
         this.teamOne = entity.getTeamOne();
         this.id = entity.getId();
         this.title = entity.getTitle();
         this.teamTwo = entity.getTeamTwo();
         this.description = entity.getDescription();
         this.score = entity.getScore();
         Iterator<Comment> iterComments = entity.getComments().iterator();
         for (; iterComments.hasNext();)
         {
            Comment element = iterComments.next();
            this.comments.add(new NestedCommentDTO(element));
         }
         this.version = entity.getVersion();
      }
   }

   public Match fromDTO(Match entity, EntityManager em)
   {
      if (entity == null)
      {
         entity = new Match();
      }
      if(this.getId() != null)
      {
    	entity.setId(this.getId());  
      }
      entity.setTeamOne(this.teamOne);
      entity.setTitle(this.title);
      entity.setTeamTwo(this.teamTwo);
      entity.setDescription(this.description);
      entity.setScore(this.score);
      Iterator<Comment> iterComments = entity.getComments().iterator();
      for (; iterComments.hasNext();)
      {
         boolean found = false;
         Comment comment = iterComments.next();
         Iterator<NestedCommentDTO> iterDtoComments = this.getComments()
               .iterator();
         for (; iterDtoComments.hasNext();)
         {
            NestedCommentDTO dtoComment = iterDtoComments.next();
            if (dtoComment.getId().equals(comment.getId()))
            {
               found = true;
               break;
            }
         }
         if (found == false)
         {
            iterComments.remove();
         }
      }
      Iterator<NestedCommentDTO> iterDtoComments = this.getComments()
            .iterator();
      for (; iterDtoComments.hasNext();)
      {
         boolean found = false;
         NestedCommentDTO dtoComment = iterDtoComments.next();
         iterComments = entity.getComments().iterator();
         for (; iterComments.hasNext();)
         {
            Comment comment = iterComments.next();
            if (dtoComment.getId().equals(comment.getId()))
            {
               found = true;
               break;
            }
         }
         if (found == false)
         {
            Iterator<Comment> resultIter = em
                  .createQuery("SELECT DISTINCT c FROM Comment c",
                        Comment.class).getResultList().iterator();
            for (; resultIter.hasNext();)
            {
               Comment result = resultIter.next();
               if (result.getId().equals(dtoComment.getId()))
               {
                  entity.getComments().add(result);
                  break;
               }
            }
         }
      }
      entity.setVersion(this.version);
      entity = em.merge(entity);
      return entity;
   }

   public String getTeamOne()
   {
      return this.teamOne;
   }

   public void setTeamOne(final String teamOne)
   {
      this.teamOne = teamOne;
   }

   public String getId()
   {
      return this.id;
   }

   public void setId(final String id)
   {
      this.id = id;
   }

   public String getTitle()
   {
      return this.title;
   }

   public void setTitle(final String title)
   {
      this.title = title;
   }

   public String getTeamTwo()
   {
      return this.teamTwo;
   }

   public void setTeamTwo(final String teamTwo)
   {
      this.teamTwo = teamTwo;
   }

   public String getDescription()
   {
      return this.description;
   }

   public void setDescription(final String description)
   {
      this.description = description;
   }

   public String getScore()
   {
      return this.score;
   }

   public void setScore(final String score)
   {
      this.score = score;
   }

   public Set<NestedCommentDTO> getComments()
   {
      return this.comments;
   }

   public void setComments(final Set<NestedCommentDTO> comments)
   {
      this.comments = comments;
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