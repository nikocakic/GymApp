package hr.fer.zr.backed.domain;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "plan_features", schema = "public", catalog = "postgres")
@IdClass(PlanFeaturesEntityPK.class)
public class PlanFeaturesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "plan_id", nullable = false)
    private int planId;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "feature_id", nullable = false)
    private int featureId;
    @Basic
    @Column(name = "included", nullable = false)
    private boolean included;

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public int getFeatureId() {
        return featureId;
    }

    public void setFeatureId(int featureId) {
        this.featureId = featureId;
    }

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
