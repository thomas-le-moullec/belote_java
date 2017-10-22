package com.jcoinche.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void setIdTurn(int idTurn) {
        this.idTurn = idTurn;
    }

    public int getIdTurn() {
        return idTurn;
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

    public Player getPlayer(String idPlayer) {
        int id = 0;
        for (int i = 0; i < this.players.size(); i++) {
            if (idPlayer == this.players.get(i).getId()) {
                id = i;
                break;
            }
        }
        return this.players.get(id);
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
