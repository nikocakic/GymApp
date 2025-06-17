package hr.fer.zr.backend.dao;

import hr.fer.zr.backend.domain.SessionsEntity;
import hr.fer.zr.backend.domain.UsersEntity;
import hr.fer.zr.backend.rest.UserAccountRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<SessionsEntity, Long> {

    @Query("SELECT s FROM SessionsEntity s ")
    List<SessionsEntity> listAll();

    @Query("SELECT s FROM SessionsEntity s WHERE s.id = :id")
    Optional<SessionsEntity> findSessionById(@Param("id") Long id);

    @Query("SELECT s FROM SessionsEntity s WHERE s.date = :date AND s.timeSlot = :timeSlot")
    Optional<SessionsEntity> findSessionByDate(@Param("date") Date date, @Param("timeSlot") LocalTime timeSlot);

    @Modifying
    @Transactional
    @Query("INSERT INTO SessionsEntity(dayInWeek, timeSlot, title, description, maxAttendants, currentAttendants, date) " +
           "VALUES (:#{#session.dayInWeek}, :#{#session.timeSlot}, :#{#session.title}, :#{#session.description}, :#{#session.maxAttendants}, :#{#session.currentAttendants}, :#{#session.date})")
    void saveSession(@Param("session") SessionsEntity session);

    @Query("SELECT s FROM SessionsEntity s WHERE s.date = :date AND s.timeSlot = :timeSlot")
    SessionsEntity fetch(@Param("date") Date date, @Param("timeSlot") LocalTime timeSlot);

    @Modifying
    @Transactional
    @Query("DELETE FROM SessionsEntity s WHERE s.date = :date AND s.timeSlot = :timeSlot")
    void deleteSession(@Param("date") Date date, @Param("timeSlot") LocalTime timeSlot);

    @Query("SELECT s FROM SessionsEntity s WHERE s.date BETWEEN :startDate AND :endDate")
    List<SessionsEntity> findSessionsByDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Modifying
    @Transactional
    @Query("UPDATE SessionsEntity s SET s.currentAttendants = s.currentAttendants + 1 WHERE s.id = :id")
    void incrementCurrentAttendants(@Param("id") Long id);
}
