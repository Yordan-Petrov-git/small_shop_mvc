package com.shop.advance.academy.yordan.petrov.git.shop.data.models;

import com.shop.advance.academy.yordan.petrov.git.shop.data.models.enums.Rating;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Class model for .
 *
 * @author Yordan Petrov
 * @version 1.0.0.0
 * @since Jul 8, 2020.
 */
@Entity
@Table(name = "opinions")
public class Opinion extends BaseEntity {

    private String comment;
    private Rating rating = Rating.NONE;
    private Double vote;
    private Set<Media> media = new HashSet<>();
    private User user;

    /**
     * Constructor
     */
    public Opinion() {
    }

    /**
     * @return
     */
    @Column(name = "comment")
    public String getComment() {
        return this.comment;
    }

    /**
     * @param comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return
     */
    @Column(name = "rating")
    @Enumerated(EnumType.STRING)
    public Rating getRating() {
        return this.rating;
    }

    /**
     * @param rating
     */
    public void setRating(Rating rating) {
        this.rating = rating;
    }

    /**
     * @return
     */
    @Column(name = "vote")
    public Double getVote() {
        return this.vote;
    }

    /**
     * @param vote
     */
    public void setVote(Double vote) {
        this.vote = vote;
    }

    /**
     * @return
     */
    @OneToMany(targetEntity = Media.class,
            fetch = FetchType.EAGER,
            cascade = {CascadeType.REMOVE, CascadeType.MERGE, CascadeType.PERSIST},
            orphanRemoval = true)
    @JoinColumn(name = "opinion_id")
    public Set<Media> getMedia() {
        return this.media;
    }

    /**
     * @param media
     */
    public void setMedia(Set<Media> media) {
        this.media = media;
    }


    /**
     * @return
     */
    @ManyToOne(targetEntity = User.class,
            fetch = FetchType.LAZY,
            cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    public User getUser() {
        return this.user;
    }

    /**
     * @param user
     */
    public void setUser(User user) {
        this.user = user;
    }


    /**
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Opinion)) return false;
        if (!super.equals(o)) return false;
        Opinion opinion = (Opinion) o;
        return Objects.equals(comment, opinion.comment) &&
                rating == opinion.rating &&
                Objects.equals(vote, opinion.vote);
    }

    /**
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), comment, rating, vote);
    }

    /**
     * @return
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Opinion{");
        sb.append("comment='").append(comment).append('\'');
        sb.append(", rating=").append(rating);
        sb.append(", vote=").append(vote);
        sb.append(", user=").append(user);
        sb.append(", id=").append(id);
        sb.append('}');
        return sb.toString();
    }
}
