package ru.testAssignment.voting.model;

import org.springframework.util.CollectionUtils;

import java.util.*;

public class User extends AbstractNamedEntity{

    private String email;
    private Set<Role> roles;
    private boolean voted;

    public User(){}

    public User(User u) {
        this(u.getId(), u.getName(), u.getEmail(), u.getRoles());
    }

    public User(Integer id, String name, String email, Role role, Role... roles) {
        this(id,name, email, EnumSet.of(role, roles));
    }

    public User(Integer id, String name, String email, Collection<Role> roles) {
        super(id, name);
        this.email = email;
        setRoles(roles);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = CollectionUtils.isEmpty(roles) ? Collections.emptySet() : EnumSet.copyOf(roles);
    }

    public boolean isVoted() {
        return voted;
    }

    public void setVoted(boolean voted) {
        this.voted = voted;
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
        return "User{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", email='" + email + '\'' +
                ", roles=" + roles +
                ", voted=" + voted +
                '}';
    }
}
