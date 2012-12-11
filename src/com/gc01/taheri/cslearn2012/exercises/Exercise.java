package com.gc01.taheri.cslearn2012.exercises;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import com.gc01.taheri.cslearn2012.helpers.StringMethods;
import com.google.gson.annotations.SerializedName;

public class Exercise implements Markable {
    private String question;
    private QuestionType type;
    @SerializedName("question_code") private String[] questionCode;
    private String[] options;
    private String answer;
    private String[] answers;
    @SerializedName("answer_code") private String[] answerCode;
    @SerializedName("possible_answer") private String[] possibleAnswer;
    private String output;
    private String[] check;
    private Difficulty difficulty;
    private URL hint;
    private Mark mark;
    
    public Exercise() {
        mark = Mark.NOT_STARTED;
    }
    
    public String getQuestion() {
        return question;
    }
    
    public void setQuestion(String question) {
        this.question = question;
    }
    
    public QuestionType getQuestionType() {
        return type;
    }
    
    public void setQuestionType(QuestionType type) {
        this.type = type;
    }
    
    public String[] getQuestionCode() {
        return questionCode;
    }
    
    public void setQuestionCode(String[] questionCode) {
        this.questionCode = questionCode;
    }
    
    public String joinQuestionCode() {
        return StringMethods.join(questionCode);
    }
    
    public String[] getOptions() {
        return options;
    }
    
    public void setOptions(String[] options) {
        this.options = options;
    }
    
    public String getAnswer() {
        return answer;
    }
    
    public void setAnswer(String answer) {
        this.answer = answer;
    }
    
    public String[] getAnswers() {
        return answers;
    }
    
    public void setAnswers(String[] answers) {
        this.answers = answers;
    }
    
    public String[] getAnswerCode() {
        return answerCode;
    }
    
    public void setAnswerCode(String[] answerCode) {
        this.answerCode = answerCode;
    }
    
    public String joinAnswerCode() {
        return StringMethods.join(answerCode);
    }
    
    public String[] getPossibleAnswer() {
        return possibleAnswer;
    }
    
    public void setPossibleAnswer(String[] possibleAnswer) {
        this.possibleAnswer = possibleAnswer;
    }
    
    public String joinPossibleAnswer() {
        return StringMethods.join(possibleAnswer);
    }
    
    public String getOutput() {
        return output;
    }
    
    public void setOutput(String output) {
        this.output = output;
    }
    
    public String[] getCheck() {
        return check;
    }
    
    public void setCheck(String[] check) {
        this.check = check;
    }
    
    public Difficulty getDifficulty() {
        return difficulty;
    }
    
    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }
    
    public URL getHint() {
        return hint;
    }
    
    public void setHint(URL hint) {
        this.hint = hint;
    }
    
    @Override
    public Mark getMark() {
        return mark;
    }
    
    @Override
    public void setMark(Mark mark) {
        this.mark = mark;
    }
    
    /** check if the answer given matches the exercise answer,
     *  using StringMethods to converting to a standard format for comparison */
    public boolean isValidAnswer(String answer) {
        return StringMethods.standardFormat(answer)
                .equals(StringMethods.standardFormat(this.answer));
    }
    
    /** lists are equal if each list contains every element of the other */
    public boolean isValidAnswers(String[] answers) {
        List<String> list1 = Arrays.asList(answers);
        List<String> list2 = Arrays.asList(this.answers);
        
        return list1.containsAll(list2) && list2.containsAll(list1);
    }
    
    /** if there is one answer, use isValidAnswer, else use isValidAnswers */
    public boolean isValidAnswerOrAnswers(String[] answers) {
        if (this.answers != null)
            return isValidAnswers(answers);
        else if (answers.length != 1)
            return false;
        else
            return isValidAnswer(answers[0]);
    }
    
    /** convert answer code String[] to String and compare with the typed answer */
    public boolean isValidAnswerCode(String answerCode) {
        return StringMethods.standardFormat(answerCode)
                .equals(StringMethods.standardFormat(this.joinAnswerCode()));
    }
    
    /** check whether the executed output matches the expected output */
    public boolean isValidOutput(String output) {
        return StringMethods.standardFormat(output)
                .equals(StringMethods.standardFormat(this.output));
    }
    
    /** check whether the executed test returns True */
    public boolean isValidCheck(String result) {
        return result.equals("True");
    }
}
