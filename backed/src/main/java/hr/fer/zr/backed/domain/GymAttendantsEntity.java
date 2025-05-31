package hr.fer.zr.backed.domain;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "gym_attendants", schema = "public", catalog = "postgres")
public class GymAttendantsEntity {
    @Basic
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;
    @Basic
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;
    @Basic
    @Column(name = "email", nullable = false, length = 100)
    private String email;
    @Basic
    @Column(name = "password", nullable = false, length = 255)
    private String password;
    @Basic
    @Column(name = "birthdate", nullable = false)
    private Date birthdate;
    @Basic
    @Column(name = "role", nullable = true)
    private Object role;
    @Basic
    @Column(name = "gym_plan_id", nullable = true)
    private Integer gymPlanId;
    @Basic
    @Column(name = "trainings_done", nullable = true)
    private Integer trainingsDone;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public Object getRole() {
        return role;
    }

    public void setRole(Object role) {
        this.role = role;
    }

    public Integer getGymPlanId() {
        return gymPlanId;
    }

    public void setGymPlanId(Integer gymPlanId) {
        this.gymPlanId = gymPlanId;
    }

    public Integer getTrainingsDone() {
        return trainingsDone;
    }

    public void setTrainingsDone(Integer trainingsDone) {
        this.trainingsDone = trainingsDone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GymAttendantsEntity that = (GymAttendantsEntity) o;
        return id == that.id && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(email, that.email) && Objects.equals(password, that.password) && Objects.equals(birthdate, that.birthdate) && Objects.equals(role, that.role) && Objects.equals(gymPlanId, that.gymPlanId) && Objects.equals(trainingsDone, that.trainingsDone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, password, birthdate, role, gymPlanId, trainingsDone);
    }
}
