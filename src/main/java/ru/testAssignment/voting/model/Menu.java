package ru.testAssignment.voting.model;

public class Menu extends AbstractNamedEntity{

    private Double price;
    private Restaurant restaurant;

    public Menu() {
    }

    public Menu(Double price) {
        this.price = price;
    }

    public Menu(Integer id, String name, Double price) {
        super(id, name);
        this.price = price;
    }

    public Menu(String name, Double price){
        this(null, name, price);
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
