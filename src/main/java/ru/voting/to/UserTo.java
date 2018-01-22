package ru.voting.to;

import ru.voting.HasId;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class UserTo implements Serializable, HasId {
    private static final long serialVersionUID = 1L;

    protected Integer id;

    @Override
    public Integer getId() {
        return id;
    }

    @NotBlank
    @Size(min = 2, max = 100, message = "length must between 2 and 100 characters")
    private String name;

    @Email
    @NotBlank
    @Size(max = 100, message = "max length must be 100 characters")
    private String email;

    @Size(min = 5, max = 32, message = "length must between 5 and 32 characters")
    private String password;

    public UserTo() {
    }

    public UserTo(Integer id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    @Override
    public String toString() {
        return "UserTo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
