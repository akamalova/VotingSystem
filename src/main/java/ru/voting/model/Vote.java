package ru.voting.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "users_vote", uniqueConstraints = {@UniqueConstraint(columnNames = {"date", "user_id"}, name = "users_vote_unique_date_idx")})
public class Vote extends AbstractBaseEntity {

    @Column(name = "date", nullable = false)
    @NotNull
    private LocalDate date;

    @Column(name = "restaurant_id", nullable = false)
    @NotNull
    private int restaurantId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private User user;

    public Vote(Integer id, int restaurantId) {
        super(id);
        this.date = LocalDate.now();
        this.restaurantId = restaurantId;
    }

    public Vote(Integer id, LocalDate date, int restaurantId) {
        super(id);
        this.date = date;
        this.restaurantId = restaurantId;
    }

    public Vote(LocalDate date, int restaurantId) {
        this(null, date, restaurantId);
    }

    public Vote() {
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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
                "date=" + date +
                ", restaurantId=" + restaurantId +
                ", id=" + id +
                '}';
    }
}
