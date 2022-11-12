package com;

import java.util.List;

public class Book {

    private String name;
    private List<Review> reviews;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public Book(String name) {
        this.name = name;
    }
}
