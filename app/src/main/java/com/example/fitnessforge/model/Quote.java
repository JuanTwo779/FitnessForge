package com.example.fitnessforge.model;

import com.google.gson.annotations.SerializedName;

public class Quote {
    @SerializedName("quote")
    private String quote;

    @SerializedName("author")
    private String author;
    @SerializedName("category")
    private String category;

    public Quote(){}
    public Quote(String quote) {
        this.quote = quote;
    }


    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }
}
