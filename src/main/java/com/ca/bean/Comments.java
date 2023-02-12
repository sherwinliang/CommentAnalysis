package com.ca.bean;

import java.io.Serializable;
import java.util.Date;

public class Comments implements Serializable {
    private Integer id;

    private String productId;

    private String userId;

    private String profileName;

    private Integer helpfulnessNumerator;

    private Integer helpfulnessDenominator;

    private Integer score;

    private Date time;

    private String summary;

    private String text;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName == null ? null : profileName.trim();
    }

    public Integer getHelpfulnessNumerator() {
        return helpfulnessNumerator;
    }

    public void setHelpfulnessNumerator(Integer helpfulnessNumerator) {
        this.helpfulnessNumerator = helpfulnessNumerator;
    }

    public Integer getHelpfulnessDenominator() {
        return helpfulnessDenominator;
    }

    public void setHelpfulnessDenominator(Integer helpfulnessDenominator) {
        this.helpfulnessDenominator = helpfulnessDenominator;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text == null ? null : text.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", productId=").append(productId);
        sb.append(", userId=").append(userId);
        sb.append(", profileName=").append(profileName);
        sb.append(", helpfulnessNumerator=").append(helpfulnessNumerator);
        sb.append(", helpfulnessDenominator=").append(helpfulnessDenominator);
        sb.append(", score=").append(score);
        sb.append(", time=").append(time);
        sb.append(", summary=").append(summary);
        sb.append(", text=").append(text);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}