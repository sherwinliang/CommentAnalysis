package com.ca.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class CommentsAnalysis implements Serializable {
    private Integer id;

    private String productId;

    private Integer saleVolumn;

    private Integer totalScore;

    private BigDecimal avgScore;

    private Integer maxScore;

    private Integer minScore;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    public Integer getSaleVolumn() {
        return saleVolumn;
    }

    public void setSaleVolumn(Integer saleVolumn) {
        this.saleVolumn = saleVolumn;
    }

    public BigDecimal getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(BigDecimal avgScore) {
        this.avgScore = avgScore;
    }

    public Integer getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(Integer maxScore) {
        this.maxScore = maxScore;
    }

    public Integer getMinScore() {
        return minScore;
    }

    public void setMinScore(Integer minScore) {
        this.minScore = minScore;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    @Override
    public String toString() {
        return "CommentsAnalysis{" +
                "id=" + id +
                ", productId='" + productId + '\'' +
                ", saleVolumn=" + saleVolumn +
                ", totalScore=" + totalScore +
                ", avgScore=" + avgScore +
                ", maxScore=" + maxScore +
                ", minScore=" + minScore +
                '}';
    }
}