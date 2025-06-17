package hr.fer.zr.backend.service;

import hr.fer.zr.backend.domain.SessionAttendantsEntity;
import hr.fer.zr.backend.domain.SessionsEntity;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface SessionService {
    List<SessionsEntity> listAll();
    Optional<SessionsEntity> findSessionById(Long id);
    Optional<SessionsEntity> findSessionByDate(Date date, LocalTime timeSlot); // Updated parameter name
    void saveSession(String dayInWeek, LocalTime timeSlot, String title, String description, Integer maxAttendants, Integer currentAttendants, Date date); // Updated parameter name
    //void saveSession(SessionsEntity sessionsEntity); // Updated parameter name
    void deleteSession(long id, Date date, LocalTime timeSlot); // Updated parameter name
    SessionsEntity fetch(Date date, LocalTime timeSlot); // Updated parameter name
    Optional<SessionsEntity> getSessionById(Long id);
    List<SessionsEntity> findSessionsByDateRange(Date startDate, Date endDate);
    void incrementNOAttendants(Long id);
    void saveSessionAttendant (Long idSes, Long idUser);
    Optional<SessionAttendantsEntity> getSessionAttendant (Long idSes, Long idUser);
    List<SessionAttendantsEntity> getSessionAttendantsForUserId (Long idUser);
}
