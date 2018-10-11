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
 * Greedy guess player (task B).
 * Please implement this class.
 *
 * @author Youhan Xia, Jeffrey Chan
 */
public class GreedyGuessPlayer  implements Player{
	
	int xCheck = 0;
	int yCheck = 0;
	Deque<Guess> raycastQueue = new LinkedList<Guess>();
	
	boolean north = false;
	boolean east = false;
	boolean south = false;
	boolean west = false;
	
	boolean previousShotHit = false;
	
	int coreX; // = null; 
	int coreY; // = null; 
	
	private int hits = 0;
    private int hp = 0;
	
	private World world;
	//private ArrayList<Guess> possibleGuesses = new ArrayList<Guess>();
	public Guess[][] possibleGuesses = new Guess[10][10];
	
    @Override
    public void initialisePlayer(World world)
	{
		System.out.println("initialisePlayer()");
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
		
		//System.out.println("Alpha");
		
		//Attempting a 2d array
		for (r = 0; r <= i-1; r++)
		{
			c = 0;
			//System.out.println("Bravo");
			
			while (c <= j-1)
			{
				//System.out.println("Charlie");
				Guess guess = new Guess();
				
				guess.row = r;
				guess.column = c;
				possibleGuesses[r][c] = guess;
				c++;
				//System.out.println("Delta: " + r + "," + c);
			}
		}
		//System.out.println("Echo");
		

		//While row is less than the max row count -1 (of course, because it starts at 0)
        /*for (r=0; r<= i-1; r++) {

        	c=0; 
            
            while (c<=j-1) { //iterate through the columns
                
                Guess guess = new Guess();
                
                guess.row = r;
                guess.column = c;
                possibleGuesses.add(guess); // used an arrayList this time
                c++;
            }
            
        } */

        while (s<=world.shipLocations.size()-1){
            hp = hp + world.shipLocations.get(s).coordinates.size();
            s++;
        }
		
		System.out.println("/initialisePlayer()");
    } // end of initialisePlayer()

