package com.company;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class BuncoGame {
    private Player[] player;
    private final int MAX_SCORE = 21;
    private final int MAX_ROUND = 6;
    private Scanner sc;
    private ArrayList<Player> turnQueue;
    private FileWriter fileWriter;
    private File gameLog;

    public BuncoGame(String playerName)
    {
        this.player = new Player[2];
        this.player[0] = new Player();
        this.player[1] = new Player(playerName);
        this.sc = new Scanner(System.in);

        // Create a text file and initialize FileWriter class to produce a text file of the game log
        // Comment out if the user does not wish to write into a file.
        createGameLog();

    }

    public void createGameLog()
    {
        /*
            This function create an instance of a File class and FileWriter class necessary to produce an output to
            a text file. It is the user's responsibility to input proper name format for a text file (i.e. fileName.txt)
         */
        String fileName;

        System.out.print("Enter file name: ");

        fileName = sc.nextLine();

        if (fileName.length() == 0)
            fileName = "a.txt";

        this.gameLog = new File(fileName);

        try
        {
            if(this.gameLog.createNewFile())
            {
                System.out.println("Created file name: " +fileName);

                try
                {
                    this.fileWriter = new FileWriter(fileName);

                }
                catch (IOException e)
                {
                    fileWriter.close();
                    e.printStackTrace();
                    System.exit(1);
                }
            }
            else
            {
                // Close the application on error
                System.out.println("File already exists");
                System.exit(1);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void writeToFile(String message)
    {
        /*
            This function writes to the text file and automatically inserts a newline in the text file for each message
            written to the text file. This method is created so the process of writing to a file does not have to be
            implemented over and over again.
         */

        // Only write to a file when there is a text file to write into
        if(this.gameLog != null)
        {
            try {
                this.fileWriter.write(message + "\n");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void createBuncoGame()
    {
        boolean gameLoop = true;
        // Start of game
        while (gameLoop) {
            this.turnQueue = new ArrayList<>();

            System.out.println("Pre-Game. Determine the order of players in which the game will be played\n");
            writeToFile("Pre-Game. Determine the order of players in which the game will be played\n");

            int firstPlayer = determineFirstPlayer();
            int round = 1;

            // Add the first player that will be playing
            turnQueue.add(player[firstPlayer]);

            // Add the player that will be playing the next
            if (firstPlayer == 0)
                turnQueue.add(player[1]);
            else
                turnQueue.add(player[0]);

            int currentPlayer = 0;

            while (round <= this.MAX_ROUND) {
                while (!isRoundOver()) {
            /*
                The current player keeps playing as long as they earn a point.
                If the player  do not earn a point after rolling the dice, the turn will be
                passed to the next player to play in the round.
             */
                    System.out.println("\nROUND " + round + "\n");
                    writeToFile("\nROUND " + round + "\n");

                    Player player = turnQueue.get(currentPlayer);

                    System.out.println("Player " + player.getPlayerName() + " turn.");
                    System.out.println("Player Stats: [Rounds Won: " + player.getRoundsWon() + ", Score: " + player.getScore() + "]");

                    writeToFile("\nPlayer " + player.getPlayerName() + " turn.");
                    writeToFile("\nPlayer Stats: [Rounds Won: " + player.getRoundsWon() + ", Score: " + player.getScore() + "]");

                    // Make the current player roll their die

                    if(!player.getPlayerName().equals("Computer"))
                        writeToFile("Press RETURN to roll your die");

                    player.playRound();

                    writeToFile("Player " + player.getPlayerName() + " has rolled " + Arrays.toString(player.getDiceRolled()) + "\n");
                    // Examine points earned from their roll
                    int score = examinePlayerMove(player, round);

                    System.out.println("Player " + player.getPlayerName() + " has earned " + score + " points \n");
                    writeToFile("Player " + player.getPlayerName() + " has earned " + score + " points \n");
                    if (score == 0) {
                        currentPlayer++;

                        // Wrap around back to index 0 to maintain proper player-turn indexing.
                        if (currentPlayer == turnQueue.size())
                            currentPlayer = 0;
                    } else
                        player.setScore(player.getScore() + score);
                }

                // Print final player stats after the round
                System.out.println("Round " + round + " final stats");
                writeToFile("Round " + round + " final stats");

                for (int i = 0; i < this.player.length; ++i) {
                    System.out.println("Player " + this.player[i].getPlayerName() +
                            " [Rounds Won: " + this.player[i].getRoundsWon() + ", Updated Total Score: " + this.player[i].getTotalScore()
                            + ", Total Buncos: " + this.player[i].getTotalBuncos() + ", final score " + this.player[i].getScore() + "]");

                    writeToFile("Player " + this.player[i].getPlayerName() +
                            " [Rounds Won: " + this.player[i].getRoundsWon() + ", Updated Total Score: " + this.player[i].getTotalScore()
                            + ", Total Buncos: " + this.player[i].getTotalBuncos() + ", final score " + this.player[i].getScore() + "]");
                }
                // Reset all player score
                for (int i = 0; i < this.player.length; ++i)
                    this.player[i].setScore(0);

                // Update round status
                ++round;
            }

            // Determine game winner by getting their player name
            String playerName = gameWinner();

            System.out.println("\nPlayer " + playerName + " is the winner of the game!");
            writeToFile("\nPlayer " + playerName + " is the winner of the game!");

            // Determine if the player wants to play again
            String playerChoice = "";
            do {
                System.out.print("\n Play Again?(Yes/No) : ");

                playerChoice = sc.nextLine();

            } while (!playerChoice.equalsIgnoreCase("Yes") && !playerChoice.equalsIgnoreCase("No"));

            writeToFile("\n Play Again?(Yes/No) : " + playerChoice);

            // An if-else statement that updates the game loop based on the user's choice
            gameLoop = playerChoice.equalsIgnoreCase("Yes") ? true : false;

            // Reset all player status if the player decided to play again.
            if (gameLoop) {
                for (int i = 0; i < this.player.length; ++i) {
                    playerName = this.player[i].getPlayerName();
                    this.player[i] = new Player(playerName);
                }
            }
        }
        // close file writer
        try
        {
            if(fileWriter != null)
                this.fileWriter.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private String gameWinner()
    {
        /*
            This method returns the winner of the game by returning the name of the player who won.
            Determining the winner of the game goes as follows:
            1) Most rounds won.
            2) There exist more than one player that have the same number of rounds won (tie)
                2.1) Determine the player who has the most number of Buncos earned.
            3) There exist more than one player that have the same number of rounds won and same number
            of buncos earned.
                3.1) Get the player with the most total number of points earned.

         */
        int maxRoundsWon = 0;
        ArrayList<Player> mostRoundsWon = new ArrayList<>();

        for(int i = 0; i < this.player.length; ++i)
            maxRoundsWon = Integer.max(maxRoundsWon,this.player[i].getRoundsWon());

        for(int i = 0; i < this.player.length; ++i)
            if(this.player[i].getRoundsWon() == maxRoundsWon)
                mostRoundsWon.add(this.player[i]);

        if(mostRoundsWon.size() > 1)
        {
            ArrayList<Player> mostBuncosWon = new ArrayList<>();
            int  maxBuncosWon = 0;
            for(int i = 0; i < mostRoundsWon.size(); ++i)
                maxBuncosWon = Integer.max(maxBuncosWon, mostRoundsWon.get(i).getTotalBuncos());
            for(int i = 0; i < mostRoundsWon.size(); ++i)
                if(mostRoundsWon.get(i).getTotalBuncos() == maxBuncosWon)
                    mostBuncosWon.add(mostRoundsWon.get(i));

            if(mostBuncosWon.size() > 1)
            {
                int maxTotalScore = 0;
                String winner = "";
                for(int i = 0; i < mostBuncosWon.size(); ++i)
                    maxTotalScore = Integer.max(maxTotalScore, mostBuncosWon.get(i).getTotalScore());
                for(int i = 0; i < mostBuncosWon.size(); ++i)
                {
                    if (maxTotalScore == mostBuncosWon.get(i).getTotalScore()) {
                        winner = mostBuncosWon.get(i).getPlayerName();
                        break;
                    }
                }

                return winner;
            }
            else
                return mostBuncosWon.get(0).getPlayerName();


        }
        else
            return mostRoundsWon.get(0).getPlayerName();
    }

    private boolean isRoundOver()
    {
        /*
            This method check each player if one of them has reached at least 21 points.
            This method returns a value of True if a player wins a round otherwise it returns false.
         */
        for(int i = 0; i < this.player.length; ++i)
        {
            // Update the stats of the player that won the round
            if (this.player[i].getScore() >= this.MAX_SCORE)
            {
                System.out.println("Player " + this.player[i].getPlayerName() + " wins the round\n");
                writeToFile("Player " + this.player[i].getPlayerName() + " wins the round\n");
                // Increase the total score earned by the winning player
                this.player[i].setTotalScore(this.player[i].getTotalScore() + this.player[i].getScore());
                this.player[i].setRoundsWon(this.player[i].getRoundsWon() + 1);
                return true;
            }
        }
        return false;
    }

    private int examinePlayerMove(Player player, int roundNumber)
    {
        /*
            This method examines the rolled dice of the current player. This checks if the
            player has rolled a Bunco, a mini-Bunco, at least one dice equal to the current
            round.

            This function returns the score earned of the current player.
         */
        int[] diceRolled = player.getDiceRolled();

        if(isABunco(player,roundNumber))
        {
            System.out.println("Player " + player.getPlayerName() + " says Bunco!\n");
            writeToFile("Player " + player.getPlayerName() + " says Bunco!\n");

            // Count increase the number of total buncos the player has reached
            player.setTotalBuncos(player.getTotalBuncos() + 1);
            return 21;
        }
        else if (isAMiniBunco(player))
        {
            System.out.println("Player " + player.getPlayerName() + " says mini-Bunco!\n");
            writeToFile("Player " + player.getPlayerName() + " says mini-Bunco!\n");
            return 5;
        }
        else
        {
            /*
                Set the score of the player based on the number of dice rolls that are equal
                to the current round number
             */
            int count = 0;
            for (int i = 0; i < diceRolled.length; ++i)
            {
                if(diceRolled[i] == roundNumber)
                    count++;
            }
            return count;
        }
    }

    private boolean isABunco(Player player, int roundNumber)
    {
        /*
            This method checks if all dice are equal to the current round.

            Returns True if the statement above is met otherwise it retuns a False.

         */
        int[] diceRolled = player.getDiceRolled();

        for(int i = 0; i < diceRolled.length; ++i)
        {
            if(diceRolled[i] != roundNumber)
                return false;
        }
        return true;
    }

    private boolean isAMiniBunco(Player player)
    {
        /*
            This method examines if all dices rolled by the player are equal.

            This returns true if the condition above is met, otherwise it returns a False
         */
        int[] diceRolled = player.getDiceRolled();

        int reference = diceRolled[0];

        for(int i = 1; i < diceRolled.length; ++i)
            if(diceRolled[i] != reference)
                return false;
        return true;
    }

    private int determineFirstPlayer()
    {
        Dice[] dice = new Dice[3];
        dice[0] = new Dice();
        dice[1] = new Dice();
        dice[2] = new Dice();
        /*
            Obtain a single roll of dice for each player to determine who will go first. If the player rolls a higher
            number than the computer then the player will go first, otherwise the computer will go first. If there are
            players that have the same dice number, this process will repeat again.


            This function returns the index of the first player that will player. 1 - if it is the player, 0 - if it's
            the computer.
         */
        System.out.println("Press RETURN to roll a dice");
        writeToFile("Press RETURN to roll a dice");

        sc.nextLine();
        int playerRollTotal = dice[0].roll() + dice[1].roll() + dice[2].roll();

        System.out.println("Player " +player[1].getPlayerName() + " has rolled a total of " + Arrays.toString(new int[] {dice[0].getDiceNumber(), dice[1].getDiceNumber(),dice[2].getDiceNumber()}) + " = " + playerRollTotal + "\n");
        writeToFile("Player " +player[1].getPlayerName() + " has rolled a total of " + Arrays.toString(new int[] {dice[0].getDiceNumber(), dice[1].getDiceNumber(),dice[2].getDiceNumber()}) + " = " + playerRollTotal + "\n");

        System.out.println("Player " + player[0].getPlayerName() + " is now rolling");
        writeToFile("Player " + player[0].getPlayerName() + " is now rolling");

        int computerRollTotal = dice[0].roll() + dice[1].roll() + dice[2].roll();
        System.out.println("Player " +player[0].getPlayerName() + " has rolled a total of "+ Arrays.toString(new int[] {dice[0].getDiceNumber(), dice[1].getDiceNumber(),dice[2].getDiceNumber()}) + " = " + computerRollTotal + "\n");
        writeToFile("Player " +player[0].getPlayerName() + " has rolled a total of "+ Arrays.toString(new int[] {dice[0].getDiceNumber(), dice[1].getDiceNumber(),dice[2].getDiceNumber()}) + " = " + computerRollTotal + "\n");

        if(computerRollTotal == playerRollTotal)
        {
            System.out.println("All players have rolled the same dice, roll again\n");
            writeToFile("All players have rolled the same dice, roll again\n");
            return determineFirstPlayer();

        }
        else
        {
            if(Integer.max(playerRollTotal,computerRollTotal) == playerRollTotal)
            {
                System.out.println("Player " + player[1].getPlayerName() + " is first");
                writeToFile("Player " + player[1].getPlayerName() + " is first");
                return 1;
            }
            else
            {
                System.out.println("Player " + player[0].getPlayerName() + " is first");
                writeToFile("Player " + player[0].getPlayerName() + " is first");
                return 0;
            }
        }
    }


}
