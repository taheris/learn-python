package com.taheris.learnpython.exercises;

public class Material {
    private final Difficulty difficulty;
    private final String text;

    public Material(Difficulty difficulty, String text) {
        this.difficulty = difficulty;
        this.text = text;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public String getText() {
        return text;
    }
}
