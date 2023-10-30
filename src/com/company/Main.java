package com.company;

import java.util.Scanner;

public class Main {



    public static void main(String[] args) {
	    Scanner sc = new Scanner(System.in);

        // Get player name
        System.out.print("Please enter your name: ");
        String playerName = sc.nextLine();

        // Create a game instance
        BuncoGame buncoGame = new BuncoGame(playerName);

        buncoGame.createBuncoGame();

        sc.close();
    }
}
