package hr.fer.zr.backend.domain;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@jakarta.persistence.Table(name = "plan_features", schema = "public", catalog = "postgres")
@IdClass(hr.fer.zr.backend.domain.PlanFeaturesEntityPK.class)
public class PlanFeaturesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @jakarta.persistence.Column(name = "plan_id", nullable = false)
    private int planId;

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @jakarta.persistence.Column(name = "feature_id", nullable = false)
    private int featureId;

    public int getFeatureId() {
        return featureId;
    }

    public void setFeatureId(int featureId) {
        this.featureId = featureId;
    }

    @Basic
    @Column(name = "included", nullable = false)
    private boolean included;

    public boolean isIncluded() {
        return included;
    }

    public void setIncluded(boolean included) {
        this.included = included;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlanFeaturesEntity that = (PlanFeaturesEntity) o;
        return planId == that.planId && featureId == that.featureId && included == that.included;
    }

    @Override
    public int hashCode() {
        return Objects.hash(planId, featureId, included);
    }
}
