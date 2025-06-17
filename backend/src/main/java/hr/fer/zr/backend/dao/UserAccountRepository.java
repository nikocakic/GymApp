package hr.fer.zr.backend.dao;

import hr.fer.zr.backend.domain.UsersEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UsersEntity, Long> {
	int countByUsername(String username);

	List<UsersEntity> findByEnabledFalse();
	Optional<UsersEntity> findByUsername(String username);

	UsersEntity getById(Long id);

	Optional<UsersEntity> findByEmail(String email);

	@Query("SELECT s FROM UsersEntity s WHERE s.role=1")
    List<UsersEntity> listAllTrainers();
}
