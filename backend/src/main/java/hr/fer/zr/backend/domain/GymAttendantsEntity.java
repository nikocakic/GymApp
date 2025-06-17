package hr.fer.zr.backend.domain;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "gym_attendants", schema = "public", catalog = "postgres")
public class GymAttendantsEntity {

    @Id
    private Long id; // This will share the ID with UsersEntity

    @OneToOne
    @MapsId // Maps the ID of GymAttendantsEntity to the ID of UsersEntity
    @JoinColumn(name = "id", referencedColumnName = "id", nullable = false)
    private UsersEntity user;

    @Column(name = "gym_plan_id")
    private Integer gymPlanId;

    @Column(name = "trainings_done")
    private Integer trainingsDone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UsersEntity getUser() {
        return user;
    }

    public void setUser(UsersEntity user) {
        this.user = user;
    }

    public Integer getGymPlanId() {
        return gymPlanId;
    }

    public void setGymPlanId(Integer gymPlanId) {
        this.gymPlanId = gymPlanId;
    }

    public Integer getTrainingsDone() {
        return trainingsDone;
    }

    public void setTrainingsDone(Integer trainingsDone) {
        this.trainingsDone = trainingsDone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GymAttendantsEntity that)) return false;
        return Objects.equals(gymPlanId, that.gymPlanId) &&
                Objects.equals(trainingsDone, that.trainingsDone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gymPlanId, trainingsDone);
    }
}
