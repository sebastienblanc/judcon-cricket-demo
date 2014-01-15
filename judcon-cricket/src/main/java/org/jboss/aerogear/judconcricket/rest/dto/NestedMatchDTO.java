package org.jboss.aerogear.judconcricket.rest.dto;

import java.io.Serializable;
import org.jboss.aerogear.judconcricket.model.Match;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class NestedMatchDTO implements Serializable
{

   private String teamOne;
   private Long id;
   private String title;
   private String teamTwo;
   private String description;
   private String score;
   private int version;

   public NestedMatchDTO()
   {
   }

   public NestedMatchDTO(final Match entity)
   {
      if (entity != null)
      {
         this.teamOne = entity.getTeamOne();
         this.id = entity.getId();
         this.title = entity.getTitle();
         this.teamTwo = entity.getTeamTwo();
         this.description = entity.getDescription();
         this.score = entity.getScore();
         this.version = entity.getVersion();
      }
   }

   public Match fromDTO(Match entity, EntityManager em)
   {
      if (entity == null)
      {
         entity = new Match();
      }
      if (this.id != null)
      {
         TypedQuery<Match> findByIdQuery = em.createQuery(
               "SELECT DISTINCT m FROM Match m WHERE m.id = :entityId",
               Match.class);
         findByIdQuery.setParameter("entityId", this.id);
         entity = findByIdQuery.getSingleResult();
         return entity;
      }
      entity.setTeamOne(this.teamOne);
      entity.setTitle(this.title);
      entity.setTeamTwo(this.teamTwo);
      entity.setDescription(this.description);
      entity.setScore(this.score);
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

   public Long getId()
   {
      return this.id;
   }

   public void setId(final Long id)
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

   public int getVersion()
   {
      return this.version;
   }

   public void setVersion(final int version)
   {
      this.version = version;
   }
}