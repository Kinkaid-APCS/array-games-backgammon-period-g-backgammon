/**
 * The Referee class keeps track of the board, any dice, and all interactions
 * with the players. It keeps track of whose turn it is, displays the board,
 * rolls dice, and asks the users to make their moves. 
 */
public class Referee {

	// TODO: decide which private member variables the Referee should have and declare them here.
	// suggestion: the dice are an array of integers, typically 1-6, where 0 means unrolled or 
	// used up already.
	private boolean continuePlaying = true;
	private DiceCup blackCup;
	private DiceCup whiteCup;
	private Board board;
	/**
	 * constructor - set up the board and players 
	 */
	// TODO: you write the Referee's constructor
	Referee() {
		blackCup = new DiceCup();
		whiteCup = new DiceCup();
		board = new Board();
	}
	/**
	 * playGame - the main game loop. Roll the dice, ask the user for a
	 * move, determine whether it is legal, and then execute the move. 
	 * Repeat for any remaining dice.
	 */
	public void playGame()
	{
		// TODO: you write the Referee's playGame method.
		blackCup.roll();
		System.out.println(blackCup.toString());
	}
}
