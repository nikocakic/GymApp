package hr.fer.zr.backend.dao;

import hr.fer.zr.backend.domain.GymAttendantsEntity;
import hr.fer.zr.backend.domain.SessionAttendantsEntity;
import hr.fer.zr.backend.domain.SessionAttendantsEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionAttendantRepository extends JpaRepository<SessionAttendantsEntity, SessionAttendantsEntityPK> {

    @Query("SELECT sa FROM SessionAttendantsEntity sa WHERE sa.sessionId = :sessionId AND sa.userId = :userId")
    Optional<SessionAttendantsEntity> findSessionAttendant(@Param("sessionId") Long sessionId, @Param("userId") Long userId);

    @Query("SELECT sa FROM SessionAttendantsEntity sa WHERE sa.userId = :userId")
    List<SessionAttendantsEntity> getSessionAttendantsForUserId (@Param("userId") Long userId);
}