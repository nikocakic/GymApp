package hr.fer.zr.backed.domain;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "session_attendants", schema = "public", catalog = "postgres")
@IdClass(SessionAttendantsEntityPK.class)
public class SessionAttendantsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "session_id", nullable = false)
    private int sessionId;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "user_id", nullable = false)
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
        SessionAttendantsEntity that = (SessionAttendantsEntity) o;
        return sessionId == that.sessionId && userId == that.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId, userId);
    }
}
