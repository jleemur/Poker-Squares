import java.util.HashMap;

/**
 * JM_NS_PokerSquaresPlayer - a simple, baseline implementation of the player interface for PokerSquares.
 * For each possible play, we set up flushes while prioritizing rank points (pairs, three of a kind, four of a kind)
 *
 * Authors: Jonathan Murray and Nicole Stewart
 */
public class JM_NS_PokerSquaresPlayer implements PokerSquaresPlayer {
	
	private final int SIZE = 5; // number of rows/columns in square grid
	private final int NUM_POS = SIZE * SIZE; // number of positions in square grid
	private final int NUM_CARDS = Card.NUM_CARDS; // number of cards in deck
	//private int numPlays = 0; // number of Cards played into the grid so far
	private boolean[][] legalMove = new boolean[SIZE][SIZE]; //true = valid move, false = invalid move
	private PokerSquaresPointSystem system; // point system
	private Card[][] grid = new Card[SIZE][SIZE]; // grid with Card objects or null (for empty positions)
	private int[] columnCounter = new int[5]; //grid counters for each column

	/*
	 * Create our Poker Squares player.
	 * Generate HashMap availableCards with base values.
	 */
	public JM_NS_PokerSquaresPlayer() {
		Card[] completeDeck = Card.getAllCards(); //array with all cards in deck
	}

	/*
	 * @see PokerSquaresPlayer#init()
	 */
	@Override
	public void init() { 
		// clear grid, set legalMove table to true
		for (int row = 0; row < SIZE; row++) {
			for (int col = 0; col < SIZE; col++) {
				grid[row][col] = null;
				legalMove[row][col] = true;
			}
		}

		/*
		 * reset column counters
		 * [0] = clubs ; [1] = diamonds ; [2] = hearts ; [3] = spades ; [4] = wild
		 */
		for (int i=0; i<5; i++)
			columnCounter[i] = 0;
	}

	/*
	 * @see PokerSquaresPlayer#getPlay(Card, long)
	 */
	@Override
	public int[] getPlay(Card card, long millisRemaining) {
		int[] playPos = boardPlacement(card);

		return playPos;
	}

	/*
	 * Find best play on current board
	 */
	public int[] boardPlacement(Card card)
	{
		int[] playPos = new int[2];
		int cardRank = card.getRank(); //A..K = 0..12
		int cardSuit = card.getSuit(); //0=C 1=D 2=H 3=S
		int bestRow = -1; //bestRow will be a row index with most potential
		int bestPoints = -1; //bestPoints will be the highest points

		//clubs
		if (cardSuit == 0 && columnCounter[0] <= 4) {
			playPos[1] = 0;
			
			//find best move
			for (int row = 0; row < SIZE; row++) {
				if (legalMove[row][0] == true)
				{
					int temp = highestPotentialPoints(card, row, 0);
					if (bestPoints < temp) {
						bestPoints = temp;
						bestRow = row;
					}
				}
			}
			columnCounter[0]++;
			legalMove[bestRow][0] = false;
			playPos[0] = bestRow;
			grid[playPos[0]][playPos[1]] = card;
		}
		//diamonds
		else if (cardSuit == 1 && columnCounter[1] <= 4) {
			playPos[1] = 1;

			//find best move
			for (int row = 0; row < SIZE; row++) {
				if (legalMove[row][1] == true)
				{
					int temp = highestPotentialPoints(card, row, 1);
					if (bestPoints < temp) {
						bestPoints = temp;
						bestRow = row;
					}
				}
			}
			columnCounter[1]++;
			legalMove[bestRow][1] = false;
			playPos[0] = bestRow;
			grid[playPos[0]][playPos[1]] = card;
		}
		//hearts
		else if (cardSuit == 2 && columnCounter[2] <= 4) {
			playPos[1] = 2;

			//find best move
			for (int row = 0; row < SIZE; row++) {
				if (legalMove[row][2] == true)
				{
					int temp = highestPotentialPoints(card, row, 2);
					if (bestPoints < temp) {
						bestPoints = temp;
						bestRow = row;
					}
				}
			}
			columnCounter[2]++;
			legalMove[bestRow][2] = false;
			playPos[0] = bestRow;
			grid[playPos[0]][playPos[1]] = card;
		}	
		//spades
		else if (cardSuit == 3 && columnCounter[3] <= 4) {
			playPos[1] = 3;

			//find best move
			for (int row = 0; row < SIZE; row++) {
				if (legalMove[row][3] == true)
				{
					int temp = highestPotentialPoints(card, row, 3);
					if (bestPoints < temp) {
						bestPoints = temp;
						bestRow = row;
					}
				}
			}
			columnCounter[3]++;
			legalMove[bestRow][3] = false;
			playPos[0] = bestRow;
			grid[playPos[0]][playPos[1]] = card;
		}
		//wild
		else if (columnCounter[4] <= 4) {
			playPos[1] = 4;

			//find best move
			for (int row = 0; row < SIZE; row++) {
				if (legalMove[row][4] == true)
				{
					int temp = highestPotentialPoints(card, row, 4);
					if (bestPoints < temp) {
						bestPoints = temp;
						bestRow = row;
					}
				}
			}
			columnCounter[4]++;
			legalMove[bestRow][4] = false;
			playPos[0] = bestRow;
			grid[playPos[0]][playPos[1]] = card;
		}
		else
		{
			//EMERGENCY PUT IT ANYWHERE
			for (int col=0; col < SIZE; col++) {
				for (int row=0; row < SIZE; row++) {
					if (legalMove[row][col] == true)
					{
						playPos[0] = row;
						playPos[1] = col;
						legalMove[row][col] = false;
						grid[playPos[0]][playPos[1]] = card;
						columnCounter[playPos[1]]++;

						return playPos;
					}
				}
			}
		}

		return playPos;
	}

