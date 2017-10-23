package com.jcoinche.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ScoreBoard {
    private List<Integer> scores;
    private int teamWin;

    public ScoreBoard() {

    }

    public ScoreBoard(int scoreTeam1, int scoreTeam2, int teamWin) {
        scores = new ArrayList<Integer>();
        scores.add(scoreTeam1);
        scores.add(scoreTeam2);
        this.teamWin = teamWin;
    }

    public void setScores(List<Integer> scores) {
        this.scores = scores;
    }

    public void setTeamWin(int teamWin) {
        this.teamWin = teamWin;
    }

    public int getTeamWin() {
        return teamWin;
    }

    public List<Integer> getScores() {
        return scores;
    }
}
