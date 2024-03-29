package lk.ijse.humanResourceManagement.entity;

import java.time.LocalDate;

public class Review {
    private String reviewId;
    private String empId;
    private String comments;
    private String rating;
    private LocalDate date;

    public Review() {
    }

    public Review(String reviewId, String empId, String comments, String rating, LocalDate date) {
        this.reviewId = reviewId;
        this.empId = empId;
        this.comments = comments;
        this.rating = rating;
        this.date = date;
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
