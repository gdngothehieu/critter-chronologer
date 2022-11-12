package com;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Book book = new Book("Harry Potter");
        System.out.println(book.getName());
        System.out.println(book.getReviews());
        //

        for(int i=12;i<=20;i++){
            if(book.getReviews()==null){
                book.setReviews(new LinkedList<>());
            }
            book.getReviews().add(new Review("Review"+i));
        }
        //book.getReviews().add(new Review("Review1"));
        System.out.println(book.getReviews());
        /*
        List<Review> reviews = new ArrayList<>();
                    reviews.add(new Review("Review2"));
            reviews.add(new Review("Review3"));
            reviews.add(new Review("Review4"));

        System.out.println("reviews.size():="+reviews.size());
        System.out.println("Pringint reviews using for each");
        for(Review r : reviews){
            System.out.println(r);
            System.out.println(r+" added");
        }

        System.out.println("\nPringint reviews using for normal");
        for(int i=0;i<reviews.size();i++){
            System.out.println(reviews.get(i));
        }
        */

    }
}
