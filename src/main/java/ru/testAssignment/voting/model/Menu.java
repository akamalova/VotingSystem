package ru.testAssignment.voting.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@NamedQueries({
        @NamedQuery(name = Menu.ALL, query = "SELECT m FROM Menu m WHERE m.restaurant.id=:restaurantId"),
        @NamedQuery(name = Menu.All_BY_DATE, query = "SELECT m FROM Menu m WHERE m.dateTime>=:dateTimeMin AND m.dateTime<=:dateTimeMax"),
        @NamedQuery(name = Menu.RESTAURANT_BY_DATE, query = "SELECT m FROM Menu m WHERE m.dateTime>=:dateTimeMin AND m.dateTime<=:dateTimeMax AND m.restaurant.id=:restaurantId"),
        @NamedQuery(name = Menu.DELETE, query = "DELETE FROM Menu m WHERE m.id=:id AND m.restaurant.id=:restaurantId")
})
@Entity
@Table(name = "Menu")
public class Menu extends AbstractBaseEntity {

    public static final String ALL = "Menu.getAll";
    public static final String DELETE = "Menu.delete";
    public static final String All_BY_DATE = "Menu.getByDate";
    public static final String RESTAURANT_BY_DATE = "Menu.RestaurantByDate";

    public Menu() {
    }

    public Menu(LocalDateTime dateTime) {
        this(null, dateTime);
    }

    public Menu(Integer id, LocalDateTime dateTime) {
        super(id);
        this.dateTime = dateTime;
    }

    @Column(name = "date_time", nullable = false)
    @NotNull
    private LocalDateTime dateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Restaurant restaurant;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "menu")
    @NotNull
    private List<Dish> dishes;

    public List<Dish> getMenu() {
        return dishes;
    }
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }


    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public List<Dish> getDishes() {
        return dishes;
    }
}
