package hr.fer.zr.backend.DTOs;

import hr.fer.zr.backend.domain.SessionsEntity;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;

public class SessionDTO {

    private int id;
    private String dayInWeek;
    private LocalTime timeSlot;
    private String title;
    private String description;
    private int maxAttendants;
    private Integer currentAttendants;
    private Date date;

    public SessionDTO() {
    }

    public SessionDTO(int id, String dayInWeek, LocalTime timeSlot, String title, String description, int maxAttendants, Integer currentAttendants, Date date) {
        this.id = id;
        this.dayInWeek = dayInWeek;
        this.timeSlot = timeSlot;
        this.title = title;
        this.description = description;
        this.maxAttendants = maxAttendants;
        this.currentAttendants = currentAttendants;
        this.date = date;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "SessionDTO{" +
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

    public static SessionDTO fromEntity(SessionsEntity session) {
        return new SessionDTO(
                session.getId(),
                session.getDayInWeek(),
                session.getTimeSlot(),
                session.getTitle(),
                session.getDescription(),
                session.getMaxAttendants(),
                session.getCurrentAttendants(),
                session.getDate()
        );
    }
}