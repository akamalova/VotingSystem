package ru.testAssignment.voting.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class Restaurant extends AbstractNamedEntity{
    private String description;
    private LocalDateTime dateTime;
    private List<Menu> menu;
    private List<User> votedUsers;

    public Restaurant(String name, Integer id, String description) {
        super(id, name);
        this.description = description;
        this.dateTime = LocalDateTime.now();
    }

    public Restaurant(String name, Integer id, String description, LocalDateTime dateTime) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.dateTime = dateTime;
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

    public List<Menu> getMenu() {
        return menu;
    }

    public List<User> getVotedUsers() {
        return votedUsers;
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
