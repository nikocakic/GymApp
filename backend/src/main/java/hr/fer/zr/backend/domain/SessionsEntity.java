package hr.fer.zr.backend.domain;

import jakarta.persistence.*;
import org.springframework.cglib.core.Local;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "sessions", schema = "public", catalog = "postgres")
public class SessionsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "dayInWeek", nullable = false, length = 20)
    private String dayInWeek;
    @Basic
    @Column(name = "timeslot", nullable = false, length = 20)
    private LocalTime timeSlot; 
    @Basic
    @Column(name = "title", nullable = false, length = 100)
    private String title;
    @Basic
    @Column(name = "description", nullable = true, length = 100)
    private String description;
    @Basic
    @Column(name = "max_attendants", nullable = false)
    private Integer maxAttendants;
    @Basic
    @Column(name = "current_attendants", nullable = true)
    private Integer currentAttendants;

    @Basic
    @Column(name = "date", nullable = false)
    private Date date;

    public SessionsEntity(String dayInWeek, LocalTime timeSlot, String title, String description, Integer maxAttendants, Integer currentAttendants, Date date) {
        this.dayInWeek = dayInWeek;
        this.timeSlot = timeSlot;
        this.title = title;
        this.description = description;
        this.maxAttendants = maxAttendants;
        this.currentAttendants = currentAttendants;
        this.date = date;
    }

    public SessionsEntity() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDayInWeek() {
        return dayInWeek;
    }

    public void setDayInWeek(String dayInWeek) {
        this.dayInWeek = dayInWeek;
    }

    public LocalTime getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(LocalTime timeSlot) {
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

    public Integer getMaxAttendants() {
        return maxAttendants;
    }

    public void setMaxAttendants(Integer maxAttendants) {
        this.maxAttendants = maxAttendants;
    }

    public Integer getCurrentAttendants() {
        return currentAttendants;
    }

    public void setCurrentAttendants(Integer currentAttendants) {
        this.currentAttendants = currentAttendants;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessionsEntity that = (SessionsEntity) o;
        return id == that.id && maxAttendants == that.maxAttendants && Objects.equals(dayInWeek, that.dayInWeek) && Objects.equals(timeSlot, that.timeSlot) && Objects.equals(title, that.title) && Objects.equals(description, that.description) && Objects.equals(currentAttendants, that.currentAttendants) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dayInWeek, timeSlot, title, description, maxAttendants, currentAttendants, date);
    }

    @Override
    public String toString() {
        return "SessionsEntity{" +
                "id=" + id +
                ", dayInWeek='" + dayInWeek + '\'' +
                ", timeSlot=" + timeSlot +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", maxAttendants=" + maxAttendants +
                ", currentAttendants=" + currentAttendants +
                ", date=" + date +
                '}';
    }
}
