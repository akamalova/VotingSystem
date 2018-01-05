package ru.testAssignment.voting.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@NamedQueries({
        @NamedQuery(name = Dish.ALL, query = "SELECT m FROM Dish m WHERE m.menu.id=:menuId"),
        @NamedQuery(name = Dish.DELETE, query = "DELETE FROM Dish m WHERE m.id=:id AND m.menu.id=:menuId")
})
@Entity
@Table(name = "dishes", uniqueConstraints = {@UniqueConstraint(columnNames = {"id", "name"}, name = "menu_dishes_idx")})
public class Dish extends AbstractNamedEntity{

    public static final String ALL = "Dish.getAll";
    public static final String DELETE = "Dish.delete";

    @Column(name = "price", nullable = false)
    @NotNull
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Menu menu;

    public Dish() {
    }

    public Dish(Integer id, String name, Double price) {
        super(id, name);
        this.price = price;
    }

    public Dish(String name, Double price){
        this(null, name, price);
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Dish dish = (Dish) o;

        if (!price.equals(dish.price)) return false;
        return menu.equals(dish.menu);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + price.hashCode();
        result = 31 * result + menu.hashCode();
        return result;
    }
}
