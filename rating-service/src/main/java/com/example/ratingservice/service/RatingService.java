package com.example.ratingservice.service;

import com.example.ratingservice.entity.Rating;

import java.util.List;

public interface RatingService {

    //create
    Rating create(Rating rating);


    //get all ratings
    List<Rating> getRatings();

    //get all by UserId
    List<Rating> getRatingByUserId(String userId);

    //get all by hotel
    List<Rating> getRatingsByProductId(String productId);




}