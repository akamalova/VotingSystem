package ru.testAssignment.voting.to;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

public class MenuTo extends BaseTo implements Serializable {
    private static final long serialVersionUID = 4L;

    @NotNull
    private LocalDateTime dateTime;

    public MenuTo() {
    }

    public MenuTo(Integer id, LocalDateTime dateTime) {
        super(id);
        this.dateTime = dateTime;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "MenuTo{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                '}';
    }
}
