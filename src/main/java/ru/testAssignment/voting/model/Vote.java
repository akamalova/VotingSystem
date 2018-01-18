package ru.testAssignment.voting.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@NamedQueries({
        @NamedQuery(name = Vote.ALL_SORTED, query = "SELECT m FROM Vote m ORDER BY m.dateTime DESC, m.restaurantId"),
        @NamedQuery(name = Vote.ALL_SORTED_BY_USER, query = "SELECT m FROM Vote m WHERE m.user.id=:userId ORDER BY m.dateTime DESC"),
        @NamedQuery(name = Vote.DELETE, query = "DELETE FROM Vote m WHERE m.id=:id AND m.user.id=:userId"),
        @NamedQuery(name = Vote.GET_VOTES_BY_DATE, query = "SELECT m FROM Vote m WHERE m.dateTime>=:dateTimeMin AND m.dateTime<=:dateTimeMax ORDER BY m.dateTime DESC")

})
@Entity
@Table(name = "users_vote", uniqueConstraints = {@UniqueConstraint(columnNames = {"date_time", "user_id"}, name = "users_vote_unique_date_time_idx")})
public class Vote extends AbstractBaseEntity {

    public static final String ALL_SORTED = "Vote.getAll";
    public static final String DELETE = "Vote.delete";
    public static final String GET_VOTES_BY_DATE = "Vote.getVotesByDate";
    public static final String ALL_SORTED_BY_USER = "Vote.getAllByUser";

    @Column(name = "date_time", nullable = false)
    @NotNull
    private LocalDateTime dateTime;

    @Column(name = "restaurant_id", nullable = false)
    @NotNull
    private int restaurantId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private User user;

    public Vote(Integer id, LocalDateTime dateTime, int restaurantId) {
        super(id);
        this.dateTime = dateTime;
        this.restaurantId = restaurantId;
    }

    public Vote(LocalDateTime dateTime, int restaurantId) {
        this(null, dateTime, restaurantId);
    }

    public Vote() {
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "dateTime=" + dateTime +
                ", restaurantId=" + restaurantId +
                ", id=" + id +
                '}';
    }
}
