package lk.ijse.humanResourceManagement.dto.tm;

import com.jfoenix.controls.JFXButton;

import java.time.LocalDate;

public class ReviewTm {
    private String reviewId;
    private String empId;
    private String comments;
    private String rating;
    private LocalDate date;

    private JFXButton updateButton;
    private JFXButton deleteButton;

    public ReviewTm() {
    }

    public ReviewTm(String reviewId, String empId, String comments, String rating, LocalDate date, JFXButton updateButton, JFXButton deleteButton) {
        this.reviewId = reviewId;
        this.empId = empId;
        this.comments = comments;
        this.rating = rating;
        this.date = date;
        this.updateButton = updateButton;
        this.deleteButton = deleteButton;
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

    public JFXButton getUpdateButton() {
        return updateButton;
    }

    public void setUpdateButton(JFXButton updateButton) {
        this.updateButton = updateButton;
    }

    public JFXButton getDeleteButton() {
        return deleteButton;
    }

    public void setDeleteButton(JFXButton deleteButton) {
        this.deleteButton = deleteButton;
    }

    @Override
    public String toString() {
        return "ReviewTm{" +
                "reviewId='" + reviewId + '\'' +
                ", empId='" + empId + '\'' +
                ", comments='" + comments + '\'' +
                ", rating='" + rating + '\'' +
                ", date=" + date +
                ", updateButton=" + updateButton +
                ", deleteButton=" + deleteButton +
                '}';
    }
}
