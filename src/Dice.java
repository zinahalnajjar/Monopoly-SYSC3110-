import java.io.Serializable;

/**
 * Class Dice- a pair of Dice in monopoly game
 * this class is part of the "Monopoly Game" application
 *
 * A Die is a pair consisting of die1 and die2
 *
 * Player rolls the dice and advance a piece clockwise around the board corresponding to
 * the sum of both die1 and die2
 *
 * @author Zinah
 */
public class Dice implements Serializable {
    public int die1;
    public int die2;

    /**
     * creates a pair of dice
     */
    public Dice(){
        this.die1 = 0;
        this.die2 = 0;
    }

    /**
     * Roll the Dice which sets each of die1 and die2 to be a random number between 1 and 6
     */
    public void roll(){

        die1 = (int)(Math.random()*6) + 1;
        die2 = (int)(Math.random()*6) + 1;


    }

    /**
     *
     * @return the generated random number on die1
     */
    public int getDie1(){
        return this.die1;
    }

    /**
     *
     * @return the generated random number on die2
     */
    public int getDie2(){
        return this.die2;

    }

    /**
     *
     * @return return the sum showing on the pair of Dice
     */
    public int sumOfDice(){
        return die1+die2;

    }

    /**
     * For testing purposes
     */
    public void setDie1(int a){
        die1 = a;
    }

    public void setDie2(int a){
        die2 = a;
    }

    public boolean isDouble() {
        return die1 == die2;
    }
}
