package ru.testAssignment.voting.model;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.util.CollectionUtils;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@NamedQueries({
        @NamedQuery(name = User.DELETE, query = "DELETE FROM User u WHERE u.id=:id"),
        @NamedQuery(name = User.BY_EMAIL, query = "SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.roles WHERE u.email=?1"),
        @NamedQuery(name = User.ALL_SORTED, query = "SELECT u FROM User u ORDER BY u.name, u.email"),
})
@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "email", name = "users_unique_email_idx")})
public class User extends AbstractNamedEntity{

    public static final String DELETE = "User.delete";
    public static final String BY_EMAIL = "User.getByEmail";
    public static final String ALL_SORTED = "User.getAllSorted";

    @Column(name = "email", nullable = false, unique = true)
    @Email
    @NotBlank
    @Size(max = 100)
    private String email;

    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @NotNull
    private List<Vote> votedUsers;

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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = CollectionUtils.isEmpty(roles) ? Collections.emptySet() : EnumSet.copyOf(roles);
    }

    public List<Vote> getVotedUsers() {
        return votedUsers;
    }
    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", roles=" + roles +
                ", name='" + name + '\'' +
                '}';
    }
}
