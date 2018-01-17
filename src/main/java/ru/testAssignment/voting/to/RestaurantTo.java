package ru.testAssignment.voting.to;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class RestaurantTo extends BaseTo implements Serializable {
    private static final long serialVersionUID = 3L;

    @NotBlank
    @Size(min = 2, max = 100, message = "length must between 2 and 100 characters")
    private String name;

    @NotBlank
    @Size(min = 5, max = 120, message = "length must between 2 and 120 characters")
    private String description;

    public RestaurantTo(Integer id, String name, String description) {
        super(id);
        this.name = name;
        this.description = description;
    }

    public RestaurantTo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "RestaurantTo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
