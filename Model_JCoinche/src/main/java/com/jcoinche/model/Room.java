package com.jcoinche.model;

import java.util.List;

public class Room {
    private int id;
    private List<Player> players;
    private int idTurn;
    private Board board;
    private int assetTaker;

    public Room() {
    }

    public Room(int id, List<Player> players, int idTurn, Board board, int assetTaker) {
        this.id = id;
        this.players = players;
        this.idTurn = idTurn;
        this.board = board;
        this.assetTaker = assetTaker;
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

    public int getIdTurn() {
        return idTurn;
    }

    public void setIdTurn(int idTurn) {
        this.idTurn = idTurn;
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
