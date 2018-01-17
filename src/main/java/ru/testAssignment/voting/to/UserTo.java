package ru.testAssignment.voting.to;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class UserTo extends BaseTo implements Serializable{
    private static final long serialVersionUID = 1L;

    @NotBlank
    @Size(min = 2, max = 100, message = "length must between 2 and 100 characters")
    private String name;

    @Email
    @NotBlank
    @Size(max = 100, message = "max length must be 100 characters")
    private String email;

    @Size(min = 5, max = 64, message = "length must between 5 and 32 characters")
    private String password;

    public UserTo() {
    }

    public UserTo(Integer id, String name, String email, String password) {
        super(id);
        this.name = name;
        this.email = email;
        this.password = password;
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
