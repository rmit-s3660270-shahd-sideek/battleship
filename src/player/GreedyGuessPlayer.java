package player;

import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import world.World;
import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.LinkedList;
import java.util.*;
import java.util.Collection;
import java.util.Collections;

/**
 * Random guess player (task A).
 * Please implement this class.
 *
 * @author Youhan Xia, Jeffrey Chan
 */
public class GreedyGuessPlayer implements Player{

	int xCheck;
	int yCheck;
	//Queue raycastQueue = new Guess();
	//Queue raycastQueue = new LinkedList();
	Deque<Guess> raycastQueue = new LinkedList<Guess>();
	
	boolean north = false;
		boolean east = false;
		boolean south = false;
		boolean west = false;

    private World world;
    //private ArrayList<Guess> shots = new ArrayList<Guess>(); realised this isnt needed 
    private ArrayList<Guess> possibleGuesses = new ArrayList<Guess>();
	
    private int hits = 0;
    private int hp = 0;

    @Override
    public void initialisePlayer(World world) {
        this.world = world;
		
		xCheck = 0;
		yCheck = 0;

        int i;
        int j;
        int r=0; // r for row
        int c=0; // c for column
        int s=0; // s for ship
        
        i = world.numRow;
        j = world.numColumn;

		//While row is less than the max row count -1 (of course, because it starts at 0)
        for (r=0; r<= i-1; r++) {

        	c=0; 
            
            while (c<=j-1) { //iterate through the columns
                
                Guess guess = new Guess();	//It appears to be making a new guess here.
                
                guess.row = r;
                guess.column = c;
                possibleGuesses.add(guess); // used an arrayList this time
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

					//The ray-casts should commence here.
					raycastQueue.add(guess);
					//guess.toString();
					System.out.println("Element: " + raycastQueue.peekLast().toString() );
					//while
						
                    hits++;
                    System.out.println("shots taken: " + hits);
					System.out.println("raycastQueue size: " + raycastQueue.size());
					System.out.println("possibleGuesses size: " + possibleGuesses.size());
					
					Iterator iter = possibleGuesses.iterator();
					
					/*
					while (iter.hasNext())
					{
						System.out.println(iter.next());
						if (guess.column == iter.next().column && guess.row == iter.next().row )
						{
							possibleGuesses.remove(iter.next());
						}
						System.out.println(iter.next());
					}*/
					
					for(i = 0; i < possibleGuesses.size(); i++)
					{
						if (guess.column == possibleGuesses.get(i).column && guess.row == possibleGuesses.get(i).row )
						{
							possibleGuesses.remove(i);
						}
					}
					
					/*
					for (Guess guessCheck : possibleGuesses)
					{
						if (guess.column == guessCheck.column && guess.row == guessCheck.row )
						{
							possibleGuesses.remove(guessCheck);
						}
					}*/
					
					
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
			
			for(i = 0; i < possibleGuesses.size(); i++)
			{
				if (guess.column == possibleGuesses.get(i).column && guess.row == possibleGuesses.get(i).row )
				{
					possibleGuesses.remove(i);
				}
			}
			
			
        }
        return answer;
    } // end of getAnswer()


    @Override
    public Guess makeGuess()
	{

        //int ranVal = ThreadLocalRandom.current().nextInt(0, location.size());
		
		//Check if a move is possible (in this arrayList.)
		
		
		int i = 0;
		boolean deciding = false;
		
		if(raycastQueue.size() > 0)
		{
			//Up 1 (North)
			for(i = 0; i < possibleGuesses.size(); i++)
			{
				if (xCheck == possibleGuesses.get(i).column && (yCheck + 1) == possibleGuesses.get(i).row )
				{
					System.out.println("North is free!");
					north = true;
				}
			}
			
			//Right 1 (East)
			for(i = 0; i < possibleGuesses.size(); i++)
			{
				if ((xCheck + 1) == possibleGuesses.get(i).column && yCheck == possibleGuesses.get(i).row )
				{
					System.out.println("East is free!");
					east = true;
				}
			}
			
			//Down 1 (South)
			for(i = 0; i < possibleGuesses.size(); i++)
			{
				if (xCheck == possibleGuesses.get(i).column && (yCheck - 1) == possibleGuesses.get(i).row )
				{
					System.out.println("South is free!");
					south = true;
				}
			}
			
			//Left 1 (West)
			for(i = 0; i < possibleGuesses.size(); i++)
			{
				if ((xCheck - 1) == possibleGuesses.get(i).column && yCheck == possibleGuesses.get(i).row )
				{
					System.out.println("West is free!");
					west = true;
				}
			}
			
			
			//Check if north can be checked
			/*
			for (Guess guessCheck : possibleGuesses)
			{
				i++;
				if ((xCheck + 1) == guessCheck.column && yCheck == guessCheck.row )
				{
					System.out.println("North is free!");
					
					possibleGuesses.remove(i);
				}
			}*/
		}
		
		
		
        Guess guess = new Guess();
		
		
		
		
		
        //ranGuess.row = location.get(xCheck).row;
        //ranGuess.column = location.get(yCheck).column;
        
		if(north || east || south || west == true)
		{
			deciding = true;
		}
		else
		{
			raycastQueue.pollLast();
		}
		
		
		if (deciding == true)
		{
			while(deciding == true)
			{
				if(north == true)
				{
					yCheck += 1;
					deciding = false;
				}
				else if(east == true)
				{
					xCheck += 1;
					deciding = false;
				}
				else if(south == true)
				{
					yCheck -= 1;
					deciding = false;
				}
				else if(west == true)
				{
					xCheck -= 1;
					deciding = false;
				}
			}
		}
		
		
		
		
		guess.row = yCheck;
		guess.column = xCheck;
		
		
		

		world.updateShot(guess);
		
		// dummy return
        return guess;
		
        //location.remove(ranVal);
		//What is this? And why does it only take one integer?
       
        //return guess;
    } // end of makeGuess()


    @Override
    public void update(Guess guess, Answer answer) {
        //the coordinates will get updated here, after each shot.
		
		/*
		if(raycastQueue.size() > 0)
		{
			if ()
		}
		*/
		
		xCheck += 2;
		
		if (xCheck >= 10)
		{
			yCheck++;
			xCheck = 0;
			System.out.println("^^^Column up^^^");
		}
		
		if ( yCheck % 2 == 1 && xCheck == 0)
		{
			xCheck = 1;
			System.out.println("odd row. Offsetting by 1.");
		}

		System.out.println("***end of update");
		System.out.println("xCheck = " + xCheck);
		System.out.println("yCheck = " + yCheck);
		
		
		
		
		
		//if (answer.)
		
		// To be implemented.
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

} // end of class GreedyGuessPlayer