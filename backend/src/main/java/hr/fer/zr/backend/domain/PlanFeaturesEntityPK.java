package hr.fer.zr.backend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.util.Objects;

public class PlanFeaturesEntityPK implements Serializable {
    @Column(name = "plan_id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int planId;
    @Column(name = "feature_id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int featureId;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlanFeaturesEntityPK that = (PlanFeaturesEntityPK) o;
        return planId == that.planId && featureId == that.featureId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(planId, featureId);
    }
}