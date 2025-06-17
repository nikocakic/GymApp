package hr.fer.zr.backend.dao;

import hr.fer.zr.backend.domain.TrainerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface TrainerRepository extends JpaRepository<TrainerEntity,Long> {
    // Optional<TrainerEntity> findByName(String Name);
}