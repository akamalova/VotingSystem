package ru.testAssignment.voting.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

public class Restaurant {
    private String name, description;
    private Integer id, votesNum;
    private LocalDateTime dateTime;
    private Map<String, Double> menu;

    public Restaurant(String name, Integer id, String description, Map<String, Double> menu) {
        this.name = name;
        this.id = id;
        this.description = description;
        this.dateTime = LocalDateTime.now();
        this.menu = menu;
        votesNum = 0;
    }

    public Restaurant(String name, Integer id, String description, Map<String, Double> menu, Integer votesNum) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.votesNum = votesNum;
        this.dateTime = LocalDateTime.now();
        this.menu = menu;
    }

    public Restaurant(String name, Integer id, String description, LocalDateTime dateTime, Map<String, Double> menu, Integer votesNum) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.votesNum = votesNum;
        this.menu = menu;
        this.dateTime = dateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Map<String, Double> getMenu() {
        return menu;
    }

    public void setMenu(Map<String, Double> menu) {
        this.menu = menu;
    }

    public void addMenu(String dish, double price) {
        menu.put(dish, price);
    }

    public int getVotesNum() {
        return votesNum;
    }

    public void setVotesNum(int votesNum) {
        this.votesNum = votesNum;
    }

    @Override
    public String toString() {
        return "Restaurant{" + "name='" + name + '\'' + ", description='" + description + '\'' + ", id=" + id + ", votesNum=" + votesNum + ", menu=" + menu + '}';
    }
}
