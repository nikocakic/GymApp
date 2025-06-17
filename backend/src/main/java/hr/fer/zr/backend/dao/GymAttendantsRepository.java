package hr.fer.zr.backend.dao;

import hr.fer.zr.backend.domain.GymAttendantsEntity;
import hr.fer.zr.backend.domain.SessionAttendantsEntity;
import hr.fer.zr.backend.domain.SessionAttendantsEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

@Repository
public interface GymAttendantsRepository extends JpaRepository<GymAttendantsEntity, Long> {

    @Modifying
    @Transactional
    @Query("INSERT INTO SessionAttendantsEntity(sessionId, userId) VALUES (:sessionId, :userId)")
    void addSessionAttendant(@Param("sessionId") Long sessionId, @Param("userId") Long userId);

    @Query("SELECT sa FROM SessionAttendantsEntity sa WHERE sa.sessionId = :sessionId AND sa.userId = :userId")
    Optional<SessionAttendantsEntity> findSessionAttendant(@Param("sessionId") Long sessionId, @Param("userId") Long userId);
}