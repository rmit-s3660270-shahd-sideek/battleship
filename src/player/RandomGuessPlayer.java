package player;

import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import world.World;
import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;


/**
 * Random guess player (task A).
 * Please implement this class.
 *
 * @author Youhan Xia, Jeffrey Chan
 */
public class RandomGuessPlayer implements Player{

    private World world;
    //private ArrayList<Guess> shots = new ArrayList<Guess>(); realised this isnt needed 
    private ArrayList<Guess> location = new ArrayList<Guess>();
    private int hits = 0;
    private int hp = 0;

    @Override
    public void initialisePlayer(World world) {
        this.world = world;

        int i;
        int j;
        int r=0; // r for row
        int c=0; // c for column
        int s=0; // s for ship 
        
        i = world.numRow;
        j = world.numColumn;
        
        for (r=0; r<= i-1; r++) {
           
        	
        	c=0;
            
            while (c<=j-1) {
                
                Guess guess = new Guess();
                
                guess.row = r;
                guess.column = c;
                location.add(guess); // used an arrayList this time
                c++;
            }
            
        }

        while (s<=world.shipLocations.size()-1){
            hp = hp + world.shipLocations.get(s).coordinates.size();
            s++;
        }

    } // end of initialisePlayer()

    @Override
    public Answer getAnswer(Guess guess) {
        Answer answer = new Answer();
        int i =0, j=0;
        while (i<=world.shipLocations.size()-1){
            while (j<=world.shipLocations.get(i).coordinates.size()-1) {
                if (world.shipLocations.get(i).coordinates.get(j).row == guess.row && world.shipLocations.get(i).coordinates.get(j).column == guess.column) {
                    answer.isHit = true;
                    System.out.println("Hit!");

                    hits++;
                    System.out.println("shots taken: " + hits);
                    return answer;
                }
                else{
                    j++;

                }
                
            }
            j=0;
            i++;
        }

        if (answer.isHit == false) {
            System.out.println("Missed!");
        }
        return answer;
    } // end of getAnswer()


    @Override
    public Guess makeGuess() {

        int ranVal = ThreadLocalRandom.current().nextInt(0, location.size());

        Guess ranGuess = new Guess();
        ranGuess.row = location.get(ranVal).row;
        ranGuess.column = location.get(ranVal).column;
        
        location.remove(ranVal);
       
        return ranGuess;
    } // end of makeGuess()


    @Override
    public void update(Guess guess, Answer answer) {
    	//this method is redundant apparently 
    	
    	
    	
    } // end of update()


    @Override
    public boolean noRemainingShips() {
        // To be implemented.
        if (hits == hp) {
            return true;
        }
        // dummy return
        return false;
    } // end of noRemainingShips()

} // end of class RandomGuessPlayer
