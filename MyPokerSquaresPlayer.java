import java.util.ArrayList;
import java.util.HashMap;

/**
 * GreedyMCPlayer - a simple, greedy Monte Carlo implementation of the player interface for PokerSquares.
 * For each possible play, continues greedy play with random possible card draws to a given depth limit 
 * (or game end).  Having sampled trajectories for all possible plays, the GreedyMCPlayer then selects the
 * play yielding the best average scoring potential in such Monte Carlo simulation.
 *
 * Authors: Jonathan Murray and Nicole Stewart
 */
public class MyPokerSquaresPlayer implements PokerSquaresPlayer {
	
	private final int SIZE = 5; // number of rows/columns in square grid
	private final int NUM_POS = SIZE * SIZE; // number of positions in square grid
	private final int NUM_CARDS = Card.NUM_CARDS; // number of cards in deck
	private int[] plays = new int[NUM_POS]; // positions of plays so far (index 0 through numPlays - 1) recorded as integers using row-major indices.
	// row-major indices: play (r, c) is recorded as a single integer r * SIZE + c (See http://en.wikipedia.org/wiki/Row-major_order)
	// From plays index [numPlays] onward, we maintain a list of yet unplayed positions.
	private int numPlays = 0; // number of Cards played into the grid so far
	private PokerSquaresPointSystem system; // point system
	private Card[][] grid = new Card[SIZE][SIZE]; // grid with Card objects or null (for empty positions)
	private HashMap<String, Integer> cardsLeft = new HashMap<String, Integer>(); //list of all cards remaining. This list will be pretty detailed.
																				 //# suits, # values, all remaining cards

	/*
	 * Create our Poker Squares player.
	 * Generate HashMap cardsLeft with base values.
	 */
	public MyPokerSquaresPlayer() {
		Card[] completeDeck = Card.getAllCards(); //array with all cards in deck

		//suits
		cardsLeft.put("hearts", 13);
		cardsLeft.put("diamonds", 13);
		cardsLeft.put("clubs", 13);
		cardsLeft.put("spades", 13);
		//values
		cardsLeft.put("A", 4);
		cardsLeft.put("2", 4);
		cardsLeft.put("3", 4);
		cardsLeft.put("4", 4);
		cardsLeft.put("5", 4);
		cardsLeft.put("6", 4);
		cardsLeft.put("7", 4);
		cardsLeft.put("8", 4);
		cardsLeft.put("9", 4);
		cardsLeft.put("T", 4);
		cardsLeft.put("J", 4);
		cardsLeft.put("Q", 4);
		cardsLeft.put("K", 4);
		//all remaining
		for (int i=0; i<completeDeck.length; i++)
		{
			cardsLeft.put(String.valueOf(completeDeck[i]), 1);
		}
	}

	/*
	 * @see PokerSquaresPlayer#init()
	 */
	@Override
	public void init() { 
		// clear grid
		for (int row = 0; row < SIZE; row++)
			for (int col = 0; col < SIZE; col++)
				grid[row][col] = null;
		// reset numPlays
		numPlays = 0;
		// (re)initialize list of play positions (row-major ordering)
		for (int i = 0; i < NUM_POS; i++)
			plays[i] = i;
	}

	/*
	 * @see PokerSquaresPlayer#getPlay(Card, long)
	 */
	@Override
	public int[] getPlay(Card card, long millisRemaining) {
		int[] playPos = {};

		//calculateTemporaryPoints with every possible grid configuration for new card
		//keep highest point configuration, use that move! 

		return playPos;
	}

	/*
	 * Arg: Grid with a new card placed.
	 * Returns: Average potential points based on probabilties. 
	 */
	public double calculateTemporaryPoints(Card[][] grid) {
		double tempPoints = 0;

		for (int r=0; r<SIZE; r++)
		{
			tempPoints += potentialPoints(); //add probability of current row here
		}
		for (int c=0; c<SIZE; c++)
		{
			tempPoints += potentialPoints(); //add probability of current column here
		}

		return tempPoints;
	}
	/*
	 * Arg: Current cards on line
	 * Returns: Probability of all potential results added together
	 */
	public double potentialPoints() {
		double currentPoints = 0.0; //points of current equation
		double points = 0.0; //overall points of configuration

		//pair
		//TODO:

		//two pair
		//TODO:

		//three of a kind
		//TODO:

		//straight
		//TODO:

		//flush
		//TODO:

		//full house
		//TODO:

		//four of a kind
		//TODO:

		//straight flush
		//TODO:

		//royal flush
		//TODO:


		return points;
	}

	/*
	 * @see PokerSquaresPlayer#setPointSystem(PokerSquaresPointSystem, long)
	 */
	@Override
	public void setPointSystem(PokerSquaresPointSystem system, long millis) {
		this.system = system;
	}

	/*
	 * @see PokerSquaresPlayer#getName()
	 */
	@Override
	public String getName() {
		return "MyPokerSquaresPlayer";
	}

	/*
	 * Demonstrate MyPokerSquaresPlayer with American point system.
	 */
	public static void main(String[] args) {
		PokerSquaresPointSystem system = PokerSquaresPointSystem.getAmericanPointSystem();
		System.out.println(system);
		new PokerSquares(new MyPokerSquaresPlayer(), system).play(); // play a single game
	}

}
