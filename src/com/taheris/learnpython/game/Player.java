package com.taheris.learnpython.game;

public class Player {
    private String name;

    public Player() {
        this(null);
    }

    public Player(String name) {
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
