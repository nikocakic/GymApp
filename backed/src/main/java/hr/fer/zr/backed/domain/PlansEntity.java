package hr.fer.zr.backed.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "plans", schema = "public", catalog = "postgres")
public class PlansEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    @Basic
    @Column(name = "description", nullable = true, length = -1)
    private String description;
    @Basic
    @Column(name = "monthly_price", nullable = false, precision = 2)
    private BigDecimal monthlyPrice;
    @Basic
    @Column(name = "annual_price", nullable = false, precision = 2)
    private BigDecimal annualPrice;
    @Basic
    @Column(name = "popular", nullable = true)
    private Boolean popular;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getMonthlyPrice() {
        return monthlyPrice;
    }

    public void setMonthlyPrice(BigDecimal monthlyPrice) {
        this.monthlyPrice = monthlyPrice;
    }

    public BigDecimal getAnnualPrice() {
        return annualPrice;
    }

    public void setAnnualPrice(BigDecimal annualPrice) {
        this.annualPrice = annualPrice;
    }

    public Boolean getPopular() {
        return popular;
    }

    public void setPopular(Boolean popular) {
        this.popular = popular;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlansEntity that = (PlansEntity) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(monthlyPrice, that.monthlyPrice) && Objects.equals(annualPrice, that.annualPrice) && Objects.equals(popular, that.popular);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, monthlyPrice, annualPrice, popular);
    }
}
