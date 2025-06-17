package hr.fer.zr.backend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.util.Objects;

public class SessionAttendantsEntityPK implements Serializable {
    @Column(name = "session_id", nullable = false)
    @Id
    private Long sessionId;
    @Column(name = "user_id", nullable = false)
    @Id
    private Long userId;

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
        SessionAttendantsEntityPK that = (SessionAttendantsEntityPK) o;
        return sessionId == that.sessionId && userId == that.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId, userId);
    }
}