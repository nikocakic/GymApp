package hr.fer.zr.backend.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "trainer")
public class TrainerEntity {

    @Id
    private Long id; // This will share the ID with UsersEntity

    @OneToOne
    @MapsId // Maps the ID of TrainerEntity to the ID of UsersEntity
    @JoinColumn(name = "id", referencedColumnName = "id", nullable = false)
    private UsersEntity user;

    @Column(name = "rating", precision = 3, scale = 2, nullable = false)
    private BigDecimal rating;

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

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }
}
