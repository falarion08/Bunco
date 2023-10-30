package com.company;

import java.util.Random;

public class Dice {
    private int diceNumber;
    private Random rand;

    public Dice()
    {
        this.rand = new Random();
    }

    public int roll()
    {
        this.diceNumber = this.rand.nextInt(6) + 1;
        // Generate a random integer from 0 to 5, and increment the number by 1
        return this.diceNumber;
    }
    public int getDiceNumber(){return this.diceNumber;}
}
