package hr.fer.zr.backed.domain;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.util.Objects;

public class SessionAttendantsEntityPK implements Serializable {
    @Column(name = "session_id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sessionId;
    @Column(name = "user_id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
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
