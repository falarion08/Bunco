package com.company;

import java.util.Scanner;

public class Main {



    public static void main(String[] args) {
	    Scanner sc = new Scanner(System.in);

        // Get player name
        System.out.print("Please enter your name: ");
        String playerName = sc.nextLine();

        Player computer = new Player();
        Player player = new Player(playerName);


        sc.close();
    }
}
