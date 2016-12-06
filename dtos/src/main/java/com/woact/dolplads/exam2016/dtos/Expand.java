package com.woact.dolplads.exam2016.dtos;

/*
        As there is only a fixed number of values for "expand",
        I am going to use an enumeration.
     */
public enum Expand {
    ALL,
    NONE,
    COMMENTS,
    VOTES;

    public boolean isWithComments() {
        return this.equals(COMMENTS) || this.equals(ALL);
    }

    public boolean isWithVotes() {
        return this.equals(VOTES) || this.equals(ALL);
    }
}