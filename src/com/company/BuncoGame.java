package com.company;

public class BuncoGame {
    private Player[] player;

    public BuncoGame(String playerName)
    {

        this.player = new Player[2];
        this.player[0] = new Player();
        this.player[1] = new Player(playerName);
    }

    public void createBuncoGame()
    {

    }
}
