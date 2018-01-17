package ru.testAssignment.voting.to;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

public class VoteTo extends BaseTo implements Serializable {
    private static final long serialVersionUID = 2L;

    @NotNull
    private LocalDateTime dateTime;

    @NotNull
    private int restaurantId;

    public VoteTo() {
    }

    public VoteTo(Integer id,LocalDateTime dateTime, int restaurantId) {
        super(id);
        this.dateTime = dateTime;
        this.restaurantId = restaurantId;
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

    @Override
    public String toString() {
        return "VoteTo{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", restaurantId=" + restaurantId +
                '}';
    }
}
