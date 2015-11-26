package com.minimize;

public class Jokes {

    public String[] getAllJokes() {
        return jokes;
    }

    //Array of jokes
    String[] jokes = {"Joke 1","Joke 2","Joke 3"};

    int index = 0;

    /***
     * Method for retrieving jokes
     * @return Joke-String
     */
    public String getNextJoke(){
        if(index==jokes.length)
            index = 0;

        return jokes[index++];
    }
}
