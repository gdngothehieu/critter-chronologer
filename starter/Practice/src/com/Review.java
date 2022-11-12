package com;

public class Review {
    private String comment;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Review(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Review{" +
                "comment='" + comment + '\'' +
                '}';
    }
}
