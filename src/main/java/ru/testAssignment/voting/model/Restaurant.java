package ru.testAssignment.voting.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@NamedQueries({
        @NamedQuery(name = Restaurant.ALL_SORTED, query = "SELECT m FROM Restaurant m ORDER BY m.name desc"),
        @NamedQuery(name = Restaurant.BY_DATE_SORTED, query = "SELECT m FROM Restaurant m WHERE m.dateTime=: dateTime ORDER BY m.name desc"),
        @NamedQuery(name = Restaurant.DELETE, query = "DELETE FROM Restaurant m WHERE m.id=:id")
})
@Entity
@Table(name = "restaurants")
public class Restaurant extends AbstractNamedEntity{

    public static final String ALL_SORTED = "Restaurant.getAll";
    public static final String BY_DATE_SORTED = "Restaurant.getByDate";
    public static final String DELETE = "Restaurant.delete";

    @Column(name = "description")
    @NotNull
    private String description;

    @Column(name = "date_time", nullable = false)
    @NotNull
    private LocalDateTime dateTime;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "restaurant")
    @NotNull
    private List<Dish> menu;

    public Restaurant(Integer id, String name, String description) {
        super(id, name);
        this.description = description;
        this.dateTime = LocalDateTime.now();
    }

    public Restaurant(Integer id, String name, String description, LocalDateTime dateTime) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.dateTime = dateTime;
    }

    public Restaurant() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public List<Dish> getMenu() {
        return menu;
    }
    @Override
    public String toString() {
        return "Restaurant{" +
                "description='" + description + '\'' +
                ", dateTime=" + dateTime +
                ", menu=" + menu +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