    @Override
    public Answer getAnswer(Guess guess) {
        Answer answer = new Answer();
		
		System.out.println("Ready, Aim, FIRE!");
		
        int i =0, j=0;
        while (i<=world.shipLocations.size()-1){
            while (j<=world.shipLocations.get(i).coordinates.size()-1) {
                if (world.shipLocations.get(i).coordinates.get(j).row == guess.row && world.shipLocations.get(i).coordinates.get(j).column == guess.column) {
                    answer.isHit = true;
                    System.out.println("$$$   Hit! :" + xCheck + "," + yCheck);
					
					//System.out.print(xCheck + "," + yCheck);
                    hits++;
                    System.out.println("shots taken: " + hits);
					//Hit occurred! Do what you need to do here.
					//previousShotHit = true;
					
					
					
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
            System.out.println("%%% Missed! :" + xCheck + "," + yCheck);
			//System.out.print(xCheck + "," + yCheck);
        }
        return answer;
    } // end of getAnswer()


    @Override
    public Guess makeGuess() {
		//int ranVal = ThreadLocalRandom.current().nextInt(0, location.size());
		System.out.println("");
		System.out.println("");
		System.out.println("greedyMakeGuess()");
		
		Guess guess = new Guess();
		guess.row = yCheck;
		guess.column = xCheck;
		
		
		
        //Guess ranGuess = new Guess();
        //ranGuess.row = location.get(ranVal).row;
        //ranGuess.column = location.get(ranVal).column;
        
        //location.remove(ranVal);
			//removal should probably be processed later.
			
			System.out.println("greedyMakeGuess()");
			System.out.println("");
			System.out.println("");
        return guess;
    } // end of makeGuess()


    @Override
    public void update(Guess guess, Answer answer)
	{
		System.out.println("");
		System.out.println("");
		System.out.println("-------------Update Start check: " + xCheck + "," + yCheck);
		//Let's start by adding the coordinate to the deque if it was a successful hit.
		north = false;
		east = false;
		south = false;
		west = false;
			
		if(answer.isHit == true)
		{
			raycastQueue.add(guess);
			previousShotHit = true;
			
			coreX = raycastQueue.peekLast().column;
			coreY = raycastQueue.peekLast().row;
		}
		
		boolean deciding = false;
		
		//Remove the guess from possible guesses, because it has already been realised!
		possibleGuesses[xCheck][yCheck] = null;
		
		
		
		//Raycast checks! Fun!
		int y=0; // r for row
        int x=0; // c for column
		
		if(raycastQueue.size() > 0)// && (raycastQueue.peekLast().row)
		{
			//if(answer.isHit == true){
			yCheck = coreY; //raycastQueue.peekLast().row;
			xCheck = coreX; //raycastQueue.peekLast().column; //}
			
			System.out.println("/------------Update pre-iteration check: " + xCheck + "," + yCheck);
			
			
			while (north || east || south || west != true && ((x <= 10) && (y <= 10)) )
			{
				System.out.println("Outer Iteration: "+ x + "," + y);
				for (y = 0; y <= 10; y++)
				{
					for (x = 0; x <= 10; x++)
					{
						
						System.out.println("Iteration: "+ x + "," + y);
						
						//North/up (y+1)
						if( possibleGuesses[xCheck][yCheck + 1] != null )
						{
							
							System.out.println("North!");
							System.out.println("yCheck: " + yCheck);
							north = true;
						}
						else
						{
							System.out.println("North has already been executed.");
						
							//Set the Ycheck back to the "default".
							yCheck = raycastQueue.peekLast().row;
						}
						
						//East/right (x+1)
						if( possibleGuesses[xCheck + 1][yCheck] != null )
						{
							//System.out.println("East is free!");
							east = true;
						}
						else
						{
							//System.out.println("East has already been executed.");
						
							//Set the Ycheck back to the "default".
							xCheck = raycastQueue.peekLast().row;
						}
						
						//South/down (y-1)
						if( (yCheck > 0) && possibleGuesses[xCheck][yCheck - 1] != null )
						{
							//System.out.println("South is free!");
							south = true;
						}
						else
						{
							//System.out.println("South has already been executed.");
						
							//Set the Ycheck back to the "default".
							yCheck = raycastQueue.peekLast().row;
						}
						
						//East/right (x - 1)
						if( possibleGuesses[xCheck - 1][yCheck] != null )
						{
							//System.out.println("West is free!");
							west = true;
						}
						else
						{
							System.out.println("West has already been executed.");
						
							//Set the Ycheck back to the "default".
							xCheck = raycastQueue.peekLast().row;
						}
						break;
					}
					break;
				}
				break;
			}
		}
		
		
		//Guess guess = new Guess();
		
		if(north || east || south || west == true)
		{
			System.out.println("Still raycasting!");
			//Infinite loop here!
			deciding = true;
			
			//By using if else with the directions in clockwise order, it will only check once in a time and in the correct order.
			while(deciding == true)
			{
				if(north == true)
				{
					yCheck += 1;
					
					//north = false;
					deciding = false;
					
					break;
				}
				else if(east == true)
				{
					xCheck += 1;
					
					//east = false;
					deciding = false;
					break;
				}
				else if(south == true)
				{
					yCheck -= 1;
					
					//south = false;
					deciding = false;
					break;
				}
				else if(west == true)
				{
					xCheck -= 1;
					//west = false;
					deciding = false;
					break;
				}
				//break;
			}
			//break;
		}
		else //If all are false, that means the raycast is either not needed or complete.
		{
			//if
			
			
			raycastQueue.pollLast();
			System.out.println("RCQ Size: " +raycastQueue.size());
			System.out.println("Updating as usual");
			
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
			
			
			
			
			
			
		}
		
		//Set the directions.
		guess.row = yCheck;
		guess.column = xCheck;
		
		//End of update array check:
		for(int c = 9;  c >= 0 ; c--)
		{
			for(int r = 0; r <= 9; r++)
			{
				if (possibleGuesses[r][c] != null)
				{
					System.out.print("X");
				}
				else
				{
					System.out.print("O");
				}
				//System.out.print(array[i][j]);
			}
			System.out.println(" " + c);
		}
		
		
		
		
			// dummy return
		System.out.println("/------------Update End queue: " + raycastQueue.size() + ", " +raycastQueue.peekLast());
		System.out.println("/------------Update End directions: N:" + north + " E:" + east + " S:" + south + " W:" + south);
		System.out.println("/------------Update End check: " + xCheck + "," + yCheck);
		//System.out.println("End of update");
		System.out.println("");
		System.out.println("");
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
