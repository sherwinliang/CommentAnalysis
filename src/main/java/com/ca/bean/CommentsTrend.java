package com.ca.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class CommentsTrend implements Serializable {
    private Integer id;

    private String productId;

    private String year;

    private BigDecimal avgScore;

    private int saleVolumn;

    private Integer totalScore;

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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year == null ? null : year.trim();
    }

    public BigDecimal getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(BigDecimal avgScore) {
        this.avgScore = avgScore;
    }

    public int getSaleVolumn() {
        return saleVolumn;
    }

    public void setSaleVolumn(int saleVolumn) {
        this.saleVolumn = saleVolumn;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    @Override
    public String toString() {
        return "CommentsTrend{" +
                "id=" + id +
                ", productId='" + productId + '\'' +
                ", year='" + year + '\'' +
                ", avgScore=" + avgScore +
                ", saleVolumn=" + saleVolumn +
                ", totalScore=" + totalScore +
                '}';
    }
}