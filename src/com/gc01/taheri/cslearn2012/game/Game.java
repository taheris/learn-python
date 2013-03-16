package com.gc01.taheri.cslearn2012.game;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import com.gc01.taheri.cslearn2012.exercises.Difficulty;
import com.gc01.taheri.cslearn2012.exercises.Exercise;
import com.gc01.taheri.cslearn2012.exercises.Mark;
import com.gc01.taheri.cslearn2012.exercises.Material;
import com.gc01.taheri.cslearn2012.helpers.LowercaseEnumTypeAdapterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Game extends Player {
    private String stateFile;
    private final Gson gson;
    private final ArrayList<Material> materials = new ArrayList<Material>();
    private final ArrayList<Exercise> exercises = new ArrayList<Exercise>();
    private ArrayList<Integer> workable = new ArrayList<Integer>();
    private Difficulty difficulty;
    
    /** start a new game */
    public Game(String name, Difficulty difficulty) throws Exception {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapterFactory(new LowercaseEnumTypeAdapterFactory());
        gson = builder.setPrettyPrinting().create();
        
        // order important: fetch exercises for name, then set difficulty to refresh
        setName(name);
        fetchMaterials();
        fetchExercises();
        setDifficulty(difficulty);
    }
    
    public ArrayList<Material> getMaterials() {
        return materials;
    }
    
    public ArrayList<Exercise> getExercises() {
        return exercises;
    }
    
    public Difficulty getDifficulty() {
        return difficulty;
    }
    
    public void setDifficulty(Difficulty difficulty) {
        if (this.difficulty != difficulty) {
            this.difficulty = difficulty;
            refreshWorkable(true);
        }
    }
    
    public void addExercise(Exercise exercise) {
        exercises.add(exercise);
    }
    
    /** loop through exercises and remove where the getQuestion() methods match */
    public void removeExercise(Exercise exercise) {
        for (Exercise e : exercises)
            if (exercise.getQuestion().equals(e.getQuestion()))
                exercises.remove(e);
    }
    
    /** loop through materials and return the text where difficulty matches */
    public String getMaterialText() {
        for (Material m : materials)
            if (m.getDifficulty() == difficulty)
                return getMaterialText(materials.indexOf(m));
        
        return null;
    }
    
    public String getMaterialText(int index) {
        return materials.get(index).getText();
    }
    
    public Exercise getExercise() {
        return getExercise(workable.get(0));
    }
    
    public Exercise getExercise(int index) {
        return exercises.get(index);
    }
    
    /** shift the workable list forward one
     *  e.g. [1,2,3] becomes [2,3,1] */
    public void nextExercise() {
        Collections.rotate(workable, -1);
    }
    
    /** shift the workable list backwards one
     *  e.g. [1,2,3] becomes [3,1,2] */
    public void previousExercise() {
        Collections.rotate(workable, 1);
    }
    
    /** return percentage of correct marks for given difficulty */
    public float getScore(Difficulty difficulty) {
        float total = 0;
        float completed = 0;
        
        for (Exercise e : exercises)
            if (e.getDifficulty() == difficulty) {
                total++;
                if (e.getMark() == Mark.CORRECT)
                    completed++;
            }
        
        return completed / total;
    }
    
    /** save the state of the game for later reuse */
    public void endGame() throws Exception {
        workable = null;
        saveState();
    }
    
    /** resets the workable list, optionally shuffling their order */
    private void refreshWorkable(boolean shuffle) {
        workable.clear();
        
        for (Exercise e : exercises)
            if (e.getDifficulty() == difficulty)
                workable.add(exercises.indexOf(e));
        
        if (shuffle)
            Collections.shuffle(workable);
    }
    
    /** retrieves the learning materials */
    private void fetchMaterials() throws Exception {
        addMaterial(Difficulty.EASY, "data/easy.txt");
        addMaterial(Difficulty.MEDIUM, "data/medium.txt");
        addMaterial(Difficulty.HARD, "data/hard.txt");
    }
    
    /** reads the contents of a text file and adds to the materials list */
    private void addMaterial(Difficulty difficulty, String file) throws Exception {
        String text = new String(Files.readAllBytes((new File(file)).toPath()));
        
        materials.add(new Material(difficulty, text));
    }
    
    /** use default exercises if guest login, otherwise use a user specific file */
    private void fetchExercises() throws Exception {
        if (getName().equals(""))
            addExercises("data/exercises.json");
        else {
            stateFile = "data/" + getName() + ".json";
            
            File file = new File(stateFile);
            if (!file.exists())
                Files.copy(Paths.get("data/exercises.json"), Paths.get(stateFile));
            
            addExercises(stateFile);
        }
    }
    
    /** use GSON to convert JSON file to Exercise, then add to exercises list */
    private void addExercises(String file) throws Exception {
        Collections.addAll(exercises, gson.fromJson(new FileReader(file), Exercise[].class));
    }
    
    /** if not a guest login, then save exercise state to the user file */
    private void saveState() throws Exception {
        if (!getName().equals("")) {
            FileWriter fw = new FileWriter(stateFile);
            
            fw.write(gson.toJson(exercises));
            fw.close();
        }
    }
}
