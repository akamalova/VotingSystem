package ru.testAssignment.voting.model;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

public class User {

    protected String name;
    protected Integer id;
    private String email;
    private String password;
    private Set<Role> roles;
    boolean Vote;
    protected Map<LocalDateTime, Restaurant> votingHistory;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public boolean isVote() {
        return Vote;
    }

    public void setVote(boolean vote) {
        Vote = vote;
    }

    public Map<LocalDateTime, Restaurant> getVotingHistory() {
        return votingHistory;
    }

    public void addVotingHistory(LocalDateTime localDateTime, Restaurant restaurant) {
        this.votingHistory.put(localDateTime, restaurant);
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

    @Override
    public String toString() {
        return "User{" + "name='" + name + '\'' + ", id=" + id + ", email='" + email + '\'' + ", password='" + password + '\'' + ", roles=" + roles + ", isVote=" + Vote + ", menuHistory=" + votingHistory + '}';
    }
}
