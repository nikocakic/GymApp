package hr.fer.zr.backend.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "session_attendants")
@IdClass(SessionAttendantsEntityPK.class)
public class SessionAttendantsEntity implements Serializable {

    @Id
    @Column(name = "session_id", nullable = false)
    private Long sessionId;

    @Id
    @Column(name = "user_id", nullable = false)
    private Long userId;

    public SessionAttendantsEntity() {
    }

    public SessionAttendantsEntity(Long sessionId, Long userId) {
        this.sessionId = sessionId;
        this.userId = userId;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessionAttendantsEntity that = (SessionAttendantsEntity) o;
        return Objects.equals(sessionId, that.sessionId) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId, userId);
    }

    @Override
    public String toString() {
        return "SessionAttendantsEntity{" +
                "sessionId=" + sessionId +
                ", userId=" + userId +
                '}';
    }
}
