package hr.fer.zr.backed.domain;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "sessions", schema = "public", catalog = "postgres")
public class SessionsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "day", nullable = false, length = 20)
    private String day;
    @Basic
    @Column(name = "time_slot", nullable = false, length = 20)
    private String timeSlot;
    @Basic
    @Column(name = "title", nullable = false, length = 100)
    private String title;
    @Basic
    @Column(name = "description", nullable = true, length = -1)
    private String description;
    @Basic
    @Column(name = "max_attendants", nullable = false)
    private int maxAttendants;
    @Basic
    @Column(name = "current_attendants", nullable = true)
    private Integer currentAttendants;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMaxAttendants() {
        return maxAttendants;
    }

    public void setMaxAttendants(int maxAttendants) {
        this.maxAttendants = maxAttendants;
    }

    public Integer getCurrentAttendants() {
        return currentAttendants;
    }

    public void setCurrentAttendants(Integer currentAttendants) {
        this.currentAttendants = currentAttendants;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessionsEntity that = (SessionsEntity) o;
        return id == that.id && maxAttendants == that.maxAttendants && Objects.equals(day, that.day) && Objects.equals(timeSlot, that.timeSlot) && Objects.equals(title, that.title) && Objects.equals(description, that.description) && Objects.equals(currentAttendants, that.currentAttendants);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, day, timeSlot, title, description, maxAttendants, currentAttendants);
    }
}
