package com.company;

public class DiceCup {
    private Dice[] dice;

    public DiceCup()
    {
        // Create a die object array with 3 die
        this.dice = new Dice[3];

        // Create Dice object for each element in the array
        for(Dice d: this.dice)
            d = new Dice();
    }

    public int[] rollDie()
    {
        return new int[] {this.dice[0].roll(), this.dice[1].roll(),this.dice[2].roll()};
    }
}
