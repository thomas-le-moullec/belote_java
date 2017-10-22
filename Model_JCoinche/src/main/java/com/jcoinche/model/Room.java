package com.jcoinche.model;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private int id;
    private List<Player> players;
    private int idTurn;
    private Board board;
    private int assetTaker;

    public Room() {
    }

    public Room(int id, List<Player> players, Board board, int assetTaker) {
        this.id = id;
        this.players = players;
        this.board = board;
        this.assetTaker = assetTaker;
    }

    public Player createPlayer(String id) {
        Player player = new Player();

        player.setId(id);
        player.setScore(0);
        System.out.println("PLAYER ID:"+player.getId());
        return (player);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public int getAssetTaker() {
        return assetTaker;
    }

    public void setAssetTaker(int assetTaker) {
        this.assetTaker = assetTaker;
    }
}
