package com.skva.mathsplayforkids;

import android.util.Log;

import java.util.Random;

/**
 * Created by kartiknarayanan on 10/20/16.
 */


public class OperatorGame {

    final int TOTAL_GAMES = 5;
    private int score = 0;
    private int total_attempts = 0;
    private String question = "";
    private int game_type = 0;
    private int[] choiceArray = new int[TOTAL_GAMES];
    private int[] difficult_min = new int[TOTAL_GAMES];
    private int[] difficult_max = new int[TOTAL_GAMES];

    public OperatorGame(int score, int total_attempts) {
        this.score = score;
        this.total_attempts = total_attempts;

    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getGame_type() {
        return game_type;
    }

    public void setGame_type(int game_type) {
        if (game_type == 5) {
            this.game_type = 100;
        } else {
            this.game_type = game_type;
        }
    }

    public int getDifficult_min(int i) {
        return difficult_min[i];
    }

    public void setDifficult_min(int i, int difficult_min) {
        this.difficult_min[i] = difficult_min;
    }

    public int getDifficult_max(int i) {
        return difficult_max[i];
    }

    public void setDifficult_max(int i, int difficult_max) {
        this.difficult_max[i] = difficult_max;
    }

    public int getTotal_attempts() {
        return total_attempts;
    }

    public void setTotal_attempts(int total_attempts) {
        this.total_attempts = total_attempts;
    }

    public void incrementTotal_attempts() {
        ++this.total_attempts;
    }

    private void updateScore(int tmpScore) {
        score += tmpScore;
    }

    public int getChoiceArray(int i) {
        return choiceArray[i];
    }

    public void setChoiceArray(int i, int choiceArray) {
        this.choiceArray[i] = choiceArray;
    }


    public void generateQuestion() {
        Random r = new Random();
        Log.d("generateQuestion", "" + game_type);
        if (game_type != 100) {
            generateQuestionBasedOnGameType(game_type);
        } else

        {
            generateQuestionBasedOnGameType(r.nextInt(5));
        }
        incrementTotal_attempts();
    }


    public boolean validateAnswer(int a) {
        boolean success = false;
        incrementTotal_attempts();
        if (a == choiceArray[3]) {
            Log.d("validateAnswer", "validation success");
            success = true;
            updateScore(1);
            generateQuestion();
        } else {
            Log.d("validateAnswer", "validation failed");
            success = false;
        }
        return success;
    }


    private void generateQuestionBasedOnGameType(int tmpgame_type) {
        Random r = new Random();
        switch (tmpgame_type) {
            case 0: {
                int difficulty_max = difficult_max[tmpgame_type];
                int difficulty_min = difficult_min[tmpgame_type];
                int q = r.nextInt(difficulty_max - difficulty_min) + difficulty_min + 1;
                if (q == difficulty_max) {
                    q = q - 2;
                }
                Log.d("generateQuestion", "q : " + q);
                question = " >  " + q + " ? ";
                choiceArray[0] = r.nextInt(q - difficulty_min) + difficulty_min;
                choiceArray[1] = r.nextInt(q - difficulty_min) + difficulty_min;
                choiceArray[2] = r.nextInt(q - difficulty_min) + difficulty_min;
                choiceArray[3] = r.nextInt(difficulty_max - q) + q + 1;
                Log.d("generateQuestion", "a1: " + choiceArray[0] + " a2: " + choiceArray[1] + " a3: " + choiceArray[2] + " a4: " + choiceArray[3]);
                break;
            }
            case 1: {
                int difficulty_max = difficult_max[tmpgame_type];
                int difficulty_min = difficult_min[tmpgame_type];
                int q = r.nextInt(difficulty_max - difficulty_min) + difficulty_min + 1;
                if (q == difficulty_max) {
                    q = q - 2;
                }
                Log.d("generateQuestion", "q : " + q);
                question = " < " + q + " ? ";
                choiceArray[0] = r.nextInt(difficulty_max - q) + q + 1;
                choiceArray[1] = r.nextInt(difficulty_max - q) + q + 1;
                choiceArray[2] = r.nextInt(difficulty_max - q) + q + 1;
                choiceArray[3] = r.nextInt(q - difficulty_min) + difficulty_min;
                Log.d("generateQuestion", "a1: " + choiceArray[0] + " a2: " + choiceArray[1] + " a3: " + choiceArray[2] + " a4: " + choiceArray[3]);
                break;
            }
            case 2: {
                int difficulty_max = difficult_max[tmpgame_type];
                int difficulty_min = difficult_min[tmpgame_type];
                int q = r.nextInt(difficulty_max - difficulty_min) + difficulty_min + 1;
                if (q == difficulty_max) {
                    q = q - 2;
                }
                int p = q + 2;
                Log.d("generateQuestion", "p: " + p + " q : " + q);
                question = " " + q + " ___ " + p + " ?";
                choiceArray[0] = r.nextInt(p) + difficulty_min;
                choiceArray[1] = r.nextInt(q) + difficulty_min;
                choiceArray[2] = r.nextInt(q) + p;
                choiceArray[3] = q + 1;
                Log.d("generateQuestion", "a1: " + choiceArray[0] + " a2: " + choiceArray[1] + " a3: " + choiceArray[2] + " a4: " + choiceArray[3]);
                break;
            }
            case 3: {
                int difficulty_max = difficult_max[tmpgame_type];
                int difficulty_min = difficult_min[tmpgame_type];
                int mid = (difficulty_min + difficulty_max) / 2;
                int p = r.nextInt(mid - difficulty_min) + difficulty_min;
                int q = r.nextInt(difficulty_max - mid) + mid;
                Log.d("generateQuestion", "p: " + p + " q : " + q);
                question = p + " + " + q + " ?";
                choiceArray[0] = p + q + r.nextInt(difficulty_max - difficulty_min) + 1;
                choiceArray[1] = q - p + r.nextInt(difficulty_max) - 1;
                choiceArray[2] = (p + q) / 2;
                choiceArray[3] = p + q;
                Log.d("generateQuestion", "a1: " + choiceArray[0] + " a2: " + choiceArray[1] + " a3: " + choiceArray[2] + " a4: " + choiceArray[3]);
                break;
            }
            case 4: {
                int difficulty_max = difficult_max[tmpgame_type];
                int difficulty_min = difficult_min[tmpgame_type];
                int mid = (difficulty_min + difficulty_max) / 2;
                int p = r.nextInt(mid - difficulty_min) + difficulty_min;
                int q = r.nextInt(difficulty_max - mid) + mid;
                Log.d("generateQuestion", "p: " + p + " q : " + q);
                question = q + " - " + p + " ?";
                choiceArray[0] = q - p + r.nextInt(difficulty_max - difficulty_min) + 1;
                choiceArray[1] = p + q;
                choiceArray[2] = (q - p) / 2;
                choiceArray[3] = q - p;
                Log.d("generateQuestion", "a1: " + choiceArray[0] + " a2: " + choiceArray[1] + " a3: " + choiceArray[2] + " a4: " + choiceArray[3]);
                break;
            }
        }
    }


}