	/*
	 * Return potential points, compare with other placements... choose highest
	 * Interference = 0
	 * No Interference = 1
	 * Pair = 2
	 * Three of a Kind = 3
	 * Four of a Kind = 4
	 */
	public int highestPotentialPoints(Card card, int row, int col)
	{
		int pointCode = 1;

		try {
			//check for interference 
			int interference = 0;
			for (int i=0; i<SIZE; i++)
			{
				if (i==col) {} //do nothing
	 			else {
	 				if (grid[row][i] != null && grid[row][i].getRank() != card.getRank()) 
	 					pointCode = 0;
				}
			}
			//check for pair 
			for (int i=0; i<SIZE; i++)
			{
				if (i==col) {} //do nothing
	 			else {
	 				if (grid[row][i].getRank() == card.getRank()) {
	 					pointCode = 2;
	 					break;
	 				}
				}
			}
			//check for three of a kind 
			int threeofakind = 0;
			for (int i=0; i<SIZE; i++)
			{
				if (i==col) {} //do nothing
	 			else {
	 				if (grid[row][i].getRank() == card.getRank()) {
	 				 	threeofakind++;
	 				}

	 				if (threeofakind == 3) {
	 					pointCode = 4;
	 					break;
	 				}
				}
			}
			//check for four of a kind 
			int fourofakind = 0;
			for (int i=0; i<SIZE; i++)
			{
				if (i==col) {} //do nothing
	 			else {
	 				if (grid[row][i].getRank() == card.getRank()) {
	 				 	fourofakind++;
	 				}

	 				if (fourofakind == 4) {
	 					pointCode = 5;
	 					break;
	 				}
				}
			}
		} catch (NullPointerException e) {};

		return pointCode;
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
		return "JM_NS_PokerSquaresPlayer";
	}

	/*
	 * Demonstrate JM_NS_PokerSquaresPlayer with American point system.
	 */
	public static void main(String[] args) {
		final long startTime = System.currentTimeMillis(); //start time
		PokerSquaresPointSystem system = PokerSquaresPointSystem.getAmericanPointSystem();
		System.out.println(system);
		for (int i=0; i<1000; i++) {
			new PokerSquares(new JM_NS_PokerSquaresPlayer(), system).play(); // play a single game
		}
		System.out.println(System.currentTimeMillis() - startTime + "ms"); //print time
	}

}