package com.company;

import java.util.Arrays;
import java.util.Scanner;

public class Player {
    private String playerName;
    private DiceCup diceCup;
    private Dice dice;
    private int totalBuncos;
    private int roundsWon;
    private int totalScore;
    private int score;
    private int[] diceRolled;
    private Scanner sc = new Scanner(System.in);

    // Constructor methods
    public Player()
    {
        this.diceCup = new DiceCup();
        this.dice = new Dice();
        this.score = 0;
        this.playerName = "Computer";
        this.totalBuncos = 0;
        this.roundsWon = 0;
        this.totalScore = 0;
    }

    public Player(String playerName)
    {
        this.diceCup = new DiceCup();
        this.dice = new Dice();
        this.score = 0;
        this.playerName = playerName;
        this.totalBuncos = 0;
        this.roundsWon = 0;
        this.totalScore = 0;
    }

    // Main methods

    public int[] playRound()
    {
        /*
            This method is responsible for letting each player play the game when it is their turn.
            If the player name is not labeled as "Computer" then it will not require additional
            key press to roll each dice otherwise it will ask the user to press the RETURN button.

            This method will int array that contains the numbers rolled for each dice.
         */

        // Split the string on space character, and  determine if there is a
        // "Computer" that can be found
        String name = playerName.split(" ")[0];

        if(name != "Computer")
        {
            System.out.println("Press RETURN to roll your die");
            sc.nextLine();
        }
        int[] rolledNumbers = this.diceCup.rollDie();
        System.out.println("Player " + getPlayerName() + " has rolled " + Arrays.toString(rolledNumbers) + "\n");

        this.diceRolled = rolledNumbers;

        return rolledNumbers;
    }

    // Setters method
    public void setScore(int score)
    {
        this.score = score;
    }

    public void setRoundsWon(int roundsWon) {
        this.roundsWon = roundsWon;
    }

    public void setTotalBuncos(int totalBuncos)
    {
        this.totalBuncos = totalBuncos;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    // Getters method
    public int[] getDiceRolled() {
        return this.diceRolled;
    }
    public String getPlayerName() {
        return playerName;
    }
    public int getScore()
    {
        return this.score;
    }
    public Dice getDice() {
        return this.dice;
    }
    public int getTotalScore(){return this.totalScore;}
    public int getRoundsWon(){return this.roundsWon;}
    public int getTotalBuncos(){return  this.totalBuncos;}
}
