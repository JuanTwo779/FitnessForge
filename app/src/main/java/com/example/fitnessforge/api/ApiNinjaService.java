package com.example.fitnessforge.api;

import com.example.fitnessforge.model.Exercise;
import com.example.fitnessforge.model.Quote;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ApiNinjaService {


    //Get exercises given muscle type
    @GET("v1/exercises")
    Call<List<Exercise>> getExercises(
        @Query("muscle") String muscle,
        @Header("X-Api-Key") String apiKey
    );

    //Get exercises given keyword for name
    @GET("v1/exercises")
    Call<List<Exercise>> getExercisesKeyword(
            @Query("name") String name,
            @Header("X-Api-Key") String apiKey
    );

    //Get exercises given keyword and muscle type
    @GET("v1/exercises")
    Call<List<Exercise>> getExercisesKeywordMuscle(
            @Query("name") String name,
            @Query("muscle") String muscle,
            @Header("X-Api-Key") String apiKey
    );

    //Get quotes given a category
    @GET("v1/quotes")
    Call<List<Quote>> getQuote(
        @Query("category") String category,
        @Header("X-Api-Key") String apiKey
    );

}
