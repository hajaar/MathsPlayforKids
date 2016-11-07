package com.skva.mathsplayforkids;

import android.util.Log;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by kartiknarayanan on 10/20/16.
 */


public class OperatorGame {

    final int TOTAL_GAMES = 11;
    private int TypeofGame = 0;
    private int score = 0;
    private int total_attempts = 0;
    private String question = "";
    private int game_type = 0;
    private int[] choiceArray = new int[4];
    private String[] choiceStringArray = new String[4];
    private int[] randomizedArray = new int[4];
    private int[] difficult_min = new int[TOTAL_GAMES];
    private int[] difficult_max = new int[TOTAL_GAMES];
    public OperatorGame(int score, int total_attempts) {
        this.score = score;
        this.total_attempts = total_attempts;


    }

    public int getRandomizedArray(int i) {
        return randomizedArray[i];
    }

    public void setTypeofGame(int typeofGame) {
        TypeofGame = typeofGame;
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
        switch (TypeofGame) {
            case 0: {
                if (game_type == 4) {
                    this.game_type = 100;
                } else {
                    this.game_type = game_type;
                }
                break;
            }
            case 1: {
                if (game_type == 3) {
                    this.game_type = 101;
                } else {
                    this.game_type = game_type + 4;
                }
                break;
            }
            case 2: {
                if (game_type == 4) {
                    this.game_type = 102;
                } else {
                    this.game_type = game_type + 7;
                }
                break;
            }
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
        switch (game_type) {
            case 100: {
                generateQuestionBasedOnGameType(r.nextInt(4));
                break;
            }
            case 101: {
                generateQuestionBasedOnGameType(r.nextInt(3) + 4);
                break;
            }
            case 102: {
                generateQuestionBasedOnGameType(r.nextInt(4) + 7);
                break;
            }
            default:
                generateQuestionBasedOnGameType(game_type);
        }
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
            case 3: {
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

            case 4: {
                int difficulty_max = difficult_max[tmpgame_type];
                int difficulty_min = difficult_min[tmpgame_type];
                int p = r.nextInt(5) + 1;
                int q = r.nextInt(difficulty_max / 2);
                Log.d("generateQuestion", "p: " + p + " q : " + q);
                question = " " + q + " " + (q + p) + " " + (q + p + p) + " ___ " + " ?";
                choiceArray[0] = q + p * 4;
                choiceArray[1] = q + 2 * p - 1;
                choiceArray[2] = q * 2;
                choiceArray[3] = q + p * 3;
                Log.d("generateQuestion", "a1: " + choiceArray[0] + " a2: " + choiceArray[1] + " a3: " + choiceArray[2] + " a4: " + choiceArray[3]);
                break;
            }
            case 5: {
                int difficulty_max = difficult_max[tmpgame_type];
                int difficulty_min = difficult_min[tmpgame_type];
                int p = r.nextInt(5) + 1;
                int q = r.nextInt(difficulty_max / 2) + 5 * p;
                Log.d("generateQuestion", "p: " + p + " q : " + q);
                question = " " + q + " " + (q - p) + " " + (q - p - p) + " ___ " + " ?";
                choiceArray[0] = q - p * 4;
                choiceArray[1] = q - 2 * p - 1;
                choiceArray[2] = p * 2;
                choiceArray[3] = q - p * 3;
                Log.d("generateQuestion", "a1: " + choiceArray[0] + " a2: " + choiceArray[1] + " a3: " + choiceArray[2] + " a4: " + choiceArray[3]);
                break;
            }
            case 6: {
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
            case 7: {
                int difficulty_max = difficult_max[tmpgame_type];
                int difficulty_min = difficult_min[tmpgame_type];
                int[] tmpArray = new int[4];
                for (int i = 0; i < 4; i++) {
                    tmpArray[i] = r.nextInt(difficulty_max - difficulty_min + 1) + difficulty_min;
                }
                Arrays.sort(tmpArray);
                choiceStringArray[3] = "";
                for (int i = 0; i < 4; i++) {
                    choiceStringArray[3] += tmpArray[i] + " ";
                }
                for (int i = 0; i < 3; i++) {
                    choiceStringArray[i] = getRandomizedStringArray(tmpArray);
                }
                Log.d("generateQuestion", "a1: " + choiceStringArray[0] + " a2: " + choiceStringArray[1] + " a3: " + choiceStringArray[2] + " a4: " + choiceStringArray[3]);
                break;
            }
            case 8: {
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
            case 9: {
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
            case 10: {
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
        }
        setRandomizedArray();
    }

    private void setRandomizedArray() {
        for (int i = 0; i <= 3; i++) {
            int j = (int) Math.floor(Math.random() * (i + 1));
            if (j != i) {
                randomizedArray[i] = randomizedArray[j];
            }
            randomizedArray[j] = getChoiceArray(i);
        }
    }

    private String getRandomizedStringArray(int[] tmpArray) {
        int[] tmparr = new int[4];
        for (int i = 0; i <= 3; i++) {
            int j = (int) Math.floor(Math.random() * (i + 1));
            if (j != i) {
                tmparr[i] = tmparr[j];
            }
            tmparr[j] = tmpArray[i];
        }
        String returnstring = "";
        for (int i = 0; i < 4; i++) {
            returnstring += tmparr[i] + " ";
        }
        return returnstring;
    }

}
