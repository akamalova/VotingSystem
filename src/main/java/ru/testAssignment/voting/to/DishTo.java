package ru.testAssignment.voting.to;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class DishTo extends BaseTo implements Serializable {
    private static final long serialVersionUID = 5L;

    @NotBlank
    @Size(min = 2, max = 100, message = "length must between 2 and 100 characters")
    private String name;

    @NotNull
    private Double price;

    public DishTo() {
    }

    public DishTo(Integer id, String name, Double price) {
        super(id);
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "DishTo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
