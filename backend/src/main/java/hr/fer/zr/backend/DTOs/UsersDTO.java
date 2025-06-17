package hr.fer.zr.backend.DTOs;

import hr.fer.zr.backend.domain.UsersEntity;
import hr.fer.zr.backend.rest.UserAccountRole;

import java.sql.Date;

public class UsersDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Date birthdate;
    private UserAccountRole role;
    private String username;
    private String password;
    private boolean enabled;

    public UsersDTO() {
    }

    public UsersDTO(Long id, String firstName, String lastName, String email, Date birthdate, UserAccountRole role, String username, String password, boolean enabled) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthdate = birthdate;
        this.role = role;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public UserAccountRole getRole() {
        return role;
    }

    public void setRole(UserAccountRole role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "UsersDTO{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", birthdate=" + birthdate +
                ", role=" + role +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                '}';
    }

    public static UsersDTO fromEntity(UsersEntity user) {
        return new UsersDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getBirthdate(),
                user.getRole(),
                user.getUsername(),
                user.getPassword(),
                user.isEnabled()
        );
    }
}
