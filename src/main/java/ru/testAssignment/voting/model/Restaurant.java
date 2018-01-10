package ru.testAssignment.voting.model;


import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cache;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.List;

@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@NamedQueries({
        @NamedQuery(name = Restaurant.ALL_SORTED, query = "SELECT m FROM Restaurant m ORDER BY m.name desc"),
        @NamedQuery(name = Restaurant.DELETE, query = "DELETE FROM Restaurant m WHERE m.id=:id")
})
@Entity
@Table(name = "restaurants")
public class Restaurant extends AbstractNamedEntity{

    public static final String ALL_SORTED = "Restaurant.getAll";
    public static final String DELETE = "Restaurant.delete";

    @Column(name = "description")
    @NotNull
    private String description;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OrderBy("dateTime DESC")
    private List<Menu> menu;

    public Restaurant() {
    }

    public Restaurant(Integer id, String name, String description) {
        super(id, name);
        this.description = description;
    }

    public Restaurant(String name, String description){
        this(null, name,description);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public List<Menu> getMenu() {
        return menu;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
