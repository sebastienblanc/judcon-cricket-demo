package org.jboss.aerogear.judconcricket.model;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;
import java.lang.Override;
import org.jboss.aerogear.judconcricket.model.Comment;
import javax.persistence.ManyToOne;
import java.util.Set;
import java.util.HashSet;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Match implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @Column
   private String teamOne;

   @Column
   private String teamTwo;

   @Column
   private String score;

   @OneToMany
   private Set<Comment> comments = new HashSet<Comment>();

   @Column
   private String title;

   @Column
   private String description;

   public Long getId()
   {
      return this.id;
   }

   public void setId(final Long id)
   {
      this.id = id;
   }

   public int getVersion()
   {
      return this.version;
   }

   public void setVersion(final int version)
   {
      this.version = version;
   }

   @Override
   public boolean equals(Object that)
   {
      if (this == that)
      {
         return true;
      }
      if (that == null)
      {
         return false;
      }
      if (getClass() != that.getClass())
      {
         return false;
      }
      if (id != null)
      {
         return id.equals(((Match) that).id);
      }
      return super.equals(that);
   }

   @Override
   public int hashCode()
   {
      if (id != null)
      {
         return id.hashCode();
      }
      return super.hashCode();
   }

   public String getTeamOne()
   {
      return this.teamOne;
   }

   public void setTeamOne(final String teamOne)
   {
      this.teamOne = teamOne;
   }

   public String getTeamTwo()
   {
      return this.teamTwo;
   }

   public void setTeamTwo(final String teamTwo)
   {
      this.teamTwo = teamTwo;
   }

   public String getScore()
   {
      return this.score;
   }

   public void setScore(final String score)
   {
      this.score = score;
   }

   public Set<Comment> getComments()
   {
      return this.comments;
   }

   public void setComments(final Set<Comment> comments)
   {
      this.comments = comments;
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

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (teamOne != null && !teamOne.trim().isEmpty())
         result += "teamOne: " + teamOne;
      if (teamTwo != null && !teamTwo.trim().isEmpty())
         result += ", teamTwo: " + teamTwo;
      if (score != null && !score.trim().isEmpty())
         result += ", score: " + score;
      if (title != null && !title.trim().isEmpty())
         result += ", title: " + title;
      if (description != null && !description.trim().isEmpty())
         result += ", description: " + description;
      return result;
   }

}